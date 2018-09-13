/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordetrafego;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arks1
 */
public class BestFirst {

    public List<No> pegaRotaMin(No noInicial, No noFinal) {

        List<No> listaNos = new ArrayList<No>();
        pegaMinF(noInicial, noFinal, listaNos);

        return listaNos;
    }

    public void pegaMinF(No noInicial, No noFinal, List<No> listaNos) {

        if (noInicial.x - 5 != noFinal.x || noInicial.y - 20 != noFinal.y) {

            float menorF = Float.MAX_VALUE;
            float F = 0;
            int j = 0;

            for (int i = 0; i < noInicial.adj.length; i++) {
                if (noInicial.adj[i] != null) {
                    float G_x = Math.abs(noInicial.adj[i].x - noInicial.x);
                    float G_y = Math.abs(noInicial.adj[i].y - noInicial.y);

                    float distG = (float) Math.sqrt(G_x * G_x + G_y * G_y);

                    float H_x = Math.abs(noFinal.x - noInicial.adj[i].x);
                    float H_y = Math.abs(noFinal.y - noInicial.adj[i].y);

                    float distH = (float) Math.sqrt(H_x * H_x + H_y * H_y);

                    F = distG + distH;

                    if (F < menorF) {
                        menorF = F;
                        j = i;
                    }
                }
            }

            listaNos.add(noInicial.adj[j]);
            pegaMinF(noInicial.adj[j], noFinal, listaNos);
        }
    }

}
