package com.codgin.paulo.keeppartying.Service;

import com.codgin.paulo.keeppartying.Model.Pedido;
import com.codgin.paulo.keeppartying.Model.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Paulo on 27/07/2017.
 */

public class FirebaseService {
    private DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();
    public void removerPedido(String id, String nomeProduto){
        DatabaseReference removeReferencia = firebaseReferencia.child("pedidos").child(id).child(nomeProduto);
        removeReferencia.removeValue();
    }

    public void gerarNumeroReferencia(Pedido pedido, String id){
        DatabaseReference pedidoReferencia = firebaseReferencia.child("entregas").child(id);
        pedidoReferencia.setValue(pedido);
    }
    public void geraTotal(String id, double total){
        DatabaseReference pedidoReferencia = firebaseReferencia.child("pedidos").child(id+"pedido").child("total");
        pedidoReferencia.setValue(total);
    }

    public void criarPedido(String id, Produto produto){
        DatabaseReference pedidoReferencia = firebaseReferencia.child("pedidos").child(id).child(produto.getNome());
        pedidoReferencia.setValue(produto);

    }

    public List<Produto> getProdutosFirebase(long tipo){
        final List<Produto> listaProduto = new ArrayList<>();
        DatabaseReference produtoReferencia = firebaseReferencia.child("produtos");
        produtoReferencia.orderByChild("tipo").equalTo(tipo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    listaProduto.add(postSnapshot.getValue(Produto.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return listaProduto;
    }

}
