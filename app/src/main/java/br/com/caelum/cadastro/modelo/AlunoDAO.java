package br.com.caelum.cadastro.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android6040 on 14/05/16.
 */
public class AlunoDAO extends SQLiteOpenHelper{

    private static final int VERSAO = 4;
    private static final String TABEL = "Alunos";
    private static final String DATABASE = "CadastroCaelum";

    public AlunoDAO(Context context){
        super(context,DATABASE, null,VERSAO);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String ddl = "CREATE TABLE " + TABEL
                + " (id INTEGER PRIMARY KEY, "
                + " nome TEXT NOT NULL, "
                + " telefone TEXT, "
                + " endereco TEXT, "
                + " site TEXT, "
                + " nota REAL);";

        database.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "ALTER TABLE " + TABEL + " ADD COLUMN caminhoFoto TEXT;";
        db.execSQL(sql);
    }

//    inserindo alunos
    public void insere(Aluno aluno){
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoFoto());

        getWritableDatabase().insert(TABEL, null, values);
    }

    public List<Aluno> getLista(){

        List<Aluno> alunos = new ArrayList<Aluno>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM " + TABEL + ";", null);

        while (c.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            alunos.add(aluno);
        }
        c.close();
        return alunos;
    }

    public void deletarAluno (Aluno aluno){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Alunos", "id=?", new String[]{aluno.getId().toString()});
    }

    public void altera (Aluno aluno){
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoFoto());

        SQLiteDatabase db = getWritableDatabase();
        String[] idAlterado = {aluno.getId().toString()};
        db.update(TABEL,values,"id=?",idAlterado);
    }

    public boolean isAluno (String telefone){
        String[] params = {telefone};
        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT telefone FROM " + TABEL + " WHERE telefone = ?", params);
        int total = rawQuery.getCount();
        rawQuery.close();

        return total > 0;
    }
}
