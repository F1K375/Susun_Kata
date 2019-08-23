package com.mad.f1k375.susunkata;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class tebak_namanya extends AppCompatActivity {

    Database db=null;
    String jbn = null;
    String[] konten = new String[2];
    ImageView[] jawaban;
    ImageView[] pilihan;
    String[][] susun_kata =null;
    char[] alpabhet = "abcdefghijklmnopqrstuvwxy".toCharArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tebak_namanya);
        Bundle terima = getIntent().getExtras();
        db = new Database(this);
        konten = terima.getStringArray("soal");
        Log.d("tebak mulai","kerjakan");
        loadContent();
    }

    private void loadContent() {
        Cursor data = db.getInfoGambar(konten);
        char[] kata = data.getString(data.getColumnIndex("jawaban")).toCharArray();
        jbn = data.getString(data.getColumnIndex("jawaban"));
        int gambar = getResources().getIdentifier("ic_"+data.getString(data.getColumnIndex("nama")),"mipmap",getPackageName());
        ImageView cover = (ImageView)findViewById(R.id.soal);
        cover.setBackgroundResource(gambar);

        jawaban = new ImageView[kata.length];
        susun_kata = new String[3][kata.length];
        for(int x=0;x<kata.length;x++){
            susun_kata[0][x]="0";
        }
        LinearLayout LnLy = (LinearLayout)findViewById(R.id.kata);

        if(data.getInt(data.getColumnIndex("status"))==0){
            for(int count = 0; count<kata.length;count++){
                final int index = count;
                ImageView image = new ImageView(this);
                if(kata.length<5)
                    image.setLayoutParams(new LinearLayout.LayoutParams(120,120));
                else if(kata.length==5)
                    image.setLayoutParams(new LinearLayout.LayoutParams(100,100));
                else if(kata.length==6)
                    image.setLayoutParams(new LinearLayout.LayoutParams(80,80));
                else
                    image.setLayoutParams(new LinearLayout.LayoutParams(60,60));
                image.setBackgroundResource(R.mipmap.ic_box);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gantiHuruf(index);
                    }
                });
                LnLy.addView(image);
                jawaban[count] = image;
            }
            drawPilihan(kata.length,kata);
        }else{
            int gbr_selesai=0;
            for(int count = 0; count<kata.length;count++){
                final int index = count;
                ImageView image = new ImageView(this);
                if(kata.length<5)
                    image.setLayoutParams(new LinearLayout.LayoutParams(120,120));
                else if(kata.length==5)
                    image.setLayoutParams(new LinearLayout.LayoutParams(100,100));
                else if(kata.length==6)
                    image.setLayoutParams(new LinearLayout.LayoutParams(80,80));
                else
                    image.setLayoutParams(new LinearLayout.LayoutParams(60,60));
                gbr_selesai = getResources().getIdentifier("ic_"+String.valueOf(kata[count]),"mipmap",getPackageName());
                image.setBackgroundResource(gbr_selesai);
                LnLy.addView(image);
            }
            Button btn = (Button)findViewById(R.id.cek);
            btn.setVisibility(View.INVISIBLE);
        }


    }

    public void bonus(View view){
        Cursor data = db.getInfoGambar(konten);
        if(data.getInt(data.getColumnIndex("status"))==1&&data.getInt(data.getColumnIndex("bonus"))==1){
            Intent bonus = new Intent(this, bonus.class);
            Bundle kirim = new Bundle();
            konten[1]= String.valueOf(Integer.parseInt(konten[1])+1);
            kirim.putStringArray("soal",konten);
            bonus.putExtras(kirim);
            startActivity(bonus);
            finish();
        }
    }

    public void check(View view){
        String hasil = "";
        for(int x=0;x<susun_kata[1].length;x++){
            hasil=hasil+susun_kata[1][x];
        }

        if(hasil.equalsIgnoreCase(jbn)){
            db.UpdateLvl(konten);
            Intent lanjut = new Intent(this,LanjutLvl.class);
            Bundle kirim = new Bundle();
            konten[1]= String.valueOf(Integer.parseInt(konten[1])+1);
            kirim.putStringArray("naikLvl",konten);
            lanjut.putExtras(kirim);
            startActivity(lanjut);
            finish();
        }else{
            Toast.makeText(this,"Salah, ayo coba lagi", Toast.LENGTH_SHORT).show();
        }
    }

    private void gantiHuruf(int index) {
        if(Integer.parseInt(susun_kata[0][index])==1) {
            jawaban[index].setBackgroundResource(R.mipmap.ic_box);
            pilihan[Integer.parseInt(susun_kata[2][index])].setVisibility(View.VISIBLE);
            susun_kata[0][index]="0";
            susun_kata[1][index]="";
            susun_kata[2][index]="";
        }
    }

    private void drawPilihan(int length, char[] kata) {
        pilihan = new ImageView[8];
        LinearLayout pil1 = (LinearLayout)findViewById(R.id.pilihan1);
        LinearLayout pil2 = (LinearLayout)findViewById(R.id.pilihan2);
        String cek = "";
        int gambar=0,cekOther=0,index=0,huruf=0,count=0,alpa=0,isi=0;
        Random random = new Random();
        Log.d("kataatas", "masuk");
        while(count<4){
            huruf = random.nextInt(length);
            alpa = random.nextInt(alpabhet.length);
            Log.d("cekindex",cek+" "+huruf);
            final int PH = isi;
            if(kata.length>6){
                if(cek.indexOf(String.valueOf(huruf))<0&&random.nextInt(10)%3==0&&index<3){
                    final char Char = kata[huruf];
                    ImageView image = new ImageView(this);
                    Log.d("CekIndex", "cek: "+cek+" huruf: "+huruf+" hasil:"+cek.indexOf(huruf)+" jawaban "+String.valueOf(kata[huruf]));
                    gambar = getResources().getIdentifier("ic_"+String.valueOf(kata[huruf]),"mipmap",getPackageName());
                    image.setBackgroundResource(gambar);
                    image.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            PilihHuruf(PH, Char);
                        }
                    });
                    pil1.addView(image);
                    pilihan[isi++] = image;
                    cek = cek+huruf;
                    index++;count++;
                }else if(cekOther<1){
                    final char Char = alpabhet[alpa];
                    ImageView image = new ImageView(this);
                    Log.d("jenis", "pengecoh "+String.valueOf(alpabhet[alpa]));
                    gambar = getResources().getIdentifier("ic_"+String.valueOf(alpabhet[alpa]),"mipmap",getPackageName());
                    image.setBackgroundResource(gambar);
                    image.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            PilihHuruf(PH, Char);
                        }
                    });
                    pil1.addView(image);
                    pilihan[isi++] = image;
                    count++;cekOther++;
                }
            }else if(kata.length<7){
                if(cek.indexOf(String.valueOf(huruf))<0&&random.nextInt(10)%3==0&&index<2){
                    final char Char = kata[huruf];
                    ImageView image = new ImageView(this);
                    Log.d("CekIndex", "cek: "+cek+" huruf: "+huruf+" hasil:"+cek.indexOf(huruf)+" jawaban "+String.valueOf(kata[huruf]));
                    gambar = getResources().getIdentifier("ic_"+String.valueOf(kata[huruf]),"mipmap",getPackageName());
                    image.setBackgroundResource(gambar);
                    image.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            PilihHuruf(PH, Char);
                        }
                    });
                    pil1.addView(image);
                    pilihan[isi++] = image;
                    cek = cek+huruf;
                    index++;count++;
                }else if(cekOther<2){
                    final char Char = alpabhet[alpa];
                    ImageView image = new ImageView(this);
                    Log.d("jenis", "pengecoh "+String.valueOf(alpabhet[alpa]));
                    gambar = getResources().getIdentifier("ic_"+String.valueOf(alpabhet[alpa]),"mipmap",getPackageName());
                    image.setBackgroundResource(gambar);
                    image.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            PilihHuruf(PH, Char);
                        }
                    });
                    pil1.addView(image);
                    pilihan[isi++] = image;
                    count++;cekOther++;
                }
            }
        }
        count=0;
        if(length<5){
            Log.d("katabawah","masuk "+count);
            while(count<4){
                alpa = random.nextInt(alpabhet.length);
                huruf = random.nextInt(length);
                final int PH = isi;
                Log.d("cekindex",cek);
                if(cek.indexOf(String.valueOf(huruf))<0&&random.nextInt(10)%4==0&&index<length){
                    final char Char = kata[huruf];
                    ImageView image = new ImageView(this);
                    Log.d("CekIndex", "cek: "+cek+" huruf: "+huruf+" hasil:"+cek.indexOf(String.valueOf(huruf))+" jawaban "+String.valueOf(kata[huruf]));
                    gambar = getResources().getIdentifier("ic_"+String.valueOf(kata[huruf]),"mipmap",getPackageName());
                    image.setBackgroundResource(gambar);
                    image.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            PilihHuruf(PH, Char);
                        }
                    });
                    pil2.addView(image);
                    pilihan[isi++] = image;
                    cek = cek+huruf;index++;
                    count++;
                }else{
                    final char Char = alpabhet[alpa];
                    if(length==3){
                        if(cekOther<5){
                            ImageView image = new ImageView(this);
                            Log.d("jenis", "pengecoh "+String.valueOf(alpabhet[alpa]));
                            gambar = getResources().getIdentifier("ic_"+String.valueOf(alpabhet[alpa]),"mipmap",getPackageName());
                            image.setBackgroundResource(gambar);
                            image.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    PilihHuruf(PH, Char);
                                }
                            });
                            pil2.addView(image);
                            pilihan[isi++] = image;
                            count++;cekOther++;
                        }
                    }else{
                        if(cekOther<4){
                            ImageView image = new ImageView(this);
                            Log.d("jenis", "pengecoh "+String.valueOf(alpabhet[alpa]));
                            gambar = getResources().getIdentifier("ic_"+String.valueOf(alpabhet[alpa]),"mipmap",getPackageName());
                            image.setBackgroundResource(gambar);
                            image.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    PilihHuruf(PH, Char);
                                }
                            });
                            pil2.addView(image);
                            pilihan[isi++] = image;
                            count++;cekOther++;
                        }
                    }
                }
            }
        }else if(length==5){
            while(count<4){
                alpa = random.nextInt(alpabhet.length);
                huruf = random.nextInt(length);
                final int PH = isi;
                if(cek.indexOf(String.valueOf(huruf))<0&&random.nextInt(10)%4==0&&index<length){
                    final char Char = kata[huruf];
                    ImageView image = new ImageView(this);
                    Log.d("CekIndex", "cek: "+cek+" huruf: "+huruf+" hasil:"+cek.indexOf(String.valueOf(huruf))+" jawaban "+String.valueOf(kata[huruf]));
                    gambar = getResources().getIdentifier("ic_"+String.valueOf(kata[huruf]),"mipmap",getPackageName());
                    image.setBackgroundResource(gambar);
                    image.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            PilihHuruf(PH, Char);
                        }
                    });
                    pil2.addView(image);
                    pilihan[isi++] = image;
                    cek = cek+huruf;index++;
                    count++;
                }else if(cekOther<3){
                    final char Char=alpabhet[alpa];
                    ImageView image = new ImageView(this);
                    Log.d("jenis", "pengecoh "+String.valueOf(alpabhet[alpa]));
                    gambar = getResources().getIdentifier("ic_"+String.valueOf(alpabhet[alpa]),"mipmap",getPackageName());
                    image.setBackgroundResource(gambar);
                    image.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            PilihHuruf(PH, Char);
                        }
                    });
                    pil2.addView(image);
                    pilihan[isi++] = image;
                    count++;cekOther++;
                }
            }
        }else{
            while(count<4){
                huruf = random.nextInt(length);
                final int PH = isi;
                if(cek.indexOf(String.valueOf(huruf))<0&&index<length){
                    final char Char = kata[huruf];
                    ImageView image = new ImageView(this);
                    Log.d("CekIndex", "cek: "+cek+" huruf: "+huruf+" hasil:"+cek.indexOf(String.valueOf(huruf))+" jawaban "+String.valueOf(kata[huruf]));
                    gambar = getResources().getIdentifier("ic_"+String.valueOf(kata[huruf]),"mipmap",getPackageName());
                    image.setBackgroundResource(gambar);
                    image.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            PilihHuruf(PH, Char);
                        }
                    });
                    pil2.addView(image);
                    pilihan[isi++] = image;
                    cek = cek+huruf;index++;
                    count++;
                }
            }
        }
    }

    private void PilihHuruf(int ph, char huruf) {
        int index=0;
        while(true){
            Log.d("ukurankata", "jumalah : "+String.valueOf(susun_kata[0].length)+" tipe "+susun_kata[0][index]+" index "+index);
            if(index<susun_kata[0].length&&Integer.parseInt(susun_kata[0][index])==0){
                int gambar = getResources().getIdentifier("ic_"+String.valueOf(huruf),"mipmap",getPackageName());
                jawaban[index].setBackgroundResource(gambar);
                pilihan[ph].setVisibility(View.INVISIBLE);
                susun_kata[0][index]="1";
                susun_kata[1][index]=String.valueOf(huruf);
                susun_kata[2][index]= ""+ph;
                Log.d("yang dipilih", String.valueOf(huruf));
                break;
            }else if (index==susun_kata[0].length-1){
                break;
            }else
                index++;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, game.class);
        Bundle kirim = new Bundle();
        kirim.putString("kategori",konten[0]);
        intent.putExtras(kirim);
        startActivity(intent);
        finish();
    }
}
