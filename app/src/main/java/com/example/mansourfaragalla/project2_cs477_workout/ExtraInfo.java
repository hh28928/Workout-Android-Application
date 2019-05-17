package com.example.mansourfaragalla.project2_cs477_workout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExtraInfo extends AppCompatActivity {

    EditText weight;
    EditText reps;
    EditText sets;
    EditText notes;

    Button update;

    DatabaseHelper myDB;
    String itemName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_info);

        weight = (EditText) findViewById(R.id.Edit_weight);
        reps = (EditText) findViewById(R.id.Edit_Reps);
        sets = (EditText) findViewById(R.id.Edit_Sets);
        notes = (EditText) findViewById(R.id.Edit_Notes);

        update = (Button) findViewById(R.id.updateValues);

        itemName = getIntent().getStringExtra("Name");

        myDB = new DatabaseHelper(this);

        new CheckStatus(this).execute(itemName);
    }

    //add to the database the user's new info:
    public void UpdatetheValues(View v){

        //getting the entry of the user:
        int weightUser = Integer.parseInt(weight.getText().toString());
        int repsUser = Integer.parseInt(reps.getText().toString());
        int setsUser = Integer.parseInt(sets.getText().toString());
        String notesUser = notes.getText().toString();

        /*myDB.updateItem(itemName, weightUser, repsUser, setsUser,notesUser);


        Cursor cursor = myDB.getItem(itemName);

        Toast.makeText(this, "The details of the record is " + cursor.getString(2) + ", " +
            cursor.getString(3) + ", " + cursor.getString(4) + ", " +
            cursor.getString(4), Toast.LENGTH_LONG).show();*/

        new UpdateTask(this).execute(itemName, weightUser + "",
                repsUser + "", setsUser + "", notesUser);


    }


    private class UpdateTask extends AsyncTask<String,Void,Void> {

        Context ctx;


        UpdateTask(Context ctx){

            this.ctx = ctx;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... paramas) {
            String name = paramas[0];
            int weight = Integer.parseInt(paramas[1]);
            int reps = Integer.parseInt(paramas[2]);
            int sets = Integer.parseInt(paramas[3]);
            String notes = paramas[4];
            DatabaseHelper mydb = new DatabaseHelper(ctx);

            mydb.updateItem(name, weight, reps, sets, notes);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent intent = new Intent(ctx, MainActivity.class);
            startActivity(intent);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    private class CheckStatus extends AsyncTask<String,Void,Cursor> {

        Context ctx;


        CheckStatus(Context ctx){

            this.ctx = ctx;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(String... paramas) {
            String name = paramas[0];

            DatabaseHelper mydb = new DatabaseHelper(ctx);

            return mydb.getItem(name);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            if (cursor.moveToFirst()) {
                if (cursor.getString(5) != null) {
                    int weight = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL3));
                    int reps = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL4));
                    int sets = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL5));
                    String notes = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL6));

                    Log.d("ExtraInfo", "weight " + weight + ", " + reps + ", " + sets +
                        ", " + notes);

                    ((ExtraInfo) ctx).updateUI(weight, reps, sets, notes);
                }
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    public void updateUI(int w, int r, int s, String note) {
        weight.setText(Integer.toString(w));
        reps.setText(Integer.toString(r));
        sets.setText(Integer.toString(s));
        notes.setText(note);
    }

}
