package br.com.caelum.cadastro;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by android6231 on 12/07/16.
 */
public class WebClient {

    public String doPost(String json) {

        String resposta;

        try {
            URL url = new URL("https://www.caelum.com.br/mobile");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream saida = new PrintStream(connection.getOutputStream());
            saida.println(json);
            connection.connect();

            resposta = new Scanner(connection.getInputStream()).next();
            
        } catch (IOException ex) {
            resposta = ex.getMessage();
        }

        return resposta;
    }
}
