package fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.modelo.AlunoDAO;
import br.com.caelum.cadastro.util.Localizador;

/**
 * Created by android6040 on 04/06/16.
 */
public class MapaFragment extends SupportMapFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap){
                Localizador localizador = new Localizador(getActivity());
                LatLng local = localizador.getCoordenada("Rua Vergueiro 3185 Vila Mariana");
                centralizaNo(local, googleMap);


                AlunoDAO dao = new AlunoDAO(getActivity());
                List<Aluno> alunos = dao.getLista();

                for (Aluno aluno : alunos){
                    LatLng coordenada = localizador.getCoordenada(aluno.getEndereco());

                    if(coordenada!=null){
                        MarkerOptions marcador = new MarkerOptions()
                                .position(coordenada)
                                .title(aluno.getNome())
                                .snippet(aluno.getEndereco());
                        googleMap.addMarker(marcador);

                    }
                }
            }
        }
        );

    }

    private void centralizaNo(LatLng local, GoogleMap mapa) {
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(local,6));
    }
}
