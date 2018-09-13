package br.com.caelum.fj59.carangos.tasks;

import android.app.Application;
import android.os.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by android6232 on 22/07/16.
 */
public class BuscaLeiloesTask extends TimerTask {
    private CustomHandler handler;
    private Calendar horarioUltimaBusca;
    Application app;

    public BuscaLeiloesTask(CustomHandler handler, Calendar horarioUltimaBusca, Application app) {
        this.horarioUltimaBusca = horarioUltimaBusca;
        this.handler = handler;
        this.app = app;
    }

    @Override
    public void run() {
        MyLog.i("Efetuando nova busca!");
        WebClient webClient = new WebClient("leilao/leilaoid54635/" +
        new SimpleDateFormat("ddMMyy-HHmmss").format(horarioUltimaBusca.getTime()), app);

        String json = webClient.get();

        MyLog.i("Lances Recebidos: " +json);

        Message message = handler.obtainMessage();
        message.obj = json;
        handler.sendMessage(message);

        horarioUltimaBusca = Calendar.getInstance();
    }

    public void executa() {
        Timer timer = new Timer();
        timer.schedule(this, 0, 30*1000);
    }
}
