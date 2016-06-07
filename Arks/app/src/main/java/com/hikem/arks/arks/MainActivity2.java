package com.hikem.arks.arks;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.os.Environment;
import android.net.Uri;
import android.widget.ImageView;
import android.hardware.camera2.*;
import android.view.SurfaceHolder;
import android.hardware.Camera.Parameters;
import android.view.SurfaceView;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.widget.Toast;
import android.widget.FrameLayout;
import android.content.res.Configuration;

import java.util.HashMap;
import java.io.FileInputStream;
import java.io.File;
import java.util.List;
import java.io.IOException;

public class MainActivity2 extends ActionBarActivity {

    UserSessionManager session;
    Intent intent;
    TextView lbl_usuario;
    Spinner cbox_pasta;
    String[] pasta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);


        session = new UserSessionManager(getApplicationContext());
        intent = getIntent();

        try {
            if (session.checkLogin(intent)) {
                finish();
            } else {
                HashMap<String, String> user = session.getUserDetails();

                String nome = user.get(UserSessionManager.KEY_NOME);
                lbl_usuario = (TextView) findViewById(R.id.lbl_usuario);
                lbl_usuario.setText(nome);

                pasta = new String[]{user.get(UserSessionManager.KEY_FOLDER_NAME)};
                PopulaCboxPasta();

                cbox_pasta = (Spinner) findViewById(R.id.cbox_pasta);
                cbox_pasta.setOnTouchListener(spinnerOnTouch);

                BancoDeDados();
            }
        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void BancoDeDados() {

        SQLiteDatabase db = openOrCreateDatabase("arks.db", Context.MODE_PRIVATE, null);
        StringBuilder sqlHistorico = new StringBuilder();
        sqlHistorico.append("CREATE TABLE IF NOT EXISTS [fila](");
        sqlHistorico.append("[_id] INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlHistorico.append("nome VARCHAR(100), ");
        sqlHistorico.append("status VARCHAR(25), ");
        sqlHistorico.append("data DATETIME DEFAULT CURRENT_TIMESTAMP);");
        db.execSQL(sqlHistorico.toString());
        db.close();
    }

    public void Camera_Click(View view) {
        if (pasta != null && pasta[0] != "") {
            Intent i = new Intent(getApplicationContext(), CameraActivity.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Selecione uma pasta de destino.", Toast.LENGTH_LONG).show();
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
        if (resultCode == 1) {
            cbox_pasta.setPressed(false);
            HashMap<String, String> user = session.getUserDetails();
            pasta = new String[]{user.get(UserSessionManager.KEY_FOLDER_NAME)};
            PopulaCboxPasta();
        } else if (resultCode == RESULT_OK) {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_historico) {
            Intent i = new Intent(getApplicationContext(), HistoricoActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if (id == R.id.action_logout) {
            session.logoutUser(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}