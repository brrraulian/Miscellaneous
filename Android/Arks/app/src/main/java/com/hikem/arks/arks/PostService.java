package com.hikem.arks.arks;

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
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.logging.Handler;

/**
 * Created by Makem2 on 14/01/2015.
 */
public class PostService extends Service implements Runnable {

    UserSessionManager session;
    Intent intent;
    Thread thread;
    int tentativas = 0;
    int id_notification = 0;
    long id_historico = 0;
    PendingIntent p;
    NotificationManager notificationManager;
    NotificationCompat.Builder mBuilder;
    Notification nt;
    String status = "";
    String linhaRetorno = "";
    InputStreamReader ipsERRO;
    BufferedReader lineERRO;
    HttpURLConnection connection = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        thread = new Thread(PostService.this);
        thread.start();
        session = new UserSessionManager(getApplicationContext());

        if (tentativas == 0) {
            id_notification = (int) Math.round(Math.random());
        }

        this.intent = intent;

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    public void run() {

        tentativas++;

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setTicker("Arks - Status de envio (0/1)");
        mBuilder.setContentTitle("Arks (0/1)");
        mBuilder.setContentText("Enviando arquivo...").setSmallIcon(R.drawable.icon);
        mBuilder.setAutoCancel(true);
        mBuilder.setProgress(0, 0, true);
        notificationManager.notify(id_notification, mBuilder.build());

        try {

            Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            String pathToOurFile = getRealPathFromURI(imageUri);

            DataOutputStream outputStream = null;
            DataInputStream inputStream = null;
            HashMap<String, String> user = session.getUserDetails();
            String urlServer = "https://ws" + user.get(UserSessionManager.KEY_EMPRESA) + ".arks.com.br/Mobile/SendFile.ashx";
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;

            if (id_historico == 0) {
                id_historico = intent.getLongExtra("ID_HISTORICO", 0);

                if (id_historico == 0) {
                    id_historico = InsereHistorico(getFileName(imageUri), "Enviando...");
                }
            }

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

            linhaRetorno = "";
            do {
                linhaRetorno += line.readLine();
                Log.d("PostService", linhaRetorno);
            }
            while (line.readLine() != null);

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
            ips.close();
            line.close();

            status = "";

            if (linhaRetorno.equals("ok")) {
                p = PendingIntent.getActivity(this, 0, new Intent(this.getApplicationContext(), HistoricoActivity.class), 0);
                mBuilder.setTicker("Arks - Status de envio (1/1)");
                mBuilder.setContentTitle("Arks (1/1)");
                mBuilder.setContentText("Arquivo enviado com sucesso.");
                mBuilder.setContentIntent(p);
                mBuilder.setProgress(0, 0, false);
				
                status = "Enviado";
                EditaHistorico(id_historico, status);
            } else {
                if (linhaRetorno.equals("There is not enough space on the disk.")) {
                    mBuilder.setTicker("Arks - Status de envio (0/1)");
                    mBuilder.setContentTitle("Arks (0/1)");
                    mBuilder.setContentText("Erro: servidor indisponível.");
                    mBuilder.setProgress(0, 0, false);

                    status = "Erro";
                    EditaHistorico(id_historico, status);
                } else {
                    if (tentativas <= 5) {
                        try {
                            thread.sleep(3000);
                        } catch (InterruptedException ex2) {

                        }
                        Log.d("PostService", String.valueOf(tentativas));
                        stopSelf();
                        new Thread(PostService.this).start();
                    } else {
                        p = PendingIntent.getService(this, 0, intent, 0);
                        mBuilder.setTicker("Arks - Status de envio (0/1)");
                        mBuilder.setContentTitle("Arks (0/1)");
                        mBuilder.setContentText("Ocorreu um erro. Clique para reenviar.");
                        mBuilder.setContentIntent(p);
                        mBuilder.setProgress(0, 0, false);

                        status = "Erro";
                        EditaHistorico(id_historico, status);
                    }
                }
            }

            stopSelf();
            mBuilder.setVibrate(new long[]{100, 2000, 1000, 2000});
            notificationManager.notify(id_notification, mBuilder.build());

        } catch (NullPointerException nullEx) {
            status = "Erro";
            EditaHistorico(id_historico, status);
            intent.putExtra("ID_HISTORICO", id_historico);
            p = PendingIntent.getService(this, 0, intent, 0);
            mBuilder.setTicker("Arks - Status de envio (0/1)");
            mBuilder.setContentTitle("Arks (0/1)");
            mBuilder.setContentText("Ocorreu um erro. Clique para reenviar.");
            mBuilder.setContentIntent(p);
            mBuilder.setProgress(0, 0, false);
            mBuilder.setVibrate(new long[]{100, 2000, 1000, 2000});
            notificationManager.notify(id_notification, mBuilder.build());
        } catch (Exception ex) {

            if (!getNetworkStatus()) {
                if (tentativas <= 5) {
                    try {
                        thread.sleep(3000);
                    } catch (InterruptedException ex2) {

                    }
                    Log.d("PostService", String.valueOf(tentativas));
                    stopSelf();
                    new Thread(PostService.this).start();
                } else {
                    intent.putExtra("ID_HISTORICO", id_historico);
                    p = PendingIntent.getService(this, 0, intent, 0);

                    status = "Erro";
                    EditaHistorico(id_historico, status);
                    stopSelf();
                    mBuilder.setTicker("Arks - Status de envio (0/1)");
                    mBuilder.setContentTitle("Arks (0/1)");
                    mBuilder.setContentText("Erro de conexão. Clique para reenviar.");
                    mBuilder.setContentIntent(p);
                    mBuilder.setProgress(0, 0, false);
                    mBuilder.setVibrate(new long[]{100, 2000, 1000, 2000});
                    notificationManager.notify(id_notification, mBuilder.build());
                }
            } else {

                ipsERRO = new InputStreamReader(connection.getErrorStream());
                lineERRO = new BufferedReader(ipsERRO);

                String linhaRetornoERRO = "";
                try {
                    do {
                        linhaRetornoERRO += lineERRO.readLine();
                    }
                    while (lineERRO.readLine() != null);
                } catch (IOException ex2) {

                }

                if (linhaRetornoERRO.contains("Maximum request length exceeded")) {
                    stopSelf();
                    mBuilder.setTicker("Arks - Status de envio (0/1)");
                    mBuilder.setContentTitle("Arks (0/1)");
                    mBuilder.setContentText("Erro: arquivo maior que 10 MB.");
                    mBuilder.setProgress(0, 0, false);
                    mBuilder.setVibrate(new long[]{100, 2000, 1000, 2000});
                    notificationManager.notify(id_notification, mBuilder.build());

                    status = "Erro";
                    EditaHistorico(id_historico, status);
                } else {
                    if (tentativas <= 5) {
                        try {
                            thread.sleep(3000);
                        } catch (InterruptedException ex2) {

                        }
                        Log.d("PostService", String.valueOf(tentativas));
                        stopSelf();
                        new Thread(PostService.this).start();
                    } else {
                        intent.putExtra("ID_HISTORICO", id_historico);
                        p = PendingIntent.getService(this, 0, intent, 0);
                        status = "Erro";
                        EditaHistorico(id_historico, status);
                        stopSelf();
                        mBuilder.setTicker("Arks - Status de envio (0/1)");
                        mBuilder.setContentTitle("Arks (0/1)");
                        mBuilder.setContentText("Ocorreu um erro. Clique para reenviar.");
                        mBuilder.setContentIntent(p);
                        mBuilder.setProgress(0, 0, false);
                        mBuilder.setVibrate(new long[]{100, 2000, 1000, 2000});
                        notificationManager.notify(id_notification, mBuilder.build());
                    }
                }
            }
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

        String ret = "";

        String[] proj = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null); 

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        ret = cursor.getString(column_index);
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

    private boolean getNetworkStatus () {

        boolean status;

        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(con.getNetworkInfo(0).isConnected()) {
            status = true;
        } else if(con.getNetworkInfo(1).isConnected()) {
            status = true;
        } else {
            status = false;
        }

        return status;
    }

}
