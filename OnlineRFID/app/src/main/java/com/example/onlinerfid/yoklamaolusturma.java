package com.example.onlinerfid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class yoklamaolusturma extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private ArrayList<Kullanici> kullaniciList;
    private ArrayList<Kullanici> secilenKullanicilar;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoklamaolusturma);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        kullaniciList = new ArrayList<>();
        secilenKullanicilar = new ArrayList<>();
        adapter = new UserAdapter(kullaniciList, secilenKullanicilar);
        recyclerView.setAdapter(adapter);

        // Firebase Realtime Database referansını alın
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Firebase'den verileri dinleyin ve listeye ekleyin
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kullaniciList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Kullanici kullanici = snapshot.getValue(Kullanici.class);
                    if (kullanici != null) {
                        kullaniciList.add(kullanici);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(yoklamaolusturma.this, "Veri çekme hatası: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yoklamaAdi = editText.getText().toString().trim();
                if (yoklamaAdi.isEmpty()) {
                    Toast.makeText(yoklamaolusturma.this, "Lütfen yoklama adı girin", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference yoklamaRef = FirebaseDatabase.getInstance().getReference("Yoklamalar").child(yoklamaAdi).child("BeklenenKullanicilar");
                for (Kullanici kullanici : secilenKullanicilar) {
                    String id = yoklamaRef.push().getKey();
                    yoklamaRef.child(id).setValue(kullanici);
                }

                Toast.makeText(yoklamaolusturma.this, "Yoklama oluşturuldu", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
