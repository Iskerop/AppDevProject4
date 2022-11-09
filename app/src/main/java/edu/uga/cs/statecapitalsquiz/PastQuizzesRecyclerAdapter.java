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

    private List<QuizQuestion> values;
    private List<QuizQuestion> originalValues;

    public PastQuizzesRecyclerAdapter(Context context, List<QuizQuestion> jobLeadList ) {
        this.context = context;
        this.values = jobLeadList;
        this.originalValues = new ArrayList<QuizQuestion>( jobLeadList );
    } // PastQuizzesRecyclerAdapter

    // reset the originalValues to the current contents of values
    public void sync()
    {
        originalValues = new ArrayList<QuizQuestion>( values );
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    public static class PastQuizzesHolder extends RecyclerView.ViewHolder {

        TextView companyName;
        TextView phone;
        TextView url;
        TextView comments;

        public PastQuizzesHolder(View itemView ) {
            super( itemView );

//            companyName = itemView.findViewById( R.id.companyName );
//            phone = itemView.findViewById( R.id.phone );
//            url = itemView.findViewById( R.id.url );
//            comments = itemView.findViewById( R.id.comments );
        } // PastQuizzesHolder constructor
    } // class PastQuizzesHolder

    @NonNull
    @Override
    public PastQuizzesHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.quiz_question, parent, false );
        return new PastQuizzesHolder( view );
    }

    // This method fills in the values of a holder to show a Quiz.
    // The position parameter indicates the position on the list of past quiz list.
    @Override
    public void onBindViewHolder(PastQuizzesHolder holder, int position ) {

        QuizQuestion quiz = values.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + quiz );

        holder.companyName.setText( quiz.getState());
        holder.phone.setText( quiz.getCapital() );
        holder.url.setText( quiz.getFirstCity() );
        holder.comments.setText( quiz.getSecondCity() );
    }

    @Override
    public int getItemCount() {
        if( values != null )
            return values.size();
        else
            return 0;
    }
}