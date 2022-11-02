package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// used within the activity_main.xml file
public class  SplashScreen extends Fragment {

    public SplashScreen() {
        // Required empty public constructor
    } // SplashScreen

    // Prob don't need
//    public static SplashScreen newInstance() {
//        SplashScreen fragment = new SplashScreen();
//        return fragment;
//    }

    // layout of the main screen
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment with the fragment_splash_screen.xml file
        // Splash screen is the first (default) fragment displayed.
        return inflater.inflate( R.layout.fragment_splash_screen, container, false );
    } // onCreateView

    @Override
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
    } // onViewCreated

    // setting the title in the action bar
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    } // onResume
} // SplashScreen