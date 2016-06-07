package com.hikem.arks.arks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ListView;
import android.content.Intent;
import android.net.Uri;
import android.database.Cursor;
import android.provider.OpenableColumns;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.view.MotionEvent;


public class MainActivity extends ActionBarActivity {

    UserSessionManager session;
    Intent it;
    Intent intent;
    TextView lbl_usuario;
    Spinner cbox_pasta;
    String[] pasta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        session = new UserSessionManager(getApplicationContext());
        intent = getIntent();

        if(session.checkLogin(intent)) {
            finish();
        } else {
            HashMap<String, String> user = session.getUserDetails();

            String nome = user.get(UserSessionManager.KEY_NOME);
            lbl_usuario = (TextView) findViewById(R.id.lbl_usuario);
            lbl_usuario.setText(nome);

            pasta = new String[] { user.get(UserSessionManager.KEY_FOLDER_NAME) };
            PopulaCboxPasta();

            cbox_pasta = (Spinner) findViewById(R.id.cbox_pasta);
            cbox_pasta.setOnTouchListener(spinnerOnTouch);

            BancoDeDados();
            PegaImagem();
        }
    }

    public void PopulaCboxPasta() {
        ArrayAdapter<CharSequence> adPasta = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, pasta);
        adPasta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbox_pasta = (Spinner) findViewById(R.id.cbox_pasta);
        cbox_pasta.setAdapter(adPasta);
    }

    private View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                cbox_pasta.setPressed(false);
                Intent i = new Intent(getApplicationContext(), PastaActivity.class);
                startActivityForResult(i, 1);
            }
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            //call populatedatamethod
            cbox_pasta.setPressed(false);
            HashMap<String, String> user = session.getUserDetails();
            pasta = new String[] { user.get(UserSessionManager.KEY_FOLDER_NAME) };
            PopulaCboxPasta();
        }
    }


    private void BancoDeDados() {

        SQLiteDatabase db = openOrCreateDatabase("arks.db", Context.MODE_PRIVATE, null);
        StringBuilder sqlHistorico = new StringBuilder();
        sqlHistorico.append("CREATE TABLE IF NOT EXISTS [historico](");
        sqlHistorico.append("[_id] INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlHistorico.append("nome VARCHAR(100), ");
        sqlHistorico.append("status VARCHAR(25), ");
        sqlHistorico.append("data DATETIME DEFAULT CURRENT_TIMESTAMP);");
        db.execSQL(sqlHistorico.toString());
        db.close();
    }

    private void PegaImagem() {

        String action = intent.getAction();
        String type = intent.getType();

        if(intent.getAction() != null && intent.getType() != null) {
            if (action.equals(Intent.ACTION_SEND) && type != null) {
                //if (type.startsWith("image/")) {
                    TextView textView = (TextView) findViewById(R.id.txt1);
                    textView.setVisibility(View.VISIBLE);
                    ListView lv_arquivos = (ListView) findViewById(R.id.lv_arquivos);
                    lv_arquivos.setVisibility(View.INVISIBLE);
                    handleSendImage(intent);
                //}
            } else if (action.equals(Intent.ACTION_SEND_MULTIPLE) && type != null) {
                //if (type.startsWith("image/")) {
                    TextView textView = (TextView) findViewById(R.id.txt1);
                    textView.setVisibility(View.INVISIBLE);
                    ListView lv_arquivos = (ListView) findViewById(R.id.lv_arquivos);
                    lv_arquivos.setVisibility(View.VISIBLE);

                    handleSendMultipleImages(intent);
                //}
            } else if (action.equals(Intent.ACTION_MAIN)) {
                if (!session.checkLogin(intent)) {
                    Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                    i.setAction(action);
                    startActivity(i);
                    finish();
                }
            }
        } else {
            if (!session.checkLogin(intent)) {
                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                i.setAction(action);
                startActivity(i);
                finish();
            }
        }
    }

    void handleSendImage(Intent intent) {

        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);

        if (imageUri != null) {
            TextView txtView = (TextView)findViewById(R.id.txt1);

            if(getFileName(imageUri).length() <= 25) {
                txtView.setText(getFileName(imageUri));
            } else {
                String inicio = getFileName(imageUri).substring(0, 13);
                String fim = getFileName(imageUri).substring(getFileName(imageUri).length() - 7);
                txtView.setText(inicio + " ... " + fim);
            }
        }

        it = new Intent("INICIAR_POST");
        it.putExtra(intent.EXTRA_STREAM, intent.getParcelableExtra(Intent.EXTRA_STREAM));
    }

    void handleSendMultipleImages(Intent intent) {

        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);

        if (imageUris != null) {
            ListView lv_arquivos = (ListView) findViewById(R.id.lv_arquivos);
            lv_arquivos.setAdapter(new CustomMainList(this, imageUris));
        }

        it = new Intent("INICIAR_POST_MULTIPLOS");
        it.putExtra(intent.EXTRA_STREAM, intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM));
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

    public void Enviar_Click(View v) {

        if (it != null) {
            if (getNetworkStatus()) {
                if(pasta != null && pasta[0] != "") {
                    startService(createExplicitFromImplicitIntent(this, it));
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Selecione uma pasta de destino.", Toast.LENGTH_LONG).show();
                }
            } else {
                //Log.d("PostService", "Sem conexão de internet");
                Toast.makeText(getBaseContext(), "Sem conexão de internet.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void Cancelar_Click(View v) {

        finish();
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

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
