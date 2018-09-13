/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordetrafego;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Arks1
 */
public class No {

    JLabel lNo;
    String nome;
    int tamanho;
    float x;
    float y;
    No[] adj;
    No pai;
    float g;
    float f;
    boolean fechado;

    public No() {
        lNo = new JLabel();
    }

    public void atualizaNoInicialFinal(String nome, float x, float y, String tipo) {
        ImageIcon iconNo;

        this.nome = nome;

        if (tipo == "carro") {
            iconNo = new ImageIcon(getClass().getResource("../imagens/car_32.png"));
            tamanho = 32;
            this.x = x - 10;
            this.y = y - 10;
        } else {
            iconNo = new ImageIcon(getClass().getResource("../imagens/flag_32.png"));
            tamanho = 32;
            this.x = x - 5;
            this.y = y - 20;
        }

        lNo.setIcon(iconNo);
        lNo.setBounds((int) this.x, (int) this.y, tamanho, tamanho);
    }

    public No(String nome, float x, float y, String tipo) {

        ImageIcon iconNo;
        this.nome = nome;

        iconNo = new ImageIcon(getClass().getResource("../imagens/ponto_green_16.png"));
        tamanho = 16;
        this.x = x;
        this.y = y;

        lNo = new JLabel(iconNo);
        lNo.setBounds((int) this.x, (int) this.y, tamanho, tamanho);
    }

    public void atualizaNo(String nome, float x, float y) {
        this.nome = nome;
        this.x = x;
        this.y = y;
        lNo.setBounds((int) x, (int) y, tamanho, tamanho);
    }

    public void setAdj(No[] adj) {
        this.adj = adj;
    }

    public void setNoToNoInicial(No no) {
        this.nome = no.nome;
        this.x = no.x;
        this.y = no.y;
        lNo.setBounds((int) no.x, (int) no.y, no.tamanho, no.tamanho);
        this.adj = no.adj;
    }
    
    public No getPai() { return pai; }    
    public void setPai(No pai) { this.pai = pai; }
    
    public float getG() { return g; }    
    public void setG(float g) { this.g = g; }
        
    public float getF() { return f; }    
    public void setF(float f) { this.f = f; }
    
    public boolean getFechado() { return fechado; }    
    public void setFechado(boolean fechado) { this.fechado = fechado; }
}
