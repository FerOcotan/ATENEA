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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class register extends AppCompatActivity {


    private FirebaseAuth auth;

    private EditText Emailvalid,PasswordInput;

    private TextView RegisterLink;

    Button SignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);


        auth = FirebaseAuth.getInstance();

        Emailvalid = findViewById(R.id.emailvalid);

        PasswordInput = findViewById(R.id.passwordInput);

        RegisterLink = findViewById(R.id.registerLink);

        SignInButton = findViewById(R.id.signInButton);


        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = Emailvalid.getText().toString().trim();
                String pass = PasswordInput.getText().toString().trim();

                if (user.isEmpty()){
                    Emailvalid.setError(getString(R.string.email_esta_vacio));
                }

                if (pass.isEmpty()){
                    PasswordInput.setError(getString(R.string.contrase_a_esta_vacio));

                }else {
                    auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(register.this, R.string.registro_existoso, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(register.this, getString(R.string.registro_fallido) + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }



            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;



        });

        RegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventana2 = new Intent(register.this, login.class);
                startActivity(ventana2);

            }
        });


    }


}