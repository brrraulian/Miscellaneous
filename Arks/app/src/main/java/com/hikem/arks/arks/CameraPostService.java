package com.hikem.arks.arks;

/**
 * Created by Makem2 on 09/02/2015.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CameraPostService extends Service implements Runnable {

    UserSessionManager session;
    ArrayList enviados;
    ArrayList ids_historico;
    ArrayList ids_fila;
    NotificationManager notificationManager;
    NotificationCompat.Builder mBuilder;
    PendingIntent p;
    Thread thread;
    int id_notification = 0;
    long id_historico = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(CameraPostService.this).start();
        session = new UserSessionManager(getApplicationContext());

        id_notification = 1;

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void run() {

        enviados = new ArrayList();
        ids_historico = new ArrayList();
        ids_fila = new ArrayList();

        SQLiteDatabase db = openOrCreateDatabase("arks.db", Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM fila WHERE status = 'Pendente' ORDER BY _id", null);

        String arquivo = "";
        ArrayList<Uri> imagens = new ArrayList<>();

        while(cursor.moveToNext()) {
            arquivo = Environment.getExternalStorageDirectory() + "/Arks/" + cursor.getString(1);
            imagens.add(Uri.fromFile(new File(arquivo)));
            ids_fila.add(cursor.getLong(0));
        }


        String status = "";
        boolean envios_ok = false;

        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        HashMap<String, String> user = session.getUserDetails();
        String urlServer = "https://ws" + user.get(UserSessionManager.KEY_EMPRESA) + ".arks.com.br/Mobile/SendFile.ashx";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        for (int i = 0; i < imagens.size(); i++) {

            try {

                if (ids_historico.size() != imagens.size()) {
                    id_historico = InsereHistorico(getFileName(imagens.get(i)), "Enviando...");
                    ids_historico.add(id_historico);
                }

                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mBuilder = new NotificationCompat.Builder(this);
                mBuilder.setTicker("Arks - Status de envio (" + enviados.size() + "/" + imagens.size() + ")");
                mBuilder.setContentTitle("Arks (" + enviados.size() + "/" + imagens.size() + ")");
                mBuilder.setContentText("Enviando arquivos...").setSmallIcon(R.drawable.icon);
                mBuilder.setAutoCancel(true);
                mBuilder.setProgress(0, 0, true);
                notificationManager.notify(id_notification, mBuilder.build());

                String pathToOurFile = getRealPathFromURI(imagens.get(i));
                FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile));

                URL url = new URL(urlServer);
                connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);

                connection.setChunkedStreamingMode(1024);

                connection.setRequestMethod("POST");

                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(twoHyphens + boundary + lineEnd);

                int index = pathToOurFile.lastIndexOf("/");
                String fileName = pathToOurFile.substring(index + 1);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName + "_arks_" + 
				user.get(UserSessionManager.KEY_FOLDER) + "_arks_" + user.get(UserSessionManager.KEY_ID) + "\"" + lineEnd);
                outputStream.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    outputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                InputStreamReader ips = new InputStreamReader(connection.getInputStream());
                BufferedReader line = new BufferedReader(ips);

                String linhaRetorno = "";
                do {
                    linhaRetorno = line.readLine();
                    Log.d("PostMultiplosService", linhaRetorno);
                }
                while (line.readLine() != null);

                fileInputStream.close();
                outputStream.flush();
                outputStream.close();
                ips.close();
                line.close();

                status = "";

                if (linhaRetorno.equals("ok")) {
                    envios_ok = true;

                    if (!enviados.contains(i)) {
                        enviados.add(i);
                        status = "Enviado";
                        EditaHistorico((long) ids_historico.get(i), status);
                        EditaFila((long) ids_fila.get(i), "Ok");

                        mBuilder.setTicker("Arks - Status de envio (" + enviados.size() + "/" + imagens.size() + ")");
                        mBuilder.setContentTitle("Arks (" + enviados.size() + "/" + imagens.size() + ")");
                        mBuilder.setContentText("Enviando arquivos...");
                        notificationManager.notify(id_notification, mBuilder.build());
                    }
                } else {
                    envios_ok = false;
                    status = "Erro";
                    EditaHistorico((long) ids_historico.get(i), status);
                }
            } catch (OutOfMemoryError e) {
                envios_ok = false;
                status = "Erro";
                EditaHistorico((long) ids_historico.get(i), status);
            } catch (NullPointerException nullEx) {
                envios_ok = false;
                status = "Erro";
                EditaHistorico((long) ids_historico.get(i), status);
            } catch (Exception ex) {
                envios_ok = false;
                status = "Erro";
                EditaHistorico((long) ids_historico.get(i), status);
            }
        }

        if (envios_ok) {
            p = PendingIntent.getActivity(this, 0, new Intent(this.getApplicationContext(), HistoricoActivity.class), 0);
            mBuilder.setTicker("Arks - Status de envio (" + enviados.size() + "/" + imagens.size() + ")");
            mBuilder.setContentTitle("Arks (" + enviados.size() + "/" + imagens.size() + ")");
            mBuilder.setContentText("Arquivos enviados com sucesso.");
            mBuilder.setContentIntent(p);
            mBuilder.setProgress(0,0,false);
            mBuilder.setVibrate(new long[]{100, 2000, 1000, 2000});
            notificationManager.notify(id_notification, mBuilder.build());
            stopSelf();
        } else {
            try {
                thread.sleep(5000);
            } catch (InterruptedException ex2) {

            }
            stopSelf();
            new Thread(CameraPostService.this).start();
        }
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    public String getRealPathFromURI(Uri contentUri) {

        if ( contentUri.toString().indexOf("file:///") > -1 ){
            return contentUri.getPath();
        }

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null); 
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        String ret = cursor.getString(column_index);
        cursor.close();

        return ret;
    }


    private long InsereHistorico(String nome, String status) {
        long id = 0;

        SQLiteDatabase db = openOrCreateDatabase("arks.db", Context.MODE_PRIVATE, null);

        ContentValues ctv = new ContentValues();
        ctv.put("nome", nome);
        ctv.put("status", status);
        ctv.put("data", getDateTime());

        Log.d("PostService", ctv.toString());

        id = db.insert("historico", "_id", ctv);

        db.close();

        return id;
    }

    private void EditaHistorico(long id, String status) {

        SQLiteDatabase db = openOrCreateDatabase("arks.db", Context.MODE_PRIVATE, null);

        ContentValues ctv = new ContentValues();
        ctv.put("status", status);
        ctv.put("data", getDateTime());

        Log.d("PostService", ctv.toString());

        db.close();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void EditaFila(long id, String status) {

        SQLiteDatabase db = openOrCreateDatabase("arks.db", Context.MODE_PRIVATE, null);

        ContentValues ctv = new ContentValues();
        ctv.put("status", status);
        ctv.put("data", getDateTime());

        db.close();
    }

}
