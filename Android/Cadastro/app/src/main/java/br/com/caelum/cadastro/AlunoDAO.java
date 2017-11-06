package br.com.caelum.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android6231 on 07/07/16.
 */
public class AlunoDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 2;
    private static final String TABELA = "Aluno";
    private static final String DATABASE = "CadastroDeAluno";

    // CONFIGURA O BANCO DE DADOS
    public AlunoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "(ID INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, tel TEXT, end TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "ALTER TABLE " + TABELA + " ADD COLUMN caminhoFoto TEXT;";
        db.execSQL(sql);
    }

    public void insere(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("tel", aluno.getTel());
        values.put("end", aluno.getEnd());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoFoto());
        getWritableDatabase().insert(TABELA, null, values);
    }

    public List<Aluno> getLista() {
        List<Aluno> alunos = new ArrayList<>();

        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA + ";", null);

        while(c.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("ID")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setTel(c.getString(c.getColumnIndex("tel")));
            aluno.setEnd(c.getString(c.getColumnIndex("end")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }

        c.close();

        return alunos;
    }

    public void deletar(Aluno aluno) {
        String[] args = { aluno.getId().toString() };
        getWritableDatabase().delete(TABELA,"ID = ?", args);
    }

    public void altera(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("tel", aluno.getTel());
        values.put("end", aluno.getEnd());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoFoto());
        getWritableDatabase().update(TABELA, values, "ID = ?", new String[] { aluno.getId().toString() });
    }

    public boolean isAluno(String telefone) {
        boolean ret = false;

        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA + " WHERE tel = ?;", new String[] { telefone });

        if(c.moveToNext()) {
            ret = true;
        }

        c.close();

        return ret;
    }
}
