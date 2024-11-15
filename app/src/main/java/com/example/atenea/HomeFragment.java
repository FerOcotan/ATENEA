package com.example.atenea;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {


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


        // Configuramos el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Agregamos los datos a la lista
        List<CardItem> cardItems = new ArrayList<>();
        cardItems.add(new CardItem("Today Class", "09:20AM", "04 Jan"));
        cardItems.add(new CardItem("Dev App Moviles 'A'", "4:45PM", "09 OCT"));
        cardItems.add(new CardItem("Math IV", "10:20AM", "11 Sep"));
        cardItems.add(new CardItem("Etica 'A'", "4:45PM", "11 Nov"));
        cardItems.add(new CardItem("Dev App Moviles 2 'A'", "4:45PM", "14 OCT"));
        cardItems.add(new CardItem("Math I", "10:20AM", "11 Sep"));



        // Creamos y configuramos el adaptador
        adapter = new CardAdapter(cardItems);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
