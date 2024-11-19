package com.example.atenea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity2 extends AppCompatActivity {

    TextView uni, materia;
    ImageView detailImage;
    String key = "";
    Button deleteButton;
    //obtener datos de user para escribir//
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userId = auth.getCurrentUser().getUid(); // Obtener UID del usuario actual
    //obtener datos de user para escribir//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        uni = findViewById(R.id.detailuni);
        materia = findViewById(R.id.detailmateria);
        detailImage = findViewById(R.id.detailImage);
        deleteButton = findViewById(R.id.deletebutton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            uni.setText(bundle.getString("uni"));
            materia.setText(bundle.getString("materia"));

            key = bundle.getString("Key");

            Glide.with(this).load(R.drawable.baseline_auto_delete_24).into(detailImage);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("users").child(userId).child("lista");


                // Eliminar el objeto de la base de datos directamente
                reference.child(key).removeValue();
                Toast.makeText(DetailActivity2.this, "Lista eliminada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), home.class));
                finish();

            }
        });
    }
}