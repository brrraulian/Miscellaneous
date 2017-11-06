package com.arks.checklist.wiinc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ProgressDialog ringProgressDialog;
    AutoCompleteTextView txt_login;
    EditText txt_senha;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getApplicationContext().getSharedPreferences("SharedPreferences", 0);

        if(pref.getBoolean("usuarioLogado", false)) {
            startActivity(new Intent(getBaseContext(), FeedActivity.class));
            finish();
        }

        editor = pref.edit();
        txt_login = (AutoCompleteTextView)findViewById(R.id.txt_login);
        txt_senha = (EditText)findViewById(R.id.txt_senha);

        txt_login.setText("testewiinc@gmail.com");
        txt_senha.setText("asd123");
    }

    private boolean ValidarCampos() {
        boolean ret = false;

        if(txt_login.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(), "Informe o login.", Toast.LENGTH_LONG).show();
        } else if(txt_senha.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(), "Informe a senha.", Toast.LENGTH_LONG).show();
        } else {
            ret = true;
        }

        return ret;
    }

    public void Logar_Click(View view) {

        if(!isConnected()){
            Toast.makeText(getBaseContext(), "Sem conexão de internet.", Toast.LENGTH_LONG).show();
        } else {
            ringProgressDialog = ProgressDialog.show(MainActivity.this, "Por favor, aguarde...", "Autenticando usuário...", true);
            ringProgressDialog.setCancelable(true);
            ringProgressDialog.onStart();


            if (!ValidarCampos()) {
                ringProgressDialog.dismiss();
                return;
            } else {

                new AutenticacaoTask().execute();
            }
        }
    }

    private class AutenticacaoTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String token = "";

            String url = "http://52.67.76.161/bokaboka/v1/TesteWiinc/Login";
            InputStream inputStream = null;
            String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(url);

                Map<String, String> comment = new HashMap<String, String>();

                comment.put("Login", txt_login.getText().toString());
                comment.put("Senha", txt_senha.getText().toString());

                String json = new GsonBuilder().create().toJson(comment, Map.class);

                StringEntity se = new StringEntity(json);

                httpPost.setEntity(se);

                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                HttpResponse httpResponse = httpclient.execute(httpPost);

                inputStream = httpResponse.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);

                    String[] par = result.split(",");
                    String[] par2 = par[1].split(":");
                    token = par2[1].replace("\"", "");

                    if(token.equals("Usuário ou senha inválidos}")) {
                        token = token.substring(0, token.length() - 1);
                    }
                } else {
                    token = "Erro na transmissão de dados";
                }
            } catch (Exception e) {
                token = "Ocorreu algum erro inesperado, por favor tente mais tarde";
            }

            return token;
        }

        protected void onPostExecute(String result) {

            if (result.equals("Usuário ou senha inválidos")) {
                Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
            } else if (result.equals("Erro na transmissão de dados")) {
                Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
            } else if (result.equals("Tente mais tarde")) {
                Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
            } else {
                editor.putBoolean("usuarioLogado", true);
                editor.putString("token", result);
                editor.commit();

                startActivity(new Intent(getBaseContext(), FeedActivity.class));
                finish();
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


    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
