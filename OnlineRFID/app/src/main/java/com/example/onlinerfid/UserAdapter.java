package com.example.onlinerfid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.KullaniciViewHolder> {
    private ArrayList<Kullanici> kullaniciList;
    private ArrayList<Kullanici> secilenKullanicilar;

    public static class KullaniciViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewAd;
        public TextView textViewSoyad;
        public TextView textViewTc;
        public CheckBox checkBox;

        public KullaniciViewHolder(View itemView) {
            super(itemView);
            textViewAd = itemView.findViewById(R.id.textViewAd);
            textViewSoyad = itemView.findViewById(R.id.textViewSoyad);
            textViewTc = itemView.findViewById(R.id.textViewTc);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public UserAdapter(ArrayList<Kullanici> kullaniciList, ArrayList<Kullanici> secilenKullanicilar) {
        this.kullaniciList = kullaniciList;
        this.secilenKullanicilar = secilenKullanicilar;
    }

    @NonNull
    @Override
    public KullaniciViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_card, parent, false);
        return new KullaniciViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KullaniciViewHolder holder, int position) {
        Kullanici currentKullanici = kullaniciList.get(position);
        holder.textViewAd.setText(currentKullanici.getAd());
        holder.textViewSoyad.setText(currentKullanici.getSoyad());
        holder.textViewTc.setText(currentKullanici.getTc());

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(secilenKullanicilar.contains(currentKullanici));
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                secilenKullanicilar.add(currentKullanici);
            } else {
                secilenKullanicilar.remove(currentKullanici);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kullaniciList.size();
    }
}
