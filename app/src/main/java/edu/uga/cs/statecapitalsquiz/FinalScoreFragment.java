package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FinalScoreFragment extends Fragment {

    // the class variables that will be used to display text in TextView
    private double numberOfQuestionsAnswered = QuizQuestionFragment.getNumberOfAnsweredQuestions();
    private double correctAnswers = QuizQuestionFragment.getNumberOfCorrectAnswers();
    private double finalQuizScore = correctAnswers / numberOfQuestionsAnswered;
    private String quizDateAndTimeFinished;

    public FinalScoreFragment() {
        // Required empty public constructor
    } // FinalScoreFragment

    public static FinalScoreFragment newInstance(String dateAndTime) {
        FinalScoreFragment fragment = new FinalScoreFragment();
        Bundle args = new Bundle();
//        args.putDouble( "numAQ", numAnsweredQuestions );
//        args.putDouble( "numCorrectAnswers", correctAnswers);
        args.putString("quizDateAndTime", dateAndTime);
        fragment.setArguments( args );
        return fragment;
    } // newInstance

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // storing the bundle values in class variables
//            numberOfQuestionsAnswered = getArguments().getDouble("numAQ");
//            correctAnswers = getArguments().getDouble("numCorrectAnswers");
            quizDateAndTimeFinished = getArguments().getString("quizDateAndTime");
        } // if
    } // onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_final_score, container, false);
    } // onCreateView

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView numCorrectAnswersTextView = getView().findViewById(R.id.textView7);
        // round 0 places for :number of correct answers" and "number of questions"
        String numberOfCorrectAnswersString = "You got " + String.format("%.0f", correctAnswers) + "/" +
                String.format("%.0f", numberOfQuestionsAnswered) + " questions correct";
        numCorrectAnswersTextView.setText(numberOfCorrectAnswersString);

        finalQuizScore = correctAnswers/numberOfQuestionsAnswered * 100;

        // displays final score
        TextView finalScoreTextView = getView().findViewById(R.id.textView8);
        // round 2 decimal points for final quiz score
        String finalScoreString = "Final Score: " + String.format("%.2f", finalQuizScore) + "%";
        finalScoreTextView.setText(finalScoreString); // display the percentage

        TextView dateAndTimeTextView = getView().findViewById(R.id.textView9);
        String dataAndTimeString = "Date and Time: " + quizDateAndTimeFinished;
        dateAndTimeTextView.setText(dataAndTimeString);

        Button pastQuizzesButton = getView().findViewById(R.id.button);

        /**
         * The View.OnClickListener class is to be invoked when a view is clicked, in this case, a button.
         * Once the button is clicked, the class will call the
         * onClick method that will cause an action to take place which is to change out the fragment.
         */
        pastQuizzesButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is called when the overviewButton has been clicked.
             *
             * @param v The view that was clicked in this case a button.
             */
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent( v.getContext(),
//                        MainActivity.class ); // the intention
//                startActivity(intent);

                // calling the method in MainActivity to change this fragment to PastQuizzesFragment
                // while also supplying the "date & time" and the score so it will be saved in the recyclerView
                ((MainActivity) getActivity()).replaceFragment(quizDateAndTimeFinished, finalQuizScore);
            } // onClick
        }); // setOnClickListener
    } // onViewCreated
} // FinalScoreFragment