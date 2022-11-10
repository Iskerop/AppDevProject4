package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class creates a fragment that has a viewpager2 widget that will control the
 * swiping of pages which in this case are the questions in the quiz.
 */
public class ViewPagerFragment extends Fragment {

    public List<QuizQuestion> questionList = new ArrayList<>();
    private int questionNum;
    private ArrayList<String> questionStateNames = new ArrayList<String>() {};
    public static final String DEBUG_TAG = "ViewPagerFragment";

    public ViewPagerFragment() {
        // Required empty public constructor
    } // ViewPagerFragment

    // Probably don't need
//    public static ViewPagerFragment newInstance() {
//        ViewPagerFragment fragment = new ViewPagerFragment();
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // get the question number from the bundle
            questionNum = getArguments().getInt("questionNum");
        } // if
        QuizData qd = new QuizData(getContext()); //
        qd.open(); // open the database so we can  retrieve all the quiz questions
        List<QuizQuestion> quizQuestions = qd.retrieveAllQuizQuestions(); // retrieveAllQuizzes returns a list of the quiz questions
        //Log.d(DEBUG_TAG, "All quizQuestions: " + quizQuestions);

        int iterations = 6;

        // Randomly select 6 questions with no duplicates
        for (int i = 0; i < iterations; i++) {

            // chooses a random question from the list of question
            int index = new Random().nextInt(quizQuestions.size()); // gets a random number between 0(inclusive) and the number passed in this argument(n), exclusive.
            QuizQuestion question = quizQuestions.get(index);

            // check the question id
            String stateName = question.getState();
            Log.d(DEBUG_TAG, "Quiz Question State Name: " + stateName);
            // checks the questionIds list and make sure there are no duplicates
            if (questionStateNames.contains(stateName)) {
                Log.d(DEBUG_TAG, "DUPLICATE STATE ");
                // when duplicate is found
                iterations++; // do another iteration of the while loop and skip adding it to the list
                continue;
            } // if
            Log.d(DEBUG_TAG, "Actual Quiz Question: " + question);
            questionStateNames.add(stateName);
            questionList.add(question); // should be 6 questions
            //Log.d(DEBUG_TAG, "All quizList: " + questionList);
        } // for
    } // onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the fragment_viewpager.xml file.
        // The only view in the xml layout file is the Viewpager2 widget.
        return inflater.inflate(R.layout.fragment_viewpager, container, false);
    } // onCreateView

    // gaining access to the views once inflated.
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // gain access to viewpager when view has already been created (so that is why we are in the
        // onViewCreated method).
        ViewPager2 pager = view.findViewById( R.id.viewpager );

        // create an adapter for this fragment
//        QuizPagerAdapter avpAdapter = new QuizPagerAdapter(this);
        QuizQPagerAdapter avpAdapter =  new QuizQPagerAdapter(this , questionList);

        // set the viewPager (pager) with the avpAdapter so we can have values (the slides).
        pager.setAdapter(avpAdapter);
        // get instance of our adapter with the custom class we made "AndroidVersionsPagerAdapter"
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
    } // onViewCreated
} // ViewPagerFragment