package com.nilscreation.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtCurrentQuestion, txtQuestion, optionA, optionB, optionC, optionD, txtNext;
    ArrayList<QuizModel> datalist;
    int indexNumber = 0;
    int currentQuestionNumber = 1;
    int datalistSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCurrentQuestion = findViewById(R.id.txtCurrentQuestion);
        txtQuestion = findViewById(R.id.txtQuestion);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        txtNext = findViewById(R.id.txtNext);

        datalist = new ArrayList<>();

        getQuestions();
        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indexNumber = indexNumber + 1;
                currentQuestionNumber = currentQuestionNumber + 1;
                setQuestions();
            }
        });
    }

    private void getQuestions() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Quiz DB");
        databaseReference.child("Quiz1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    QuizModel quizModel = dataSnapshot.getValue(QuizModel.class);
                    datalist.add(quizModel);
                }
                datalistSize = datalist.size();
                setQuestions();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setQuestions() {

        if (indexNumber < datalistSize) {
            txtCurrentQuestion.setText("" + currentQuestionNumber);
            txtQuestion.setText(datalist.get(indexNumber).getQuestion());
            optionA.setText(datalist.get(indexNumber).getOptionA());
            optionB.setText(datalist.get(indexNumber).getOptionB());
            optionC.setText(datalist.get(indexNumber).getOptionC());
            optionD.setText(datalist.get(indexNumber).getOptionD());
        } else {
            Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
            startActivity(intent);
        }
    }
}