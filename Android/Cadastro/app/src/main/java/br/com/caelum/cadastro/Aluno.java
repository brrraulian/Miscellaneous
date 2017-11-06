package br.com.caelum.cadastro;

import java.io.Serializable;

/**
 * Created by android6231 on 06/07/16.
 */
public class Aluno implements Serializable {

    private Long id;
    private String nome;
    private String tel;
    private String end;
    private String site;
    private Double nota;
    private String caminhoFoto;


    public Long getId() {        return id;    }

    public void setId(Long id) {        this.id = id;    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTel() {        return tel;    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.nome;
    }

    public String getCaminhoFoto() {        return caminhoFoto;    }

    public void setCaminhoFoto(String caminhoFoto) {        this.caminhoFoto = caminhoFoto;    }
}
