package com.hikem.arks.arks;

/**
 * Created by koki on 17/01/2015.
 */
public class autenticarReceive {

    private String mensagem, id_seguranca, nome;

    public autenticarReceive(){}

    public autenticarReceive(String mensagem, String id_seguranca, String nome){
        this.mensagem = mensagem;
        this.id_seguranca = id_seguranca;
        this.nome = nome;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getId_seguranca() {
        return id_seguranca;
    }

    public void setId_seguranca(String id_seguranca) {
        this.id_seguranca = id_seguranca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
