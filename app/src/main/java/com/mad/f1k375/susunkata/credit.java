package com.mad.f1k375.susunkata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class credit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
    }

    public void back(View view){
        finish();
    }
}
