/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordetrafego;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author Arks1
 */
public class Aresta {

    No[] listaNos;
    float peso;

    public Aresta(No[] listaNos, float peso) {
        this.listaNos = listaNos;
        this.peso = peso;
    }
    
    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
    
    public void setSemaforo(float peso) {
        for (int i = 0; i < this.listaNos.length; i++) {
            if (peso == 1) {
                this.listaNos[i].lNo.setIcon(new ImageIcon(getClass().getResource("../imagens/ponto_green_16.png")));
            } else {
                this.listaNos[i].lNo.setIcon(new ImageIcon(getClass().getResource("../imagens/ponto_red_16.png")));
            }
        }
    }

}
