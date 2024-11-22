package com.example.atenea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class menu_settings extends AppCompatActivity {


    protected GoogleSignInClient mGoogleSignInClient;
    private ShapeableImageView buttonProfile;
    private TextView TextViewUserName;
    private TextView TextViewUserEmail;
    private ImageView backButton;
    private RelativeLayout AboutLayout;
    private RelativeLayout Fqs;
    private RelativeLayout Helplayaout;
    private RelativeLayout Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_settings);




        //PARA CARGAR LA IMAGEN
        buttonProfile = findViewById(R.id.buttonprofile);
        TextViewUserName = findViewById(R.id.TextViewUserName);
        TextViewUserEmail = findViewById(R.id.TextViewUserEmail);
        backButton = findViewById(R.id.backButton);
        AboutLayout = findViewById(R.id.aboutLayout);
        Helplayaout = findViewById(R.id.helplayaout);
        Exit = findViewById(R.id.exitlayaout);
        Fqs = findViewById(R.id.faqss);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresa a la actividad anterior
                onBackPressed();
            }
        });


        Exit.setOnClickListener(v -> {
            ventanaexit();
        });

// lclic para redirigir a la actividad
        Fqs.setOnClickListener(v -> {
            // a la actividad AboutActivity
            Intent intent = new Intent(menu_settings.this, faqs.class);
            startActivity(intent);
        });

        // lclic para redirigir a la actividad
        AboutLayout.setOnClickListener(v -> {
            // a la actividad AboutActivity
            Intent intent = new Intent(menu_settings.this, menu_about_us.class);
            startActivity(intent);
        });

        //   clic para redirigir a la actividad
        Helplayaout.setOnClickListener(v -> {
            //a la actividad menu_help
            Intent intent = new Intent(menu_settings.this, menu_help.class);
            startActivity(intent);
        });





        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Carga la imagen de perfil en el ShapeableImageView
        String photoUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null;
        if (photoUrl != null) {
            Glide.with(this)
                    .load(photoUrl)
                    .placeholder(R.drawable.profileimage) // Imagen de placeholder
                    .error(R.drawable.profileimage) // Imagen de error
                    .into(buttonProfile);
        } else {
            // Si no hay URL de imagen, usa la imagen por defecto
            buttonProfile.setImageResource(R.drawable.profileimage);
        }

        if (user != null) {
            // Si el usuario está logueado, obtiene el nombre
            String displayName = user.getDisplayName();

            if (displayName != null && !displayName.isEmpty()) {
                // Divide el nombre completo y toma solo el primer nombre
                String firstName = displayName.split(" ")[0];
                // Asigna el primer nombre al TextView
                TextViewUserName.setText(firstName);
            } else {
                // Si no hay un displayName, utiliza el email
                String email = user.getEmail();
                if (email != null && email.contains("@")) {
                    // Obtiene la parte antes del @
                    String firstName = email.split("@")[0];
                    TextViewUserName.setText(firstName);
                } else {
                    // Si no hay correo, establece un texto por defecto
                    TextViewUserName.setText(R.string.usuario);
                }
            }
        } else {
            // Si no hay usuario logueado, un texto por defecto
            TextViewUserName.setText(R.string.invitado);
        }

        // Obtén y muestra el correo del usuario
        if (user != null) {
            String email = user.getEmail();
            if (email != null && !email.isEmpty()) {
                TextViewUserEmail.setText(email);
            } else {
                TextViewUserEmail.setText(R.string.no_email_provided);
            }
        } else {
            TextViewUserEmail.setText(R.string.guest);
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;





        });




    }

    private void ventanaexit() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Intent intent = new Intent(this, welcome.class);
            startActivity(intent);
            this.finish();
        });
    }
}