package com.example.thequizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.thequizapp.databinding.ActivityMainBinding;
import com.example.thequizapp.model.Question;
import com.example.thequizapp.model.QuestionList;
import com.example.thequizapp.viewmodel.QuizViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    QuizViewModel quizViewModel;
    List<Question> questionList;

    static int result = 0;
    static int totalQuestion = 0;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Data Binding
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
        );

        // Resetting the Scores
        result = 0;
        totalQuestion = 0;

        // Creating an instance of 'QuizViewModel'
        quizViewModel = new ViewModelProvider(this)
                .get(QuizViewModel.class);

        // Displaying the First Question:
        DisplayFirstQuestion();

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayNextQuestion();
            }
        });



    }

    public void DisplayFirstQuestion() {
        quizViewModel.getQuestionListLiveData().observe(this,
                new Observer<QuestionList>() {
                    @Override
                    public void onChanged(QuestionList questions) {
                        questionList = questions;
                        binding.tvQuestion.setText("Question 1:" + questions.get(0).getQuestion());
                        binding.rbtnAnswer1.setText(questions.get(0).getOption1());
                        binding.rbtnAnswer2.setText(questions.get(0).getOption2());
                        binding.rbtnAnswer3.setText(questions.get(0).getOption3());
                        binding.rbtnAnswer4.setText(questions.get(0).getOption4());
                    }
                });
    }

    public void DisplayNextQuestion() {

        // Direct the user to the Rsults activity
        if (binding.btnNext.getText().equals("Finish")) {
            Intent i = new Intent(MainActivity.this, ResultActivity.class);
            startActivity(i);
            finish();
        }


        // Display the question
        int selectedOption = binding.radioGroup.getCheckedRadioButtonId();
        if ( selectedOption != -1) {
            RadioButton radioButton = findViewById(selectedOption);

            // More Questions to Display

            if ((questionList.size() - i) > 0) {

                // Getting the number of questions
                totalQuestion = questionList.size();

                // Check anwser is correct
                if(radioButton.getText().toString().equals(questionList.get(i).getCorrectOption())) {
                    result++;
                    binding.tvResult.setText(
                            "Correct Anwsers: " + result
                    );
                }

                if (i== 0) {
                    i++;
                }

                // Displaying next question
                binding.tvQuestion.setText("Question " + (i+1) + " : " + questionList.get(i));

                binding.rbtnAnswer1.setText(questionList.get(i).getOption1());
                binding.rbtnAnswer2.setText(questionList.get(i).getOption2());
                binding.rbtnAnswer3.setText(questionList.get(i).getOption3());
                binding.rbtnAnswer4.setText(questionList.get(i).getOption4());

                // Check if it is the last question
                if (i == (questionList.size() -1)) {
                    binding.btnNext.setText("Finish");
                }

                binding.radioGroup.clearCheck();
                i++;


            } else {
                if (radioButton.getText().toString().equals(questionList.get(i-1).getCorrectOption())) {
                    result++;
                    binding.tvResult.setText("Correct Anwsers:" +result);
                }
            }
        } else {
            Toast.makeText(this, "You need to make a selection", Toast.LENGTH_SHORT).show();
        }

    }



}