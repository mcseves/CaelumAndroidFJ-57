package br.com.caelum.cadastro.modelo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;


/**
 * Created by android6040 on 21/05/16.
 */
public class Permissao {
    private static int CODE = 123;
    private static ArrayList<String> listaPermissoes = new ArrayList<>();

    public static void fazPermissao (Activity activity){
        String [] permissoes = {Manifest.permission.CALL_PHONE,
                                Manifest.permission.RECEIVE_SMS,
                                Manifest.permission.INTERNET,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.CAMERA};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permissao : permissoes) {
                if (activity.checkSelfPermission(permissao) != PackageManager.PERMISSION_GRANTED) {
                    listaPermissoes.add(permissao);
                }
            }
            request(activity);
        }
    }

    public static void request (Activity activity){
        String[] array = listaPermissoes.toArray(new String[]{});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (listaPermissoes.size() > 0) {
                activity.requestPermissions(array, CODE);
            }
        }
    }
}
