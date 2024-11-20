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


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            codigomateria.setText(bundle.getString("codigo"));
            nombremateria.setText(bundle.getString("nombre_materia"));
            participantes.setText(bundle.getString("participantes"));
            seccion.setText(bundle.getString("seccion"));
            horainicio.setText(bundle.getString("hora_inicio"));
            horasalida.setText(bundle.getString("hora_salida"));
            creador.setText(bundle.getString("carnet_creador"));
            key = bundle.getString("Key");

            Glide.with(this).load(R.drawable.baseline_auto_delete_24).into(detailImage);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un diálogo de confirmación
                new androidx.appcompat.app.AlertDialog.Builder(DetailActivity.this)
                        .setTitle("Confirmación")
                        .setMessage("¿Estás seguro de que deseas eliminar esta materia?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            // Referencia a la base de datos
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("users").child(userId).child("materias");

                            // Eliminar el objeto de la base de datos directamente
                            reference.child(key).removeValue();
                            Toast.makeText(DetailActivity.this, "Materia eliminada", Toast.LENGTH_SHORT).show();
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