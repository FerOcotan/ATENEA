package com.example.atenea;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {


    private FirebaseAuth auth;
    private EditText emailInput,passwordInput;

    private TextView registerLink;


    Button signInButton;
    boolean botonBackPresionado= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.emailInput);


        passwordInput = findViewById(R.id.passwordInput);
        signInButton = findViewById(R.id.signInButton);
        registerLink = findViewById(R.id.registerLink);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!botonBackPresionado) {

                    String email = emailInput.getText().toString().trim();
                    String pass = passwordInput.getText().toString().trim();

                    if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                        if (!pass.isEmpty()) {
                            auth.signInWithEmailAndPassword(email, pass)
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            Toast.makeText(login.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                                            Intent ventana2 = new Intent(login.this, home.class);
                                            startActivity(ventana2);
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(login.this, "Fallo en el inicio de sesión", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            passwordInput.setError("Contraseña no puede estar vacía");
                        }

                    } else if (email.isEmpty()) {
                        emailInput.setError("Email no puede estar vacío");
                    } else {
                        emailInput.setError("Por favor introduzca un email válido");
                    }

                }

            }
        });




        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventana2 = new Intent(login.this, login.class);
                startActivity(ventana2);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        botonBackPresionado=true;

    }
}