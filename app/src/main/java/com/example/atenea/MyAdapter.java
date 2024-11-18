package com.example.atenea;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recImage);
        holder.codigomateria.setText(dataList.get(position).getCodigo());
        holder.nombremateria.setText(dataList.get(position).getNombre_materia());
        holder.seccion.setText("Sección: " + dataList.get(position).getSeccion());
        holder.participantes.setText("Participantes: " +dataList.get(position).getParticipantes());
        holder.horainicio.setText("Hora inicio: " +dataList.get(position).getHora_inicio());
        holder.horasalida.setText("Hora salida: " +dataList.get(position).getHora_salida());
        holder.creador.setText("Docente: " +dataList.get(position).getCarnet_creador());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("codigo", dataList.get(holder.getAdapterPosition()).getCodigo());
                intent.putExtra("nombre_materia", dataList.get(holder.getAdapterPosition()).getNombre_materia());
                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("seccion", dataList.get(holder.getAdapterPosition()).getSeccion());
                intent.putExtra("participantes", dataList.get(holder.getAdapterPosition()).getParticipantes());
                intent.putExtra("hora_inicio", dataList.get(holder.getAdapterPosition()).getHora_inicio());
                intent.putExtra("hora_salida", dataList.get(holder.getAdapterPosition()).getHora_salida());
                intent.putExtra("carnet_creador", dataList.get(holder.getAdapterPosition()).getCarnet_creador());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();


    }

    public void searchDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();

    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView codigomateria,nombremateria,participantes,seccion,horainicio,horasalida,creador;
    CardView recCard;

    public MyViewHolder(View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        nombremateria = itemView.findViewById(R.id.tmateria);
        codigomateria = itemView.findViewById(R.id.tcodigo);
        participantes = itemView.findViewById(R.id.tparticipantes);
        seccion = itemView.findViewById(R.id.tseccion);
        horainicio = itemView.findViewById(R.id.thoraini);
        horasalida = itemView.findViewById(R.id.thorasali);
        creador = itemView.findViewById(R.id.docente);

    }
}
