package com.example.atenea;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class AddFragment extends Fragment {


    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        // Configuración de GoogleSignInClient
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        // Configuración del menú contextual en el ImageView
        ImageView menuprofile2 = view.findViewById(R.id.buttonprofile);

        if (menuprofile2 != null) {
            menuprofile2.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(getContext(), v);
                popup.getMenuInflater().inflate(R.menu.menu_profile, popup.getMenu());

                popup.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.op1) {
                        ventanaprofile();
                        return true;
                    } else if (item.getItemId() == R.id.op2) {
                        ventanaabout();
                        return true;
                    } else if (item.getItemId() == R.id.op3) {
                        ventanahelp();
                        return true;
                    } else if (item.getItemId() == R.id.op4) {
                        ventanasettings();
                        return true;
                    } else if (item.getItemId() == R.id.op5) {


                        ventanaexit();
                        return true;
                    }
                    return false;
                });

                popup.show();
            });
        }

        return view;
    }

    // Implementación de métodos para cada opción del menú
    public void ventanaprofile() {
        Intent intent = new Intent(requireActivity(), menu_profile.class);
        startActivity(intent);

    }
    public void ventanaabout() {
        Intent intent = new Intent(requireActivity(), menu_about_us.class);
        startActivity(intent);
    }
    public void ventanahelp() {
        Intent intent = new Intent(requireActivity(), menu_help.class);
        startActivity(intent);
    }
    public void ventanasettings() {
        Intent intent = new Intent(requireActivity(), menu_settings.class);
        startActivity(intent);
    }
    public void ventanaexit() {

        FirebaseAuth.getInstance().signOut();

        // Cierra la sesión en la cuenta de Google
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
            // Redirige al usuario a la pantalla de bienvenida después de cerrar sesión
            Intent intent = new Intent(requireActivity(), welcome.class);
            startActivity(intent);
            requireActivity().finish();
        });


    }
}
