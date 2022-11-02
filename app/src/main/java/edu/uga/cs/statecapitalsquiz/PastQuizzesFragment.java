package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PastQuizzesFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private String quizFinalDate;
    private String quizFinalScore;

    public PastQuizzesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PastQuizzesFragment newInstance(String date, double score) {
        PastQuizzesFragment fragment = new PastQuizzesFragment();
        Bundle args = new Bundle();
        args.putString("quizDate", date);
        args.putDouble("quizScore", score);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizFinalDate = getArguments().getString("quizDate");
            quizFinalScore = getArguments().getString("quizScore");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_past_quizzes, container, false);
    }
}