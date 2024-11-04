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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.example.atenea.databinding.ActivityHomeBinding;


public class home extends AppCompatActivity {

    ActivityHomeBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.add) {
                replaceFragment(new AddFragment());
            } else if (item.getItemId() == R.id.list) {
                replaceFragment(new listfragment());

            } else if (item.getItemId() == R.id.reports) {
                replaceFragment(new reportsfragment());
            }
            return true;

        });

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

    private  void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }


}
