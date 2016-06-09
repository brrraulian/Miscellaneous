package com.arks.checklist.wiinc;

/**
 * Created by Arks1 on 09/06/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] titulo;
    private final Drawable[] imagem;
    private final String[] descricao;
    private final String[] data;

    public CustomListAdapter(Context context, String[] titulo, Drawable[] imagem, String[] descricao, String[] data) {
        super(context, R.layout.model_feed, titulo);
        this.context = context;
        this.titulo = titulo;
        this.imagem = imagem;
        this.descricao = descricao;
        this.data = data;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.model_feed, null, true);

        try {
            ImageView iv_imagem = (ImageView) rowView.findViewById(R.id.iv_imagem);
            iv_imagem.setImageDrawable(imagem[position]);
            TextView lbl_titulo = (TextView) rowView.findViewById(R.id.lbl_titulo);
            lbl_titulo.setText(titulo[position]);
            TextView lbl_descricao = (TextView) rowView.findViewById(R.id.lbl_descricao);
            lbl_descricao.setText(descricao[position]);
            TextView lbl_data = (TextView) rowView.findViewById(R.id.lbl_data);
            lbl_data.setText(data[position]);
        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return rowView;
    }
}