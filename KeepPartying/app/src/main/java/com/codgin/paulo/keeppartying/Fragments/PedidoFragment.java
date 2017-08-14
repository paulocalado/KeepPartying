package com.codgin.paulo.keeppartying.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codgin.paulo.keeppartying.Model.Produto;
import com.codgin.paulo.keeppartying.PedidoAdapter;
import com.codgin.paulo.keeppartying.R;
import com.codgin.paulo.keeppartying.Service.DialogService;
import com.facebook.Profile;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PedidoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PedidoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedidoFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<Produto> listaProduto = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public GoogleApiClient googleApiClient;
    public double totalPedido = 0;
    private OnFragmentInteractionListener mListener;
    public String idProfile;
    public PedidoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PedidoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PedidoFragment newInstance(String param1, String param2) {
        PedidoFragment fragment = new PedidoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        googleApiClient = new GoogleApiClient.Builder(getContext(), this, this).addApi(LocationServices.API).build();
        googleApiClient.connect();
        View v= inflater.inflate(R.layout.fragment_pedido, container, false);
        final RecyclerView rvPedido = (RecyclerView)v.findViewById(R.id.rvPedido);
        final TextView txtTotalPedido = (TextView)v.findViewById(R.id.txtTotalPedido);
        Button btnChamaDialogPedido = (Button)v.findViewById(R.id.btnChamaDialogPedido);
        Bundle bundle = this.getArguments();
        final Profile profile = bundle.getParcelable("profile");
        idProfile = profile.getId();
        final DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference produtoReferencia = firebaseReferencia.child("pedidos").child(profile.getId());
        produtoReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(listaProduto.size()!=0){
                    listaProduto.clear();
                    totalPedido = 0;
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Produto produto = new Produto();
                    produto = postSnapshot.getValue(Produto.class);
                    double totalProduto = produto.getPreco()*produto.getQuantidade();
                    totalPedido+=totalProduto;
                    produto.setTotalPorProduto(totalProduto);
                    listaProduto.add(produto);
                }

                txtTotalPedido.setText("Seu total é de: R$"+totalPedido);
                final PedidoAdapter adapter = new PedidoAdapter(listaProduto, getContext(), profile);
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                rvPedido.setLayoutManager(llm);
                rvPedido.setAdapter(adapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnChamaDialogPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int PLACE_PICKER_REQUEST = 1;
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), 1);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
              /*  ;*/
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                //locationButton.setText(String.format("Place: %s", place.getName()));
                DialogService dialogService = new DialogService();
                LatLng latLng = place.getLatLng();

                dialogService.createDialogFinalizarPedido(getContext(), idProfile, totalPedido, latLng);
                String toastMsg = String.format("Place: %s", latLng.latitude);
                Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        googleApiClient.disconnect();
        mListener = null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * A simple {@link Fragment} subclass.
     * Activities that contain this fragment must implement the
     * {@link OnFragmentInteractionListener} interface
     * to handle interaction events.
     * Use the {@link FidelidadeFragment#newInstance} factory method to
     * create an instance of this fragment.
     */
}
