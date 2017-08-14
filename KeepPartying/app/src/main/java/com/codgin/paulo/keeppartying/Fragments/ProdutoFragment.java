package com.codgin.paulo.keeppartying.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import com.codgin.paulo.keeppartying.ProdutoAdapter;
import com.codgin.paulo.keeppartying.R;
import com.codgin.paulo.keeppartying.Service.FirebaseService;
import com.facebook.Profile;
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
 * {@link ProdutoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProdutoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProdutoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   // FirebaseService firebaseService = new FirebaseService();
    DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();
    DatabaseReference produtoReferencia = firebaseReferencia.child("produtos");
    List<Produto> listaProduto = new ArrayList<>();
    RecyclerView rvProduto;
    private OnFragmentInteractionListener mListener;

    public ProdutoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProdutoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProdutoFragment newInstance(String param1, String param2) {
        ProdutoFragment fragment = new ProdutoFragment();
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
        View v = inflater.inflate(R.layout.fragment_produto, container, false);
        rvProduto = (RecyclerView)v.findViewById(R.id.recyclerProduto);
       /* listaProduto.add(new Produto("Heineken 600ml", 5.00));
        listaProduto.add(new Produto("Vodka Smirnoff 1L", 20.00));
        listaProduto.add(new Produto("Cerveja Origianl", 7.00));*/
        Bundle bundle = this.getArguments();
        final Profile profile = bundle.getParcelable("profile");
        produtoReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(listaProduto.size()!=0){
                    listaProduto.clear();
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    listaProduto.add(postSnapshot.getValue(Produto.class));
                }


                final ProdutoAdapter adapter = new ProdutoAdapter(listaProduto, getContext(), profile);
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                rvProduto.setLayoutManager(llm);
                rvProduto.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //listaProduto = firebaseService.getProdutosFirebase();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume(){
        super.onResume();
        //listaProduto = firebaseService.getProdutosFirebase();

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
}
