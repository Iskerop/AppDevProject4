package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import android.widget.TextView;

// DON"T NEED THIS CLASS ANYMORE

public class StartNewQuizFragment extends Fragment {

    // Array of states in the U.S.
    // temporary for now b/c database hasn't been created yet.
    private static final String[] states = {
            "Georgia",
            "Florida",
            "Alabama",
            "Arkansas",
            "Texas",
            "South Carolina"
    }; // countries

    private static final String TAG = "StartNewQuizFragment";

    // We will create a variable called quizQuestions and quizAnswers (maybe quizQAndAData)
//    private JobLeadsData jobLeadsData = null;

    // which quiz question to display in the fragment
    private int quizNum;

    public StartNewQuizFragment() {
        // Required empty public constructor
        // gain access to viewpager
    } // StartNewQuizFragment

    // creates a new instance of a quiz question.
    public static StartNewQuizFragment newInstance(int position) {
        StartNewQuizFragment fragment = new StartNewQuizFragment();

        // store int position param in args (bundle) so we can pass the bundle (contains position value)
        // to other functions.
        // assists with keeping track of which state question to display.
        Bundle args = new Bundle();
        args.putInt( "quizQuestionPosition", position );
        fragment.setArguments( args );
        return fragment;
    } // newInstance

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // storing the bundle value in a class variable
            quizNum = getArguments().getInt("quizQuestionPosition");
        } // if
    } // onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quiz_question, container, false);
    } // onCreateView

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // this is the second textview in quiz_question.xml file that asks the user what capital for
        // the asked "state".
        TextView capitalTextView = getView().findViewById(R.id.stateName); // the "state"
        capitalTextView.setText(states[quizNum] + "?");
    } // onViewCreated

        // helps with deciding how many question we want to display to the user
        public static int getNumberOfQuestions() {
            return states.length; // 6 questions (6 screens to slide)
        } // getNumberOfVersions

    } // StartNewQuizFragment