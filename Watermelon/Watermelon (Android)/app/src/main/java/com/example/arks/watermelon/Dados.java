package com.example.arks.watermelon;

import android.graphics.drawable.Drawable;

/**
 * Created by ARKS on 01/07/2016.
 */
public class Dados {
    String nome;
    String idade;
    Drawable picture_url;

    public Dados() {}
    public Dados(String nome, String idade, Drawable picture_url) {
        this.nome = nome;
        this.idade = idade;
        this.picture_url = picture_url;
    }

    public String getNome() { return nome; }
    public String getIdade() { return idade; }
    public Drawable getPictureUrl() { return picture_url; }

    public void setNome(String nome) { this.nome = nome; }
    public void setIdade(String idade) { this.idade = idade; }
    public void setPictureUrl(Drawable picture_url) { this.picture_url = picture_url; }
}
