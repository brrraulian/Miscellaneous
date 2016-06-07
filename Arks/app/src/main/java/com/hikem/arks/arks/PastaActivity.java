package com.hikem.arks.arks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter;
import android.widget.ImageButton;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;


public class PastaActivity extends ActionBarActivity {

    String sUrl;
    String id_seguranca;
    String empresa;
    PastaParametroModel paramentros;
    UserSessionManager session;
    Intent intent;
    static GetFolderReceive folderReceive;
    boolean tem_permissao;
    ImageButton btn_voltar;
    ListView lv_pastas;
    ProgressDialog ringProgressDialog;
    boolean voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasta);

        session = new UserSessionManager(getApplicationContext());

        intent = getIntent();

        if (session.checkLogin(intent))
            finish();

        folderReceive = new GetFolderReceive();

        HashMap<String, String> user = session.getUserDetails();
        id_seguranca = user.get(UserSessionManager.KEY_ID);
        empresa = user.get(UserSessionManager.KEY_EMPRESA);
        folderReceive.id_folder = user.get(UserSessionManager.KEY_FOLDER);
        folderReceive.id_folder_parent = user.get(UserSessionManager.KEY_FOLDER_PARENT);

        btn_voltar = (ImageButton) findViewById(R.id.btn_voltar);
        btn_voltar.setImageResource(R.drawable.voltar);
        voltar = false;
        btn_voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                voltar = true;
                ringProgressDialog = ProgressDialog.show(PastaActivity.this, "Por favor, aguarde...", "Carregando...", true);
                ringProgressDialog.setCancelable(true);
                ringProgressDialog.onStart();
                new HttpAsyncTask().execute(sUrl);
            }
        });

        tem_permissao = true;

        lv_pastas = (ListView) findViewById(R.id.lv_pastas);

        ringProgressDialog = ProgressDialog.show(PastaActivity.this, "Por favor, aguarde...", "Carregando...", true);
        ringProgressDialog.setCancelable(true);
        ringProgressDialog.onStart();

        sUrl = "https://ws" + empresa + ".arks.com.br/Mobile/GetFolders.ashx";
        new HttpAsyncTask().execute(sUrl);
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            paramentros = new PastaParametroModel();

            paramentros.setIdSeguranca(id_seguranca);

            if(voltar) {
                paramentros.setIdFolder(folderReceive.id_folder_parent);
            } else {
                paramentros.setIdFolder(folderReceive.id_folder);
            }

            voltar = false;

            return POST(urls[0], paramentros);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if(result.contains("ok") || result.contains("sem permissao")) {
                try {
                    if(result.contains("sem permissao")) {
                        tem_permissao = false;
                    }
                    PopulaLvPastas();

                } catch (Exception ex) {
                    Thread.currentThread().interrupt();
                    Log.d("PastaActivity", ex.getMessage());
                }
            } else if(result.contains("inexistente")) {
                Toast.makeText(getBaseContext(), "Pasta Inexistente", Toast.LENGTH_LONG).show();
            } else if(result.contains("excluido")) {
                Toast.makeText(getBaseContext(), "Pasta excluída", Toast.LENGTH_LONG).show();
            } else if(result.contains("problema servidor")) {
                Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            }

            Thread.currentThread().interrupt();
            ringProgressDialog.dismiss();
        }
    }

    public static String POST(String url, PastaParametroModel paramentros){
        InputStream inputStream = null;
        String result = "";
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = new GsonBuilder().create().toJson(paramentros.getIdSeguranca() + ";" + paramentros.getIdFolder());


            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
                Gson gson = new Gson();
                GetFolderReceive sGFR = null;
                sGFR = gson.fromJson(result, GetFolderReceive.class);

                folderReceive.id_folder = sGFR.id_folder;
                folderReceive.titulo = sGFR.titulo;
                folderReceive.tipo = sGFR.tipo;
                folderReceive.id_folder_parent = sGFR.id_folder_parent;
                folderReceive.foldernodes = sGFR.foldernodes;

                result = sGFR.mensagem;
            }
            else
                result = "Did not work!";
        }
        catch (Exception e) {
            Thread.currentThread().interrupt();
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    private void PopulaLvPastas() {

        lv_pastas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> ad, View v, int position,
                                    long id) {

                FolderNodes folderNodes = (FolderNodes)ad.getAdapter().getItem(position);
                folderReceive.id_folder = folderNodes.id_folder;

                ringProgressDialog = ProgressDialog.show(PastaActivity.this, "Por favor, aguarde...", "Carregando...", true);
                ringProgressDialog.setCancelable(true);
                ringProgressDialog.onStart();

                new HttpAsyncTask().execute(sUrl);
            }
        });

        TextView lbl_pastaAtual = (TextView) findViewById(R.id.lbl_pastaAtual);

        if(folderReceive.titulo.length() <= 25) {
            lbl_pastaAtual.setText(folderReceive.titulo);
        } else {
            String inicio = folderReceive.titulo.substring(0, 13);
            String fim = folderReceive.titulo.substring(folderReceive.titulo.length() - 7);
            lbl_pastaAtual.setText(inicio + " ... " + fim);
        }

        lv_pastas.setAdapter(new CustomPastaList(this, folderReceive.foldernodes));
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public void Selecionar_Click(View v) {
        session.SaveFolder(folderReceive.id_folder, folderReceive.titulo, folderReceive.id_folder_parent);
        setResult(1, intent);
        finish();
    }

    public void Cancelar_Click(View v) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pasta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
