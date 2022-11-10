package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QuizHistoryData {
    public static final String DEBUG_TAG = "QuizHistoryData";

    // this is a reference to our database; it is used later to run SQL commands
    private SQLiteDatabase db;
    private SQLiteOpenHelper quizHistoryDBHelper;
    private static final String[] allColumns = {
            QuizHistoryDBHelper.QUIZZES_COLUMN_ID,
            QuizHistoryDBHelper.QUIZZES_COLUMN_DATE,
            QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_ONE,
            QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_TWO,
            QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_THREE,
            QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_FOUR,
            QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_FIVE,
            QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_SIX,
            QuizHistoryDBHelper.QUIZZES_COLUMN_RESULT,
            QuizHistoryDBHelper.QUIZZES_COLUMN_NUM_ANSWERED
    };

    public QuizHistoryData(Context context ) {
        this.quizHistoryDBHelper = QuizHistoryDBHelper.getInstance( context );
    }

    // Open the database
    public void open() {
        db = quizHistoryDBHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "QuizHistoryData: db open" );
    }

    // Close the database
    public void close() {
        if( quizHistoryDBHelper != null ) {
            quizHistoryDBHelper.close();
            Log.d(DEBUG_TAG, "QuizHistoryData: db closed");
        }
    }

    public boolean isDBOpen()
    {
        return db.isOpen();
    }

    // Retrieve all quiz questions and return them as a List.
    // This is how we restore persistent objects stored as rows in the job leads table in the database.
    // For each retrieved row, we create a new JobLead (Java POJO object) instance and add it to the list.
    public List<QuizHistory> retrieveHistory() {
        ArrayList<QuizHistory> quizzes = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( QuizHistoryDBHelper.TABLE_QUIZZES, allColumns,
                    null, null, null, null, null );

            // collect all job leads into a List
            if( cursor != null && cursor.getCount() > 0 ) {

                while( cursor.moveToNext() ) {

                    if( cursor.getColumnCount() >= 6) {

                        // get all attribute values of this job lead
                        columnIndex = cursor.getColumnIndex( QuizHistoryDBHelper.QUIZZES_COLUMN_ID );
                        long id = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizHistoryDBHelper.QUIZZES_COLUMN_DATE );
                        String date = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_ONE );
                        String one = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_TWO );
                        String two = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_THREE );
                        String three = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex( QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_FOUR);
                        String four = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex( QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_FIVE);
                        String five = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex( QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_SIX);
                        String six = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex( QuizHistoryDBHelper.QUIZZES_COLUMN_RESULT);
                        String result = cursor.getString(columnIndex);
                        columnIndex = cursor.getColumnIndex( QuizHistoryDBHelper.QUIZZES_COLUMN_NUM_ANSWERED);
                        int numAnswered = cursor.getInt(columnIndex);

                        // create a new JobLead object and set its state to the retrieved values
                        QuizHistory quiz = new QuizHistory(date, one, two, three, four, five, six, result, numAnswered);
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
        //Log.i("QUIZ DATA", "number of quizzes: " + quizzes.size());
        return quizzes;
    }

    // Store a new quiz question in the database.
    public QuizHistory storeQuizHistory(QuizHistory quiz ) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the JobLead argument.
        // This is how we are providing persistence to a JobLead (Java object) instance
        // by storing it as a new row in the database table representing job leads.
        ContentValues values = new ContentValues();
        values.put(QuizHistoryDBHelper.QUIZZES_COLUMN_DATE, quiz.getDate());
        values.put(QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_ONE, quiz.getFirstQuest() );
        values.put(QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_TWO, quiz.getSecondQuest() );
        values.put(QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_THREE, quiz.getThirdQuest() );
        values.put(QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_FOUR, quiz.getFourthQuest());
        values.put(QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_FIVE, quiz.getFifthQuest());
        values.put(QuizHistoryDBHelper.QUIZZES_COLUMN_QUEST_SIX, quiz.getSixthQuest());
        values.put(QuizHistoryDBHelper.QUIZZES_COLUMN_RESULT, quiz.getResult());
        values.put(QuizHistoryDBHelper.QUIZZES_COLUMN_NUM_ANSWERED, quiz.getNumAnswered());

        // Insert the new row into the database table;
        // The id (primary key) is automatically generated by the database system
        // and returned as from the insert method call.
        long id = db.insert( QuizHistoryDBHelper.TABLE_QUIZZES, null, values );

        // store the id (the primary key) in the JobLead instance, as it is now persistent
        quiz.setId( id );

        Log.d( DEBUG_TAG, "Stored new quiz history with id: " + String.valueOf( quiz.getId() ) );

        return quiz;
    }
}
