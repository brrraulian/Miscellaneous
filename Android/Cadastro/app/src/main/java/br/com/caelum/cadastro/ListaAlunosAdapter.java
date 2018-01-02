package br.com.caelum.cadastro;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

/**
 * Created by android6231 on 11/07/16.
 */
public class ListaAlunosAdapter extends BaseAdapter {

    private Activity activity;
    private List<Aluno> alunos;

    public ListaAlunosAdapter(Activity activity, List<Aluno> alunos) {
        this.activity = activity;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((Aluno)getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.item, parent, false);
        Aluno aluno = alunos.get(position);
        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        nome.setText(aluno.getNome());
        ImageView foto = (ImageView) view.findViewById(R.id.item_foto);
        Bitmap bmFoto;

        if(aluno.getCaminhoFoto() != null) {
            bmFoto = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
            bmFoto = Bitmap.createScaledBitmap(bmFoto, 100, 100, true);
        } else {
            bmFoto = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
        }

        foto.setImageBitmap(bmFoto);

        return view;
    }

}
