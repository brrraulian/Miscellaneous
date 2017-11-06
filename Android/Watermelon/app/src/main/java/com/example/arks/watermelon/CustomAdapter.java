package com.example.arks.watermelon;

/**
 * Created by ARKS on 01/07/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
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
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private final LayoutInflater context;
    private final List<Dados> lista;

    public CustomAdapter(Context context, List<Dados> lista) {
        this.context = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView lbl_nome;
        public TextView lbl_idade;
        public ImageView iv_picture_url;

        public ViewHolder(View v) {
            super(v);

            lbl_nome = (TextView) v.findViewById(R.id.lbl_nome);
            lbl_idade = (TextView) v.findViewById(R.id.lbl_idade);
            iv_picture_url = (ImageView) v.findViewById(R.id.iv_picture_url);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = context.inflate(R.layout.model, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.lbl_nome.setText(lista.get(position).getNome());
        holder.lbl_idade.setText(lista.get(position).getIdade());
        holder.iv_picture_url.setImageDrawable(lista.get(position).getPictureUrl());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
