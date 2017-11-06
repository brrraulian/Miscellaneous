package br.com.caelum.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

/**
 * Created by android6231 on 06/07/16.
 */
public class FormularioHelper {

    private EditText nome;
    private EditText tel;
    private EditText end;
    private EditText site;
    private RatingBar nota;
    private Aluno aluno;
    private Button botaoFoto;
    private ImageView ivFoto;

    // PEGA A REFERENCIA DE TODAS AS VIEWS DO FORMULARIO
    public FormularioHelper(FormularioActivity activity) {
        this.nome = (EditText) activity.findViewById(R.id.nome);
        this.tel = (EditText) activity.findViewById(R.id.tel);
        this.end = (EditText) activity.findViewById(R.id.end);
        this.site = (EditText) activity.findViewById(R.id.site);
        this.nota = (RatingBar) activity.findViewById(R.id.bar);
        this.aluno = new Aluno();
        this.botaoFoto = (Button) activity.findViewById(R.id.botao_foto);
        this.ivFoto = (ImageView) activity.findViewById(R.id.ivFoto);
    }

    // RETORNA O OBJETO "Aluno" COM OS DADOS PREENCHIDOS DO FORMULARIO
    public Aluno pegaAlunoDoFormulario() {
        aluno.setNome(nome.getText().toString());
        aluno.setTel(tel.getText().toString());
        aluno.setEnd(end.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));

        if(ivFoto.getTag() != null)
            aluno.setCaminhoFoto(ivFoto.getTag().toString());

        return aluno;
    }

    public boolean temNome() {        return !nome.getText().toString().isEmpty();    }   // VERIFICA SE O CAMPO NOME DO FORMULÁRIO NÃO ESTÁ VAZIO

    public void mostraErro() {
        nome.setError("Informe o nome");
    }

    public void colocaNoFormulario(Aluno aluno) {
        this.aluno.setId(aluno.getId());
        nome.setText(aluno.getNome());
        tel.setText(aluno.getTel());
        end.setText(aluno.getEnd());
        site.setText(aluno.getSite());
        nota.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getCaminhoFoto());
    }

    public Button getBotaoFoto() {        return botaoFoto;    }    // RETORNA O BOTAO "botaoFoto" DO FORMULARIO

    public void carregaImagem(String caminhoFoto) {
        Bitmap bmFoto = BitmapFactory.decodeFile(caminhoFoto);
        //Bitmap fotoReduzida = Bitmap.createScaledBitmap(bmFoto, ivFoto.getWidth(), 300, true);
        ivFoto.setTag(caminhoFoto);
        ivFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        ivFoto.setImageBitmap(bmFoto);
    }

}
