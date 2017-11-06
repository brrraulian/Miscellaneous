package com.hikem.arks.arks;

import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.view.View;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Makem2 on 16/01/2015.
 */
public class CustomHistoricoList extends CursorAdapter {

    private final LayoutInflater mInflater;

    public CustomHistoricoList(Context context, Cursor c) {
        super(context, c);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView lbl_nome = (TextView) view.findViewById(R.id.lbl_nome);
        TextView lbl_status = (TextView) view.findViewById(R.id.lbl_status);
        TextView lbl_data = (TextView) view.findViewById(R.id.lbl_data);
        TextView lbl_hora = (TextView) view.findViewById(R.id.lbl_hora);

        if(cursor.getString(cursor.getColumnIndex("nome")).length() <= 25) {
            lbl_nome.setText(cursor.getString(cursor.getColumnIndex("nome")));
        } else {
            String inicio = cursor.getString(cursor.getColumnIndex("nome")).substring(0, 13);
            String fim = cursor.getString(cursor.getColumnIndex("nome")).substring(cursor.getString(cursor.getColumnIndex("nome")).length() - 7);
            lbl_nome.setText(inicio + " ... " + fim);
        }

        lbl_status.setText(cursor.getString(cursor.getColumnIndex("status")));
        lbl_data.setText(cursor.getString(cursor.getColumnIndex("data")).subSequence(0, 10));
        lbl_hora.setText(cursor.getString(cursor.getColumnIndex("data")).subSequence(10, cursor.getString(cursor.getColumnIndex("data")).length() - 3));
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.historico_lista, parent, false);
        bindView(v, context, cursor);
        return v;
    }

}
