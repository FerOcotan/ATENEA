package com.example.atenea;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;


public class AddFragment extends BaseFragment {

    private View viewCreateSubject, viewListSubject;
    private EditText codigomateria,nombremateria,participantes,seccion,horainicio,horasalida,creador;
    Button btnagregarmateria;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);



        codigomateria = (EditText) view.findViewById(R.id.codigomateria);
        nombremateria = (EditText)  view.findViewById(R.id.nombremateria);
        participantes = (EditText)  view.findViewById(R.id.participantes);
        seccion = (EditText)  view.findViewById(R.id.seccion);
        horainicio = (EditText)  view.findViewById(R.id.horainicio);
        horasalida = (EditText)  view.findViewById(R.id.horasalida);
        creador = (EditText)  view.findViewById(R.id.creador);
        btnagregarmateria = (Button)  view.findViewById(R.id.btnagregarmateria);


        btnagregarmateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = codigomateria.getText().toString();
                String nombremate = nombremateria.getText().toString();
                String participante = participantes.getText().toString();
                String seccionn = seccion.getText().toString();
                String horaini = horainicio.getText().toString();
                String horasali = horasalida.getText().toString();
                String creator = creador.getText().toString();

                if (codigo.isEmpty()){
                    codigomateria.setError("Por favor ingrese el codigo de materia");
                    return;
                }
                if (nombremate.isEmpty()){
                    nombremateria.setError("Por favor ingrese el nombre de la materia");
                    return;
                }
                if (participante.isEmpty()){
                    participantes.setError("Por favor ingrese el numero de participantes");
                    return;
                }
                if (seccionn.isEmpty()){
                    seccion.setError("Por favor ingrese la sección");
                    return;
                }
                if (horaini.isEmpty()){
                    horainicio.setError("Por favor ingrese la hora de inicio");
                    return;
                }
                if (horasali.isEmpty()){
                    horasalida.setError("Por favor ingrese la hora de salida");
                    return;
                }
                if (creator.isEmpty()){
                    creador.setError("Por favor ingrese su carnet");
                    return;
                }

                AgregandoMateria(codigo,nombremate,participante,seccionn,horaini,horasali,creator);

            }
        });






        // Configuración del menú en el ImageView del fragmento
        ImageView menuprofile2 = view.findViewById(R.id.buttonprofile);
        setupProfileMenu(menuprofile2);

        // Inicializar vistas
        viewCreateSubject = view.findViewById(R.id.view_create_subject);
        viewListSubject = view.findViewById(R.id.view_list_subject);

        // Botones
        Button buttonCreateSubject = view.findViewById(R.id.button_create_subject);
        Button buttonListSubject = view.findViewById(R.id.button_list_subject);

        // Configurar click listeners para cambiar entre las vistas
        buttonCreateSubject.setOnClickListener(v -> showCreateSubjectView());
        buttonListSubject.setOnClickListener(v -> showListSubjectView());

        return view;






    }
    private void showCreateSubjectView() {
        viewCreateSubject.setVisibility(View.VISIBLE);
        viewListSubject.setVisibility(View.GONE);
    }

    private void showListSubjectView() {
        viewCreateSubject.setVisibility(View.GONE);
        viewListSubject.setVisibility(View.VISIBLE);
    }
    public void saveData(){

    }

    private void AgregandoMateria(String codigo, String nombremate, String participante, String seccionn, String horaini, String horasali, String creator) {

        HashMap<String, Object> quoteHashmap = new HashMap<>();
        quoteHashmap.put("codigo",codigo);
        quoteHashmap.put("nombre_materia",nombremate);
        quoteHashmap.put("participantes",participante);
        quoteHashmap.put("seccion",seccionn);
        quoteHashmap.put("hora_inicio",horaini);
        quoteHashmap.put("hora_salida",horasali);
        quoteHashmap.put("carnet_creador",creator);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference materiasRef = database.getReference("materia");

        String key = materiasRef.push().getKey();
        quoteHashmap.put("key",key);

        materiasRef.child(key).setValue(quoteHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                Toast.makeText(requireContext(), "Materia agregada", Toast.LENGTH_SHORT).show();
                codigomateria.getText().clear();
                nombremateria.getText().clear();
                participantes.getText().clear();
                seccion.getText().clear();
                horainicio.getText().clear();
                horasalida.getText().clear();
                creador.getText().clear();


            }
        });
        }

}




