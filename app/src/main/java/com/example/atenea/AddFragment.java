package com.example.atenea;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.Button;



public class AddFragment extends BaseFragment {

    private View viewCreateSubject, viewListSubject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

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
}




