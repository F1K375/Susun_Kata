package com.mad.f1k375.susunkata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void Start_game(View v){
        Intent open = new Intent(this, Home.class);
        startActivity(open);
        finish();
    }
}
