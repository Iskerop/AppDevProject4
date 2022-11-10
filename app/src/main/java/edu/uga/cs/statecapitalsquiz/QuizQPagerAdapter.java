package edu.uga.cs.statecapitalsquiz;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizQPagerAdapter extends FragmentStateAdapter {

    public List<QuizQuestion> sixQuestions = new ArrayList<>();
    public static final String DEBUG_TAG = "QuizQPagerAdapter";

    // custom constructor
    public QuizQPagerAdapter(@NonNull Fragment fragment, List<QuizQuestion> questionList) {
        super(fragment);
        this.sixQuestions = questionList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Fragment createFragment(int position) {
        Log.d(DEBUG_TAG, "createFragment: " + position);
        return QuizQuestionFragment.newInstance(position, sixQuestions);
    }

    // In this case, 6 quiz question + 1 final score screen
    // call this method when it wants to know "how many pages will be available while swiping" (6 versions on the quiz)
    @Override
    public int getItemCount() {
        return QuizQuestionFragment.getNumberOfQuestions() + 1;
    }
}
