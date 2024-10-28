package com.example.atenea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class welcome extends AppCompatActivity {

    Button btnloginwelcome,btnregisterwelcome;

    boolean botonBackPresionado= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnloginwelcome = findViewById(R.id.btnloginwelcome);
        btnloginwelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!botonBackPresionado) {
                    Intent ventana2 = new Intent(welcome.this, login.class);
                    startActivity(ventana2);
                }

            }
        });

        btnregisterwelcome = findViewById(R.id.btnregisterwelcome);
        btnregisterwelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!botonBackPresionado) {
                    Intent ventana3 = new Intent(welcome.this, register.class);
                    startActivity(ventana3);
                }

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        botonBackPresionado=true;


    }

}