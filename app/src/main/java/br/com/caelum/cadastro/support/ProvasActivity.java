package br.com.caelum.cadastro.support;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Prova;
import fragment.DetalhesProvaFragment;
import fragment.ListaProvasFragment;

/**
 * Created by android6040 on 04/06/16.
 */
public class ProvasActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(isTablet()){
            transaction
                    .replace(R.id.provas_lista, new ListaProvasFragment())
                    .replace(R.id.detalhe_prova_topicos, new DetalhesProvaFragment());
        }
        else{
            transaction.replace(R.id.provas_view, new ListaProvasFragment());
        }
        transaction.commit();
    }

    private boolean isTablet(){
        return getResources().getBoolean(R.bool.isTablet);
    }

    public void selecionaProva(Prova prova){
        FragmentManager manager = getSupportFragmentManager();

        if(isTablet()){
            DetalhesProvaFragment detalhesProva = (DetalhesProvaFragment) manager.findFragmentById(R.id.provas_lista);
            detalhesProva.populaCamposComDados(prova);
        }
        else {
            Bundle argumentos = new Bundle();
            argumentos.putSerializable("prova", prova);

            DetalhesProvaFragment detalhesProva = new DetalhesProvaFragment();
            detalhesProva.setArguments(argumentos);

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.provas_view, detalhesProva);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
