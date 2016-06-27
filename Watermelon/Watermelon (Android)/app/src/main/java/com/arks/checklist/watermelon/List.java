package com.arks.checklist.watermelon;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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
import java.net.URLConnection;

public class List extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ProgressDialog ringProgressDialog;
    ListView lv_feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        pref = getApplicationContext().getSharedPreferences("SharedPreferences", 0);
        editor = pref.edit();

        lv_feed = (ListView)findViewById(R.id.lv_feed);
        lv_feed.setEmptyView(findViewById(R.id.emptyList));

        ringProgressDialog = ProgressDialog.show(MainActivity.this, "Por favor, aguarde...", "Carregando feeds...", true);
        ringProgressDialog.setCancelable(true);
        ringProgressDialog.onStart();

        new importacaoFeedTask().execute();
    }


    private class importacaoFeedTask extends AsyncTask<String, Void, CustomListAdapter> {

        @Override
        protected CustomListAdapter doInBackground(String... urls) {

            CustomListAdapter adapter = null;

            String url = "http://testwatermelon.azurewebsites.net/todo/api/v1.0/tasks";
            InputStream inputStream = null;
            String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();

                HttpGet httpGet = new HttpGet(url);

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

                        String[] nome = new String[par.length];
                        String[] idade = new String[par.length];
                        String[] picture_url = new String[par.length];

                        for (int i = 0; i < par.length; i++) {
                            String[] par2 = par[i].split(",");
                            nome[i] = par2[0].replace("\"Nome\":", "");
                            nome[i] = nome[i].replace("\"", "");
                            idade[i] = par2[1].replace("\"idade\":", "");
                            idade[i] = idade[i].replace("\"", "");
                            picture_url[i] = par2[3].replace("\"picture_url\":", "");
                            picture_url[i] = picture_url[i].replace("\"", "");

                            if (i == par.length - 1) {
                                picture_url[i] = picture_url[i].substring(0, picture_url[i].length() - 2);
                            }
                        }

                        Drawable[] img = new Drawable[picture_url.length];
                        for (int i = 0; i < picture_url.length; i++) {
                            InputStream is = (InputStream) new URL(picture_url[i]).getContent();
                            img[i] = Drawable.createFromStream(is, "src name");
                        }

                        adapter = new CustomListAdapter(getBaseContext(), nome, img, idade);
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
                    Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "Ocorreu algum erro inesperado, por favor tente mais tarde", Toast.LENGTH_SHORT).show();
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



}
