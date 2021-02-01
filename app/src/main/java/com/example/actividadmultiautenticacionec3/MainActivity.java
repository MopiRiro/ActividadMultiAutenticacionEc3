package com.example.actividadmultiautenticacionec3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;

    public static final int REQUEST_CODE=54654;
    List<AuthUI.IdpConfig> provider= Arrays.asList(
            new AuthUI.IdpConfig.FacebookBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.GitHubBuilder().build()
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener= new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user !=null){
                    Toast.makeText(MainActivity.this, "Iniciaste sesion ¡BIENVENIDO!", Toast.LENGTH_SHORT).show();
                }else{
                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(provider)
                            .setIsSmartLockEnabled(false)
                            .build(),REQUEST_CODE);
                }
            }
        };
    }
    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public void CerraSesion(View view) {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this,"SESION CERRADA",Toast.LENGTH_SHORT).show();
            }
        });
    }
}