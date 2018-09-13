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
public class AEstrela {

    ListaArestas listaArestas;

    public AEstrela(ListaArestas listaArestas) {
        this.listaArestas = listaArestas;
    }

    public List<No> pegaRotaMin(No noInicial, No noFinal) {

        List<No> listaRota = new ArrayList<No>();
        List<No> listaAberta = new ArrayList<No>();
        List<No> listaFechada = new ArrayList<No>();
        pegaMenorRota(noInicial, noFinal, listaAberta, listaFechada, listaRota);

        return listaRota;
    }

    public void pegaMenorRota(No noInicial, No noFinal, List<No> listaAberta, List<No> listaFechada, List<No> listaRota) {

        No noAtual = noInicial;
        listaAberta.add(noAtual);

        while (noAtual.nome != noFinal.nome) {

            for (int i = 0; i < noAtual.adj.length; i++) {
                if (!listaFechada.contains(noAtual.adj[i])) {
                    if (!listaAberta.contains(noAtual.adj[i])) {
                        listaAberta.add(noAtual.adj[i]);                        
                        noAtual.adj[i].setPai(noAtual);
                        noAtual.adj[i].setG(calculaG(noAtual, noAtual.adj[i]));
                        noAtual.adj[i].setF(calculaF(noAtual, noAtual.adj[i], noFinal));
                    } else {
                        float novoG = calculaG(noAtual, noAtual.adj[i]);
                        if (noAtual.adj[i].getG() > novoG) {
                            noAtual.adj[i].setPai(noAtual);
                            noAtual.adj[i].setF(calculaF(noAtual, noAtual.adj[i], noFinal));
                        }
                    }
                }
            }

            noAtual = pegaMenorAdj(noInicial, noFinal, listaAberta);
            listaAberta.remove(noAtual);
            listaFechada.add(noAtual);
        }

        noAtual = listaFechada.get(listaFechada.size() - 1);
        listaRota.add(noAtual);
        while (noAtual.nome != noInicial.nome) {
            listaRota.add(0, noAtual.getPai());
            noAtual = noAtual.getPai();
        }

        listaRota.remove(0);
    }

    public float calculaG(No noAtual, No noAdj) {
        float G = 0;

        if (noAtual != null) {
            float G_x = Math.abs(noAtual.x - noAdj.x);
            float G_y = Math.abs(noAtual.y - noAdj.y);

            G += (float) (Math.sqrt(G_x * G_x + G_y * G_y)) / 65;
            G += calculaG(noAtual.getPai(), noAtual);
        }

        return G;
    }

    public float calculaH(No noAdj, No noFinal) {
        float H = 0;

        float H_x = Math.abs(noFinal.x - noAdj.x);
        float H_y = Math.abs(noFinal.y - noAdj.y);

        H = (float) (Math.sqrt(H_x * H_x + H_y * H_y)) / 65;

        for (int i = 0; i < listaArestas.listaArestas.size(); i++) {
            for (int j = 0; j < listaArestas.listaArestas.get(i).listaNos.length; j++) {
                if (listaArestas.listaArestas.get(i).listaNos[j].nome == noAdj.nome) {
                    H *= listaArestas.listaArestas.get(i).peso;
                }
            }
        }

        return H;
    }

    public float calculaF(No noAtual, No noAdj, No noFinal) {
        float F = 0;

        float distG = calculaG(noAtual, noAdj);
        float distH = calculaH(noAdj, noFinal);

        F = distG + distH;

        return F;
    }

    public No pegaMenorAdj(No noAtual, No noFinal, List<No> listaAberta) {

        float menorF = Float.MAX_VALUE;
        float F = 0;
        int j = 0;

        for (int i = 0; i < listaAberta.size(); i++) {
            if (listaAberta.get(i) != null) {
                float distG = calculaG(noAtual, listaAberta.get(i));
                float distH = calculaH(listaAberta.get(i), noFinal);

                F = distG + distH;

                if (F < menorF) {
                    menorF = F;
                    j = i;
                }
            }
        }

        return listaAberta.get(j);
    }

}
