package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class PastQuizzesFragment extends Fragment {

    private static final String TAG = "PastQuizzesFragment";

    private QuizHistoryData quizHistoryData = null;
    private List<QuizHistory> quizList;

    private RecyclerView recyclerView;
    private PastQuizzesRecyclerAdapter recyclerAdapter;

    public PastQuizzesFragment() {
        // Required empty public constructor
    }

    // NOT NEEDED
    public static PastQuizzesFragment newInstance() {
        PastQuizzesFragment fragment = new PastQuizzesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    } // onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_past_quizzes, container, false);
    } // onCreateView

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        recyclerView = getView().findViewById( R.id.recyclerView );

        // use a linear layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        recyclerView.setLayoutManager( layoutManager );

        quizList = new ArrayList<QuizHistory>();

        // Create a JobLeadsData instance, since we will need to save a new JobLead to the dn.
        // Note that even though more activites may create their own instances of the JobLeadsData
        // class, we will be using a single instance of the JobLeadsDBHelper object, since
        // that class is a singleton class.
        quizHistoryData = new QuizHistoryData( getActivity() );

        // Open that database for reading of the full list of job leads.
        // Note that onResume() hasn't been called yet, so the db open in it
        // was not called yet!
        quizHistoryData.open();

        // Execute the retrieval of the quizzes in an asynchronous way,
        // without blocking the main UI thread.
        new QuizDBReader().execute(); // create an instance of the db reader and call execute method that is in the AsyncTask.java class.

    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB reading of job leads, asynchronously.
    private class QuizDBReader extends AsyncTask<Void, List<QuizHistory>> {
        // This method will run as a background process to read from db.
        // It returns a list of retrieved JobLead objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the job leads review activity is started).
        @Override
        protected List<QuizHistory> doInBackground(Void... params ) {
            List<QuizHistory> quizList = quizHistoryData.retrieveHistory();

            Log.d( TAG, "QuizDBReader: Quizzes retrieved: " + quizList );
            return quizList;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( List<QuizHistory> quizList ) {
            Log.d( TAG, "QuizDBReader: quizList.size(): " + quizList.size() );
            quizList.addAll( quizList );

            // create the RecyclerAdapter and set it for the RecyclerView. HOW THE POPULATION OF QUIZZES OCCURS
            recyclerAdapter = new PastQuizzesRecyclerAdapter( getActivity(), quizList );
            recyclerView.setAdapter( recyclerAdapter );
        }
    }

//    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing of a Quiz, asynchronously.
//    public class QuizDBWriter extends AsyncTask<QuizHistory, QuizHistory> {
//
//        // This method will run as a background process to write into db.
//        // It will be automatically invoked by Android, when we call the execute method
//        // in the onClick listener of the Save button.
//        @Override
//        protected QuizHistory doInBackground(QuizHistory... quizzes ) {
//            quizHistoryData.storeQuizHistory( quizzes[0] );
//            return quizzes[0];
//        }
//
//        // This method will be automatically called by Android once the writing to the database
//        // in a background process has finished.  Note that "doInBackground" returns a Quiz object.
//        // That object will be passed as argument to onPostExecute.
//        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
//        @Override
//        protected void onPostExecute( QuizHistory quiz ) {
//            // Update the recycler view to include the new quiz
//            quizList.add( quiz );
//            // Sync the originalValues list in the recyler adapter to the new updated list (QuizList)
//            recyclerAdapter.sync();
//            // Notify the adapter that an item has been inserted
//            recyclerAdapter.notifyItemInserted(quizList.size() - 1 );
//            // Reposition the view to show to newly added item by smoothly scrolling to it
//            recyclerView.smoothScrollToPosition( quizList.size() - 1 );
//            Log.d( TAG, "Quiz saved: " + quiz );
//        } // onPostExecute
//    } // QuizDBWriter

    @Override
    public void onResume() {
        super.onResume();

        // Open the database
        if( quizHistoryData != null && !quizHistoryData.isDBOpen() ) {
            quizHistoryData.open();
            Log.d( TAG, "PastQuizzesFragment.onResume(): opening DB" );
        } // if

        // Update the app name in the Action Bar to be the same as the app's name
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    } // onResume

    // We need to save job leads into a file as the activity stops being a foreground activity
    @Override
    public void onPause() {
        super.onPause();

        // close the database in onPause
        if( quizHistoryData != null ) {
            quizHistoryData.close();
            Log.d( TAG, "PastQuizzesFragment.onPause(): closing DB" );
        } // if
    } // onPause
} // PastQuizzesFragment