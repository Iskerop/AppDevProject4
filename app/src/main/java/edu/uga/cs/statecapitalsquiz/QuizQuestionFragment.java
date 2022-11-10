package edu.uga.cs.statecapitalsquiz;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizQuestionFragment extends Fragment {

    private List<QuizQuestion> sixQuestions;
    private int questionNum;
    private List<String> answers = new ArrayList<>();
    private String selectedAnswer;

    //empty constructor
    public QuizQuestionFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Fragment newInstance(int questionNum, List<QuizQuestion> sixQuestions) {

        // when the index position is 6 (7th screen) the last screen will be displayed showing the quiz results
        if (questionNum == 6) {

            // WE WILL UPDATE THE NUMBER OF CORRECT ANSWERS AND SCORE HERE
            // AND THIS IS WHERE WE WILL STORE THE QUIZ IN THE DATABASE FOR THE PASTQUIZZESFRAGMENT

            LocalDateTime instance = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy h:mm a");
            String formattedDateAndTime = formatter.format(instance);

            // place values in here for now (will load this with real values later.)
            return FinalScoreFragment.newInstance(3, 6, formattedDateAndTime);
        }
        QuizQuestionFragment fragment = new QuizQuestionFragment(sixQuestions);
        Bundle args = new Bundle();
        args.putInt("questionNum", questionNum);

        fragment.setArguments(args);
        return fragment;
    }

    public QuizQuestionFragment(List<QuizQuestion> sixQuestions) {
        this.sixQuestions = sixQuestions;
    }

    // get the appropriate question number for retrieving question to display in this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // get the question number from the bundle
            questionNum = getArguments().getInt("questionNum");
        }
//        QuizData qd = new QuizData(getContext()); //
//        qd.open(); // open the database so we can  retrieve all the quiz questions
//        List<QuizQuestion> quizQuestions = qd.retrieveAllQuizQuestions(); // retrieveAllQuizzes returns a list of the quiz questions
//
//        // Randomly select 6 questions with no duplicates
//        for (int i = 0; i < 6; i++) {
//            // chooses a random question from the list of question
//            int index = new Random().nextInt(quizQuestions.size());
//            QuizQuestion question = quizQuestions.get(index);
//
//            // check the question id
//            long id = question.getId();
//
//            // prevents duplicates
//            if (questionIDs.contains(id)) {
//                // when duplicate is found
//                i = i; // reset this iteration and choose a different question
//                continue;
//            } // if
//            questionIDs.add(id); // store the id's of the question questions that we are going to use in the question
//
//            // DO WE NEED THIS?
//             quizQuestions.add(question);
//             questionList = quizQuestions;
////            questionList.add(question);
//        } // for
    } // onCreate

    // inflate the quiz question fragment within the view pager
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quiz_question, container, false);
    }

    // set the quiz question information for the appropriate views
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ANSWER CHOICES NEED TO BE RANDOMLY PLACED

        //  QUESTION Section
        TextView stateName = view.findViewById(R.id.stateName);
        RadioButton cityOne = view.findViewById(R.id.cityOne);
        RadioButton cityTwo = view.findViewById(R.id.cityTwo);
        RadioButton cityThree = view.findViewById(R.id.cityThree);

        // randomize answer choices
        answers.add(sixQuestions.get(questionNum).getCapital());
        answers.add(sixQuestions.get(questionNum).getFirstCity());
        answers.add(sixQuestions.get(questionNum).getSecondCity());

        Collections.shuffle(answers); //randomize answer order

        if (questionNum < 6) {
            stateName.setText(sixQuestions.get(questionNum).getState());
            cityOne.setText(answers.get(0));
            cityTwo.setText(answers.get(1));
            cityThree.setText(answers.get(2));
        } // if
        // THIS IS WHERE WE WILL CHECK USER INPUT OF PRESSING A RADIO BUTTON AND
        // WE WILL RECORD THIS ANSWER AND IF IT IS RIGHT OR WRONG
        cityOne.setOnClickListener(onRadioButtonClicked);
        cityTwo.setOnClickListener(onRadioButtonClicked);
        cityThree.setOnClickListener(onRadioButtonClicked);

    } // onViewCreated

    public View.OnClickListener onRadioButtonClicked = view -> {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.cityOne:
                if (checked) {
                    selectedAnswer = answers.get(0);
                    Toast t = Toast.makeText(getContext(), selectedAnswer, Toast.LENGTH_SHORT);
                    t.show();
                    break;
                }
            case R.id.cityTwo:
                if (checked) {
                    selectedAnswer = answers.get(1);
                    Toast t = Toast.makeText(getContext(), selectedAnswer, Toast.LENGTH_SHORT);
                    t.show();
                    break;
                }
            case R.id.cityThree:
                if (checked) {
                    selectedAnswer = answers.get(2);
                    Toast t = Toast.makeText(getContext(), selectedAnswer, Toast.LENGTH_SHORT);
                    t.show();
                    break;
                }
        }
    };

    // controls how many screens there are for the quiz
    public static int getNumberOfQuestions() {
        return 6;
    }
}
