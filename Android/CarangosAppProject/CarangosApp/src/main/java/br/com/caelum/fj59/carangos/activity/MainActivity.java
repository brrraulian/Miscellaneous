package br.com.caelum.fj59.carangos.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.adapter.PublicacaoAdapter;
import br.com.caelum.fj59.carangos.app.CarangosApplicantion;
import br.com.caelum.fj59.carangos.evento.EventoPublicacoesRecebidas;
import br.com.caelum.fj59.carangos.fragments.ListaDePublicacoesFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesDelegate;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesTask;

public class MainActivity extends ActionBarActivity implements BuscaMaisPublicacoesDelegate {
    private ListView listView;
    private List<Publicacao> publicacoes;
    private PublicacaoAdapter adapter;
    private EstadoMainActivity estado;
    private static final String ESTADO_ATUAL = "ESTADO_ATUAL";

    private EventoPublicacoesRecebidas evento;



    @Override
    protected void onResume() {
        super.onResume();
        MyLog.i("EXECUTANDO ESTADO: " + this.estado);
        this.estado.executa(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyLog.i("SALVANDO ESTADO!");

        outState.putSerializable(ESTADO_ATUAL,this.estado);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MyLog.i("RESTAURANDO ESTADO!");

        this.estado = (EstadoMainActivity) savedInstanceState.getSerializable(ESTADO_ATUAL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.estado = EstadoMainActivity.INICIO;

        this.evento = EventoPublicacoesRecebidas.registraObservador(this);
     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.evento.desregistra(getCarangosApplication());
    }

    public void buscaPublicacoes() {
        new BuscaMaisPublicacoesTask(getCarangosApplication()).execute();

     }



    public void alteraEstadoEExecuta(EstadoMainActivity estado){
        this.estado = estado;
        this.estado.executa(this);
    }




    @Override
    public void lidaComRetorno(List<Publicacao> resultado) {
        CarangosApplicantion applicantion = (CarangosApplicantion) getApplication();
        List<Publicacao> publicacoes = applicantion.getPublicacoes();

        publicacoes.clear();
        publicacoes.addAll(resultado);

        this.estado = EstadoMainActivity.PRIMEIRAS_PUBLICACOES_RECEBIDAS;
        this.estado.executa(this);
    }

    @Override
    public void lidaComErro(Exception e) {

    }

    @Override
    public CarangosApplicantion getCarangosApplication() {

        return (CarangosApplicantion) getApplication();
    }


}
