package task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.cadastro.ListaAlunosActivity;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.modelo.AlunoConverter;
import br.com.caelum.cadastro.modelo.AlunoDAO;
import br.com.caelum.cadastro.support.WebClient;

/**
 * Created by android6040 on 28/05/16.
 */
public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private ProgressDialog progress;

    public EnviaAlunosTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progress = ProgressDialog.show(context,"Aguarde...", "Envio de dados para web", true, true);
    }

    @Override
    protected String doInBackground(Object... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.getLista();
        dao.close();

        String json = new AlunoConverter().toJson(alunos);
        String resposta = new WebClient().post(json);
        return resposta;
    }

    @Override
    protected void onPostExecute(String s) {
        progress.dismiss();
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }
}
