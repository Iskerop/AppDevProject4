package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizHistoryDBHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "QuizHistoryDBHelper";

    private static final String DB_NAME = "quizHistory.db";
    private static final int DB_VERSION = 1;

    // Define all names (strings) for table and column names.
    // This will be useful if we want to change these names later.
    public static final String TABLE_QUIZZES = "quizHistory";
    public static final String QUIZZES_COLUMN_ID = "_id";
    public static final String QUIZZES_COLUMN_DATE = "date";
    public static final String QUIZZES_COLUMN_QUEST_ONE = "questOne";
    public static final String QUIZZES_COLUMN_QUEST_TWO = "questTwo";
    public static final String QUIZZES_COLUMN_QUEST_THREE = "questThree";
    public static final String QUIZZES_COLUMN_QUEST_FOUR = "questFour";
    public static final String QUIZZES_COLUMN_QUEST_FIVE = "questFive";
    public static final String QUIZZES_COLUMN_QUEST_SIX = "questSix";
    public static final String QUIZZES_COLUMN_RESULT = "result";
    public static final String QUIZZES_COLUMN_NUM_ANSWERED = "numAnswered";

    // This is a reference to the only instance for the helper.
    private static QuizHistoryDBHelper helperInstance;

    // A Create table SQL statement to create a table for job leads.
    // Note that _id is an auto increment primary key, i.e. the database will
    // automatically generate unique id values as keys.
    private static final String CREATE_QUIZZES =
            "create table " + TABLE_QUIZZES + " ("
                    + QUIZZES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZZES_COLUMN_DATE + " TEXT, "
                    + QUIZZES_COLUMN_QUEST_ONE + " TEXT, "
                    + QUIZZES_COLUMN_QUEST_TWO + " TEXT, "
                    + QUIZZES_COLUMN_QUEST_THREE + " TEXT, "
                    + QUIZZES_COLUMN_QUEST_FOUR + " TEXT, "
                    + QUIZZES_COLUMN_QUEST_FIVE + " TEXT, "
                    + QUIZZES_COLUMN_QUEST_SIX + " TEXT, "
                    + QUIZZES_COLUMN_RESULT + " INTEGER, "
                    + QUIZZES_COLUMN_NUM_ANSWERED + " INTEGER"
                    + ")";

    // Note that the constructor is private!
    // So, it can be called only from
    // this class, in the getInstance method.
    private QuizHistoryDBHelper(Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    // Access method to the single instance of the class.
    // It is synchronized, so that only one thread can executes this method, at a time.
    public static synchronized QuizHistoryDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new QuizHistoryDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    } // QuizDBHelper getInstance

    // We must override onCreate method, which will be used to create the database if
    // it does not exist yet.
    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( CREATE_QUIZZES );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " created" );
    } // onCreate

    // We should override onUpgrade method, which will be used to upgrade the database if
    // its version (DB_VERSION) has changed.  This will be done automatically by Android
    // if the version will be bumped up, as we modify the database schema.
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_QUIZZES );
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " upgraded" );
    } // onUpgrade
}
