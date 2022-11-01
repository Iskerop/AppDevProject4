package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HelpFragment extends Fragment {

    public HelpFragment() {
        // Required empty public constructor
    }

    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_help, container, false );
    }

    @Override
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated(view,savedInstanceState);

        TextView textView = getView().findViewById( R.id.textView );
        String text = getResources().getString( R.string.quiz_purpose );
        textView.setText( HtmlCompat.fromHtml( text, HtmlCompat.FROM_HTML_MODE_LEGACY ) );

        TextView textView2 = getView().findViewById( R.id.textView2 );
        String text2 = getResources().getString( R.string.how_to_use );
        textView2.setText( HtmlCompat.fromHtml( text2, HtmlCompat.FROM_HTML_MODE_LEGACY ) );
    }
}