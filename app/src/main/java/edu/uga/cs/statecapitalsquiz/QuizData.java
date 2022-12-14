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
 * This class is facilitates storing and quiz questions stored.
 */
public class QuizData {

    public static final String DEBUG_TAG = "QuizData";
    private List<QuizQuestion> quizQuestions;

    // this is a reference to our database; it is used later to run SQL commands
    private SQLiteDatabase   db;
    private SQLiteOpenHelper quizDBHelper;
    private static final String[] allColumns = {
            QuizDBHelper.QUIZZES_COLUMN_ID,
            QuizDBHelper.QUIZZES_COLUMN_STATE,
            QuizDBHelper.QUIZZES_COLUMN_CAPITAL,
            QuizDBHelper.QUIZZES_COLUMN_FIRST,
            QuizDBHelper.QUIZZES_COLUMN_SECOND,
            QuizDBHelper.QUIZZES_COLUMN_CAPITAL_SINCE
    };

    public QuizData(Context context ) {
        this.quizDBHelper = QuizDBHelper.getInstance( context );
    }

    // Open the database
    public void open() {
        db = quizDBHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "QuizData: db open" );
    }

    // Close the database
    public void close() {
        if( quizDBHelper != null ) {
            quizDBHelper.close();
            Log.d(DEBUG_TAG, "QuizData: db closed");
        }
    }

    public boolean isDBOpen()
    {
        return db.isOpen();
    }

    // Retrieve all quiz questions and return them as a List.
    // This is how we restore persistent objects stored as rows in the job leads table in the database.
    // For each retrieved row, we create a new JobLead (Java POJO object) instance and add it to the list.
    public List<QuizQuestion> retrieveAllQuizQuestions() {
        ArrayList<QuizQuestion> questions = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( QuizDBHelper.TABLE_QUIZZES, allColumns,
                    null, null, null, null, null );

            // collect all job leads into a List
            if( cursor != null && cursor.getCount() > 0 ) {

                while( cursor.moveToNext() ) {

                    if( cursor.getColumnCount() >= 6) {

                        // get all attribute values of this quiz data
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_ID );
                        long id = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_STATE );
                        String state = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_CAPITAL );
                        String capital = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_FIRST );
                        String first = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_SECOND );
                        String second = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_CAPITAL_SINCE);
                        String capitalSince = cursor.getString(columnIndex);

                        // create a new question object and set its state to the retrieved values
                        QuizQuestion question = new QuizQuestion( state, capital, first, second, capitalSince );
                        question.setId(id); // set the id (the primary key) of this object

                        // add each question to the list of questions
                        questions.add( question );
                        //Log.d(DEBUG_TAG, "Retrieved Quiz Question Values: " + question);
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

        //Log.i("QUIZ DATA", "number of questions: " + questions.size());
        return questions;
    }

    // Store a new question question in the database.
    public QuizQuestion storeQuizQuestion(QuizQuestion question ) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the JobLead argument.
        // This is how we are providing persistence to a JobLead (Java object) instance
        // by storing it as a new row in the database table representing job leads.
        ContentValues values = new ContentValues();
        values.put(QuizDBHelper.QUIZZES_COLUMN_STATE, question.getState());
        values.put(QuizDBHelper.QUIZZES_COLUMN_CAPITAL, question.getCapital() );
        values.put(QuizDBHelper.QUIZZES_COLUMN_FIRST, question.getFirstCity() );
        values.put(QuizDBHelper.QUIZZES_COLUMN_SECOND, question.getSecondCity() );
        values.put(QuizDBHelper.QUIZZES_COLUMN_CAPITAL_SINCE, question.getCapitalSince());

        // Insert the new row into the database table;
        // The id (primary key) is automatically generated by the database system
        // and returned as from the insert method call.
        long id = db.insert( QuizDBHelper.TABLE_QUIZZES, null, values );

        // store the id (the primary key) in the JobLead instance, as it is now persistent
        question.setId( id );

        Log.d( DEBUG_TAG, "Stored new question " + question + " with id: " + String.valueOf( question.getId() ) );

        return question;
    }

    public QuizQuestion getQuizByID(long id) {
        Cursor cursor = db.query(QuizDBHelper.TABLE_QUIZZES, allColumns, "_id = ?", new String[]{String.valueOf(id)}, null, null, null, "1");
        int columnIndex;
        cursor.moveToNext();
        // get all attribute values of this job lead
        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_ID );
        long ident = cursor.getLong( columnIndex );
        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_STATE );
        String state = cursor.getString( columnIndex );
        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_CAPITAL );
        String capital = cursor.getString( columnIndex );
        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_FIRST );
        String first = cursor.getString( columnIndex );
        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_SECOND );
        String second = cursor.getString( columnIndex );
        columnIndex = cursor.getColumnIndex( QuizDBHelper.QUIZZES_COLUMN_CAPITAL_SINCE);
        String capitalSince = cursor.getString(columnIndex);

        QuizQuestion question = new QuizQuestion(state, capital, first, second, capitalSince);
        return question;
    }
}


