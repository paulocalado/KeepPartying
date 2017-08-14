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
import com.codgin.paulo.keeppartying.Service.FirebaseService;
import com.codgin.paulo.keeppartying.Service.PicassoService;
import com.facebook.Profile;

import java.util.List;

/**
 * Created by Paulo on 03/08/2017.
 */

public class PedidoAdapter extends  RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {
    List<Produto> listaProduto;
    Context context;
    Profile profile;

    public PedidoAdapter(List<Produto> listaProduto, Context context){
        this.listaProduto = listaProduto;
        this.context = context;
    }
    public PedidoAdapter(List<Produto> listaProduto, Context context, Profile profile){
        this.listaProduto = listaProduto;
        this.context = context;
        this.profile = profile;
    }
    @Override
    public PedidoAdapter.PedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_produtos_pedido, parent, false);
        PedidoAdapter.PedidoViewHolder pvh = new PedidoAdapter.PedidoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PedidoAdapter.PedidoViewHolder holder, final int position) {
        holder.txtNomeProduto.setText(String.valueOf(listaProduto.get(position).getNome()));
        holder.txtValorProduto.setText("R$ "+String.valueOf(listaProduto.get(position).getPreco()));
        PicassoService.downloadImage(context,listaProduto.get(position).getImagem(), holder.imgProduto);
        holder.txtTotalProduto.setText("R$ total neste produto: "+String.valueOf(listaProduto.get(position).getTotalPorProduto()));
        holder.btnRemoverProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseService firebaseService = new FirebaseService();
                firebaseService.removerPedido(profile.getId(), listaProduto.get(position).getNome());
                Toast.makeText(context, listaProduto.get(position).getNome()+ "Removido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProduto.size();
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtNomeProduto;
        TextView txtValorProduto;
        TextView txtTotalProduto;
        ImageView imgProduto;
        Button btnRemoverProduto;

        PedidoViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardProdutosPedido);
            txtNomeProduto = (TextView)itemView.findViewById(R.id.txtNomeProdutoPedido);
            txtValorProduto = (TextView)itemView.findViewById(R.id.txtValorProdutoPedido);
            imgProduto = (ImageView)itemView.findViewById(R.id.imgProdutoPedido);
            txtTotalProduto = (TextView)itemView.findViewById(R.id.txtTotalProdutoPedido);
            btnRemoverProduto = (Button)itemView.findViewById(R.id.btnRemoverProduto);
            //qual itemView.setOnClickListener( itemView);
        }


    }
}
