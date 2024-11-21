package com.example.atenea;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class reportsfragment extends BaseFragment {

    public String  nombreArchivo;

    //obtener datos de user para escribir//
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userId = auth.getCurrentUser().getUid(); // Obtener UID del usuario actual
    //obtener datos de user para escribir//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reportsfragment, container, false);

        // Configuración del menú
        ImageView menuprofile2 = view.findViewById(R.id.buttonprofile);
        setupProfileMenu(menuprofile2);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Document> documentList = new ArrayList<>();
        DocumentAdapter adapter = new DocumentAdapter(getContext(), documentList, (keyLista,materia) -> {
            // Lógica para descargar las asistencias específicas de esta lista
            descargarAsistenciasDeLista(keyLista,materia);
        });

        recyclerView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference documentsRef = database.getReference("users").child(userId).child("lista");

        documentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot listaSnapshot : snapshot.getChildren()) {
                    // Obtener datos de cada lista
                    String keyLista = listaSnapshot.getKey();
                    String materia = listaSnapshot.child("materia").getValue(String.class);
                    String uni = listaSnapshot.child("uni").getValue(String.class);

                    // Añadir a la lista con su key
                    documentList.add(new Document(keyLista, materia, uni));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getContext(), "Error al cargar listas: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void descargarAsistenciasDeLista(String keyLista,String materia) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference asistenciasRef = database.getReference("users")
                .child(userId)
                .child("lista")
                .child(keyLista)
                .child("asistencias");

        asistenciasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Crear el archivo CSV en almacenamiento interno privado


                    // se obtiene la variable del nombre gracias al adpter
                    String nombreArchivo = materia.replace(" ", "_") + "_asistencia.csv"; //
                    File archivoTemporal = new File(requireContext().getExternalFilesDir(null), nombreArchivo);

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

                        // Mover a Descargas
                        moverArchivoADescargas(archivoTemporal,nombreArchivo);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Error al guardar archivo CSV", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "No se encontraron asistencias", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(requireContext(), "Error al acceder a Firebase: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void moverArchivoADescargas(File archivoTemporal, String nombreArchivo)
        {
            // Ruta de destino en la carpeta de Descargas,/ pasa el nombre del archivo como variable obtenida antes
            File destino = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), nombreArchivo);


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
