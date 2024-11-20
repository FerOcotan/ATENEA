package com.example.atenea;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private final List<CardItem> cardItemList;

    public CardAdapter(List<CardItem> cardItemList) {
        this.cardItemList = cardItemList;
    }


    @Override
    public CardViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        // Inflar el layout del item_card
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder( CardViewHolder holder, int position) {
        // Vincular los datos con las vistas
        CardItem currentItem = cardItemList.get(position);
        holder.titleTextView.setText(currentItem.getTitle());
        holder.timeDateTextView.setText(currentItem.getTime() + "    " + currentItem.getDate());
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    // Clase ViewHolder para mantener las referencias de las vistas
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, timeDateTextView;

        public CardViewHolder( View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            timeDateTextView = itemView.findViewById(R.id.timeDateTextView);
        }
    }
}
