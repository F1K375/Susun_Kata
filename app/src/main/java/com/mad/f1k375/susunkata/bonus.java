package com.mad.f1k375.susunkata;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class bonus extends AppCompatActivity {

    String[] konten=new String[2];
    Database db =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus);

        Bundle terima = getIntent().getExtras();
        konten = terima.getStringArray("soal");

        String[] data = new String[]{konten[0],String.valueOf(Integer.parseInt(konten[1])-1)};
        db = new Database(this);
        Cursor bonus =  db.getInfoGambar(data);

        loadTampilan(bonus);
    }

    private void loadTampilan(Cursor bonus) {
        int gambar = 0;
        ImageView cover = (ImageView)findViewById(R.id.cv_bonus);
        ImageView label = (ImageView)findViewById(R.id.label_bonus);
        ImageView info = (ImageView)findViewById(R.id.info_bonus);

        gambar = getResources().getIdentifier("ic_"+bonus.getString(bonus.getColumnIndex("nama")),"mipmap",getPackageName());
        cover.setBackgroundResource(gambar);

        gambar = getResources().getIdentifier("lbl_"+bonus.getString(bonus.getColumnIndex("nama")),"drawable",getPackageName());
        label.setBackgroundResource(gambar);

        gambar = getResources().getIdentifier("bonus_"+bonus.getString(bonus.getColumnIndex("nama")),"drawable",getPackageName());
        info.setBackgroundResource(gambar);
    }

    public void back(View view){
        if(Integer.parseInt(konten[1])<7){
            String[] data = new String[]{konten[0],konten[1]};
            Cursor bonus =  db.getInfoGambar(data);
            Intent lanjut = null;
            Bundle kirim = new Bundle();
            if(bonus.getInt(bonus.getColumnIndex("status"))==1){
                lanjut = new Intent(this,game.class);
                kirim.putString("kategori",konten[0]);
                lanjut.putExtras(kirim);
            }else{
                lanjut = new Intent(this, tebak_namanya.class);
                kirim.putStringArray("soal", konten);
                lanjut.putExtras(kirim);
            }
            startActivity(lanjut);
            finish();
        }finish();
    }

}
