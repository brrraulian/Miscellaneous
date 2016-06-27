package com.arks.checklist.watermelon;

/**
 * Created by Arks1 on 27/06/2016.
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
    private final String[] nome;
    private final Drawable[] picture_url;
    private final String[] idade;

    public CustomListAdapter(Context context, String[] nome, Drawable[] picture_url, String[] idade) {
        super(context, R.layout.model, nome);
        this.context = context;
        this.nome = nome;
        this.picture_url = picture_url;
        this.idade = idade;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.model, null, true);

        try {
            ImageView iv_picture_url = (ImageView) rowView.findViewById(R.id.iv_picture_url);
            iv_picture_url.setImageDrawable(picture_url[position]);
            TextView lbl_nome = (TextView) rowView.findViewById(R.id.lbl_nome);
            lbl_nome.setText(nome[position]);
            TextView lbl_idade = (TextView) rowView.findViewById(R.id.lbl_idade);
            lbl_idade.setText(idade[position]);
        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return rowView;
    }
}
