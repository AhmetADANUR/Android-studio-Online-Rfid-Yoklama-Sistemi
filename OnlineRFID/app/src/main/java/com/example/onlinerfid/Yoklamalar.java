package com.example.onlinerfid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Yoklamalar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private YoklamalarAdapter adapter;
    private ArrayList<String> yoklamaList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoklamalar);

        recyclerView = findViewById(R.id.recyclerViewYoklamalar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        yoklamaList = new ArrayList<>();
        adapter = new YoklamalarAdapter(yoklamaList);
        recyclerView.setAdapter(adapter);

        // Firebase Realtime Database referansını alın
        databaseReference = FirebaseDatabase.getInstance().getReference("Yoklamalar");

        // Firebase'den verileri dinleyin ve listeye ekleyin
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                yoklamaList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String yoklama = snapshot.getKey();
                    if (yoklama != null) {
                        yoklamaList.add(yoklama);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Yoklamalar.this, "Veri çekme hatası: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });

    }
}
