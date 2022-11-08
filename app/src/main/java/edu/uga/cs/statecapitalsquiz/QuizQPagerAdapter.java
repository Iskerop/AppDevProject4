package edu.uga.cs.statecapitalsquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizQPagerAdapter extends FragmentStateAdapter {
    public QuizQPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @Override
    public Fragment createFragment(int position) {
        return QuizQuestionFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return QuizQuestionFragment.getNumberOfQuestions();
    }
}
