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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
 * {@link BuscaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuscaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();
    DatabaseReference produtoReferencia = firebaseReferencia.child("produtos");
    List<Produto> listaProduto = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public BuscaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuscaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuscaFragment newInstance(String param1, String param2) {
        BuscaFragment fragment = new BuscaFragment();
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
        View v= inflater.inflate(R.layout.fragment_busca, container, false);
        final RecyclerView rvBusca = (RecyclerView)v.findViewById(R.id.rvBusca);
        Bundle bundle = this.getArguments();
        final Profile profile = bundle.getParcelable("profile");
        TextView txtTeste = (TextView)v.findViewById(R.id.textView2);
        txtTeste.setText(profile.getId());
       String[] arraySpinner = new String[] {
                "Cerveja", "Vodka", "Whisky", "Vinho", "Aperitivos"
        };
        final Spinner spinner = (Spinner) v.findViewById(R.id.spinnerBusca);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, arraySpinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long tipo = spinner.getSelectedItemId();
                Toast.makeText(getContext(), ""+tipo,Toast.LENGTH_SHORT).show();
                produtoReferencia.orderByChild("tipo").equalTo(tipo).addValueEventListener(new ValueEventListener() {
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
                        rvBusca.setLayoutManager(llm);
                        rvBusca.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
