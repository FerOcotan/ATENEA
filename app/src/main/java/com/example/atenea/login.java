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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class login extends AppCompatActivity {



    private FirebaseAuth auth;

    //LOGEON CON FIREBASE NORMAL

    private EditText emailInput,passwordInput;

    private TextView registerLink;

    //LOGEON CON FIREBASE NORMAL FIN


    //LOGEON CON FIREBASE GOOGLE

    private TextView GoogleSignInButton;
    private GoogleSignInClient client;


    //LOGEON CON FIREBASE GOOGLE FIN


    Button signInButton;
    boolean botonBackPresionado= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //LOGEON CON FIREBASE NORMAL

        auth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.emailInput);

        passwordInput = findViewById(R.id.passwordInput);
        signInButton = findViewById(R.id.signInButton);
        registerLink = findViewById(R.id.registerLink);
        //LOGEON CON FIREBASE NORMAL FIN


        //LOGEON CON FIREBASE GOOGLE
        GoogleSignInButton = findViewById(R.id.googleSignInButton);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(this, options);
        GoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = client.getSignInIntent();
            startActivityForResult(i,1234);
            }


        });



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
                                        public void onFailure( Exception e) {
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
                Intent ventana2 = new Intent(login.this, register.class);
                startActivity(ventana2);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete( Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    Intent intent = new Intent(getApplicationContext(), home.class);
                                    startActivity(intent);

                                }else {
                                    Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            } catch (ApiException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(this, home.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        botonBackPresionado=true;

    }
}