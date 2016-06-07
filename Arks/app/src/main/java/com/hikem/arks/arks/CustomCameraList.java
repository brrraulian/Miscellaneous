package com.hikem.arks.arks;

import android.app.Activity;
import android.hardware.Camera;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Makem2 on 23/01/2015.
 */
public class CustomCameraList extends ArrayAdapter {

    private final Activity context;
    private final List<Camera.Size> resolucoes;
    int low;
    int medium;
    int high;

    public CustomCameraList(Activity context, List<Camera.Size> resolucoes) {
        super(context, R.layout.camera_lista, resolucoes);
        this.context = context;
        this.resolucoes = resolucoes;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.camera_lista, null, true);

        try {
            TextView lbl_resolucao = (TextView) rowView.findViewById(R.id.lbl_resolucao);
            lbl_resolucao.setText(resolucoes.get(position).width + " x " + resolucoes.get(position).height);
        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return rowView;
    }

}
