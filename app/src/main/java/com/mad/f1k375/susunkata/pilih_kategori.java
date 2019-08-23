package com.mad.f1k375.susunkata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class pilih_kategori extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_kat);
    }

    public void Play_game(View v){
        Intent jenis_kategori= new Intent(this, game.class);;
        Bundle kirim = new Bundle();
        switch (v.getId()){
            case R.id.hewan:
                kirim.putString("kategori","hewan");
                break;
            case R.id.buah:
                kirim.putString("kategori","buah");
                break;
            case R.id.sayur:
                kirim.putString("kategori","sayur");
                break;
            case R.id.profesi:
                kirim.putString("kategori","profesi");
                break;
            case R.id.keluarga:
                kirim.putString("kategori","keluarga");
                break;
            case R.id.transport:
                kirim.putString("kategori","transport");
                break;
        }
        Log.d("Apa_ya", "ini mau ngirim");
        jenis_kategori.putExtras(kirim);
        startActivity(jenis_kategori);

    }
}
