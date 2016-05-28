package br.com.caelum.cadastro;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by android6040 on 28/05/16.
 */
public class ListaAlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Activity activity;

    public ListaAlunosAdapter(Activity activity, List<Aluno> alunos){
        this.activity = activity;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        Aluno alunoPosicao = alunos.get(position);
        return alunoPosicao.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item, parent, false);
        Aluno aluno = alunos.get(position);

        if(position % 2 == 0){
            view.setBackgroundColor(activity.getResources().getColor(R.color.linhaPar));
        }
        else {
            view.setBackgroundColor(activity.getResources().getColor(R.color.linhaImpar));
        }

        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        nome.setText(aluno.toString());

        Bitmap bm;
        if(aluno.getCaminhoFoto()!=null){
            bm = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
        }
        else {
            bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
        }

        bm = Bitmap.createScaledBitmap(bm,100,100,true);

        ImageView foto = (ImageView) view.findViewById(R.id.item_foto);
        foto.setImageBitmap(bm);

        return view;
    }


}
