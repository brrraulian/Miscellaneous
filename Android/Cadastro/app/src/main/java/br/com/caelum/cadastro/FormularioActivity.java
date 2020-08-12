package br.com.caelum.cadastro;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;
    private String caminhoFoto;
    private static final int REQUEST_FOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        this.helper = new FormularioHelper(this);

        Intent intent = getIntent();    // RECUPERA A INTENT PASSADA PELA ACTIVITY ANTERIOR
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno"); // RECUPERA O VALOR DA CHAVE "aluno" (OBJETO "Aluno") DE DENTRO DA INTENT
        if(aluno != null)   // SE CONSEGUIR RECUPERAR O OBJETO "Aluno"...
            helper.colocaNoFormulario(aluno);   // PREENCHE O FORMULÁRIO COM OS VALORES DO OBJETO RECUPERADO

        Button botaoFoto = helper.getBotaoFoto();   // RECUPERA O BOTÃO "botaoFoto" DO FormularioHelper
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";    // ATRIBUI O CAMINHO, NOME (TEMPO ATUAL) E EXTENSÃO DA FOTO
                Uri caminho = Uri.fromFile(new File(caminhoFoto));  // CONVERTE O CAMINHO DA FOTO PARA UMA Uri

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    // INSTANCIA A INTENT COM A ACAO DE CAPTURA DE IMAGEM (CAMERA)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, caminho);  // INFORMA A INTENT O CAMINHO, NOME E EXTENSÃO DA FOTO
                startActivityForResult(intent, REQUEST_FOTO);   // INFORMA UM CODIGO DE REQUISICÃO E INICIA A ACTIVITY (DA CAMERA) QUE RETORNARÁ UM RESULTADO
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_formulario, menu);    // LIGA O MENU "menu_formulario.xml" COM O MENU DA ACTIVITY "FormularioActivity"

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_formulario_ok) {   // VERIFICA QUAL O ID DO ITEM DO MENU FOI SELECIONADO

            if(helper.temNome()){   // VALIDA O CAMPO "Nome"

                Aluno aluno = helper.pegaAlunoDoFormulario();   // POPULA O OBJETO "Aluno" COM OS DADOS PREENCHIDOS NO FORMULÁRIO

                AlunoDAO dao = new AlunoDAO(this);

                if(aluno.getId() == null)
                    dao.insere(aluno);  // INSERE OS DADOS DA INSTANCIA "Aluno" NO SQLite
                else
                    dao.altera(aluno);

                dao.close();
                finish();
            }
            else
                helper.mostraErro();

            return false;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    // RETORNO DA ACTIVITY
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_FOTO) {   // VERIFICA SE O CÓDIGO DE REQUISIÇÃO É IGUAL AO CÓDIGO PASSADO NA INICIALIZAÇÃO DA ACTIVITY
            if(resultCode == RESULT_OK) {   // VERIFICA SE O CÓDIGO DE RESULTADO É IGUAL A CONSTANTE QUE REPRESENTA O PROCESSO SEM NENHUM ERRO
                helper.carregaImagem(caminhoFoto);
            } else {
                caminhoFoto = null;
            }
        }
    }
}
