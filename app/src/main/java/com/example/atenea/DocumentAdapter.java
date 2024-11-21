package com.example.atenea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {

    private final Context context;
    private final List<Document> documentList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String keyLista,String materia);
    }

    public DocumentAdapter(Context context, List<Document> documentList, OnItemClickListener listener) {
        this.context = context;
        this.documentList = documentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_document, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Document document = documentList.get(position);
        holder.bind(document, listener);
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewMateria;
        private final TextView textViewUni;
        private final Button btnDescargar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMateria = itemView.findViewById(R.id.documentName);
            textViewUni = itemView.findViewById(R.id.documentuniver);
            btnDescargar = itemView.findViewById(R.id.downloadButton);
        }

        public void bind(Document document, OnItemClickListener listener) {
            textViewMateria.setText(document.getMateria());
            textViewUni.setText(document.getUni());

            btnDescargar.setOnClickListener(v -> listener.onItemClick(document.getKeyLista(), document.getMateria()));
        }
    }
}
