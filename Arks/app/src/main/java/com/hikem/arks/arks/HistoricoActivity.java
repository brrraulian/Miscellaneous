package com.hikem.arks.arks;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;
import android.content.Context;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.ArrayList;

public class HistoricoActivity extends ActionBarActivity {

    UserSessionManager session;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        session = new UserSessionManager(getApplicationContext());

        intent = getIntent();

        if (session.checkLogin(intent))
            finish();

        HashMap<String, String> user = session.getUserDetails();
        String nome = user.get(UserSessionManager.KEY_NOME);
        TextView lbl_usuario = (TextView) findViewById(R.id.lbl_usuario);
        lbl_usuario.setText(nome);

        PopulaLista();
    }

    public void PopulaLista() {
        SQLiteDatabase db = openOrCreateDatabase("arks.db", Context.MODE_PRIVATE, null);

        Cursor cursor = db.rawQuery("SELECT * FROM historico ORDER BY _id DESC", null);

        /*
        String[] from = { "nome", "status", "data", "data" };
        int[] to = { R.id.lbl_nome, R.id.lbl_status, R.id.lbl_data, R.id.lbl_hora };
        SimpleCursorAdapter ad = new SimpleCursorAdapter(getBaseContext(), R.layout.historico_lista, cursor, from, to);
        */

        CustomHistoricoList ad = new CustomHistoricoList(getBaseContext(), cursor);

        ListView lv_historico = (ListView) findViewById(R.id.lv_historico);
        lv_historico.setAdapter(ad);

        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historico, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_main2) {
            Intent i = new Intent(getApplicationContext(), MainActivity2.class);
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
