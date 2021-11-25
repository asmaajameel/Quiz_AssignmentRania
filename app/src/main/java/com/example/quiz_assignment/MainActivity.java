package com.example.quiz_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView questionText;
    Button tButton;
    Button fButton;
    int total , totalAttempts;
    ProgressBar developmentBar;
    boolean done = false;
    int questionIndex = 0;
    TrueFalseQuestion questionBank[];
    int attemptNumber = 0;
    AlertDialog.Builder builder;
    int score = 0;
    int allAttempts[] = new int[10];
    QuestionBank bank;
    int totalQuestionNumber;
    StorageManager sManager ;
    Activity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sManager = ((QuizApplication) getApplication()).getsManager();
        bank = ((QuizApplication) this.getApplication()).getBank();
        if (savedInstanceState != null){
            questionIndex = savedInstanceState.getInt("index");
            updateQuestionFragment(bank.getNextQuestion(questionIndex), bank.getNextQuestionColor(questionIndex));
        }
        else {
            questionIndex = 0;
            updateQuestionFragment(bank.getNextQuestion(0), bank.getNextQuestionColor(0));
        }

        mainActivity = this;
        // questionText = (TextView) findViewById(R.id.question);
        tButton = (Button) findViewById(R.id.trueButton);
        fButton = (Button) findViewById(R.id.falseButton);
        developmentBar = (ProgressBar) findViewById(R.id.progressBar);

        developmentBar.setMax(bank.getQuestionBankSize());
        builder = new AlertDialog.Builder(this);
        totalQuestionNumber = bank.getQuestionBankSize();
        convertStats();

        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bank.checkAnswer(questionIndex)) {
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                    score++;
                } else {
                    Toast.makeText(getApplicationContext(), "inCorrect", Toast.LENGTH_SHORT).show();
                }
                questionIndex++;
                if (totalQuestionNumber == questionIndex) {
                    done = true;
                    developmentBar.setProgress(questionIndex);
                    report();
                } else {
                    done = false;
                    developmentBar.setProgress(questionIndex);
                    updateQuestionFragment(bank.getNextQuestion(questionIndex), bank.getNextQuestionColor(questionIndex));
                }

            }
        });
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!bank.checkAnswer(questionIndex)) {
                    Toast.makeText(getApplicationContext(), R.string.truestring, Toast.LENGTH_SHORT).show();
                    score++;
                } else {
                    Toast.makeText(getApplicationContext(), R.string.falsestring, Toast.LENGTH_SHORT).show();
                }
                questionIndex++;
                if (totalQuestionNumber == questionIndex) {
                    done = true;
                    developmentBar.setProgress(questionIndex);
                    report();
                } else {
                    done = false;
                    developmentBar.setProgress(questionIndex);
                    updateQuestionFragment(bank.getNextQuestion(questionIndex), bank.getNextQuestionColor(questionIndex));
                }
            }
        });
    }
    public void convertStats() {
        String stats = sManager.getStoredStats(MainActivity.this);
        String totalString = "";
        String totalAttemptstr = "";
        for (int i = 0; i < stats.toCharArray().length; i++) {
            if (stats.toCharArray()[i] == '/') {
                totalString = stats.substring(0, i);
                totalAttemptstr = stats.substring(i + 1);
            }
        }

        total = Integer.parseInt(totalString);
        totalAttempts = Integer.parseInt(totalAttemptstr);
    }

    private void updateButtomsText() {
        tButton.setText(R.string.truestring);
        fButton.setText(R.string.falsestring);
    }

    public void report() {
        builder.setMessage("Your Score is: " + score + " out of " + bank.getQuestionBankSize())
                .setPositiveButton(R.string.save_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  sManager.reset(getApplicationContext());
                        total = total + score;
                        totalAttempts = totalAttempts + 1;
                        sManager.saveScore(MainActivity.this, total, totalAttempts);
                        //sManager.saveNewAttemptInternal(MainActivity.this,score);
                        done = false;
                        questionIndex = 0;
                        score = 0;
                        developmentBar.setProgress(0);
                        bank.shuffle();
                        bank = new QuestionBank();
                        updateQuestionFragment(bank.getNextQuestion(questionIndex), bank.getNextQuestionColor(questionIndex));

                    }
                })
                .setNegativeButton(R.string.ignore_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        done = false;
                        questionIndex = 0;
                        score = 0;
                        developmentBar.setProgress(0);
                        bank.shuffle();
                        bank = new QuestionBank();
                        updateQuestionFragment(bank.getNextQuestion(questionIndex), bank.getNextQuestionColor(questionIndex));

                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Result");
        alert.show();

    }

    public void updateQuestionFragment(int questionID, int colorID) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment questionFragmentObject = (QuestionFragment) fm.findFragmentById(R.id.question_container);
        QuestionFragment questionFragment = QuestionFragment.newInstance(questionID, colorID);

        if (questionFragmentObject == null) {// that mean the area is empty
            fm.beginTransaction().add(R.id.question_container,questionFragment,"tag").commit();

//
        } else {// the area is already has third fragment
            // I'm able to delete that fragment
            //fm.beginTransaction().replace(R.id.question_container,questionFragment);

          //  fm.beginTransaction().remove(questionFragmentObject);

            fm.beginTransaction().replace(R.id.question_container, questionFragment).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index", questionIndex);

    }



    @Override
    protected void onStop() {
        super.onStop();
        Log.d("language ", " on stop " + questionIndex);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.quizmenu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.ave: {
              String msg = sManager.getStoredStats(MainActivity.this);
              if (msg.equals("")){
                  msg = "No available data!!";
              }
                builder.setMessage(msg).setNegativeButton("OK",null).show();
              break;
            }

            case R.id.reset: {
                sManager.resetTheStorage(MainActivity.this);
                total = 0;
                totalAttempts = 0;
                break;

            }


        }
        return true;
    }
}
