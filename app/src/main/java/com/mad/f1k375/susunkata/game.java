package com.mad.f1k375.susunkata;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class game extends AppCompatActivity {

    Database db = null;
    String kategori=null;
    Button item[]= null;
    int akses[] = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle terima = getIntent().getExtras();
        db = new Database(this);
        kategori = terima.getString("kategori");
        Log.d("kategori",kategori);
        item = new Button[]{(Button) findViewById(R.id.item_1), (Button) findViewById(R.id.item_2), (Button) findViewById(R.id.item_3)
                , (Button) findViewById(R.id.item_4), (Button) findViewById(R.id.item_5), (Button) findViewById(R.id.item_6)};
        loadIcon(kategori);

    }

    private void loadIcon(String jenis){
        Cursor data = db.getInfoKategori(jenis);
        ImageView cover = (ImageView)findViewById(R.id.cover);
        ImageView label = (ImageView)findViewById(R.id.label);

        String uri1 = "@drawable/cv_"+jenis;
        String uri2 = "@drawable/btn_"+jenis;
        int imageResource1 = getResources().getIdentifier(uri1, null, getPackageName());
        int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
        Drawable dr1 = getResources().getDrawable(imageResource1);
        Drawable dr2 = getResources().getDrawable(imageResource2);
        cover.setImageDrawable(dr1);
        label.setImageDrawable(dr2);


        for(int count=0;count<6;count++){
            akses[count] = data.getInt(data.getColumnIndex("akses"));
            if(data.getInt(data.getColumnIndex("akses"))==0) {
                if(data.getInt(data.getColumnIndex("bonus"))==0)
                    item[count].setBackgroundResource(R.mipmap.ic_closed);
                else
                    item[count].setBackgroundResource(R.mipmap.ic_closed_star);
            }else {
                String gbr = "";
                if(data.getInt(data.getColumnIndex("status"))==0)
                    gbr = "ic_"+data.getString(data.getColumnIndex("nama"));
                else{
                    if(data.getInt(data.getColumnIndex("bonus"))==0)
                        gbr = "ic_selesai_"+data.getString(data.getColumnIndex("nama"));
                    else
                        gbr = "ic_selesai_bonus_"+data.getString(data.getColumnIndex("nama"));
                }
                int gambar = getResources().getIdentifier(gbr,"mipmap",getPackageName());
                item[count].setBackgroundResource(gambar);

            }
            data.moveToNext();
        }

    }

    public void ayo_tebak(View v){
        Intent ayoTebak= new Intent(this, tebak_namanya.class);;
        Bundle kirim = new Bundle();
        boolean masuk = false;
        String soal[] = new String[2];
        soal[0] = kategori;
        switch (v.getId()){
            case R.id.item_1 :
                if(akses[0]==1){
                    soal[1]="1";
                    masuk = true;
                }
                break;
            case R.id.item_2 :
                if(akses[1]==1){
                    soal[1]="2";
                    masuk = true;
                }
                break;
            case R.id.item_3 :
                if(akses[2]==1){
                    soal[1]="3";
                    masuk = true;
                }
                break;
            case R.id.item_4 :
                if(akses[3]==1){
                    soal[1]="4";
                    masuk = true;
                }
                break;
            case R.id.item_5 :
                if(akses[4]==1){
                    soal[1]="5";
                    masuk = true;
                }
                break;
            case R.id.item_6 :
                if(akses[5]==1){
                    soal[1]="6";
                    masuk = true;
                }
                break;
        }
        if(masuk){
            kirim.putStringArray("soal",soal);
            ayoTebak.putExtras(kirim);
            Log.d("mulai tebak", "ayo");
            startActivity(ayoTebak);
            finish();
        }else{
            Toast.makeText(this,"Maaf ya, belum bisa dibuka\n" +
                    "Selesaikan susun kata yang sebelumnya dulu", Toast.LENGTH_LONG).show();
        }
    }
}
