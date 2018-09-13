package br.com.caelum.fj59.carangos.tasks;

import java.util.List;

import br.com.caelum.fj59.carangos.app.CarangosApplicantion;
import br.com.caelum.fj59.carangos.modelo.Publicacao;

/**
 * Created by android6232 on 18/07/16.
 */
public interface BuscaMaisPublicacoesDelegate {
    void lidaComRetorno(List<Publicacao> retorno);
    void lidaComErro(Exception e);
    CarangosApplicantion getCarangosApplication();
}
