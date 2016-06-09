package com.arks.checklist.wiinc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FeedActivity extends ActionBarActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ProgressDialog ringProgressDialog;
    ListView lv_feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        pref = getApplicationContext().getSharedPreferences("SharedPreferences", 0);
        editor = pref.edit();

        lv_feed = (ListView)findViewById(R.id.lv_feed);
        lv_feed.setEmptyView(findViewById(R.id.emptyList));

        ringProgressDialog = ProgressDialog.show(FeedActivity.this, "Por favor, aguarde...", "Carregando feeds...", true);
        ringProgressDialog.setCancelable(true);
        ringProgressDialog.onStart();

        new importacaoFeedTask().execute();
    }

    private class importacaoFeedTask extends AsyncTask<String, Void, CustomListAdapter> {

        @Override
        protected CustomListAdapter doInBackground(String... urls) {

            CustomListAdapter adapter = null;
            String url = "http://52.67.76.161/bokaboka/v1/TesteWiinc/Feeds";
            InputStream inputStream = null;
            String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();

                HttpGet httpGet = new HttpGet(url);

                httpGet.setHeader("Authorization", pref.getString("token", null));

                HttpResponse httpResponse = httpclient.execute(httpGet);

                inputStream = httpResponse.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);

                    String[] parTest = result.split(",");
                    String[] parTest2 = parTest[0].split(":");
                    String ret = parTest2[1].replace("\"", "");

                    if(ret.equals("false")) {
                        adapter = null;
                    } else {
                        result = result.substring(11);
                        String[] par = result.split("\\},\\{");

                        String[] titulo = new String[par.length];
                        String[] descricao = new String[par.length];
                        String[] data = new String[par.length];
                        String[] imagem = new String[par.length];

                        for (int i = 0; i < par.length; i++) {
                            String[] par2 = par[i].split(",");
                            titulo[i] = par2[0].replace("\"Titulo\":", "");
                            titulo[i] = titulo[i].replace("\"", "");
                            descricao[i] = par2[1].replace("\"Descricao\":", "");
                            descricao[i] = descricao[i].replace("\"", "");
                            data[i] = par2[2].replace("\"Data\":", "");
                            data[i] = data[i].replace("\"", "");
                            imagem[i] = par2[3].replace("\"Imagem\":", "");
                            imagem[i] = imagem[i].replace("\"", "");

                            if (i == par.length - 1) {
                                imagem[i] = imagem[i].substring(0, imagem[i].length() - 2);
                            }
                        }

                        Drawable[] img = new Drawable[imagem.length];
                        for (int i = 0; i < imagem.length; i++) {
                            InputStream is = (InputStream) new URL(imagem[i]).getContent();
                            img[i] = Drawable.createFromStream(is, "src name");
                        }

                        adapter = new CustomListAdapter(getBaseContext(), titulo, img, descricao, data);
                    }
                }
            }
            catch (Exception e) {
                adapter = null;
            }

            return adapter;
        }

        protected void onPostExecute(CustomListAdapter result) {
            if(result != null) {
                try {

                    lv_feed.setAdapter(result);

                } catch (Exception ex) {
                    Toast.makeText(getBaseContext(), "Ocorreu algum erro inesperado, por favor tente mais tarde", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "Token de autenticação expirado, por favor faça seu login novamente", Toast.LENGTH_SHORT).show();
            }

            ringProgressDialog.dismiss();
        }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            editor.clear();
            editor.commit();
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
