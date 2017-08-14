package com.codgin.paulo.keeppartying;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codgin.paulo.keeppartying.Model.Produto;
import com.codgin.paulo.keeppartying.Service.DialogService;
import com.codgin.paulo.keeppartying.Service.PicassoService;
import com.facebook.Profile;

import java.util.List;

/**
 * Created by Paulo on 26/07/2017.
 */

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {
    List<Produto> listaProduto;
    Context context;
    Profile profile;
    public ProdutoAdapter(List<Produto> listaProduto, Context context){
        this.listaProduto = listaProduto;
        this.context = context;
    }
    public ProdutoAdapter(List<Produto> listaProduto, Context context, Profile profile){
        this.listaProduto = listaProduto;
        this.context = context;
        this.profile = profile;
    }
    @Override
    public ProdutoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_produtos, parent, false);
        ProdutoViewHolder pvh = new ProdutoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProdutoViewHolder holder, final int position) {
        holder.txtNomeProduto.setText(String.valueOf(listaProduto.get(position).getNome()));
        holder.txtValorProduto.setText("R$ "+String.valueOf(listaProduto.get(position).getPreco()));
        PicassoService.downloadImage(context,listaProduto.get(position).getImagem(), holder.imgProduto);

        holder.btnFazerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogService dialogService = new DialogService();
                dialogService.createDialogPedido(context, listaProduto.get(position), profile);
                Toast.makeText(context, listaProduto.get(position).getNome()+" Adicionado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProduto.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtNomeProduto;
        TextView txtValorProduto;
        ImageView imgProduto;
        Button btnFazerPedido;

        ProdutoViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardProdutos);
            txtNomeProduto = (TextView)itemView.findViewById(R.id.txtNomeProduto);
            txtValorProduto = (TextView)itemView.findViewById(R.id.txtValorProduto);
            imgProduto = (ImageView)itemView.findViewById(R.id.imgProduto);
            btnFazerPedido = (Button)itemView.findViewById(R.id.btnFazerPedidoLista);
            //qual itemView.setOnClickListener( itemView);
        }


    }
}
