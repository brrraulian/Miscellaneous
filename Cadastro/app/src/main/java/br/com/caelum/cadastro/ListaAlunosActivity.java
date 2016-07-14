package br.com.caelum.cadastro;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;
    Aluno aluno;
    private static final int REQUEST_LIGACAO = 1;
    private static final int REQUEST_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        // CLIQUE NO ITEM DA LISTA
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ListaAlunosActivity.this, "Item clicado na posição: " + position, Toast.LENGTH_LONG).show();
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intent.putExtra("aluno", aluno);
                startActivity(intent);
            }
        });

        // CLIQUE LONGO NO ITEM DA LISTA (SUBSTITUIDO PELO EVENTO "onMenuItemClick" DA CLASSE ANONIMA "MenuItem.OnMenuItemClickListener" DO CONTEXT MENU)
        /*
        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String nomeAluno = parent.getAdapter().getItem(position).toString();
                Toast.makeText(ListaAlunosActivity.this, "Aluno selecionado: " + nomeAluno, Toast.LENGTH_LONG).show();

                return true;
            }
        });
        */

        // BOTAO ADICIONAR
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listaAlunos);    // REGISTRA O CONTEXT MENU NO LISTVIEW "listaAlunos"


        String permissaoReceberSMS = Manifest.permission.RECEIVE_SMS;
        ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{permissaoReceberSMS}, REQUEST_SMS);
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregaLista(); // ATUALIZA A LISTA
    }

    public void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.getLista();

        //ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        ListaAlunosAdapter adapter = new ListaAlunosAdapter(this, alunos);
        listaAlunos.setAdapter(adapter);
        dao.close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        aluno = (Aluno) ((ListView) v).getItemAtPosition(info.position);

        MenuItem sms = menu.add("Enviar SMS");
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:" + aluno.getTel()));
        intentSms.putExtra("sms_body", "Olá querido aluno");
        sms.setIntent(intentSms);

        MenuItem mapa = menu.add("Achar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?z=14&q=" + Uri.encode(aluno.getEnd())));
        mapa.setIntent(intentMapa);

        MenuItem site = menu.add("Site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        intentSite.setData(Uri.parse("http://" + aluno.getSite()));
        site.setIntent(intentSite);

        MenuItem ligar = menu.add("Ligar");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String permissaoLigar = Manifest.permission.CALL_PHONE;

                if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, permissaoLigar) == PackageManager.PERMISSION_GRANTED)
                    fazerLigacao();
                else
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[] { permissaoLigar }, REQUEST_LIGACAO);

                return false;
            }
        });


        MenuItem del = menu.add("Deletar");
        del.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // EXIBE CAIXA DE CONFIRMACAO DA EXCLUSAO
                new AlertDialog.Builder(ListaAlunosActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar")
                        .setMessage("Deseja mesmo deletar?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // DELETA O ITEM SELECIONADO
                                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                                dao.deletar(aluno);
                                dao.close();
                                carregaLista();
                            }
                        }).setNegativeButton("Não", null).show();

                return false;
            }
        });
    }

    public void fazerLigacao() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + aluno.getTel()));
            startActivity(intent);
        } catch (SecurityException ex) {
            String ret = ex.getMessage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_LIGACAO) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                fazerLigacao();
            else
                Toast.makeText(ListaAlunosActivity.this, "PERDEU PLAYBOY!", Toast.LENGTH_SHORT);
        } else if (requestCode == REQUEST_SMS) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                new SMSReceiver();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_lista, menu);    // LIGA O MENU "menu_formulario.xml" COM O MENU DA ACTIVITY "FormularioActivity"
        getMenuInflater().inflate(R.menu.menu_receber_provas, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_lista_enviar) {   // VERIFICA QUAL O ID DO ITEM DO MENU FOI SELECIONADO

            new EnviaAlunosTask(this).execute();

            return false;
        } else if(item.getItemId() == R.id.menu_receber_provas) {
            Intent intent = new Intent(this, ProvasActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
