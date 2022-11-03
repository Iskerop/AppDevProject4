package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is facilitates storing and restoring job leads stored.
 */
public class QuizData {

    public static final String DEBUG_TAG = "QuizData";

    // this is a reference to our database; it is used later to run SQL commands
    private SQLiteDatabase   db;
    private SQLiteOpenHelper jobLeadsDbHelper;
    private static final String[] allColumns = {
            QuizDBHelper.QUIZZES_COLUMN_ID,
            QuizDBHelper.QUIZZES_COLUMN_NAME,
            QuizDBHelper.QUIZZES_COLUMN_PHONE,
            QuizDBHelper.QUIZZES_COLUMN_URL,
            QuizDBHelper.QUIZZES_COLUMN_COMMENTS
    };

    public QuizData(Context context ) {
        this.jobLeadsDbHelper = QuizDBHelper.getInstance( context );
    }

    // Open the database
    public void open() {
        db = jobLeadsDbHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "JobLeadsData: db open" );
    }

    // Close the database
    public void close() {
        if( jobLeadsDbHelper != null ) {
            jobLeadsDbHelper.close();
            Log.d(DEBUG_TAG, "JobLeadsData: db closed");
        }
    }

    public boolean isDBOpen()
    {
        return db.isOpen();
    }

    // Retrieve all job leads and return them as a List.
    // This is how we restore persistent objects stored as rows in the job leads table in the database.
    // For each retrieved row, we create a new JobLead (Java POJO object) instance and add it to the list.
    public List<Quiz> retrieveAllQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( QuizDBHelper.TABLE_QUIZZES, allColumns,
                    null, null, null, null, null );

            // collect all job leads into a List
            if( cursor != null && cursor.getCount() > 0 ) {

                while( cursor.moveToNext() ) {

                    if( cursor.getColumnCount() >= 5) {

                        // get all attribute values of this job lead
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_ID );
                        long id = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_NAME );
                        String name = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_PHONE );
                        String phone = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_URL );
                        String uri = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_COMMENTS );
                        String comments = cursor.getString( columnIndex );

                        // create a new JobLead object and set its state to the retrieved values
                        Quiz quiz = new Quiz( name, phone, uri, comments );
                        quiz.setId(id); // set the id (the primary key) of this object
                        // add it to the list
                        quizzes.add( quiz );
                        Log.d(DEBUG_TAG, "Retrieved Quiz: " + quiz);
                    }
                }
            }
            if( cursor != null )
                Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
            else
                Log.d( DEBUG_TAG, "Number of records from DB: 0" );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // return a list of retrieved job leads
        return quizzes;
    }

    // Store a new quiz in the database.
    public Quiz storeJobLead( Quiz quiz ) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the JobLead argument.
        // This is how we are providing persistence to a JobLead (Java object) instance
        // by storing it as a new row in the database table representing job leads.
        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.QUIZZES_COLUMN_NAME, quiz.getCompanyName());
        values.put(QuizDBHelper.QUIZZES_COLUMN_PHONE, quiz.getPhone() );
        values.put(QuizDBHelper.QUIZZES_COLUMN_URL, quiz.getUrl() );
        values.put(QuizDBHelper.QUIZZES_COLUMN_COMMENTS, quiz.getComments() );

        // Insert the new row into the database table;
        // The id (primary key) is automatically generated by the database system
        // and returned as from the insert method call.
        long id = db.insert( QuizDBHelper.TABLE_QUIZZES, null, values );

        // store the id (the primary key) in the JobLead instance, as it is now persistent
        quiz.setId( id );

        Log.d( DEBUG_TAG, "Stored new quiz with id: " + String.valueOf( quiz.getId() ) );

        return quiz;
    }
}

