package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.app.CarangosApplicantion;
import br.com.caelum.fj59.carangos.converter.PublicacaoConverter;
import br.com.caelum.fj59.carangos.evento.EventoPublicacoesRecebidas;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.webservice.Pagina;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by erich on 7/16/13.
 */
public class BuscaMaisPublicacoesTask extends AsyncTask<Pagina, Void, List<Publicacao>> {

    private CarangosApplicantion applicantion;

    private BuscaMaisPublicacoesDelegate delegate;
    private Exception erro;


    public BuscaMaisPublicacoesTask(CarangosApplicantion applicantion) {
        this.applicantion = applicantion;
        applicantion.registra(this);
    }

    @Override
    protected List<Publicacao> doInBackground(Pagina... paginas) {
        try {
            Pagina paginaParaBuscar = paginas.length > 1? paginas[0] : new Pagina();

            String jsonDeResposta = new WebClient("post/list?" + paginaParaBuscar, applicantion).get();

            List<Publicacao> publicacoesRecebidas = new PublicacaoConverter().converte(jsonDeResposta);

            return publicacoesRecebidas;
        } catch (Exception e) {
            this.erro = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Publicacao> retorno) {
        MyLog.i("RETORNO OBTIDO!" + retorno);

        if (retorno!=null) {
            EventoPublicacoesRecebidas.notifica(this.applicantion, (Serializable) retorno, true);
        } else {
            //Toast.makeText(this.delegate, "Erro na busca dos dados", Toast.LENGTH_SHORT).show();
            EventoPublicacoesRecebidas.notifica(this.applicantion, erro, false);
        }
        this.applicantion.desregistra(this);
    }
}
