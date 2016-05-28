package br.com.caelum.cadastro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.AlunoDAO;

/**
 * Created by android6040 on 28/05/16.
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        Object[] mensagens = (Object[]) bundle.get("pdus");
        byte[] mensagem = (byte[]) mensagens[0];
        String formato = (String) bundle.get("format");

        MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            SmsMessage sms = SmsMessage.createFromPdu(mensagem,formato);
            String telefone = sms.getDisplayOriginatingAddress();
            AlunoDAO dao = new AlunoDAO(context);
            if(dao.isAluno(telefone)){
                Toast.makeText(context,"SMS recebido de: "+telefone, Toast.LENGTH_LONG).show();
                mp.start();
            }
            dao.close();
        }
        else {
            Toast.makeText(context,"Chegou um SMS!!!", Toast.LENGTH_LONG).show();
        }
    }
}
