package br.com.caelum.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActivityChooserView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.text.Normalizer;

import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.modelo.AlunoDAO;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;
    public static final String ALUNO_SELECIONADO = "alunoSelecionado";

    private String localArquivoFoto;
    private static final int TIRA_FOTO = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        this.helper = new FormularioHelper(this);

        final Aluno alunoAlterado = (Aluno) getIntent().getSerializableExtra(ALUNO_SELECIONADO);
        if (alunoAlterado != null) {
            helper.colocaFormulario(alunoAlterado);
        }

        Button selfie = helper.getSelfieButton();
        selfie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                localArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivo = new File(localArquivoFoto);
                Uri localFoto = Uri.fromFile(arquivo);
                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
                startActivityForResult(irParaCamera,TIRA_FOTO);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuFormularioOk:

                Aluno aluno = helper.pegaAlunoDoFormulario();
                AlunoDAO DAO = new AlunoDAO(this);

                if (!helper.temNome()) { helper.mostraErro(); }
                if (!helper.temTel()) { helper.mostraErroTel(); }
                if (!helper.temEnd()) { helper.mostraErroEnd(); }
                if (!helper.temSite()) { helper.mostraErroSite(); }
                if (helper.temNome() && helper.temEnd() && helper.temSite() && helper.temTel()) {
                    if (aluno.getId() == null) {
                        DAO.insere(aluno);
                    } else {
                        DAO.altera(aluno);
                    }
                    DAO.close();
                    finish();
                    return false;
                }

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == TIRA_FOTO){
            if(resultCode == Activity.RESULT_OK){
                helper.carregaImagem(this.localArquivoFoto);
            }
            else {
                this.localArquivoFoto = null;
            }
        }
    }
}
