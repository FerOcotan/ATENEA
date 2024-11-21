package com.example.atenea;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.example.atenea.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class home extends AppCompatActivity {

    ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnApplyWindowInsetsListener(null);
        binding.bottomNavigationView.setPadding(0, 0, 0, 0);


        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.add) {
                replaceFragment(new AddFragment());
            } else if (item.getItemId() == R.id.list) {
                replaceFragment(new listfragment());

            } else if (item.getItemId() == R.id.reports) {
                replaceFragment(new reportsfragment());
            }
            return true;

        });



        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBottomDialog();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




    }



    private  void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    private void showBottomDialog()
    {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_scanfragment);

        ImageView cancelbutton = dialog.findViewById(R.id.cancel_button);
            cancelbutton.setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }


        });

        // Configurar botón de cámara para iniciar el escáner
        ImageView cameraIcon = dialog.findViewById(R.id.camera_icon);
        cameraIcon.setOnClickListener(v -> scanQRCode());

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);


    }

    private void scanQRCode()
    {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Escanea el código QR");
        integrator.setCameraId(0); // Cámara trasera
        integrator.setOrientationLocked(false); // Forzar la orientacion vertical
        integrator.setBeepEnabled(true); // Sonido al escanear
        integrator.setCaptureActivity(CaptureActivityPortrait.class); //Solo se llama la clase la cual esta vacia
        integrator.setBarcodeImageEnabled(false); // Guardar imagen OPCIONAL
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show();
            } else {
                // Procesa el contenido del QR escaneado
                String qrContent = result.getContents(); // Contenido escaneado del QR
                String[] dataArray = qrContent.split(";"); // Divide los datos por ";"

                if (dataArray.length >= 4) {
                    try {
                        // Extrae los valores individuales
                        String carnet = dataArray[0].split(":")[1];
                        String nombre = dataArray[1].split(":")[1];
                        String apellido = dataArray[2].split(":")[1];
                        String email = dataArray[3].split(":")[1];

                        // Genera la fecha y hora actuales
                        String fechaActual = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        String horaActual = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                        // Llama al método para guardar en Firebase, pasando los datos escaneados
                        mostrarSelectorDeMateria(carnet, nombre, apellido, email, fechaActual, horaActual);

                    } catch (Exception e) {
                        Toast.makeText(this, "Formato de QR inválido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Datos insuficientes en el QR", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //obtener datos de user para escribir//
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userId = auth.getCurrentUser().getUid(); // Obtener UID del usuario actual
    String lista = auth.getCurrentUser().getUid(); // Obtener UID del usuario actual
    //obtener datos de user para escribir//




    private void mostrarSelectorDeMateria(final String carnet, final String nombre, final String apellido, final String email, final String fechaActual, final String horaActual) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference materiasRef = database.getReference("users").child(userId).child("lista");

        materiasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Crear listas para nombres y keys
                List<String> nombresMaterias = new ArrayList<>();
                List<String> keysMaterias = new ArrayList<>();

                for (DataSnapshot materiaSnapshot : dataSnapshot.getChildren()) {
                    String nombreMateria = materiaSnapshot.child("materia").getValue(String.class);
                    String key = materiaSnapshot.getKey(); // Clave de la materia
                    nombresMaterias.add(nombreMateria);
                    keysMaterias.add(key);
                }

                // Convertir la lista a un array para usarlo en el diálogo
                String[] opciones = nombresMaterias.toArray(new String[0]);

                // Mostrar el cuadro de diálogo
                AlertDialog.Builder builder = new AlertDialog.Builder(home.this);
                builder.setTitle("Selecciona una Materia")
                        .setItems(opciones, (dialog, which) -> {
                            // Obtener la clave de la materia seleccionada
                            String selectedKey = keysMaterias.get(which);

                            // Llamar al método para guardar los datos en la materia seleccionada, pasando los datos escaneados
                            guardarDatosEnMateria(selectedKey, carnet, nombre, apellido, email, fechaActual, horaActual);
                        })
                        .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                        .show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(home.this, "Error al cargar materias: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void guardarDatosEnMateria(String selectedKey, String carnet, String nombre, String apellido, String email, String fecha, String hora) {
        // Guardar los datos de asistencia en el nodo correspondiente de Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference asistenciaRef = database.getReference("users").child(userId).child("lista").child(selectedKey).child("asistencias");

        // Crear el objeto con los datos de la asistencia
        Map<String, String> asistenciaData = new HashMap<>();
        asistenciaData.put("carnet", carnet);
        asistenciaData.put("nombre", nombre);
        asistenciaData.put("apellido", apellido);
        asistenciaData.put("email", email);
        asistenciaData.put("fecha", fecha);
        asistenciaData.put("hora", hora);

        // Usamos push() para crear un nuevo nodo único para cada entrada de asistencia
        asistenciaRef.push().setValue(asistenciaData)
                .addOnSuccessListener(aVoid -> Toast.makeText(home.this, "Asistencia registrada exitosamente", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    Toast.makeText(home.this, "Error al registrar asistencia", Toast.LENGTH_SHORT).show();
                });
    }


}
