package com.codgin.paulo.keeppartying.Service;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codgin.paulo.keeppartying.Model.Pedido;
import com.codgin.paulo.keeppartying.Model.Produto;
import com.codgin.paulo.keeppartying.R;
import com.facebook.Profile;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by Paulo on 01/08/2017.
 */

public class DialogService {
     FirebaseService firebaseService = new FirebaseService();
    public void createDialogFinalizarPedido(final Context context, final String idProfile, final double total, LatLng latLng) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_finalizar_pedido);
        dialog.setTitle("Vamos finalizar seu pedido");

        final EditText edtNumero = (EditText) dialog.findViewById(R.id.edtNumeroDialog);
        final EditText edtReferencia = (EditText) dialog.findViewById(R.id.edtReferenciaDialog);
        Button btnFinalizarPedido = (Button) dialog.findViewById(R.id.btnFinalizarPedido);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {


            final double lat = latLng.latitude, lon = latLng.longitude;

            btnFinalizarPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pedido pedido = new Pedido(edtNumero.getText().toString(), edtReferencia.getText().toString());
                    pedido.setTotal(total);
                    pedido.setLat(lat);
                    pedido.setLon(lon);

                    firebaseService.gerarNumeroReferencia(pedido, idProfile);
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    public void createDialogPedido(Context context, final Produto produto, final Profile profile){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_fazer_pedido);
        dialog.setTitle("Adicione a seu Pedido");

        ImageView imageProduto = (ImageView)dialog.findViewById(R.id.imgProdutoFazerPedido);
        TextView txtNomeProduto = (TextView)dialog.findViewById(R.id.txtNomeProdutoDialog);
        TextView txtPrecoProduto = (TextView)dialog.findViewById(R.id.txtPrecoProdutoDialog);
        Button btnAdicionarPedido = (Button)dialog.findViewById(R.id.btnAdicionarPedidoDialog);
       final EditText edtQuantidade = (EditText)dialog.findViewById(R.id.editQuantidadeProduto);
        txtPrecoProduto.setText(""+produto.getPreco());
        txtNomeProduto.setText(""+produto.getNome());
        PicassoService.downloadImage(context,produto.getImagem(), imageProduto);
        btnAdicionarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                produto.setQuantidade(Integer.parseInt(edtQuantidade.getText().toString()));

                firebaseService.criarPedido(profile.getId(), produto);
                dialog.dismiss();
            }
        });
        dialog.show();


    }
}
