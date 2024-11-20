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

public class MyAdapter2 extends RecyclerView.Adapter<MyViewHolder2> {
    private Context context;
    private List<DataClass2> dataList;

    public MyAdapter2(Context context, List<DataClass2> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item2, parent, false);
        return new MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder2 holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.recImage);
        holder.uni.setText(dataList.get(position).getUni());
        holder.materia.setText(dataList.get(position).getMateria());

        holder.recCard.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity2.class);
            intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
            intent.putExtra("uni", dataList.get(holder.getAdapterPosition()).getUni());
            intent.putExtra("materia", dataList.get(holder.getAdapterPosition()).getMateria());
            intent.putExtra("Key", dataList.get(holder.getAdapterPosition()).getKey());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass2> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }
}


class MyViewHolder2 extends RecyclerView.ViewHolder {
    ImageView recImage;
    TextView uni, materia;
    CardView recCard;

    public MyViewHolder2(View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        uni = itemView.findViewById(R.id.tuni);
        materia = itemView.findViewById(R.id.tmateria);
    }
}

