package com.nilscreation.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    TextView txtCorrectAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        txtCorrectAns = findViewById(R.id.txtCorrectAns);
        Intent intent = getIntent();
        int mCorrectAnswers = intent.getIntExtra("CorrectAnswers", 0);
        txtCorrectAns.setText("" + mCorrectAnswers);
    }
}