package com.mad.f1k375.susunkata;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LanjutLvl extends AppCompatActivity {

    String[] konten=new String[2];
    Database db = null;
    int cek = 0;
    Button back = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanjut_lvl);
        Bundle terima = getIntent().getExtras();
        konten = terima.getStringArray("naikLvl");
        String[] data = new String[]{konten[0],String.valueOf(Integer.parseInt(konten[1])-1)};
        db = new Database(this);
        Cursor bonus =  db.getInfoGambar(data);
        cek = bonus.getInt(bonus.getColumnIndex("bonus"));
        if(cek==1) {
            Button btn = (Button) findViewById(R.id.lanjut);
            btn.setLayoutParams(new LinearLayout.LayoutParams(210,80));
            btn.setBackgroundResource(R.drawable.btn_bonus);
        }
        back = (Button)findViewById(R.id.kembali);
        back.setVisibility(View.INVISIBLE);
    }

    public void lanjut(View view){
        Intent lanjut =null;
        Bundle kirim = new Bundle();
        if(Integer.parseInt(konten[1])<7||cek==1) {
            if(cek==1){
                lanjut = new Intent(this, bonus.class);
            }else{
                lanjut = new Intent(this, tebak_namanya.class);
            }
            kirim.putStringArray("soal", konten);
            lanjut.putExtras(kirim);
            startActivity(lanjut);
            finish();
        }else{
            Toast.makeText(this,"yeeee, susun kata "+konten[0]+" selesai", Toast.LENGTH_SHORT).show();
            back.setVisibility(View.VISIBLE);
        }
    }

    public void back(View view){
        Intent lanjut = null;
        Bundle kirim = new Bundle();
        lanjut = new Intent(this,game.class);
        kirim.putString("soal",konten[0]);
        lanjut.putExtras(kirim);
        startActivity(lanjut);
        finish();
    }
}
