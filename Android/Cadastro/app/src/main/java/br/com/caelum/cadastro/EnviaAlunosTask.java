package br.com.caelum.cadastro;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

/**
 * Created by android6231 on 13/07/16.
 */
public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {

    Context context;
    ProgressDialog pd;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = ProgressDialog.show(context, "Enviando dados...", "Carregando", true, true);
    }

    @Override
    protected String doInBackground(Void... parametro) {

        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.getLista();
        String json = new AlunoConverter().toJSON(alunos);
        dao.close();

        return new WebClient().doPost(json);
    }

    @Override
    protected void onPostExecute(String retorno) {
        super.onPostExecute(retorno);
        pd.dismiss();

        Toast.makeText(context, retorno, Toast.LENGTH_LONG).show();
    }

}
