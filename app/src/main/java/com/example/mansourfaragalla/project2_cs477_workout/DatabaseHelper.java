package com.example.mansourfaragalla.project2_cs477_workout;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "GymList.db";
    private static final String TABLE_NAME = "GymList_data";

    //col 0
    private static final String COL1 = "ID";

    //col 1
    public static final String COL2 = "ITEM1";
    public static final String COL3 = "WEIGHT";
    public static final String COL4 = "REPS";
    public static final String COL5 = "SETS";
    public static final String COL6 = "NOTES";

    //clear the data here
    //set def




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 COL2 +" TEXT, "+
                COL3 + " INTEGER, " +
                COL4 +" INTEGER,  " +
                COL5 +" INTEGER, " +
                COL6 +" INTEGER " +
                ") ";
        db.execSQL(createTable);

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, "Shoulder lifts");
        contentValues.put(COL3, "150");
        contentValues.put(COL4, "12");
        contentValues.put(COL5, "10");
        contentValues.put(COL6, "only free weights");
        db.insert(TABLE_NAME, null, contentValues);

        contentValues.put(COL2, "Bicep Curls");
        contentValues.put(COL3, "140");
        contentValues.put(COL4, "12");
        contentValues.put(COL5, "10");
        contentValues.put(COL6, "only free weights");
        db.insert(TABLE_NAME, null, contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //a function to help add to the schedule
    public boolean addData(String item1){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        //we put the data in col2 because that is where we are storing item1
        contentValues.put(COL2, item1);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if it fails:
        if (result == -1){

            return false;

        }


        else{
            return true;
        }

    }

    //get contents of the database:
    public Cursor getListContents()
    {
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return data;

    }


    //this function returns the ID of the elements:
    public Cursor getItemID(String sport){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + sport + "'";

        Cursor data = db.rawQuery(query, null);
        return data;


    }

    public boolean checkItem(String sport) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + sport + "'";

        Cursor data = db.rawQuery(query, null);
        if (data.getString(6) == null) return false;
        return true;
    }

    public Cursor getItem(String sport) {
        SQLiteDatabase db = this.getWritableDatabase();

// Filter results WHERE "title" = 'My Title'
        String selection = COL2 + " = ?";
        String[] selectionArgs = {sport };


// How you want the results sorted in the resulting Cursor

        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        return cursor;
    }

    //this function will be used to delete stuff:
    public void DeleteSport(int id, String sport){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + id + "'" + " AND " + COL2 + " = '" + sport + "'";

        db.execSQL(query);



    }

    public void updateItem(String item1, int weight, int reps, int sets, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);
        contentValues.put(COL3, weight);
        contentValues.put(COL4, reps);
        contentValues.put(COL5, sets);
        contentValues.put(COL6, notes);
        db.update(TABLE_NAME, contentValues, "ITEM1 = '" + item1 + "'", new String[] {});
    }

}
