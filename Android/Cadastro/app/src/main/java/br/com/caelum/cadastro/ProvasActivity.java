package br.com.caelum.cadastro;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by android6231 on 13/07/16.
 */
public class ProvasActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.provas_view, new ListaProvasFragment());
        transaction.commit();
    }
}
