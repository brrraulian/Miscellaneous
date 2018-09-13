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
public class ListaNos {

    List<No> listaNos;
    No verg1;
    No verg2;
    No verg3;
    No verg4;    
    No verg5;
    No verg6;
    No verg7;
    No verg8;
    No verg9;
    No verg10;

    No v_ben_port1;    
    No dr_j_morais;    
    No paraiso;    
    No dr_ed_amaro;        
    No c_dias;

    No apen1;
    No apen2;
    No apen3;
    No apen4;
    No apen5;
    No apen6;
    No apen7;

    public ListaNos() {
        listaNos = new ArrayList<No>();
        verg1 = new No("verg1", 645, 35, "no");
        listaNos.add(verg1);
        verg2 = new No("verg2", 642, 95, "no");
        listaNos.add(verg2);
        verg3 = new No("verg3", 648, 190, "no");
        listaNos.add(verg3);        
        verg4 = new No("verg4", 652, 285, "no");
        listaNos.add(verg4);        
        verg5 = new No("verg5", 636, 375, "no");
        listaNos.add(verg5);        
        verg6 = new No("verg6", 620, 450, "no");
        listaNos.add(verg6);        
        verg7 = new No("verg7", 605, 505, "no");
        listaNos.add(verg7);
        verg8 = new No("verg8", 605, 560, "no");
        listaNos.add(verg8);        
        verg9 = new No("verg9", 600, 610, "no");
        listaNos.add(verg9);
        verg10 = new No("verg10", 590, 665, "no");
        listaNos.add(verg10);

        v_ben_port1 = new No("v_ben_port1", 575, 90, "no");
        listaNos.add(v_ben_port1);                
        dr_j_morais = new No("dr_j_morais", 673, 284, "no");
        listaNos.add(dr_j_morais);        
        paraiso = new No("paraiso", 652, 461, "no");
        listaNos.add(paraiso);                
        dr_ed_amaro = new No("dr_ed_amaro", 640, 561, "no");
        listaNos.add(dr_ed_amaro);        
        c_dias = new No("c_dias", 630, 666, "no");
        listaNos.add(c_dias);

        apen1 = new No("apen1", 695, 283, "no");
        listaNos.add(apen1);
        apen2 = new No("apen2", 692, 378, "no");
        listaNos.add(apen2);
        apen3 = new No("apen3", 685, 473, "no");
        listaNos.add(apen3);
        apen4 = new No("apen4", 680, 518, "no");
        listaNos.add(apen4);
        apen5 = new No("apen5", 675, 563, "no");
        listaNos.add(apen5);
        apen6 = new No("apen6", 673, 615, "no");
        listaNos.add(apen6);
        apen7 = new No("apen7", 670, 668, "no");
        listaNos.add(apen7);

        verg1.setAdj(new No[]{verg2});
        verg2.setAdj(new No[]{verg1, verg3, v_ben_port1});
        verg3.setAdj(new No[]{verg2, verg4});
        verg4.setAdj(new No[]{verg3, verg5, dr_j_morais});                
        verg5.setAdj(new No[]{verg4, verg6});        
        verg6.setAdj(new No[]{verg5, verg7, paraiso});
        verg7.setAdj(new No[]{verg6, verg8});
        verg8.setAdj(new No[]{verg7, verg9, dr_ed_amaro});
        verg9.setAdj(new No[]{verg8, verg10});
        verg10.setAdj(new No[]{verg9, c_dias});

        v_ben_port1.setAdj(new No[]{verg2});        
        dr_j_morais.setAdj(new No[]{verg4, apen1});
        paraiso.setAdj(new No[]{verg6, apen3});        
        dr_ed_amaro.setAdj(new No[]{verg8, apen5});        
        c_dias.setAdj(new No[]{verg10, apen7});
        
        apen1.setAdj(new No[]{apen2, dr_j_morais});
        apen2.setAdj(new No[]{apen1, apen3});
        apen3.setAdj(new No[]{apen2, apen4, paraiso});
        apen4.setAdj(new No[]{apen3, apen5,});
        apen5.setAdj(new No[]{apen4, apen6, dr_ed_amaro});
        apen6.setAdj(new No[]{apen5, apen7});
        apen7.setAdj(new No[]{apen6, c_dias});
    }

}
