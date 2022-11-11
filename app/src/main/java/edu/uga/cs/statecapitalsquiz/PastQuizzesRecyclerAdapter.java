package edu.uga.cs.statecapitalsquiz;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all job leads.
 */
public class PastQuizzesRecyclerAdapter
        extends RecyclerView.Adapter<PastQuizzesRecyclerAdapter.PastQuizzesHolder> {

    public static final String DEBUG_TAG = "PastQuizzesRecyclerAdapter";

    private final Context context;

    private List<QuizHistory> values;
    private List<QuizHistory> originalValues;

    public PastQuizzesRecyclerAdapter(Context context, List<QuizHistory> quizList ) {
        this.context = context;
        this.values = quizList;
        this.originalValues = new ArrayList<QuizHistory>( quizList );
    } // PastQuizzesRecyclerAdapter

    // reset the originalValues to the current contents of values
    public void sync()
    {
        originalValues = new ArrayList<QuizHistory>( values );
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    public static class PastQuizzesHolder extends RecyclerView.ViewHolder {

        TextView quizDate;
        TextView quizScore;

        public PastQuizzesHolder(View itemView ) {
            super( itemView );

            quizDate = itemView.findViewById( R.id.quizDate );
            quizScore = itemView.findViewById( R.id.quizScore );
        } // PastQuizzesHolder constructor
    } // class PastQuizzesHolder

    @NonNull
    @Override
    public PastQuizzesHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.quiz, parent, false );
        return new PastQuizzesHolder( view );
    }

    // This method fills in the values of a holder to show a Quiz.
    // The position parameter indicates the position on the list of past quiz list.
    @Override
    public void onBindViewHolder(PastQuizzesHolder holder, int position ) {

        QuizHistory quiz = values.get( position );

        //Log.d( DEBUG_TAG, "onBindViewHolder: " + quiz );

        holder.quizDate.setText( quiz.getDate() );
        holder.quizScore.setText( String.valueOf(quiz.getResult()) );
    }


    // returns the total number of items in the data set held by the adapter
    @Override
    public int getItemCount() {
        if( values != null )
            return values.size();
        else
            return 0;
    } // getItemCount
}