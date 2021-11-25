package com.example.quiz_assignment;

public class TrueFalseQuestion {

        int question;
        boolean answer;
        int color;

       public TrueFalseQuestion(int q, Boolean a, int  c){
            question = q;
            answer = a;
            color = c;

        }

        public int getQuestion() {
            return question;
        }

        public void setQuestion(int question) {
            this.question = question;
        }

        public boolean isAnswer() {
            return answer;
        }

        public void setAnswer(boolean answer) {
            this.answer = answer;
        }
    }


