package edu.uga.cs.statecapitalsquiz;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// DON"T NEED THIS CLASS ANYMORE

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Fragment createFragment(int position) {

        // when the index position is 6 (7th screen) the last screen will be displayed showing the quiz results
        if (position == 6) {

            // get the date here so it can supplied to FinalScoreFragment and to PastQuizzesFragment
            LocalDateTime instance = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy h:mm a");
            String formattedDateAndTime = formatter.format(instance);

            // place values in here for now (will load this with real values later.)
            return FinalScoreFragment.newInstance(3, 6, formattedDateAndTime);
        }

        // returns the quiz questions
        return StartNewQuizFragment.newInstance(position); // creates new instance a quiz question with answers
    } // createFragment

    // In this case, 6 quiz question + 1 final score screen
    // call this method when it wants to know "how many pages will be available while swiping" (6 versions on the quiz)
    @Override
    public int getItemCount() {
        return StartNewQuizFragment.getNumberOfQuestions() + 1;
    } // getItemCount
} // QuizPagerAdapter