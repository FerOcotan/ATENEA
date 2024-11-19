package com.example.atenea;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    //obtener datos de user para escribir//
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userId = auth.getCurrentUser().getUid(); // Obtener UID del usuario actual
    //obtener datos de user para escribir//

    //XML donde se declara el nombre a cambiarse
    private TextView TextViewUserName;

    private RecyclerView recyclerView;
    private CardAdapter adapter;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextViewUserName = view.findViewById(R.id.textView10);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Si el usuario está logueado, obtiene el nombre

            //CAMBIAR A MAYUSCULAS!!!!
            String displayName = user.getDisplayName();
            if (displayName != null && !displayName.isEmpty()) {
                // Divide el nombre completo y toma solo el primer nombre
                String firstName = displayName.split(" ")[0].toUpperCase();
                // Asigna el primer nombre al TextView
                TextViewUserName.setText(firstName);
            } else {
                // Si el nombre está vacío, un texto por defecto
                TextViewUserName.setText("FRIEND");
            }
        } else {
            // Si no hay usuario logueado, un texto por defecto
            TextViewUserName.setText("Invitado");
        }

        // Configuración del menú en el ImageView del fragmento Home
        ImageView menuprofile2 = view.findViewById(R.id.buttonprofile);
        setupProfileMenu(menuprofile2);

        //se modifica para que segun el usuario pueda escribir//
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference materiasRef = database.getReference("users").child(userId).child("materias");
        //se modifica para que segun el usuario pueda escribir//

        DatabaseReference listasRef = database.getReference("users").child(userId).child("lista");



        // Configuramos el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Creamos una lista para almacenar los datos
        List<CardItem> cardItems = new ArrayList<>();

        // Configuramos el adaptador
        adapter = new CardAdapter(cardItems);
        recyclerView.setAdapter(adapter);

        // Leer datos desde Firebase
        materiasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                cardItems.clear(); // Limpiamos la lista para evitar duplicados
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataClass materia = snapshot.getValue(DataClass.class); // Mapear el objeto
                    if (materia != null) {
                        // Convertir a CardItem y agregar a la lista
                        cardItems.add(new CardItem(
                                materia.getNombre_materia(),
                                materia.getHora_inicio(),
                                materia.getCodigo()
                        ));
                    }
                }
                adapter.notifyDataSetChanged(); // Actualizamos el adaptador
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejo de errores
                Log.e("FirebaseError", databaseError.getMessage());
            }
        });

        // Listener para "lista"
        listasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataClass2 lista = snapshot.getValue(DataClass2.class);
                    if (lista != null) {
                        cardItems.add(new CardItem(
                                lista.getMateria(),
                                "N/A", // Hora no está en DataClass2
                                lista.getUni()
                        ));
                    }
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {
                Log.e("FirebaseError", "Error leyendo lista: " + databaseError.getMessage());

            }
        });

        return view;
    }
}
