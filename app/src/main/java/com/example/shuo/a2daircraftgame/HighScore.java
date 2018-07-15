package com.example.shuo.a2daircraftgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HighScore extends AppCompatActivity {
    TextView textView1,textView2,textView3,textView4;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        sharedPreferences = getSharedPreferences("SHARE_PREF_NAME", Context.MODE_PRIVATE);
        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        textView4 = (TextView)findViewById(R.id.textView4);

        textView1.setText("1. "+sharedPreferences.getInt("score1",0));
        textView2.setText("2. "+sharedPreferences.getInt("score2",0));
        textView3.setText("3. "+sharedPreferences.getInt("score3",0));
        textView4.setText("4. "+sharedPreferences.getInt("score4",0));
    }
}
