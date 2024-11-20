package com.example.atenea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {

    private List<Document> documentList;
    private Context context;
    private OnDownloadClickListener listener;

    public DocumentAdapter(Context context, List<Document> documentList, OnDownloadClickListener listener) {
        this.context = context;
        this.documentList = documentList;
        this.listener = listener;
    }


    @Override
    public DocumentViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_document, parent, false);
        return new DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder( DocumentViewHolder holder, int position) {
        Document document = documentList.get(position);
        holder.documentName.setText(document.getUni());
        holder.documentuniver.setText(document.getMateria());

        holder.downloadButton.setOnClickListener(v -> {
            // Llamar al listener definido en el fragmento
            listener.onDownloadClick(document.getMateria(), document.getUni());
        });
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public static class DocumentViewHolder extends RecyclerView.ViewHolder {
        TextView documentName;
        TextView documentuniver;
        Button downloadButton;

        public DocumentViewHolder( View itemView) {
            super(itemView);
            documentName = itemView.findViewById(R.id.documentName);
            documentuniver = itemView.findViewById(R.id.documentuniver);
            downloadButton = itemView.findViewById(R.id.downloadButton);
        }
    }

    public interface OnDownloadClickListener {
        void onDownloadClick(String filePath, String fileName);
    }
}
