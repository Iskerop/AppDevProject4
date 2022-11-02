package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class StartNewQuizFragment extends Fragment {

    // Array of states in the U.S.
    // temporary for now b/c database hasn't been created yet.
    private static final String[] countries = {
            "Georgia",
            "Florida",
            "Alabama",
            "Arkansas",
            "Texas",
            "South Carolina"
    }; // countries

    private static final String TAG = "StartNewQuizFragment";

    // We will create a variable called quizQuestions and quizAnswers (maybe quizQAndAData)
//    private JobLeadsData jobLeadsData = null;

    // which quiz question to display in the fragment
    private int quizNum;

    public StartNewQuizFragment() {
        // Required empty public constructor
        // gain access to viewpager
    } // StartNewQuizFragment

    // creates a new instance of a quiz question.
    public static StartNewQuizFragment newInstance(int position) {
        StartNewQuizFragment fragment = new StartNewQuizFragment();

        // store int position param in args (bundle) so we can pass the bundle (contains position value)
        // to other functions.
        // assists with keeping track of which state question to display.
        Bundle args = new Bundle();
        args.putInt( "quizQuestionPosition", position );
        fragment.setArguments( args );
        return fragment;
    } // newInstance

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // storing the bundle value in a class variable
            quizNum = getArguments().getInt("quizQuestionPosition");
        } // if
    } // onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quiz_question, container, false);
    } // onCreateView

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // this is the second textview in quiz_question.xml file that asks the user what capital for
        // the asked "state".
        TextView capitalTextView = getView().findViewById(R.id.textView5); // the "state"
        capitalTextView.setText(countries[quizNum] + "?");
    } // onViewCreated

//    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing of a job lead, asynchronously.
//    public class JobLeadDBWriter extends AsyncTask<JobLead, JobLead> {
//
//        // This method will run as a background process to write into db.
//        // It will be automatically invoked by Android, when we call the execute method
//        // in the onClick listener of the Save button.
//        @Override
//        protected JobLead doInBackground( JobLead... jobLeads ) {
//            jobLeadsData.storeJobLead( jobLeads[0] );
//            return jobLeads[0];
//        }
//
//        // This method will be automatically called by Android once the writing to the database
//        // in a background process has finished.  Note that doInBackground returns a JobLead object.
//        // That object will be passed as argument to onPostExecute.
//        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
//        @Override
//        protected void onPostExecute( JobLead jobLead ) {
//            // Show a quick confirmation message
//            Toast.makeText( getActivity(), "Job lead created for " + jobLead.getCompanyName(),
//                    Toast.LENGTH_SHORT).show();
//
//            // Clear the EditTexts for next use.
//            companyNameView.setText( "" );
//            phoneView.setText( "" );
//            urlView.setText( "" );
//            commentsView.setText( "" );
//
//            Log.d( TAG, "Job lead saved: " + jobLead );
//        }
//    }
//
//    private class SaveButtonClickListener implements View.OnClickListener {
//        @Override
//        public void onClick( View v ) {
//            String companyName = companyNameView.getText().toString();
//            String phone = phoneView.getText().toString();
//            String url = urlView.getText().toString();
//            String comments = commentsView.getText().toString();
//
//            JobLead jobLead = new JobLead( companyName, phone, url, comments );
//
//            // Store this new job lead in the database asynchronously,
//            // without blocking the UI thread.
//            new JobLeadDBWriter().execute( jobLead );
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        // open the database in onResume
//        if( jobLeadsData != null )
//            jobLeadsData.open();
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
//    }
//
//    // We need to save job leads into a file as the activity stops being a foreground activity
//    @Override
//    public void onPause() {
//        Log.d( TAG, "AddJobLeadFragment.onPause()" );
//        super.onPause();
//        // close the database in onPause
//        if( jobLeadsData != null )
//            jobLeadsData.close();
    // gain access to viewpager
//    }

        // helps with deciding how many question we want to display to the user
        public static int getNumberOfQuestions() {
            return countries.length; // 6 questions (6 screens to slide)
        } // getNumberOfVersions

    } // StartNewQuizFragment