import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

/**
 * Created by android6040 on 04/06/16.
 */
public class Configurador implements GoogleApiClient.ConnectionCallbacks {
    private AtualizadorDeLocalizacao atualizadorDeLocalizacao;

    public Configurador(AtualizadorDeLocalizacao atualizadorDeLocalizacao){
        this.atualizadorDeLocalizacao = atualizadorDeLocalizacao;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = LocationRequest.create();
        request.setInterval(2000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setSmallestDisplacement(50);

        atualizadorDeLocalizacao.inicia(request);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
