package com.example.quiz_assignment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class QuestionFragment extends Fragment {

       static public QuestionFragment newInstance (int textID,int colorID){
            QuestionFragment q = new QuestionFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("question_text",textID);
            bundle.putInt("color",colorID);
            q.setArguments(bundle);

            return q;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // return inflater.inflate(R.layout.question_fragment,container,false);
            View view = inflater.inflate(R.layout.question_fragment,container,false);

            TextView textInDialgo =  (TextView)view.findViewById(R.id.question);
            textInDialgo.setText(getArguments().getInt("question_text"));
            textInDialgo.setBackgroundColor(getResources().getColor(getArguments().getInt("color"), getActivity().getTheme()));
            textInDialgo.setTextColor(getResources().getColor(R.color.white,getActivity().getTheme()));


            return view;
        }

    }


