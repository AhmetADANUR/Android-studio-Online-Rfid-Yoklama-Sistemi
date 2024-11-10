package com.example.onlinerfid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button kayit,sil,yoklamaolustur,yoklmagor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kayit = findViewById(R.id.KullaniciKayit);
        yoklamaolustur = findViewById(R.id.YoklamaOlustur);
        yoklmagor = findViewById(R.id.YoklamaGor);

        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kullKayit = new Intent(MainActivity.this, RFIDAL.class);
                startActivity(kullKayit);
            }
        });
        yoklamaolustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent kullKayit = new Intent(MainActivity.this, yoklamaolusturma.class);
                startActivity(kullKayit);
            }
        });
        yoklmagor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kullKayit = new Intent(MainActivity.this, Yoklamalar.class);
                startActivity(kullKayit);
            }
        });


    }
}