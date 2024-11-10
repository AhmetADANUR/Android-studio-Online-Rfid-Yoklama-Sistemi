package com.example.onlinerfid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class kayit extends AppCompatActivity {

    Button btnRFIDTanit,btnKAYIT;
    TextView textViewRfidNo;
    EditText Tc,TelNo,OkulNo,Sinif,Ad,Soyad;
    RadioGroup radioGroupCinsiyet;
    RadioButton radioButtonErkek, radioButtonKadin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

       // btnRFIDTanit = findViewById(R.id.RFIDTanit);
        btnKAYIT = findViewById(R.id.buttonKayit);

        textViewRfidNo = findViewById(R.id.textViewRfidNo);

        Tc = findViewById(R.id.editTextTc);
        TelNo = findViewById(R.id.editTextTelNo);
        OkulNo = findViewById(R.id.editTextOkulNo);
        Sinif = findViewById(R.id.editTextSinif);
        Ad = findViewById(R.id.editTextAd);
        Soyad = findViewById(R.id.editTextSoyad);

        radioGroupCinsiyet = findViewById(R.id.radioGroupCinsiyet);
        radioButtonErkek = findViewById(R.id.radioButtonErkek);
        radioButtonKadin = findViewById(R.id.radioButtonKadin);

        Intent intent = getIntent();
        String data = intent.getStringExtra("RFIDNo");

        textViewRfidNo.setText(data);

       // btnRFIDTanit.setOnClickListener(new View.OnClickListener() {
          //  @Override
          //  public void onClick(View v) {
               /* FirebaseDatabase database = FirebaseDatabase.getInstance();
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
                                    textViewRfidNo.setText(rfidValue);
                                    FirebaseDatabase databases = FirebaseDatabase.getInstance();
                                    DatabaseReference myRefs = databases.getReference("RFIDNo/RFID");
                                    myRefs.setValue("________");
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("KartOkuma/Durum");
                                    myRef.setValue("Pasif");
                                }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(kayit.this, "Veri okunurken hata oluştu", Toast.LENGTH_SHORT).show();
                        }
                    });*/
           // }
     //   });

        btnKAYIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewRfidNo.getText().equals("________"))
                {
                    Toast.makeText(kayit.this, "Lütfen RFID Kart Tanıtıp Kayıda Bu Şekilde Devam Ediniz!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users/"+textViewRfidNo.getText().toString());
                    String STc = Tc.getText().toString();
                    String STelNo = TelNo.getText().toString();
                    String SOkulNo = OkulNo.getText().toString();
                    String SSinif = Sinif.getText().toString();
                    String SAd = Ad.getText().toString();
                    String SSoyad = Soyad.getText().toString();
                    int selectedId = radioGroupCinsiyet.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String SCinsiyet = selectedRadioButton.getText().toString();


                    Users userse = new Users(STc, STelNo, SOkulNo, SSinif, SAd, SSoyad, SCinsiyet);

                    myRef.setValue(userse);

                    Tc.setText("");
                    TelNo.setText("");
                    OkulNo.setText("");
                    Sinif.setText("");
                    Ad.setText("");
                    Soyad.setText("");
                    textViewRfidNo.setText("________");

                    Intent intent = new Intent(kayit.this, RFIDAL.class);
                    startActivity(intent);
                }
            }
        });


    }
}

class Users {
    public String tc;
    public String telNo;
    public String okulNo;
    public String sinif;
    public String ad;
    public String soyad;
    public String cinsiyet;

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(String tc, String telNo, String okulNo, String sinif, String ad, String soyad, String cinsiyet) {
        this.tc = tc;
        this.telNo = telNo;
        this.okulNo = okulNo;
        this.sinif = sinif;
        this.ad = ad;
        this.soyad = soyad;
        this.cinsiyet = cinsiyet;
    }
}