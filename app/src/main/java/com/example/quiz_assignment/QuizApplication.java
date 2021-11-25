package com.example.quiz_assignment;

import android.app.Application;
import android.content.Context;

public class QuizApplication extends Application {
    private StorageManager sManager = new StorageManager();
    Context storage_context;

    public QuestionBank getBank() {
        return bank;
    }

    private QuestionBank  bank = new QuestionBank();
    public StorageManager getsManager() {
        return sManager;
    }

}
