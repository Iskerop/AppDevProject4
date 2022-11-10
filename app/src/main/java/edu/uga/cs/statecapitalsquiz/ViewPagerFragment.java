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

    // stores all the questions retrieved from the database
    public List<QuizQuestion> questionList = new ArrayList<>();

    // stores the 6 questions that will be used in the quiz
    public List<QuizQuestion> selectedQuestionList = new ArrayList<>();

    private int questionNum;
    private ArrayList<String> questionStateNames = new ArrayList<String>() {};
    public static final String DEBUG_TAG = "ViewPagerFragment";

    // single instance of database class
    private QuizData quizQuestionsData = null;

    // gain access to viewpager when view has already been created (so that is why we are in the
    // onViewCreated method).
    ViewPager2 pager;

    // create an adapter for this fragment
    QuizQPagerAdapter avpAdapter;


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

        // retrieve instance of QuizData
        quizQuestionsData = new QuizData(getActivity());
        quizQuestionsData.open(); //open database for reading full list of questions

        // execute retrieval of job leads in an asynchronous way
        new QuizQuestionDBReader().execute();

        if (getArguments() != null) {
            // get the question number from the bundle
            questionNum = getArguments().getInt("questionNum");
        } // if
        QuizData qd = new QuizData(getContext()); //
        qd.open(); // open the database so we can  retrieve all the quiz questions
        List<QuizQuestion> quizQuestions = qd.retrieveAllQuizQuestions(); // retrieveAllQuizzes returns a list of the quiz questions
        //Log.d(DEBUG_TAG, "All quizQuestions: " + quizQuestions);


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
        pager = view.findViewById( R.id.viewpager );

        // create an adapter for this fragment
//        QuizPagerAdapter avpAdapter = new QuizPagerAdapter(this);

    } // onViewCreated

    private class QuizQuestionDBReader extends AsyncTask<Void, List<QuizQuestion>> {
        @Override
        protected List<QuizQuestion> doInBackground(Void... params) {
            List<QuizQuestion> quizQuestionList = quizQuestionsData.retrieveAllQuizQuestions();

            Log.d(DEBUG_TAG, "QuizQuestionDBReader: Quiz questions retrieved: " + quizQuestionList.size());
            return quizQuestionList;
        }

        @Override
        protected void onPostExecute(List<QuizQuestion> questionsList) {
            Log.d(DEBUG_TAG, "onPostExecute: questionsList.size(): " + questionsList.size());
            questionList.addAll(questionsList);

            int iterations = 6;

            // Randomly select 6 questions with no duplicates
            for (int i = 0; i < iterations; i++) {

                // chooses a random question from the list of question
                int index = new Random().nextInt(questionList.size()); // gets a random number between 0(inclusive) and the number passed in this argument(n), exclusive.
                QuizQuestion question = questionList.get(index);

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
                selectedQuestionList.add(question); // should be 6 questions
                //Log.d(DEBUG_TAG, "All quizList: " + questionList);
            } // for

            avpAdapter =  new QuizQPagerAdapter(getActivity(), selectedQuestionList);

            // set the viewPager (pager) with the avpAdapter so we can have values (the slides).
            pager.setAdapter(avpAdapter);
            // get instance of our adapter with the custom class we made "AndroidVersionsPagerAdapter"
            pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        } // onPostExecute

    }
} // ViewPagerFragment