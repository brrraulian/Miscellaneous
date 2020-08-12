package br.com.caelum.cadastro;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by android6231 on 13/07/16.
 */
public class ListaProvasFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);
        ListView listaProvas = (ListView) view.findViewById(R.id.lista_provas);
        List<Prova> provas = new ArrayList<>();
        Prova p1 = new Prova("Matemática", "01/01/2016");
        p1.setTopicos(Arrays.asList("Algebra", "Geometria"));
        provas.add(p1);
        Prova p2 = new Prova("Português", "02/02/2016");
        p1.setTopicos(Arrays.asList("Gramática", "Redação"));
        provas.add(p2);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, provas);
        listaProvas.setAdapter(adapter);

        listaProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova selecionada = (Prova) parent.getItemAtPosition(position);

                Toast.makeText(getActivity(), "Prova selecionada: " + selecionada, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
