package com.example.atenea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class BaseFragment extends Fragment {

    protected GoogleSignInClient mGoogleSignInClient;
    private ShapeableImageView buttonProfile;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //PARA CARGAR LA IMAGEN
        buttonProfile = view.findViewById(R.id.buttonprofile);

        // Configuración de GoogleSignInClient

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Carga la imagen de perfil en el ShapeableImageView
        String photoUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null;
        if (photoUrl != null) {
            Glide.with(this)
                    .load(photoUrl)
                    .placeholder(R.drawable.profileimage) // Imagen de placeholder
                    .error(R.drawable.profileimage) // Imagen de error
                    .into(buttonProfile);
        } else {
            // Si no hay URL de imagen, usa la imagen por defecto
            buttonProfile.setImageResource(R.drawable.profileimage);
        }

    }


    protected void setupProfileMenu(ImageView menuIcon) {
        if (menuIcon != null) {
            menuIcon.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(getContext(), v);
                popup.getMenuInflater().inflate(R.menu.menu_profile, popup.getMenu());

                popup.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.op1) {
                        startActivity(new Intent(requireActivity(), menu_profile.class));
                        return true;
                    } else if (item.getItemId() == R.id.op2) {
                        startActivity(new Intent(requireActivity(), menu_about_us.class));
                        return true;
                    } else if (item.getItemId() == R.id.op3) {
                        startActivity(new Intent(requireActivity(), menu_help.class));
                        return true;
                    } else if (item.getItemId() == R.id.op4) {
                        startActivity(new Intent(requireActivity(), menu_settings.class));
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
    }

    private void ventanaexit() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
            Intent intent = new Intent(requireActivity(), welcome.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}
