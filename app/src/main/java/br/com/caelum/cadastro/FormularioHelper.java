package br.com.caelum.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.caelum.cadastro.FormularioActivity;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by android6040 on 14/05/16.
 */
public class FormularioHelper {
    
    private Aluno aluno;

    private ImageView selfie;
    private Button  selfieButton;

    private EditText nome;
    private EditText telefone;
    private EditText endereco;
    private EditText site;
    private RatingBar nota;
    
    public FormularioHelper(FormularioActivity activity){
        
        this.aluno = new Aluno();
        this.selfie = (ImageView) activity.findViewById(R.id.fotoFormulario);
        this.selfieButton = (Button) activity.findViewById(R.id.fotoBotaoFormulario);
        this.nome = (EditText) activity.findViewById(R.id.editName);
        this.telefone = (EditText) activity.findViewById(R.id.editTelephone);
        this.endereco = (EditText) activity.findViewById(R.id.editAddress);
        this.site = (EditText) activity.findViewById(R.id.editSite);
        this.nota = (RatingBar) activity.findViewById(R.id.ratingBar);
               
    }
    
    public Aluno pegaAlunoDoFormulario(){
        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));
        aluno.setCaminhoFoto((String) selfie.getTag());
        
        return aluno;
    }

    public boolean temNome(){
        return !nome.getText().toString().isEmpty();
    }

    public boolean temTel(){
        return !telefone.getText().toString().isEmpty();
    }

    public boolean temEnd(){
        return !endereco.getText().toString().isEmpty();
    }

    public boolean temSite(){
        return !site.getText().toString().isEmpty();
    }

    public boolean temNota(){
        return (nota==null);
    }

    public void mostraErro(){
        nome.setError("Campo nao pode ser vazio!!!");
    }

    public void mostraErroTel(){
        telefone.setError("Campo nao pode ser vazio!!!");
    }

    public void mostraErroEnd(){
        endereco.setError("Campo nao pode ser vazio!!!");
    }

    public void mostraErroSite(){
        site.setError("Campo nao pode ser vazio!!!");
    }

    public void colocaFormulario (Aluno aluno){
        nome.setText(aluno.getNome());
        telefone.setText(aluno.getTelefone());
        endereco.setText(aluno.getEndereco());
        site.setText(aluno.getSite());
        nota.setProgress(aluno.getNota().intValue());

        this.aluno = aluno;

        if (aluno.getCaminhoFoto() != null){
            this.carregaImagem(aluno.getCaminhoFoto());
        }
    }

    public Button getSelfieButton() {
        return selfieButton;
    }

    public void carregaImagem (String localArquivoFoto){
        Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
        Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imagemFoto,imagemFoto.getWidth(),300,true);

        selfie.setImageBitmap(imagemFotoReduzida);
        selfie.setTag(localArquivoFoto);
        selfie.setScaleType(ImageView.ScaleType.FIT_XY);

        imagemFoto.recycle();
    }
}
