package com.example.atenea;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class listfragment extends BaseFragment {

    private TextView tvName;
    private View viewCreateSubject, viewListSubject;
    Button btnagregarmateria;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<DataClass2> dataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    SearchView searchView;


    MyAdapter2 adapter;


    private Spinner uni,materia;



    //obtener datos de user para escribir//
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String userId = auth.getCurrentUser().getUid(); // Obtener UID del usuario actual
    //obtener datos de user para escribir//




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listfragment, container, false);

        uni = (Spinner) view.findViewById(R.id.uni);
        materia = (Spinner) view.findViewById(R.id.materia);
        btnagregarmateria = (Button) view.findViewById(R.id.btnagregarmateria);

        // Inicializar Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference materiasRef = database.getReference("users").child(userId).child("materias");

        // Lista para almacenar las materias
        List<String> materiasList = new ArrayList<>();
        materiasList.add("Selecciona una materia"); // Opción por defecto

        // Configurar adaptador del Spinner
        ArrayAdapter<String> materiasAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, materiasList);
        materiasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materia.setAdapter(materiasAdapter);

        // Escuchar cambios en la tabla materias de Firebase
        materiasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                materiasList.clear();
                materiasList.add("Selecciona una materia"); // Mantener opción por defecto

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    String nombreMateria = itemSnapshot.child("nombre_materia").getValue(String.class);
                    if (nombreMateria != null) {
                        materiasList.add(nombreMateria);
                    }
                }

                // Notificar cambios al adaptador
                materiasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled( DatabaseError error) {
                Toast.makeText(requireContext(), "Error al cargar materias", Toast.LENGTH_SHORT).show();
            }
        });




        btnagregarmateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unii = uni.getSelectedItem().toString();

                String materiaa = materia.getSelectedItem().toString();



                // Validar que ambos Spinners tengan valores seleccionados
                if ("Selecciona una universidad".equals(unii)) {
                    Toast.makeText(requireContext(), "Por favor selecciona una universidad", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("Selecciona una materia".equals(materiaa)) {
                    Toast.makeText(requireContext(), "Por favor selecciona una materia", Toast.LENGTH_SHORT).show();
                    return;
                }


                AgregandoLista(unii, materiaa);
            }


        });




            //oBTECION DE RECYLC//

        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.search);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new MyAdapter2(requireContext(), dataList);
        recyclerView.setAdapter(adapter);




        DatabaseReference listasref = database.getReference("users").child(userId).child("lista");
        dialog.show();




        eventListener = listasref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClass2 dataClass = itemSnapshot.getValue(DataClass2.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled( DatabaseError error) {
                dialog.dismiss();

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });




        // Configuración del menú en el ImageView del fragmento Home
        ImageView menuprofile2 = view.findViewById(R.id.buttonprofile);
        setupProfileMenu(menuprofile2);

        // Obtener el usuario actual de Firebase
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       // if (user != null) {
            // Establecer el nombre en el TextView
           // tvName.setText(user.getDisplayName());
       // } else {
       //     tvName.setText("No user logged in");
        //}

        // Inicializar vistas
        viewCreateSubject = view.findViewById(R.id.card_create_list);
        viewListSubject = view.findViewById(R.id.view_list_subject);

        // Botones
        Button buttonCreateSubject = view.findViewById(R.id.button_create_subject);
        Button buttonListSubject = view.findViewById(R.id.button_list_subject);

        // Configurar click listeners para cambiar entre las vistas
        buttonCreateSubject.setOnClickListener(v -> showCreateSubjectView());
        buttonListSubject.setOnClickListener(v -> showListSubjectView());

        return view;
    }



    private void AgregandoLista(String unii, String materiaa) {

        HashMap<String, Object> quoteHashmap = new HashMap<>();
        quoteHashmap.put("uni", unii);
        quoteHashmap.put("materia", materiaa);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference listasref = database.getReference("users").child(userId).child("lista");

        String key = listasref.push().getKey();

        quoteHashmap.put("key", key);

        listasref.child(key).setValue(quoteHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                Toast.makeText(requireContext(), "Lista agregada", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void showCreateSubjectView() {
        viewCreateSubject.setVisibility(View.VISIBLE);
        viewListSubject.setVisibility(View.GONE);
    }

    private void showListSubjectView() {
        viewCreateSubject.setVisibility(View.GONE);
        viewListSubject.setVisibility(View.VISIBLE);
    }

    public void searchList(String text){
        ArrayList<DataClass2> searchList = new ArrayList<>();
        for (DataClass2 dataClass: dataList){
            if (dataClass.getMateria().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }
}