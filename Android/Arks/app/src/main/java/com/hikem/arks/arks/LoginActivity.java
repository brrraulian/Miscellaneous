package com.hikem.arks.arks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.app.ProgressDialog;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity {

    UserSessionManager session;
    AutoCompleteTextView txt_empresa;
    AutoCompleteTextView txt_email;
    EditText txt_senha;
    Intent intent;
    ProgressDialog ringProgressDialog;

    AutenticarModel Autenticar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intent = getIntent();
        session = new UserSessionManager(getApplicationContext());
        txt_empresa = (AutoCompleteTextView)findViewById(R.id.txt_empresa);
        txt_email = (AutoCompleteTextView)findViewById(R.id.txt_email);
        txt_senha = (EditText)findViewById(R.id.txt_senha);
    }

    private boolean ValidarCampos() {
        boolean ret = false;

        if(txt_empresa.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(), "Informe a empresa.", Toast.LENGTH_LONG).show();
        } else if(txt_email.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(), "Informe o e-mail.", Toast.LENGTH_LONG).show();
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
            ringProgressDialog = ProgressDialog.show(LoginActivity.this, "Por favor, aguarde...", "Autenticando usuário...", true);
            ringProgressDialog.setCancelable(true);
            ringProgressDialog.onStart();

            if (!ValidarCampos()) {
                ringProgressDialog.dismiss();
                return;
            } else {

                String sUrl = "http://ws" + txt_empresa.getText() + ".arks.com.br/mobile/autenticar.ashx";
                new HttpAsyncTask().execute(sUrl);
            }
        }
    }


    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            Autenticar = new AutenticarModel();

            Autenticar.setUsuario(txt_email.getText().toString());
            Autenticar.setSenha(txt_senha.getText().toString());

            return POST(urls[0], Autenticar);
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.contains("ok")) {
                try {

                    String[] par = result.split(";");
                    session.createUserLoginSession(par[1], txt_empresa.getText().toString(), par[2], "", "", "");

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    i.setAction(intent.getAction());
                    i.setType(intent.getType());

                    if (intent.getAction() != null && intent.getType() != null) {
                        if (intent.getAction().equals(Intent.ACTION_SEND) && intent.getType() != null) {
                            if (intent.getType().startsWith("image/")) {
                                i.putExtra(intent.EXTRA_STREAM, intent.getParcelableExtra(Intent.EXTRA_STREAM));
                            }
                        } else if (intent.getAction().equals(Intent.ACTION_SEND_MULTIPLE) && intent.getType() != null) {
                            if (intent.getType().startsWith("image/")) {
                                i.putExtra(intent.EXTRA_STREAM, intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM));
                            }
                        }
                    }

                    startActivity(i);

                    finish();
                } catch (Exception ex) {
                    Log.d("LoginActivity", ex.getMessage());
                }
            } else if(result.contains("invalido")) {
                    Toast.makeText(getBaseContext(), "Usuário ou senha inválidos.", Toast.LENGTH_LONG).show();
            } else if(result.contains("ip")) {
                Toast.makeText(getBaseContext(), "Restrição de IP.", Toast.LENGTH_LONG).show();
            } else if(result.contains("inativo")) {
                Toast.makeText(getBaseContext(), "Usuário encontra-se inativo.", Toast.LENGTH_LONG).show();
            } else if(result.contains("HTTP 404") || result.contains("problema")) {
                Toast.makeText(getBaseContext(), "Servidor indisponível. Por favor, tente mais tarde.", Toast.LENGTH_LONG).show();
            } else if(result.equals("")) {
                Toast.makeText(getBaseContext(), "Empresa inválida.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            }

            ringProgressDialog.dismiss();
        }
    }

    public static String POST(String url, AutenticarModel Autenticar){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);

            Map<String, String> comment = new HashMap<String, String>();

            comment.put("usuario",Autenticar.getUsuario());
            comment.put("senha",Autenticar.getSenha());
            comment.put("os", Build.VERSION.CODENAME );
            comment.put("versaoclient",Build.VERSION.RELEASE + " " + Build.VERSION.INCREMENTAL);

            String json = new GsonBuilder().create().toJson(comment, Map.class);

            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
                Gson gson = new Gson();
                autenticarReceive sAR = null;
                sAR = gson.fromJson(result, autenticarReceive.class);
                result = sAR.getMensagem() + ";" + sAR.getId_seguranca() + ";" + sAR.getNome();

            }
            else
                result = "Did not work!";
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
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
