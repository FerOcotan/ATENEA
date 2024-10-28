package com.example.atenea;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView menuprofile2 = findViewById(R.id.buttonprofile);


        menuprofile2.setOnClickListener(v -> {PopupMenu popup = new PopupMenu(this, v);

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

    private void ventanaexit() {
        finish();
    }

    private void ventanasettings() {
        // Código para abrir la configuración
    }

    private void ventanahelp() {
        // Código para abrir la ayuda
    }

    private void ventanaabout() {
        // Código para mostrar información sobre la aplicación
    }

    private void ventanaprofile() {
        // Código para abrir el perfil
    }
}
