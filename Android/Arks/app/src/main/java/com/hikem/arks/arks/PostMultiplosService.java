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
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

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
import java.util.List;

/**
 * Created by Makem2 on 15/01/2015.
 */
public class PostMultiplosService extends Service implements Runnable {

    UserSessionManager session;
    Intent intent;
    ArrayList enviados;
    ArrayList ids_historico;
    ArrayList mt_grandes;
    NotificationManager notificationManager;
    NotificationCompat.Builder mBuilder;
    Notification nt;
    PendingIntent p;
    Thread thread;
    int tentativas = 0;
    int id_notification = 0;
    long id_historico = 0;
    InputStreamReader ipsERRO;
    BufferedReader lineERRO;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        this.intent = intent;

        if (intent.getParcelableArrayListExtra("ENVIADOS") != null) {
            enviados = intent.getParcelableArrayListExtra("ENVIADOS");
        } else {
            enviados = new ArrayList();
        }

        if (intent.getParcelableArrayListExtra("IDS_HISTORICO") != null) {
            ids_historico = intent.getParcelableArrayListExtra("IDS_HISTORICO");
        } else {
            ids_historico = new ArrayList();
        }

        mt_grandes = new ArrayList();

        new Thread(PostMultiplosService.this).start();
        session = new UserSessionManager(getApplicationContext());

        tentativas = 0;
        id_notification = (int) Math.round(Math.random());

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void run() {

        tentativas++;

        ArrayList<Uri> imagens = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setTicker("Arks - Status de envio (" + enviados.size() + "/" + imagens.size() + ")");
        mBuilder.setContentTitle("Arks (" + enviados.size() + "/" + imagens.size() + ")");
        mBuilder.setContentText("Enviando arquivos...").setSmallIcon(R.drawable.icon);
        mBuilder.setAutoCancel(true);
        mBuilder.setProgress(0, 0, true);
        notificationManager.notify(id_notification, mBuilder.build());

        String status = "";
        boolean envios_ok = false;
        boolean servidor_disponivel = true;
        boolean conexao_disponivel = true;


        HttpURLConnection connection = null;
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

        for (int i = 0; i < imagens.size(); i++) {

            if (!enviados.contains(i)) {

                try {

                    if(tentativas == 1 && ids_historico.size() != imagens.size()) {
                        id_historico = InsereHistorico(getFileName(imagens.get(i)), "Enviando...");
                        ids_historico.add(id_historico);
                    }

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
                    outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName + "_arks_" + user.get(UserSessionManager.KEY_FOLDER) + 
					"_arks_" + user.get(UserSessionManager.KEY_ID) + "\"" + lineEnd);
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
                            EditaHistorico((long)ids_historico.get(i), status);

                            mBuilder.setTicker("Arks - Status de envio (" + enviados.size() + "/" + imagens.size() + ")");
                            mBuilder.setContentTitle("Arks (" + enviados.size() + "/" + imagens.size() + ")");
                            mBuilder.setContentText("Enviando arquivos...");
                            notificationManager.notify(id_notification, mBuilder.build());
                        }
                    } else {
                        if (linhaRetorno.equals("There is not enough space on the disk.")) {
                            envios_ok = false;
                            servidor_disponivel = false;
                            status = "Erro";
                            EditaHistorico((long)ids_historico.get(i), status);
                        } else {
                            envios_ok = false;

                            if (tentativas > 5) {
                                status = "Erro";
                                EditaHistorico((long)ids_historico.get(i), status);
                            }
                        }
                    }
                } catch (OutOfMemoryError e) {
                    envios_ok = false;
                    status = "Erro";
                    EditaHistorico((long)ids_historico.get(i), status);
                } catch (NullPointerException nullEx) {
                    envios_ok = false;
                    status = "Erro";
                    EditaHistorico((long)ids_historico.get(i), status);
                } catch (Exception ex) {
                    envios_ok = false;
                    status = "Erro";
                    EditaHistorico((long)ids_historico.get(i), status);

                    if (!getNetworkStatus()) {
                        conexao_disponivel = false;
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
                            if (!mt_grandes.contains(i)) {
                                mt_grandes.add(getFileName(imagens.get(i)));
                            }
                        }
                    }
                }
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
        } else {

            if (!servidor_disponivel) {
                mBuilder.setTicker("Arks - Status de envio (" + enviados.size() + "/" + imagens.size() + ")");
                mBuilder.setContentTitle("Arks (" + enviados.size() + "/" + imagens.size() + ")");
                mBuilder.setContentText("Erro: servidor indisponível.");
                mBuilder.setProgress(0,0,false);
                mBuilder.setVibrate(new long[]{100, 2000, 1000, 2000});
                notificationManager.notify(id_notification, mBuilder.build());
            } else if (mt_grandes.size() > 0) {
                mBuilder.setTicker("Arks - Status de envio (" + enviados.size() + "/" + imagens.size() + ")");
                mBuilder.setContentTitle("Arks (" + enviados.size() + "/" + imagens.size() + ")");
                mBuilder.setContentText("Erro: arquivo maior que 10 MB.");
                mBuilder.setProgress(0,0,false);
                mBuilder.setVibrate(new long[]{100, 2000, 1000, 2000});
                notificationManager.notify(id_notification, mBuilder.build());
            } else {
                if (tentativas <= 5) {
                    try {
                        thread.sleep(3000);
                    } catch (InterruptedException ex2) {

                    }
                    stopSelf();
                    new Thread(PostMultiplosService.this).start();
                } else {
                    if (!conexao_disponivel) {
                        intent.putExtra("ENVIADOS", enviados);
                        intent.putExtra("IDS_HISTORICO", ids_historico);
                        p = PendingIntent.getService(this, 0, intent, 0);
                        mBuilder.setTicker("Arks - Status de envio (" + enviados.size() + "/" + imagens.size() + ")");
                        mBuilder.setContentTitle("Arks (" + enviados.size() + "/" + imagens.size() + ")");
                        mBuilder.setContentText("Erro de conexão. Clique para reenviar.");
                        mBuilder.setContentIntent(p);
                    } else {
                        intent.putExtra("ENVIADOS", enviados);
                        intent.putExtra("IDS_HISTORICO", ids_historico);
                        p = PendingIntent.getService(this, 0, intent, 0);
                        mBuilder.setTicker("Arks - Status de envio (" + enviados.size() + "/" + imagens.size() + ")");
                        mBuilder.setContentTitle("Arks (" + enviados.size() + "/" + imagens.size() + ")");
                        mBuilder.setContentText("Ocorreu um erro. Clique para reenviar.");
                        mBuilder.setContentIntent(p);
                    }

                    stopSelf();
                    mBuilder.setProgress(0,0,false);
                    mBuilder.setVibrate(new long[]{100, 2000, 1000, 2000});
                    notificationManager.notify(id_notification, mBuilder.build());
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
