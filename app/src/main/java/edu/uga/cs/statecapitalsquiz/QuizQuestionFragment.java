package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Random;

public class QuizQuestionFragment extends Fragment {
    private static Quiz[] questionList;

    //empty constructor
    public QuizQuestionFragment() {

    }

    private int questionNum;
    private List<Long> quizIDs;

    public static QuizQuestionFragment newInstance(int questionNum) {
        QuizQuestionFragment fragment = new QuizQuestionFragment();
        Bundle args = new Bundle();
        args.putInt("questionNum", questionNum);
        fragment.setArguments(args);
        return fragment;
    }

    // get the appropriate question number for retrieving question to display in this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionNum = getArguments().getInt("questionNum");
        }
        QuizData qd = new QuizData(getContext());
        List<Quiz> quizQuestions = qd.retrieveAllQuizzes();

        for (int i = 0; i < 6; i++) {
            int index = new Random().nextInt(quizQuestions.size());
            Quiz quiz = quizQuestions.get(index);
            long id = quiz.getId();
            if (quizIDs.contains(id)) {
                i = i; //reset this iteration
                continue;
            }
            quizIDs.add(id);
            quizQuestions.add(quiz);
        }
    }

    // inflate the quiz question fragment within the view pager
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quiz_question, container, false);
    }

    // set the quiz question information for the appropriate views
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView stateName = view.findViewById(R.id.stateName);
        RadioButton cityOne = view.findViewById(R.id.cityOne);
        RadioButton cityTwo = view.findViewById(R.id.cityTwo);
        RadioButton cityThree = view.findViewById(R.id.cityThree);

        stateName.setText(questionList[questionNum].getState());
        cityOne.setText(questionList[questionNum].getCapital());
        cityTwo.setText(questionList[questionNum].getFirstCity());
        cityThree.setText(questionList[questionNum].getSecondCity());
    }
}
