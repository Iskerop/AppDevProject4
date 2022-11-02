package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This class creates a fragment that has a viewpager2 widget that will control the
 * swiping of pages which in this case are the questions in the quiz.
 */
public class ViewPagerFragment extends Fragment {

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
        QuizPagerAdapter avpAdapter = new QuizPagerAdapter(this);

        // set the viewPager (pager) with the avpAdapter so we can have values (the slides).
        pager.setAdapter(avpAdapter);
        // get instance of our adapter with the custom class we made "AndroidVersionsPagerAdapter"
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
    } // onViewCreated
} // ViewPagerFragment