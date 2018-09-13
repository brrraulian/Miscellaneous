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
public class AEstrela_Quadro {

    public final int SEM_ROTA = -1, NAO_ACHADO = 0, ACHADO = 1;
    private int passoMax = 1000;

    public List<No> pegaRotaMin(No no_inicio, No no_fim) {

        List<No> listaNos = new ArrayList<No>();
        pegaMenorRota(no_inicio, no_fim, listaNos);

        return listaNos;
    }

    public void pegaMenorRota(No noInicial, No noFinal, List<No> listaNos) {

        List<No> listaNosPossiveis = new ArrayList<No>();
        listaNosPossiveis.add(noInicial);
        int estado = NAO_ACHADO;
        int passo = 0;
        boolean achado = false;
        boolean tem_rota = false;

        while (estado == NAO_ACHADO && passo < passoMax) {
            passo++;
            for (int j = 0; j < listaNosPossiveis.size(); j++) {
                No noAtual = listaNosPossiveis.get(j);
                for (int i = 0; i < noAtual.adj.length; i++) {
                    if (noAtual.adj[i] != null) {
//                        if (noAtual.adj[i].getNome() == noFinal.getNome()) {
//                            achado = true;
//                        }

                        if (noAtual.adj[i] != noInicial) {
                            float g = pegaG(noAtual.adj[i], noAtual);
                            float h = pegaH(noAtual.adj[i], noFinal);
//                            noAtual.adj[i].setF(g + h);
                        }

                        if (!listaNosPossiveis.contains(noAtual.adj[i])) {
                            listaNosPossiveis.add(noAtual.adj[i]);
                            tem_rota = true;
                        }
                    }
                }

                if (achado) {
                    estado = ACHADO;
                }
            }
            if (!tem_rota) {
                estado = SEM_ROTA;
            }
        }

        if (estado == ACHADO) {
//            while (pegaMenorAdjacente(noInicial).getNome() != noFinal.getNome()) {
//                listaNos.add(pegaMenorAdjacente(noInicial));
//            }
        } else {
//            mapa.principal.lbl_msg.setText("Não é possível traçar a rota");
        }
    }

    public float pegaG(No noAtual, No noAnterior) {
        float G_x = Math.abs(noAnterior.x - noAtual.x);
        float G_y = Math.abs(noAnterior.y - noAtual.y);

        float distG = (float) Math.sqrt(G_x * G_x + G_y * G_y);

        return distG;
    }

    public float pegaH(No noAtual, No noFim) {
        float H_x = Math.abs(noFim.x - noAtual.x);
        float H_y = Math.abs(noFim.y - noAtual.y);

        float distH = (float) Math.sqrt(H_x * H_x + H_y * H_y);

        return distH;
    }

    public No pegaMenorAdjacente(No noAtual) {
        No menorAdjacente = noAtual.adj[0];
        float F = Float.MAX_VALUE;

        for (int i = 0; i < noAtual.adj.length; i++) {
            if (noAtual.adj[i] != null) {
//                if (noAtual.adj[i].getF() < F && noAtual.adj[i].getF() >= 0) {
//                    menorAdjacente = noAtual.adj[i];
//                    F = noAtual.adj[i].getF();
//                }
            }
        }
        return menorAdjacente;
    }

}
