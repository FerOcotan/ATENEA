package com.example.atenea;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show();
            } else {
                // Procesa el contenido del QR escaneado
                String qrContent = result.getContents(); // Contenido escaneado del QR
                String[] dataArray = qrContent.split(";"); // Divide los datos por ";"

                if (dataArray.length >= 3) {
                    try {
                        // Extrae los valores individuales
                        String email = dataArray[0].split(":")[1];
                        String nombre = dataArray[1].split(":")[1];
                        String apellido = dataArray[2].split(":")[1];

                        // Genera la fecha y hora actuales
                        String fechaActual = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        String horaActual = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                        // Llama al método para guardar en Firebase
                        saveDataToFirebase(email, nombre, apellido, fechaActual, horaActual);

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
    //obtener datos de user para escribir//

    private void saveDataToFirebase(String email, String nombre,
                                    String apellido, String fechaActual, String horaActual)
    {
        //se modifica para que segun el usuario pueda escribir//
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference asistenciasRef = database.getReference("users").child(userId).child("asistencias");
        //se modifica para que segun el usuario pueda escribir//

        // Crear un objeto con los datos escaneados
        Map<String, String> asistenciaData = new HashMap<>();
        asistenciaData.put("email", email);
        asistenciaData.put("nombre", nombre);
        asistenciaData.put("apellido", apellido);
        asistenciaData.put("fecha", fechaActual);
        asistenciaData.put("hora", horaActual);

        // Guardar los datos en un nodo único generado por push()
        asistenciasRef.push().setValue(asistenciaData)
                .addOnSuccessListener(aVoid -> Toast.makeText(this,
                        "Datos subidos a Firebase Realtime Database",
                        Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                {
                    e.printStackTrace();
                    Toast.makeText(this, "Error al subir datos a Firebase",
                            Toast.LENGTH_SHORT).show();
                });
    }












}
