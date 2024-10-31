package com.example.atenea;

import android.content.Intent;
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

//ABRA QUE VALIDAR LA SALIDA CON EL FINISH MAYBE
    private void ventanaexit() {
        finish();
    }

    private void ventanasettings() {
        Intent intent = new Intent(this, menu_settings.class);
        startActivity(intent);
    }

    private void ventanahelp() {
        Intent intent = new Intent(this, menu_help.class);
        startActivity(intent);
    }

    private void ventanaabout() {
        Intent intent = new Intent(this, menu_about_us.class);
        startActivity(intent);
    }

    private void ventanaprofile() {
        Intent intent = new Intent(this, menu_profile.class);
        startActivity(intent);
    }

}
