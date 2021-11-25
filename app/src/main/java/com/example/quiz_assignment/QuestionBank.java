package com.example.quiz_assignment;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionBank {

        //static int lang = 0;
        private ArrayList<TrueFalseQuestion> questions = new ArrayList<>(0);
        private ArrayList<Integer> colores = new ArrayList<>();

        public QuestionBank(){
            buildColorArray();
            Collections.shuffle(colores);
            TrueFalseQuestion question1 = new TrueFalseQuestion(R.string.question1,false,colores.get(0));
            TrueFalseQuestion question2 = new TrueFalseQuestion(R.string.question2,false,colores.get(1));
            TrueFalseQuestion question3 = new TrueFalseQuestion(R.string.question3,true,colores.get(2));
            TrueFalseQuestion question4 = new TrueFalseQuestion(R.string.question4,false,colores.get(3));
            TrueFalseQuestion question5 = new TrueFalseQuestion(R.string.question5,true,colores.get(3));
            TrueFalseQuestion question6 = new TrueFalseQuestion(R.string.question6,false,colores.get(2));

            TrueFalseQuestion question7 = new TrueFalseQuestion(R.string.question7,true,colores.get(1));
            TrueFalseQuestion question8 = new TrueFalseQuestion(R.string.question8,true,colores.get(0));


            questions.add(question1);
            questions.add(question2);
            questions.add(question3);
            questions.add(question4);
            questions.add(question5);
            questions.add(question6);
            questions.add(question7);
            questions.add(question8);
        }

        public void buildColorArray (){
            colores.add(R.color.Red);
            colores.add(R.color.Blue);
            colores.add(R.color.Brown);
            colores.add(R.color.Green);
            colores.add(R.color.SandyBrown);
            colores.add(R.color.Olive);
            colores.add(R.color.SkyBlue);
            colores.add(R.color.Pink);


        }
        public void shuffle(){
            buildColorArray();
            Collections.shuffle(colores);
            Collections.shuffle(questions);

        }

        public int getQuestionBankSize(){
            return questions.size();
        }
        public int getNextQuestion(int index){
            return questions.get(index).question;
        }
        public int getNextQuestionColor(int index) {return questions.get(index).color;}

        public boolean checkAnswer(int questionIndex){
            return questions.get(questionIndex).answer;
        }




    }


