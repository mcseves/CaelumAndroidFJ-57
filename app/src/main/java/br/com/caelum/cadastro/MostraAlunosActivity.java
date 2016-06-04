package br.com.caelum.cadastro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import fragment.MapaFragment;

/**
 * Created by android6040 on 04/06/16.
 */
public class MostraAlunosActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_aluno);

        MapaFragment mapaFragment = new MapaFragment();

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.mostra_alunos_mapa, mapaFragment);
        tx.commit();
    }
}
