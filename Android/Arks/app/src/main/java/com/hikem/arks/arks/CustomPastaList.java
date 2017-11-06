package com.hikem.arks.arks;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Makem2 on 23/01/2015.
 */
public class CustomPastaList extends ArrayAdapter {

    private final Activity context;
    private final List<FolderNodes> folderNotes;

    public CustomPastaList(Activity context, List<FolderNodes> folderNotes) {
        super(context, R.layout.pasta_lista, folderNotes);
        this.context = context;
        this.folderNotes = folderNotes;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.pasta_lista, null, true);

        try {
            ImageView img_pasta = (ImageView) rowView.findViewById(R.id.img_pasta);
            img_pasta.setImageResource(R.drawable.folder);
            TextView lbl_pasta = (TextView) rowView.findViewById(R.id.lbl_pasta);
            lbl_pasta.setText(folderNotes.get(position).titulo);
        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return rowView;
    }

}
