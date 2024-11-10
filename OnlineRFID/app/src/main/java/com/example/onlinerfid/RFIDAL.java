package com.example.onlinerfid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RFIDAL extends AppCompatActivity {

    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfidal);

        txt = findViewById(R.id.rfidno);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("KartOkuma/Durum");
        myRef.setValue("Aktif");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("RFIDNo");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String rfidValue = dataSnapshot.child("RFID").getValue(String.class);
                if (rfidValue.equals("________"))
                {}
                else
                {
                    txt.setText(rfidValue);
                    FirebaseDatabase databases = FirebaseDatabase.getInstance();
                    DatabaseReference myRefs = databases.getReference("RFIDNo/RFID");
                    myRefs.setValue("________");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("KartOkuma/Durum");
                    myRef.setValue("Pasif");

                    Intent intent = new Intent(RFIDAL.this, kayit.class);
                    intent.putExtra("RFIDNo", txt.getText());
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RFIDAL.this, "Veri okunurken hata oluştu", Toast.LENGTH_SHORT).show();
            }
        });


    }
}