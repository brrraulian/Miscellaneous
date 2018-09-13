/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordetrafego;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Arks1
 */
public class ListaArestas {

    List<Aresta> listaArestas;
    List<Aresta> listaArestas_v;
    List<Aresta> listaArestas_h;

    Aresta verg1_verg2;
    Aresta verg2_verg4;
    Aresta verg4_verg6;
    Aresta verg6_verg8;
    Aresta verg8_verg10;

    Aresta apen1_apen3;
    Aresta apen4_apen5;
    Aresta apen5_apen7;

    Aresta verg4_apen1;
    Aresta verg6_apen3;
    Aresta verg8_apen5;
    Aresta verg10_apen7;
    
    public ListaArestas(ListaNos listaNos) {
        listaArestas = new ArrayList<Aresta>();
        listaArestas_v = new ArrayList<Aresta>();
        listaArestas_h = new ArrayList<Aresta>();

        verg1_verg2 = new Aresta(new No[]{listaNos.verg1}, 1);
        listaArestas.add(verg1_verg2);
        listaArestas_v.add(verg1_verg2);
        verg2_verg4 = new Aresta(new No[]{listaNos.verg3}, 1);
        listaArestas.add(verg2_verg4);             
        listaArestas_v.add(verg2_verg4);             
        verg4_verg6 = new Aresta(new No[]{listaNos.verg5}, 1);
        listaArestas.add(verg4_verg6);        
        listaArestas_v.add(verg4_verg6);        
        verg6_verg8 = new Aresta(new No[]{listaNos.verg7}, 1);
        listaArestas.add(verg6_verg8);        
        listaArestas_v.add(verg6_verg8);        
        verg8_verg10 = new Aresta(new No[]{listaNos.verg9}, 1);
        listaArestas.add(verg8_verg10);
        listaArestas_v.add(verg8_verg10);

        apen1_apen3 = new Aresta(new No[]{listaNos.apen2}, 1);
        listaArestas.add(apen1_apen3);
        listaArestas_v.add(apen1_apen3);
        apen4_apen5 = new Aresta(new No[]{listaNos.apen4}, 1);
        listaArestas.add(apen4_apen5);
        listaArestas_v.add(apen4_apen5);
        apen5_apen7 = new Aresta(new No[]{listaNos.apen6}, 1);
        listaArestas.add(apen5_apen7);
        listaArestas_v.add(apen5_apen7);

        verg4_apen1 = new Aresta(new No[]{listaNos.dr_j_morais}, 1);
        listaArestas.add(verg4_apen1);
        listaArestas_h.add(verg4_apen1);
        verg6_apen3 = new Aresta(new No[]{listaNos.paraiso}, 1);
        listaArestas.add(verg6_apen3);
        listaArestas_h.add(verg6_apen3);
        verg8_apen5 = new Aresta(new No[]{listaNos.dr_ed_amaro}, 1);
        listaArestas.add(verg8_apen5);
        listaArestas_h.add(verg8_apen5);
        verg10_apen7 = new Aresta(new No[]{listaNos.c_dias}, 1);
        listaArestas.add(verg10_apen7);
        listaArestas_h.add(verg10_apen7);
    }
}
