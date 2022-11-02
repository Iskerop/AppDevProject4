package edu.uga.cs.statecapitalsquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

// use position values to indicate which question/answer to display
public class QuizPagerAdapter extends FragmentStateAdapter {

    // We will create an instance of this adapter in the
    // ViewPagerFragment class and set it to work with a ViewPager2.
    // The ViewPager2 lives directly in a Fragment subclass (ViewPagerFragment) so
    // that is why we are using this constructor instead of the one provided in class.
    public QuizPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    } // QuizPagerAdapter

    // Android will call this method "every time" the user
    // swipes left or right; the supplied "position"
    // parameter indicates the page position to display
    // while swiping
    @Override
    public Fragment createFragment(int position) {
        return StartNewQuizFragment.newInstance(position); // creates new instance a quiz question with answers
    } // createFragment

    // In this case, 6 quiz question
    // call this method when it wants to know "how many pages will be available while swiping" (6 versions on the quiz)
    @Override
    public int getItemCount() {
        return StartNewQuizFragment.getNumberOfQuestions();
    } // getItemCount
} // QuizPagerAdapter