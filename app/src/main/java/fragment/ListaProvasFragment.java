package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Prova;

/**
 * Created by android6040 on 04/06/16.
 */
public class ListaProvasFragment extends Fragment {

    private ListView listViewProvas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layoutProvas = inflater.inflate(R.layout.fragment_lista_provas, container, false);
        this.listViewProvas = (ListView) layoutProvas.findViewById(R.id.lista_provas_listview);

        //populando listview
        Prova prova1 = new Prova("20/06/2016", "Matematica");
        prova1.setTopicos(Arrays.asList("Algebra Linear","Calculo","Estatisticas"));

        Prova prova2 = new Prova("25/07/2016", "Portugues");
        prova2.setTopicos(Arrays.asList("Complemento nominal", "Oracoes Subordinadas", "Analise Sintatica"));

        List<Prova> provas = Arrays.asList(prova1,prova2);

        this.listViewProvas.setAdapter(new ArrayAdapter<Prova>(getActivity(), android.R.layout.simple_list_item_1,provas));

        //retornando prova com click
        this.listViewProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Prova selecionada = (Prova) adapter.getItemAtPosition(position);
                Toast.makeText(getActivity(),"Prova Selecionada: " + selecionada, Toast.LENGTH_LONG).show();
            }
        });

        return layoutProvas;
    }


}
