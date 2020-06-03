package com.example.astronomyforkids.fragments.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.astronomyforkids.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class TestFragment extends Fragment implements View.OnClickListener {
    private TextView textViewQuestion;
    private TextView textViewCount;
    private Button buttonRestart;
    private Button buttonBack;
    private LinearLayout linearLayoutAnswer;
    private Button button1Answer;
    private Button button2Answer;
    private Button button3Answer;
    private Button button4Answer;

    private String title;
    private int k = 0;
    private int index = 1;
    private int countTrueAnswer = 0;

    private ArrayList<Map<String, Boolean>> answerAList = new ArrayList<>();
    private ArrayList<String> qList = new ArrayList<>();

    private static final String APP_PREFERENCES = "settings";
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Створення та ініціалазація фрагменту тестової вікторини
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        TextView textViewTopic = view.findViewById(R.id.textViewTopic);
        textViewQuestion = view.findViewById(R.id.textViewQuestion);
        buttonRestart = view.findViewById(R.id.buttonRestart);
        buttonBack = view.findViewById(R.id.buttonBackToInfo);
        textViewCount = view.findViewById(R.id.textViewCount);
        linearLayoutAnswer = view.findViewById(R.id.linearLayoutTest);
        button1Answer = view.findViewById(R.id.button1Answer);
        button2Answer = view.findViewById(R.id.button2Answer);
        button3Answer = view.findViewById(R.id.button3Answer);
        button4Answer = view.findViewById(R.id.button4Answer);


        if (getArguments() != null) {
            ArrayList<String> testList = getArguments().getStringArrayList("testList");
            int ID = getArguments().getInt("id");
            title = getArguments().getString("name");
            textViewTopic.setText(title);

            if (testList != null) {
                String testStr = testList.get(ID);
                String[] arrOfStr = testStr.split(Pattern.quote("={"));
                ArrayList<String> questionsList = new ArrayList<>();
                ArrayList<String> answersList = new ArrayList<>();

                System.out.println(testList.get(ID));
                System.out.println("////");

                for (String s : arrOfStr) {
                    String[] arr = s.split(Pattern.quote("},"));
                    for (int j = 0; j < arr.length; j++) {
                        if (j % 2 == 0) {
                            answersList.add(arr[j]);
                        } else {
                            questionsList.add(arr[j]);
                        }
                    }
                }
                qList.add(answersList.get(0).replace("{", ""));
                qList.addAll(questionsList);

                answersList.remove(0);
                final ArrayList<String> aList = new ArrayList<>(answersList);

                aList.set(aList.size() - 1, aList.get(aList.size() - 1).replace("}", ""));

                System.out.println("qL size: " + qList.size());
                for (String item : qList) {
                    System.out.println("qL: " + item);
                }

                System.out.println("aL size: " + aList.size());
                for (String item : aList) {
                    String[] allA = item.split(Pattern.quote(", "));
                    for (String ans : allA) {
                        String[] arr = ans.split("=");
                        Map<String, Boolean> answerMap = new HashMap<String, Boolean>();
                        answerMap.put(arr[0], Boolean.valueOf(arr[1]));
                        answerAList.add(answerMap);
                    }
                    System.out.println("qL: " + item);
                }

                textViewQuestion.setText(qList.get(0));

                button1Answer.setText(getTextButtonAnswer(answerAList, k, 0));
                button2Answer.setText(getTextButtonAnswer(answerAList, k, 1));
                button3Answer.setText(getTextButtonAnswer(answerAList, k,  2));
                button4Answer.setText(getTextButtonAnswer(answerAList, k,  3));

                k += 4;

                button1Answer.setOnClickListener(this);
                button2Answer.setOnClickListener(this);
                button3Answer.setOnClickListener(this);
                button4Answer.setOnClickListener(this);
            }
        }
        return view;
    }


    /**
     * Обробка подій кліку кнопок
     * @param view
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        if (index <= 10) {
            if (view.getId() == R.id.button1Answer) {
                if(answerEqualsTrue(answerAList, k, 4)){
                    ++countTrueAnswer;
                }
            } else if (view.getId() == R.id.button2Answer) {
                if(answerEqualsTrue(answerAList, k, 3)){
                    ++countTrueAnswer;
                }
            } else if (view.getId() == R.id.button3Answer) {
                if(answerEqualsTrue(answerAList, k, 2)){
                    ++countTrueAnswer;
                }
            } else if (view.getId() == R.id.button4Answer) {
                if(answerEqualsTrue(answerAList, k, 1)){
                    ++countTrueAnswer;
                }
            }
        }

        if (index < 10) {
            textViewCount.setText("Кількість питань " + (index + 1) + " із 10");
            textViewQuestion.setText(qList.get(index));
            button1Answer.setText(getTextButtonAnswer(answerAList, k,0));
            button2Answer.setText(getTextButtonAnswer(answerAList, k,1));
            button3Answer.setText(getTextButtonAnswer(answerAList, k,2));
            button4Answer.setText(getTextButtonAnswer(answerAList, k,3));
            k += 4;
            index++;
        } else {
            textViewQuestion.setText("Бали: " + countTrueAnswer);
            linearLayoutAnswer.setVisibility(View.GONE);
            buttonRestart.setVisibility(View.VISIBLE);
            buttonRestart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    refreshVars();
                    refreshFragment();
                }
            });

            buttonBack.setVisibility(View.VISIBLE);
            buttonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    refreshVars();
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                }
            });
            //get
            Set<String> hashSet = sharedPreferences.getStringSet("hashSet", new HashSet<String>());
            sharedPreferences.edit().clear().apply();
            for(String r : hashSet) {
                System.out.println("test: " + r);
            }

            //set
            hashSet.add(title + "/" + String.valueOf(countTrueAnswer));
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putStringSet("hashSet", hashSet);
            edit.apply();
        }
    }

    /**
     * Повертає список відповідей
     * @param answerList
     * @param id
     * @param position
     * @return
     */
    private String getTextButtonAnswer(ArrayList<Map<String, Boolean>> answerList, int id, int position){
        return answerList.get(id + position).keySet().toString().replace("[", "").replace("]", "");
    }

    /**
     * Повертає чи правильна відповідь
     * @param answerList
     * @param id
     * @param position
     * @return
     */
    private boolean answerEqualsTrue(ArrayList<Map<String, Boolean>> answerList, int id, int position){
        String[] arr = answerList.get(id - position).toString().split("=");
        String text = arr[1].replace("}", "");
        return text.equals("true");
    }

    /**
     * Задає текст всім TextView
     * @param text
     * @param textView
     */
    private void setTextAllTextView(String text, TextView... textView)
    {
        for(TextView textViewItem : textView){
            textViewItem.setText(text);
        }
    }

    private void refreshFragment(){
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    private void refreshVars(){
        k = 0;
        index = 1;
        countTrueAnswer = 0;
    }
}
