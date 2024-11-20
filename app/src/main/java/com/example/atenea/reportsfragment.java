package com.example.atenea;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


public class reportsfragment extends BaseFragment {


    //obtener datos de user para escribir//
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userId = auth.getCurrentUser().getUid(); // Obtener UID del usuario actual
    //obtener datos de user para escribir//

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_reportsfragment, container, false);

            // Configuración del menú en el ImageView del fragmento
            ImageView menuprofile2 = view.findViewById(R.id.buttonprofile);
            setupProfileMenu(menuprofile2);

            // Encontrar el botón en el diseño
            Button btnDescargarCSV = view.findViewById(R.id.btnDescargarCSV);

            // Asignar funcionalidad al botón
            btnDescargarCSV.setOnClickListener(v -> descargarYGuardarCSVEnDescargas());

            return view;

        }

        private void descargarYGuardarCSVEnDescargas() {
            // Referencia al nodo 'asistencias' en Firebase Realtime Database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference asistenciasRef = database.getReference("users").child(userId).child("asistencias");

            // Escuchar los datos
            asistenciasRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Crear el archivo CSV en almacenamiento interno privado
                        File archivoTemporal = new File(requireContext().getExternalFilesDir(null), "asistencias_descargadas.csv");

                        try (FileWriter fileWriter = new FileWriter(archivoTemporal)) {
                            // Escribir encabezados
                            fileWriter.append("Correo,Nombre,Apellido,Fecha,Hora\n");

                            // Iterar sobre los datos y escribirlos en el archivo
                            for (DataSnapshot asistenciaSnapshot : dataSnapshot.getChildren()) {
                                Map<String, String> asistencia = (Map<String, String>) asistenciaSnapshot.getValue();
                                String email = asistencia.get("email");
                                String nombre = asistencia.get("nombre");
                                String apellido = asistencia.get("apellido");
                                String fecha = asistencia.get("fecha");
                                String hora = asistencia.get("hora");

                                // Escribir una línea en el archivo CSV
                                fileWriter.append(email).append(",")
                                        .append(nombre).append(",")
                                        .append(apellido).append(",")
                                        .append(fecha).append(",")
                                        .append(hora).append("\n");
                            }

                            fileWriter.flush();

                            // Mover archivo a la carpeta Descargas
                            moverArchivoADescargas(archivoTemporal);

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Error al guardar archivo CSV", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "No se encontraron datos en Firebase", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(requireContext(), "Error al descargar datos: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void moverArchivoADescargas(File archivoTemporal)
        {
            // Ruta de destino en la carpeta de Descargas
            File destino = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "asistencias_descargadas.csv");

            try (FileInputStream fis = new FileInputStream(archivoTemporal);
                 FileOutputStream fos = new FileOutputStream(destino)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }

                Toast.makeText(requireContext(), "Archivo guardado en Descargas", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Error al mover archivo a Descargas", Toast.LENGTH_SHORT).show();
            }
        }


    }
