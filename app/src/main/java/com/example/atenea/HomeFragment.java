package com.example.atenea;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends BaseFragment {

        //XML donde se declara el nombre a cambiarse
    private TextView TextViewUserName;

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
                String firstName = displayName.split(" ")[0];
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

        return view;
    }
}
