package edu.uga.cs.statecapitalsquiz;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizQPagerAdapter extends FragmentStateAdapter {
    public QuizQPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Fragment createFragment(int position) {

        return QuizQuestionFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return QuizQuestionFragment.getNumberOfQuestions();
    }
}
