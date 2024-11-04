package com.example.atenea;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


    public class reportsfragment extends BaseFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_reportsfragment, container, false);

            // Configuración del menú en el ImageView del fragmento
            ImageView menuprofile2 = view.findViewById(R.id.buttonprofile);
            setupProfileMenu(menuprofile2);

            return view;
        }
    }
