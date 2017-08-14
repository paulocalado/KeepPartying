package com.codgin.paulo.keeppartying;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import android.Manifest;

public class MainActivity extends AppCompatActivity {
    TextView txtStatus;
    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        loginButton = (LoginButton) findViewById(R.id.login_button);


        if (isLoggedIn()) {
            Profile profile = Profile.getCurrentProfile();
            txtStatus.setText("ENTROU??????W ");

            Toast.makeText(MainActivity.this,"ja ta logado login",Toast.LENGTH_SHORT).show();
            Intent intentListaMesa = new Intent(MainActivity.this, Home.class);
            intentListaMesa.putExtra("profile", profile);
            startActivity(intentListaMesa);
        } else {
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Profile profile = Profile.getCurrentProfile();
                   // FirebaseService firebaseService = new FirebaseService();
                    //txtStatus.setText("ENTROU??????W ");
                   // User user = new User(profile.getId(), profile.getFirstName());
                   // firebaseService.criarUsuarioFirebase(user);
                    Intent intentListaMesa = new Intent(MainActivity.this, Home.class);
                    intentListaMesa.putExtra("profile", profile);
                    startActivity(intentListaMesa);
                    Toast.makeText(MainActivity.this,"onSucess login",Toast.LENGTH_SHORT).show();
                    txtStatus.setText(profile.getFirstName());
                }

                @Override
                public void onCancel() {
                    Toast.makeText(MainActivity.this, "cancelou?", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d("qual foi o erro?", error.toString());
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
