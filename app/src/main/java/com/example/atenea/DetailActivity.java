package com.example.atenea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {
    private ImageView backButton;
    TextView codigomateria, nombremateria,participantes,seccion,horainicio,horasalida,creador;
    ImageView detailImage;
    String key = "";
    Button deleteButton;

    //obtener datos de user para escribir//
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userId = auth.getCurrentUser().getUid(); // Obtener UID del usuario actual
    //obtener datos de user para escribir//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        codigomateria = findViewById(R.id.detailcodigo);
        nombremateria = findViewById(R.id.detailnombre);
        participantes = findViewById(R.id.detailparticipantes);
        seccion = findViewById(R.id.detailseccion);
        horainicio = findViewById(R.id.detailinicio);
        horasalida = findViewById(R.id.detailsalida);
        creador = findViewById(R.id.detaildocente);
        detailImage = findViewById(R.id.detailImage);
        deleteButton = findViewById(R.id.deletebutton);
        backButton = findViewById(R.id.backButton);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            codigomateria.setText(bundle.getString("codigo"));
            nombremateria.setText(bundle.getString("nombre_materia"));
            participantes.setText("Students: " + bundle.getString("participantes"));
            seccion.setText("Seccion: " + bundle.getString("seccion"));
            horainicio.setText("Start time: " + bundle.getString("hora_inicio"));
            horasalida.setText("End time: " + bundle.getString("hora_salida"));
            creador.setText("Professor: " + bundle.getString("carnet_creador"));
            key = bundle.getString("Key");

            Glide.with(this).load(R.drawable.clastrash).into(detailImage);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresa a la actividad anterior
                onBackPressed();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un diálogo de confirmación
                new androidx.appcompat.app.AlertDialog.Builder(DetailActivity.this)
                        .setTitle("¡Attention, you are about to delete a list! ")
                        .setMessage("Are you sure you want to delete this list? This action cannot be undone, deleting this list could cause you problems.")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // Referencia a la base de datos
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("users").child(userId).child("materias");

                            // Eliminar el objeto de la base de datos directamente
                            reference.child(key).removeValue();
                            Toast.makeText(DetailActivity.this, "Subject deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), home.class));
                            finish();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            // Cerrar el diálogo sin hacer nada
                            dialog.dismiss();
                        })
                        .show();
            }
        });

    }
}