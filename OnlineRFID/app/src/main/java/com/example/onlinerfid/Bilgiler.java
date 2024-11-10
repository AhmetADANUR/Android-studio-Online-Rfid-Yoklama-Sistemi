package com.example.onlinerfid;

import static java.lang.Math.log;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.util.List;

public class Bilgiler extends AppCompatActivity {

TextView tvCinsiyet,tvTc,tvAd,tvSoyad,tvTelNo,tvOkulNo,tvSinif;
Button bitirS;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private ArrayList<Kullanici> kullaniciList;
    ArrayList<String> adList = new ArrayList<>();
    ArrayList<String> soyadList = new ArrayList<>();
    ArrayList<String> tcList = new ArrayList<>();
    ArrayList<String> kartNoList = new ArrayList<>(); // Kart numarası listesi

    ArrayList<String> adList2 = new ArrayList<>();
    ArrayList<String> soyadList2 = new ArrayList<>();
    ArrayList<String> cinsiyetList2 = new ArrayList<>();
    ArrayList<String> okulNoList2 = new ArrayList<>();
    ArrayList<String> sinifList2 = new ArrayList<>();
    ArrayList<String> telNoList2 = new ArrayList<>();
    ArrayList<String> tcList2 = new ArrayList<>();

    ArrayList<String> kartNumarasiBulunanKullanicilarAdList = new ArrayList<>();
    ArrayList<String> kartNumarasiBulunanKullanicilarSoyadList = new ArrayList<>();
    ArrayList<String> kartNumarasiBulunanKullanicilarkartno = new ArrayList<>();

    boolean kullaniciVar = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilgiler);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("YoklamaAlmaDurumu/durumu");
        myRef.setValue("Aktif");
        DatabaseReference myRefs = database.getReference("YoklamaAlmaDurumu/KartNo");
        myRefs.setValue("______");

        tvCinsiyet = findViewById(R.id.textViewCinsiyet);
        tvTc = findViewById(R.id.textViewTc);
        tvAd = findViewById(R.id.textViewAd);
        tvSoyad = findViewById(R.id.textViewSoyad);
        tvTelNo = findViewById(R.id.textViewTelNo);
        tvOkulNo = findViewById(R.id.textViewOkulNo);
        tvSinif = findViewById(R.id.textViewSinif);
        bitirS = findViewById(R.id.bitir);

        String yoklamaAdi = getIntent().getStringExtra("YOKLAMA_ADI");

        Toast.makeText(Bilgiler.this, yoklamaAdi, Toast.LENGTH_SHORT).show();

        String yoklamaAdis = yoklamaAdi; // İlgili yoklamanın adı

        DatabaseReference yoklamaRef = FirebaseDatabase.getInstance().getReference("Yoklamalar").child(yoklamaAdis);

        yoklamaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.child("BeklenenKullanicilar").getChildren()) {
                    String ad = snapshot.child("ad").getValue(String.class);
                    String soyad = snapshot.child("soyad").getValue(String.class);
                    String tc = snapshot.child("tc").getValue(String.class);

                    adList.add(ad);
                    soyadList.add(soyad);
                    tcList.add(tc);
                }

                // Burada RecyclerViewAdapter'ınızı güncelleyerek ekranda listeleri gösterebilirsiniz
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Veritabanından veri alınamazsa buraya düşeceğiniz hata işleme kodunu ekleyebilirsiniz
            }
        });


        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tc = snapshot.child("tc").getValue(String.class);

                    if (tcList.contains(tc)) {
                        String kartNo = snapshot.getKey(); // Kart numarası
                        kartNoList.add(kartNo);

                        String ad = snapshot.child("ad").getValue(String.class);
                        String soyad = snapshot.child("soyad").getValue(String.class);
                        String cinsiyet = snapshot.child("cinsiyet").getValue(String.class);
                        String okulNo = snapshot.child("okulNo").getValue(String.class);
                        String sinif = snapshot.child("sinif").getValue(String.class);
                        String telNo = snapshot.child("telNo").getValue(String.class);
                        String Tc = snapshot.child("tc").getValue(String.class);

                        adList2.add(ad);
                        soyadList2.add(soyad);
                        cinsiyetList2.add(cinsiyet);
                        okulNoList2.add(okulNo);
                        sinifList2.add(sinif);
                        telNoList2.add(telNo);
                        tcList2.add(Tc);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Hata durumunda yapılacak işlemler
            }
        });

        bitirS.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DatabaseReference myRef = database.getReference("YoklamaAlmaDurumu/durumu");
        myRef.setValue("Pasif");
        finish();
    }
});


        DatabaseReference kartNoRef = FirebaseDatabase.getInstance().getReference("YoklamaAlmaDurumu").child("KartNo");
        kartNoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                DatabaseReference kartNumarasiRef = FirebaseDatabase.getInstance().getReference("KartNumarasiBulunanKullanicilar");

                kartNumarasiRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String kartNumarasi = snapshot.getKey(); // Kart numarasını al
                            kartNumarasiBulunanKullanicilarkartno.add(kartNumarasi);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Hata durumunda yapılacak işlemler
                    }
                });



                String yeniKartNo = dataSnapshot.getValue(String.class);
                // Kart numarası değiştiğinde yapılacak işlemler
                if (yeniKartNo != null) {
                    int index = kartNoList.indexOf(yeniKartNo);
                    if (index != -1) {
                        // Kart numarası bulundu
                        tvAd.setText(adList2.get(index));
                        tvSoyad.setText(soyadList2.get(index));
                        tvCinsiyet.setText(cinsiyetList2.get(index));
                        tvTc.setText(tcList2.get(index));
                        tvTelNo.setText(telNoList2.get(index));
                        tvOkulNo.setText(okulNoList2.get(index));
                        tvSinif.setText(sinifList2.get(index));

                        for (String kartNo : kartNumarasiBulunanKullanicilarkartno) {
                            if (kartNo.equals(yeniKartNo)) {
                                kullaniciVar = true;
                                break;
                            }
                        }
                        if (!kullaniciVar) {
                            // Kart numarası bulunan kullanıcıyı ilgili yoklama adının altında sakla
                            DatabaseReference kartNumarasiBulunanKullaniciRef = FirebaseDatabase.getInstance().getReference("Yoklamalar").child(yoklamaAdis).child("KartNumarasiBulunanKullanicilar/" + yeniKartNo);
                            kartNumarasiBulunanKullaniciRef.child("ad").setValue(adList2.get(index));
                            kartNumarasiBulunanKullaniciRef.child("soyad").setValue(soyadList2.get(index));
                            kartNumarasiBulunanKullaniciRef.child("cinsiyet").setValue(cinsiyetList2.get(index));
                            kartNumarasiBulunanKullaniciRef.child("tc").setValue(tcList2.get(index));
                            kartNumarasiBulunanKullaniciRef.child("telNo").setValue(telNoList2.get(index));
                            kartNumarasiBulunanKullaniciRef.child("okulNo").setValue(okulNoList2.get(index));
                            kartNumarasiBulunanKullaniciRef.child("sinif").setValue(sinifList2.get(index));

                            // Arka plan rengini ayarla
                            LinearLayout rootLayout = findViewById(R.id.rootLayout);
                            rootLayout.setBackgroundColor(Color.parseColor("#00ff00"));
                        }
                        else {
                            LinearLayout rootLayout = findViewById(R.id.rootLayout);
                            rootLayout.setBackgroundColor(Color.parseColor("#00007f"));
                            Toast.makeText(Bilgiler.this, "Kullanıcı daha önce giriş yaptı", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(Bilgiler.this, "Kart numarası bulunamadı", Toast.LENGTH_SHORT).show();
                        LinearLayout rootLayout = findViewById(R.id.rootLayout);
                        rootLayout.setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
                else
                {
                    Toast.makeText(Bilgiler.this, "Kart numarası silindi", Toast.LENGTH_SHORT).show();
                }
                DatabaseReference yoklamasRef = FirebaseDatabase.getInstance().getReference("Yoklamalar").child(yoklamaAdis);

                yoklamasRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Beklenen kişi sayısını al
                        int beklenenKisiSayisi = (int) dataSnapshot.child("BeklenenKullanicilar").getChildrenCount();

                        // Kart numarası bulunan kişi sayısını al
                        int bulunanKisiSayisi = (int) dataSnapshot.child("KartNumarasiBulunanKullanicilar").getChildrenCount();

                        // Beklenen ve gelen kişi sayılarını TextView'lara yazdır
                        TextView textViewBeklenenKisiSayisi = findViewById(R.id.textViewBeklenenKisiSayisi);
                        textViewBeklenenKisiSayisi.setText(String.valueOf(beklenenKisiSayisi));

                        TextView textViewGelenKisiSayisi = findViewById(R.id.textViewGelenKisiSayisi);
                        textViewGelenKisiSayisi.setText(String.valueOf(bulunanKisiSayisi));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Veri alınamazsa yapılacak işlemler
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Hata durumunda yapılacak işlemler
            }
        });




    }
}