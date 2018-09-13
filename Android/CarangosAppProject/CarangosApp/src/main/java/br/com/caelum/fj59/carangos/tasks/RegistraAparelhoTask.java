package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import br.com.caelum.fj59.carangos.app.CarangosApplicantion;
import br.com.caelum.fj59.carangos.gcm.Constantes;
import br.com.caelum.fj59.carangos.gcm.InformacoesDoUsuario;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by android6232 on 21/07/16.
 */
public class RegistraAparelhoTask extends AsyncTask<Void, Void, String> {
    private CarangosApplicantion app;

    public RegistraAparelhoTask(CarangosApplicantion app) {
        this.app = app;
    }

    @Override
    protected String doInBackground(Void... params) {
        String registrationId = null;

        try {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this.app);
            registrationId = gcm.register(Constantes.GCM_SERVER_ID);

            MyLog.i("Aparelho registrado com o ID: " + registrationId);

            String email = InformacoesDoUsuario.getEmail(this.app);

            String url = "device/register/"+email+"/"+registrationId;
            WebClient client = new WebClient(url, app);
            client.post();

        }catch (Exception e) {
            MyLog.e("Problema no registro do aparelho no server!" + e.getMessage());
        }
        return registrationId;
    }

    @Override
    protected void onPostExecute(String s) {
        app.lidaComRespostaDoRegistroNoServidor(s);
    }
}
