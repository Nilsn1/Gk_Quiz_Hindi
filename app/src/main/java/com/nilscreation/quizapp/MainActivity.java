package com.nilscreation.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
    TextView txtCurrentQuestion, txtAllQuestionno, txtQuestion, optionA, optionB, optionC, optionD, txtNext;
    ArrayList<QuizModel> datalist;
    int indexNumber = 0;
    int currentQuestionNumber = 1;
    int datalistSize;
    int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCurrentQuestion = findViewById(R.id.txtCurrentQuestion);
        txtAllQuestionno = findViewById(R.id.txtAllQuestionno);
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
                txtNext.setClickable(false);
                indexNumber = indexNumber + 1;
                currentQuestionNumber = currentQuestionNumber + 1;
                setQuestions();
                enableButtons();
                Toast.makeText(MainActivity.this, " " + correctAnswers, Toast.LENGTH_SHORT).show();
            }
        });

        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableButtons();
                if (datalist.get(indexNumber).getOptionA().equals(datalist.get(indexNumber).getAnswer())) {
                    correctAnswers = correctAnswers + 1;
                    optionA.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
                } else {
                    optionA.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.red_card));
                    getRightAnswer();
                }
            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableButtons();
                if (datalist.get(indexNumber).getOptionB().equals(datalist.get(indexNumber).getAnswer())) {
                    correctAnswers = correctAnswers + 1;
                    optionB.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
                } else {
                    optionB.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.red_card));
                    getRightAnswer();
                }
            }
        });

        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableButtons();
                if (datalist.get(indexNumber).getOptionC().equals(datalist.get(indexNumber).getAnswer())) {
                    correctAnswers = correctAnswers + 1;
                    optionC.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
                } else {
                    optionC.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.red_card));
                    getRightAnswer();
                }
            }
        });

        optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableButtons();
                if (datalist.get(indexNumber).getOptionD().equals(datalist.get(indexNumber).getAnswer())) {
                    correctAnswers = correctAnswers + 1;
                    optionD.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
                } else {
                    optionD.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.red_card));
                    getRightAnswer();
                }
            }
        });
        txtNext.setClickable(false);
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
                txtAllQuestionno.setText("" + datalistSize);
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
            txtQuestion.setText("Q. " + datalist.get(indexNumber).getQuestion());
            optionA.setText("A) " + datalist.get(indexNumber).getOptionA());
            optionB.setText("B) " + datalist.get(indexNumber).getOptionB());
            optionC.setText("C) " + datalist.get(indexNumber).getOptionC());
            optionD.setText("D) " + datalist.get(indexNumber).getOptionD());
        } else {
            Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
            intent.putExtra("CorrectAnswers", correctAnswers);
            startActivity(intent);
            finish();
        }
    }

    private void enableButtons() {
        optionA.setClickable(true);
        optionB.setClickable(true);
        optionC.setClickable(true);
        optionD.setClickable(true);
        optionA.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.white_card));
        optionB.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.white_card));
        optionC.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.white_card));
        optionD.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.white_card));
    }

    private void disableButtons() {
        optionA.setClickable(false);
        optionB.setClickable(false);
        optionC.setClickable(false);
        optionD.setClickable(false);
        txtNext.setClickable(true);
    }

    private void getRightAnswer() {
        if (datalist.get(indexNumber).getOptionA().equals(datalist.get(indexNumber).getAnswer())) {
            optionA.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
        } else if (datalist.get(indexNumber).getOptionB().equals(datalist.get(indexNumber).getAnswer())) {
            optionB.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
        } else if (datalist.get(indexNumber).getOptionC().equals(datalist.get(indexNumber).getAnswer())) {
            optionC.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
        } else {
            optionD.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.green_card));
        }
    }
}