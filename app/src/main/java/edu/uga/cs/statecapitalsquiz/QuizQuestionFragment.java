package edu.uga.cs.statecapitalsquiz;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
    private List<String> answerChoices = new ArrayList<>();;
    private String answerKey;
    private List<String> answers = new ArrayList<>();
    private String selectedAnswer;
    // taking the quiz
    // Declaring a variable or a method as static, it belongs to the class.
    // Only one instance of a static member exists, even if you create multiple objects of the class.
    // It will be shared by all objects.
    private boolean radioButtonPressed = false;
    public static int numberOfAnsweredQuestions;
    public static int numberOfCorrectAnswers;
    private boolean choseCorrectAnswer;
    public int questionNum;

    RadioButton cityOne;
    RadioButton cityTwo;
    RadioButton cityThree;

    public static final String DEBUG_TAG = "QuizQuestionFragment";

    //empty constructor
    public QuizQuestionFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Fragment newInstance(int questionNum, List<QuizQuestion> sixQuestions) {
        Log.d(DEBUG_TAG, "newInstance:" + questionNum);
        // when the index position is 6 (7th screen) the last screen will be displayed showing the quiz results
        if (questionNum == 6) {
            Log.d(DEBUG_TAG, "Going to last screen, numberOfAnsweredQuestions:" + numberOfAnsweredQuestions);
            // WE WILL UPDATE THE NUMBER OF CORRECT ANSWERS AND SCORE HERE
            // AND THIS IS WHERE WE WILL STORE THE QUIZ IN THE DATABASE FOR THE PASTQUIZZESFRAGMENT

            LocalDateTime instance = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy h:mm a");
            String formattedDateAndTime = formatter.format(instance);

            // place values in here for now (will load this with real values later.)
            return FinalScoreFragment.newInstance();
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

    // sets quiz question information for the appropriate views
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // only do this operation if the questioNum position is less than 6. Prevents IOB.
        if (questionNum < 6) {
            this.answerKey = (sixQuestions.get(questionNum).getCapital()); // will be used to compare user answer and quiz answer
            Log.d(DEBUG_TAG, "AnswerKey: " + answerKey);

            // places the three answer choices in a list
            answerChoices.add(sixQuestions.get(questionNum).getCapital());
            answerChoices.add(sixQuestions.get(questionNum).getFirstCity());
            answerChoices.add(sixQuestions.get(questionNum).getSecondCity());

            Log.d(DEBUG_TAG, "Unshuffled answer choices: " + answerChoices);
            Collections.shuffle(answerChoices); // shuffle the answer choices
            Log.d(DEBUG_TAG, "Shuffled answer choices: " + answerChoices);

            //  QUESTION Section
            TextView stateName = view.findViewById(R.id.stateName);
            cityOne = view.findViewById(R.id.cityOne);
            cityTwo = view.findViewById(R.id.cityTwo);
            cityThree = view.findViewById(R.id.cityThree);


            // writing text that contains the questions and answer choices to the textView.
            String state = sixQuestions.get(questionNum).getState() + "?";
            stateName.setText(state);

            // must have A.), B.), C.) before the answer choices according to rubric
            String choice1 = answerChoices.get(0);
            cityOne.setText(choice1);
            String choice2 = answerChoices.get(1);
            cityTwo.setText(choice2);
            String choice3 = answerChoices.get(2);
            cityThree.setText(choice3);

            cityOne.setOnClickListener( new onRadioButtonClicked() );
            cityTwo.setOnClickListener( new onRadioButtonClicked() );
            cityThree.setOnClickListener( new onRadioButtonClicked() );
        } // if

    } // onViewCreated

    // THIS IS WHERE WE WILL CHECK USER INPUT OF PRESSING A RADIO BUTTON AND
    // WE WILL RECORD THIS ANSWER AND IF IT IS RIGHT OR WRONG
    private class onRadioButtonClicked implements View.OnClickListener  {

        public void onClick (View view) {
            // Check which radio button was clicked
            switch (view.getId()) {
                case R.id.cityOne:
                    // if the radio button hasn't been pressed yet, we can increment count of numberOfAnsweredQuestions
                    // however if the radio button is already pressed, then we don't increment numberOfAnsweredQuestions
                    // anymore because we don't want it to increase when changing answers
                    if (!radioButtonPressed) {
                        numberOfAnsweredQuestions++;
                        radioButtonPressed = true; // don't increment count anymore
                    } // if
                    cityOne = view.findViewById(R.id.cityOne);
                    checkCorrectAnswer(cityOne.getText().toString());
                    Log.d(DEBUG_TAG, "Chose Choice A: " + "# correct questions= " + numberOfCorrectAnswers +
                            " & # answered Questions= " + numberOfAnsweredQuestions);
                    break;
                case R.id.cityTwo:
                    if (!radioButtonPressed) {
                        numberOfAnsweredQuestions++;
                        radioButtonPressed = true; // don't increment count anymore
                    } // if
                    RadioButton cityTwo = view.findViewById(R.id.cityTwo);
                    checkCorrectAnswer(cityTwo.getText().toString());
                    Log.d(DEBUG_TAG, "Chose Choice B: " + "# correct questions= " + numberOfCorrectAnswers +
                            " & # answered Questions= " + numberOfAnsweredQuestions);
                    break;
                case R.id.cityThree:
                    if (!radioButtonPressed) {
                        numberOfAnsweredQuestions++;
                        radioButtonPressed = true; // don't increment count anymore
                    } // if
                    RadioButton cityThree = view.findViewById(R.id.cityThree);
                    checkCorrectAnswer(cityThree.getText().toString());
                    Log.d(DEBUG_TAG, "Chose Choice C: " + "# correct questions= " + numberOfCorrectAnswers +
                            " & # answered Questions= " + numberOfAnsweredQuestions);
                    break;
            } // Switch
        } // onClick

        // should be called everytime the user selects a radio button. Manages the computation of the score and answered questions
        public void checkCorrectAnswer (String choice){
            Log.d(DEBUG_TAG, "INSIDE checkCorrectAnswer");
            Log.d(DEBUG_TAG, "choice = " + choice);
            Log.d(DEBUG_TAG, "answerKey = " + answerKey);
            if (choice.equals(answerKey)) {
                // user picks the right answer for their very first choice
                choseCorrectAnswer = true;
                numberOfCorrectAnswers++; // incremented
                Log.d(DEBUG_TAG, "CHOSE THE CORRECT ANSWER");
            } else if (!(choice.equals(answerKey)) && !(choseCorrectAnswer)) {
                // user picks the wrong answer for their very first choice (number of correct answers is not decremented)
                choseCorrectAnswer = false;
                Log.d(DEBUG_TAG, "CHOSE THE INCORRECT ANSWER");
            } else if (!(choice.equals(answerKey)) && (choseCorrectAnswer)) {
                // if the user changes their answer from the correct answer to the wrong answer
                --numberOfCorrectAnswers;
                choseCorrectAnswer = false;
                Log.d(DEBUG_TAG, "CHANGED TO THE INCORRECT ANSWER");
            } else {
                choseCorrectAnswer = false;
            } // else-if
        } // checkCorrectAnswer
    } // on RadioButtonClicked

    public static int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    } // getNumberOfCorrectAnswers()

    public static int getNumberOfAnsweredQuestions() {
        return numberOfAnsweredQuestions;
    } // getNumberOfCorrectAnswers()
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
