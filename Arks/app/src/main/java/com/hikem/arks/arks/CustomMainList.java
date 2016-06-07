package com.hikem.arks.arks;

/**
 * Created by Makem2 on 07/01/2015.
 */

import java.util.ArrayList;

import android.database.Cursor;
import android.provider.OpenableColumns;
import android.widget.ArrayAdapter;
import android.net.Uri;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.Toast;

public class CustomMainList extends ArrayAdapter<Uri>{
    private final Activity context;
    private final ArrayList<Uri> images;

    public CustomMainList(Activity context, ArrayList<Uri> images) {
        super(context, R.layout.main_lista, images);
        this.context = context;
        this.images = images;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.main_lista, null, true);

        try {
            TextView textView = (TextView) rowView.findViewById(R.id.txt);

            if(getFileName(images.get(position)).length() <= 25) {
                textView.setText(getFileName(images.get(position)));
            } else {
                String inicio = getFileName(images.get(position)).substring(0, 13);
                String fim = getFileName(images.get(position)).substring(getFileName(images.get(position)).length() - 7);
                textView.setText(inicio + " ... " + fim);
            }
        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return rowView;
    }


    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
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
}
