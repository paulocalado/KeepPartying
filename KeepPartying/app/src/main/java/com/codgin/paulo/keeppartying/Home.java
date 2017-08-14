package com.codgin.paulo.keeppartying;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.codgin.paulo.keeppartying.Fragments.BuscaFragment;
import com.codgin.paulo.keeppartying.Fragments.FidelidadeFragment;
import com.codgin.paulo.keeppartying.Fragments.PedidoFragment;
import com.codgin.paulo.keeppartying.Fragments.ProdutoFragment;
import com.facebook.Profile;

public class Home extends AppCompatActivity {

    private TextView mTextMessage;
    Profile profile;
    Bundle bundle = new Bundle();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Bundle bundleProduto = new Bundle();
                    bundle.putParcelable("profile", profile);
                    ProdutoFragment produtoFragment = new ProdutoFragment();
                    produtoFragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, produtoFragment, "produtoFragment")
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    Bundle bundleBusca = new Bundle();
                    bundle.putParcelable("profile", profile);
                    BuscaFragment buscaFragment = new BuscaFragment();
                    buscaFragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, buscaFragment, "buscaFragment")
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_notifications:
                    Bundle bundlePedido = new Bundle();
                    bundle.putParcelable("profile", profile);
                    PedidoFragment pedidoFragment = new PedidoFragment();
                    pedidoFragment.setArguments(bundle);
                    getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, pedidoFragment, "pedidoFragment")
                        .addToBackStack(null)
                        .commit();
                    return true;
                case R.id.navigation_fidelidade:
                    bundle.putParcelable("profile", profile);
                    FidelidadeFragment fidelidadeFragment = new FidelidadeFragment();
                    fidelidadeFragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, fidelidadeFragment, "fidelidadeFragment")
                            .addToBackStack(null)
                            .commit();
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intentMain = getIntent();
         profile = intentMain.getParcelableExtra("profile");
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION },
                    PermissionChecker.PERMISSION_GRANTED);
        }

        Bundle bundleProdutoCreate = new Bundle();
        bundle.putParcelable("profile", profile);
        ProdutoFragment produtoFragment = new ProdutoFragment();
        produtoFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, produtoFragment, "produtoFragment")
                .addToBackStack(null)
                .commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionChecker.PERMISSION_GRANTED:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "LOCALIZACAO PERMITIDA!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

}
