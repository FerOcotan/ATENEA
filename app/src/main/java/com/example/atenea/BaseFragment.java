package com.example.atenea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public abstract class BaseFragment extends Fragment {

    protected GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configuración de GoogleSignInClient
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
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
