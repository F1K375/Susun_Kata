package com.mad.f1k375.susunkata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void game(View v){
        Intent open=null;
        switch(v.getId()){
            case R.id.play :
                open = new Intent(this, pilih_kategori.class);
                break;
            case R.id.help :
                open = new Intent(this, about.class);
                break;
            case R.id.exit :
                finish();
                break;
            case R.id.credit :
                open = new Intent(this, credit.class);
                break;
        }
        startActivity(open);
    }
}
