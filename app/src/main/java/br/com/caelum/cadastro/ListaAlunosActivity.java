//Criado Por Maria Carolina Santos

package br.com.caelum.cadastro;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.List;

import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.modelo.AlunoConverter;
import br.com.caelum.cadastro.modelo.AlunoDAO;
import br.com.caelum.cadastro.modelo.Permissao;
import br.com.caelum.cadastro.support.ProvasActivity;
import br.com.caelum.cadastro.support.WebClient;
import task.EnviaAlunosTask;

public class ListaAlunosActivity extends AppCompatActivity {

    // array com nomes que compoe a lista de alunos
    private ListView listaAlunos;
    private List<Aluno> alunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Permissao.fazPermissao(this);

        this.listaAlunos = (ListView) findViewById(R.id.listaAlunos);

// CRIANDO TOASTSSSSS
//        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ListaAlunosActivity.this, "Posicao: "+ position, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Aluno alunoClicado = (Aluno) listaAlunos.getItemAtPosition(position);
//                Toast.makeText(ListaAlunosActivity.this, "Aluno: " + alunoClicado, Toast.LENGTH_LONG).show();
//
//                return false;
//            }
//        });

//        Criando botao flutuante kk
        Button addButton = (Button) findViewById(R.id.listaAlunosFloatingButton);
        if (addButton != null) {
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                    startActivity(intent);
                }
            });
        }

        registerForContextMenu(listaAlunos);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent edicao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                Aluno alunoSelecionado = (Aluno) listaAlunos.getItemAtPosition(position);
                edicao.putExtra(FormularioActivity.ALUNO_SELECIONADO,alunoSelecionado);
                startActivity(edicao);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) listaAlunos.getAdapter().getItem(info.position);

        MenuItem ligar = menu.add("Ligar");
        MenuItem enviarSms = menu.add("Enviar SMS");
        MenuItem localizacao = menu.add("Achar no Mapa");
        MenuItem navegarSite = menu.add("Navegar no Site");
        MenuItem deletar = menu.add("Deletar");

        // deletando itens da lista
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick (MenuItem item){
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deletarAluno(alunoSelecionado);
                dao.close();
                carregaLista();
                return false;
            }
        });

        // Habilitando ligacoes
        Intent intentLigar = new Intent(Intent.ACTION_CALL);
        intentLigar.setData(Uri.parse("tel:"+alunoSelecionado.getTelefone()));
        ligar.setIntent(intentLigar);

        //Habilitando SMS
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:"+alunoSelecionado.getTelefone()));
        enviarSms.setIntent(intentSMS);

        //Habilitando Mapa
        Intent intentLocalizacao = new Intent(Intent.ACTION_VIEW);
        intentLocalizacao.setData(Uri.parse("geo:0,0?z=14&q="+alunoSelecionado.getEndereco()));
        localizacao.setIntent(intentLocalizacao);

        //Habilitando visita ao site
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = alunoSelecionado.getSite();
        if(!site.startsWith("http://")){
            site = "http://" + site;
        }
        intentSite.setData(Uri.parse(site));
        navegarSite.setIntent(intentSite);
    }

    protected void onResume(){
        super.onResume();
        this.carregaLista();
    }

    private void carregaLista(){
        AlunoDAO dao = new AlunoDAO(this);
         List<Aluno> alunos = dao.getLista();
        dao.close();

        ListaAlunosAdapter adapter = new ListaAlunosAdapter(this, alunos);
        this.listaAlunos.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.getLista();
        dao.close();
        String json = new AlunoConverter().toJson(alunos);

        switch (item.getItemId()){
            case R.id.menu_enviar_notas:
                new EnviaAlunosTask(this).execute();
                return true;
            case R.id.menu_receber_provas:
                Intent provas = new Intent(this, ProvasActivity.class);
                startActivity(provas);
                return true;
            case R.id.menu_mapa:
                Intent mapa = new Intent(this,MostraAlunosActivity.class);
                startActivity(mapa);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
