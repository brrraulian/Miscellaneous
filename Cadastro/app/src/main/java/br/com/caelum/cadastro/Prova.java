package br.com.caelum.cadastro;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android6231 on 13/07/16.
 */
public class Prova implements Serializable {

    String materia;
    String data;
    List<String> topicos;
    String descricao;

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<String> topicos) {
        this.topicos = topicos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Prova(String materia, String data) {
        this.materia = materia;
        this.data = data;
    }

    @Override
    public String toString() {
        return materia;
    }
}



