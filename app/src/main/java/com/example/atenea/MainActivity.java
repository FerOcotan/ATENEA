package com.example.atenea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button logout;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar GoogleSignInOptions
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Asegúrate de que este ID es correcto
                .requestEmail()
                .build();

        // Inicializar GoogleSignInClient
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Configurar botón de logout
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cierra la sesión en Firebase
                FirebaseAuth.getInstance().signOut();

                // Cierra la sesión en la cuenta de Google
                mGoogleSignInClient.signOut().addOnCompleteListener(MainActivity.this, task -> {
                    // Redirige al usuario a la pantalla de bienvenida después de cerrar sesión
                    Intent intent = new Intent(MainActivity.this, welcome.class);
                    startActivity(intent);
                    finish();
                });
            }
        });
    }
}
