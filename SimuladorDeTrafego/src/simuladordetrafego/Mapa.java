/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladordetrafego;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author koki
 */
public class Mapa extends JPanel {

    int tempo = 1;
    SimuladorDeTrafego simuladorDeTrafego;
    BufferedImage bfMapa;
    ImageIcon iconMapa;
    JLabel lMapa;

    No lCarro;
    No lDestino;

    int s_mapa = 0;
    int s = 0;
    int m = 0;
    int h = 0;

    boolean semaforoStatus = false;
    Timer timer_mapa;
    Timer timer_tempo;
    Timer timer_carro;
    Timer timer_rota;
    boolean timer_carro_ready;
    float distX = 0;
    float distY = 0;
    float distancia;
    float passoX = 0;
    float passoY = 0;
    int margem_ponto_chegadaX = 10;
    int margem_ponto_chegadaY = 10;
    int ajuste_ponto_chegadaY = 0;
    int ajuste_ponto = 0;

    ListaNos listaPontos;
    ListaArestas listaArestas;
    AEstrela aEstrela;

    List<No> listaNos = new ArrayList<No>();
    List<No> listaRota = new ArrayList<No>();
    float distanciaReal = 0;

    public Mapa(SimuladorDeTrafego simuladorDeTrafego) {
        this.simuladorDeTrafego = simuladorDeTrafego;
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(725, 725);
        //setLocationRelativeTo(null);
        setLocation(1, 1);
        setVisible(true);
        setLayout(null);

        listaPontos = new ListaNos();
        for (int i = 0; i < listaPontos.listaNos.size(); i++) {
            add(listaPontos.listaNos.get(i).lNo);
        }
        listaArestas = new ListaArestas(listaPontos);
        aEstrela = new AEstrela(listaArestas);

        lCarro = new No();
        add(lCarro.lNo);

        lDestino = new No();
        add(lDestino.lNo);

        putMapa();

//        timer_mapa = new Timer();
//        timer_mapa.schedule(new Mapa.TimerMapa(), 0, 1000);
    }

    public void putMapa() {
        try {
            bfMapa = ImageIO.read(new File("src/imagens/mapa.png"));
            iconMapa = new ImageIcon(bfMapa);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

//        lMapa = new JLabel(iconMapa);
        lMapa = new JLabel(iconMapa) {
            public void paintComponent(Graphics newG) {
                super.paintComponent(newG);

                BasicStroke stroke = new BasicStroke(10);
                AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);

                Graphics2D g2d = (Graphics2D) newG;
                g2d.setColor(Color.BLUE);
                g2d.setStroke(stroke);
                g2d.setComposite(alcom);

                for (int i = 0; i < listaRota.size(); i++) {
                    if (i == 0 || listaRota.size() == 1) {
                        g2d.drawLine((int) lCarro.x + 17, (int) lCarro.y + 20, (int) listaRota.get(i).x + 7, (int) listaRota.get(i).y + 7);
                    } else {
                        g2d.drawLine((int) listaRota.get(i - 1).x + 7, (int) listaRota.get(i - 1).y + 7, (int) listaRota.get(i).x + 7, (int) listaRota.get(i).y + 7);
                    }
                    repaint();
                }
            }
        };

        lMapa.setBounds(0, 0, 725, 725);
        add(lMapa);
    }

    class TimerMapa extends TimerTask {

        public void run() {
            s_mapa++;
            mudaPeso();
            mudaSemaforo(); // INCOMPLETO -----------------------------------------------------------------------------!
            simuladorDeTrafego.setErro("Mapa: " + s_mapa);
        }
    }

    private void mudaPeso() {
        if (semaforoStatus) {
            for (int i = 0; i < listaArestas.listaArestas_v.size(); i++) {
                listaArestas.listaArestas_v.get(i).setPeso(listaArestas.listaArestas_v.get(i).getPeso() + 1);
            }

            for (int i = 0; i < listaArestas.listaArestas_h.size(); i++) {
                listaArestas.listaArestas_h.get(i).setPeso(listaArestas.listaArestas_h.get(i).getPeso() - 1);
            }
        } else {
            for (int i = 0; i < listaArestas.listaArestas_v.size(); i++) {
                listaArestas.listaArestas_v.get(i).setPeso(listaArestas.listaArestas_v.get(i).getPeso() - 1);
            }

            for (int i = 0; i < listaArestas.listaArestas_h.size(); i++) {
                listaArestas.listaArestas_h.get(i).setPeso(listaArestas.listaArestas_h.get(i).getPeso() + 1);
            }
        }
    }

    private void mudaSemaforo() {
        if (s_mapa % 30 == 0) {
            if (semaforoStatus) {
                for (int i = 0; i < listaArestas.listaArestas_v.size(); i++) {
                    listaArestas.listaArestas_v.get(i).setPeso(1);
                    listaArestas.listaArestas_v.get(i).setSemaforo(1);
                    for (int j = 0; j < listaArestas.listaArestas_v.get(i).listaNos.length; j++) {
                        for (int k = 0; k < listaArestas.listaArestas_v.get(i).listaNos[j].adj.length; k++) {
                            listaArestas.listaArestas_v.get(i).listaNos[j].adj[k].fechado = true;
                        }
                    }
                }

                for (int i = 0; i < listaArestas.listaArestas_h.size(); i++) {
                    listaArestas.listaArestas_h.get(i).setPeso(15);
                    listaArestas.listaArestas_h.get(i).setSemaforo(15);
                    for (int j = 0; j < listaArestas.listaArestas_h.get(i).listaNos.length; j++) {
                        for (int k = 0; k < listaArestas.listaArestas_h.get(i).listaNos[j].adj.length; k++) {
                            listaArestas.listaArestas_h.get(i).listaNos[j].adj[k].fechado = false;
                        }
                    }
                }

                semaforoStatus = false;
            } else {
                for (int i = 0; i < listaArestas.listaArestas_v.size(); i++) {
                    listaArestas.listaArestas_v.get(i).setPeso(15);
                    listaArestas.listaArestas_v.get(i).setSemaforo(15);
                    for (int j = 0; j < listaArestas.listaArestas_v.get(i).listaNos.length; j++) {
                        for (int k = 0; k < listaArestas.listaArestas_v.get(i).listaNos[j].adj.length; k++) {
                            listaArestas.listaArestas_v.get(i).listaNos[j].adj[k].fechado = false;
                        }
                    }
                }

                for (int i = 0; i < listaArestas.listaArestas_h.size(); i++) {
                    listaArestas.listaArestas_h.get(i).setPeso(1);
                    listaArestas.listaArestas_h.get(i).setSemaforo(1);
                    for (int j = 0; j < listaArestas.listaArestas_h.get(i).listaNos.length; j++) {
                        for (int k = 0; k < listaArestas.listaArestas_h.get(i).listaNos[j].adj.length; k++) {
                            listaArestas.listaArestas_h.get(i).listaNos[j].adj[k].fechado = true;
                        }
                    }
                }

                semaforoStatus = true;
            }
        }
    }

    public void moverCarro() {

        if (lCarro.x == lDestino.x - 5 && lCarro.y == lDestino.y + 10) {
            simuladorDeTrafego.setErro("O ponto inicial não pode ser igual ao ponto final");
        } else {
            this.tempo = simuladorDeTrafego.tempo;
            No noInicial = lCarro;
            No noFinal = lDestino;

            for (int i = 0; i < listaPontos.listaNos.size(); i++) {
                if (listaPontos.listaNos.get(i).nome == lCarro.nome) {
                    noInicial = listaPontos.listaNos.get(i);
                }
                if (listaPontos.listaNos.get(i).nome == lDestino.nome) {
                    noFinal = listaPontos.listaNos.get(i);
                }
            }

            resetaNos();
            listaNos = aEstrela.pegaRotaMin(noInicial, noFinal);
            listaRota = aEstrela.pegaRotaMin(noInicial, noFinal);

            s_mapa = 0;
            simuladorDeTrafego.setTempo("00:00:00");
            s = 0;
            m = 0;
            h = 0;
            simuladorDeTrafego.setDistanciaInicial("0 mt");
            simuladorDeTrafego.setDistanciaReal("0 mt");
            distanciaReal = 0;

            float dist = Math.round(listaNos.get(listaNos.size() - 1).g * 10);
            if (dist >= 100) {
                simuladorDeTrafego.setDistanciaInicial(String.format("%.2f", (dist * 10) / 1000) + " km");
            } else {
                simuladorDeTrafego.setDistanciaInicial(Math.round(dist * 10) + " mt");
            }

            Wait wait = new Wait(listaNos);
            wait.start();

            timer_tempo = new Timer();
            timer_tempo.schedule(new Mapa.TimerTempo(), 0, 1000 / tempo);

            timer_mapa = new Timer();
            timer_mapa.schedule(new Mapa.TimerMapa(), 0, 1000 / tempo);
        }
    }

    class TimerTempo extends TimerTask {

        public void run() {

            s++;

            if (s == 60) {
                s = 0;
                m++;

                if (m == 60) {
                    m = 0;
                    h++;
                }
            }

            if (h < 10) {
                if (m < 10) {
                    if (s < 10) {
                        simuladorDeTrafego.setTempo("0" + h + ":0" + m + ":0" + s);
                    } else {
                        simuladorDeTrafego.setTempo("0" + h + ":0" + m + ":" + s);
                    }
                } else {
                    if (s < 10) {
                        simuladorDeTrafego.setTempo("0" + h + ":" + m + ":0" + s);
                    } else {
                        simuladorDeTrafego.setTempo("0" + h + ":" + m + ":" + s);
                    }
                }
            } else {
                if (m < 10) {
                    if (s < 10) {
                        simuladorDeTrafego.setTempo(h + ":0" + m + ":0" + s);
                    } else {
                        simuladorDeTrafego.setTempo(h + ":0" + m + ":" + s);
                    }
                } else {
                    if (s < 10) {
                        simuladorDeTrafego.setTempo(h + ":" + m + ":0" + s);
                    } else {
                        simuladorDeTrafego.setTempo(h + ":" + m + ":" + s);
                    }
                }
            }
        }
    }

    public class Wait extends Thread {

        private List<No> nos;

        public Wait(List<No> nos) {
            margem_ponto_chegadaX = 1;
            margem_ponto_chegadaY = 1;
            ajuste_ponto_chegadaY = 0;
            timer_carro_ready = true;
            this.nos = nos;
        }

        public void run() {
            int i = 0;
            while (lCarro.nome != lDestino.nome) {

                if (distanciaReal != 0) {
                    distanciaReal /= 10;
                }
                distanciaReal += this.nos.get(i).g;
                distanciaReal *= 10;
                if (distanciaReal >= 100) {
                    simuladorDeTrafego.setDistanciaReal(String.format("%.2f", (distanciaReal * 10) / 1000) + " km");
                } else {
                    simuladorDeTrafego.setDistanciaReal(Math.round(distanciaReal * 10) + " mt");
                }

                margem_ponto_chegadaX = 1;
                margem_ponto_chegadaY = 1;
                ajuste_ponto_chegadaY = 0;
                ajuste_ponto = 0;

                if ((this.nos.get(i).x - ajuste_ponto) == lDestino.x && (this.nos.get(i).y - ajuste_ponto) == lDestino.y) {
                    margem_ponto_chegadaX = 20;
                    margem_ponto_chegadaY = 40;
                    ajuste_ponto_chegadaY = 10;
                } else {
                    ajuste_ponto = 10;
                }

                distX = Math.abs((this.nos.get(i).x - ajuste_ponto) - lCarro.x);
                distY = Math.abs(((this.nos.get(i).y - ajuste_ponto) + ajuste_ponto_chegadaY) - lCarro.y);

                distancia = (float) Math.sqrt(distX * distX + distY * distY);

                passoX = distX / distancia;
                passoY = distY / distancia;

                timer_carro_ready = false;
                timer_carro = new Timer();
                timer_carro.schedule(new Mapa.TimerCarro(this.nos.get(i)), 0, 90 / tempo);

                while (!timer_carro_ready) {
                    try {
                        Thread.yield();
                    } catch (Exception ex) {
                        // Log the interruption somewhere.
                        System.out.println(ex.getMessage());
                    }
                }

                while (this.nos.get(i).fechado) {
                    try {
                        Thread.yield();
                    } catch (Exception ex) {
                        // Log the interruption somewhere.
                        System.out.println(ex.getMessage());
                    }
                }

                No noInicial = lCarro;
                No noFinal = lDestino;

                for (int j = 0; j < listaPontos.listaNos.size(); j++) {
                    if (listaPontos.listaNos.get(j).nome == lCarro.nome) {
                        noInicial = listaPontos.listaNos.get(j);
                    }
                    if (listaPontos.listaNos.get(j).nome == lDestino.nome) {
                        noFinal = listaPontos.listaNos.get(j);
                    }
                }

                if (noInicial.nome != noFinal.nome) {
                    resetaNos();
                    this.nos = aEstrela.pegaRotaMin(noInicial, noFinal);
                    listaRota = aEstrela.pegaRotaMin(noInicial, noFinal);
                }
            }

            simuladorDeTrafego.setButtomsEnable();
            timer_tempo.cancel();
            timer_mapa.cancel();
        }
    }

    class TimerCarro extends TimerTask {

        No no;

        public TimerCarro(No no) {
            this.no = no;
        }

        public void run() {
            if ((distX > margem_ponto_chegadaX) || (distY > margem_ponto_chegadaY)) {
                if ((no.x - ajuste_ponto) - lCarro.x > 0) {
                    lCarro.x += passoX;
                } else {
                    lCarro.x -= passoX;
                }

                if ((no.y - ajuste_ponto) - lCarro.y > 0) {
                    lCarro.y += passoY;
                } else {
                    lCarro.y -= passoY;
                }

                distX = Math.abs((no.x - ajuste_ponto) - lCarro.x);
                distY = Math.abs(((no.y - ajuste_ponto) + ajuste_ponto_chegadaY) - lCarro.y);

                lCarro.atualizaNo(no.nome, lCarro.x, lCarro.y);
            } else {
                timer_carro.cancel();
                timer_carro_ready = true;

                listaRota.remove(0);
            }
        }
    }

    private void resetaNos() {
        for (int i = 0; i < listaPontos.listaNos.size(); i++) {
            listaPontos.listaNos.get(i).setPai(null);
            listaPontos.listaNos.get(i).setG(0);
            listaPontos.listaNos.get(i).setF(0);
        }
    }

}
