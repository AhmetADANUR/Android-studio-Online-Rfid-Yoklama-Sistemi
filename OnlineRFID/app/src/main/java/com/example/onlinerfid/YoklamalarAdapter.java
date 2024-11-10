package com.example.onlinerfid;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class YoklamalarAdapter extends RecyclerView.Adapter<YoklamalarAdapter.YoklamaViewHolder> {
    private ArrayList<String> yoklamaList;

    public static class YoklamaViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewYoklama;

        public YoklamaViewHolder(View itemView) {
            super(itemView);
            textViewYoklama = itemView.findViewById(R.id.textViewYoklama);
        }
    }

    public YoklamalarAdapter(ArrayList<String> yoklamaList) {
        this.yoklamaList = yoklamaList;
    }

    @NonNull
    @Override
    public YoklamaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.yoklama_item, parent, false);
        return new YoklamaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull YoklamaViewHolder holder, int position) {
        String currentYoklama = yoklamaList.get(position);
        holder.textViewYoklama.setText(currentYoklama);

        holder.textViewYoklama.setText(yoklamaList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Bilgiler.class);
                intent.putExtra("YOKLAMA_ADI", currentYoklama);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return yoklamaList.size();
    }

}
