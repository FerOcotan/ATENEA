package com.example.atenea;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class listfragment extends BaseFragment {

    private TextView tvName;
    private View viewCreateSubject, viewListSubject;
    Button btnagregarmateria;


    private EditText uni,materia;


    //obtener datos de user para escribir//
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userId = auth.getCurrentUser().getUid(); // Obtener UID del usuario actual
    //obtener datos de user para escribir//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listfragment, container, false);

        uni = (EditText) view.findViewById(R.id.uni);
        materia = (EditText) view.findViewById(R.id.materia);
        btnagregarmateria = (Button) view.findViewById(R.id.btnagregarmateria);


        btnagregarmateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unii = uni.getText().toString();
                String materiaa = materia.getText().toString();


                if (unii.isEmpty()) {
                    uni.setError("Por favor ingrese la universidad");
                    return;
                }
                if (materiaa.isEmpty()) {
                    materia.setError("Por favor ingrese el nombre de la materia");
                    return;
                }


                AgregandoLista(unii, materiaa);
            }


        });












        // Configuración del menú en el ImageView del fragmento Home
        ImageView menuprofile2 = view.findViewById(R.id.buttonprofile);
        setupProfileMenu(menuprofile2);

        // Obtener el usuario actual de Firebase
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       // if (user != null) {
            // Establecer el nombre en el TextView
           // tvName.setText(user.getDisplayName());
       // } else {
       //     tvName.setText("No user logged in");
        //}

        // Inicializar vistas
        viewCreateSubject = view.findViewById(R.id.card_create_list);
        viewListSubject = view.findViewById(R.id.view_list_subject);

        // Botones
        Button buttonCreateSubject = view.findViewById(R.id.button_create_subject);
        Button buttonListSubject = view.findViewById(R.id.button_list_subject);

        // Configurar click listeners para cambiar entre las vistas
        buttonCreateSubject.setOnClickListener(v -> showCreateSubjectView());
        buttonListSubject.setOnClickListener(v -> showListSubjectView());

        return view;
    }

    private void AgregandoLista(String unii, String materiaa) {

        HashMap<String, Object> quoteHashmap = new HashMap<>();
        quoteHashmap.put("uni", unii);
        quoteHashmap.put("materia", materiaa);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference listasref = database.getReference("users").child(userId).child("lista");

        String key = listasref.push().getKey();
        quoteHashmap.put("key", key);

        listasref.child(key).setValue(quoteHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                Toast.makeText(requireContext(), "Lista agregada", Toast.LENGTH_SHORT).show();
                uni.getText().clear();
                materia.getText().clear();


            }
        });
    }

    private void showCreateSubjectView() {
        viewCreateSubject.setVisibility(View.VISIBLE);
        viewListSubject.setVisibility(View.GONE);
    }

    private void showListSubjectView() {
        viewCreateSubject.setVisibility(View.GONE);
        viewListSubject.setVisibility(View.VISIBLE);
    }
}