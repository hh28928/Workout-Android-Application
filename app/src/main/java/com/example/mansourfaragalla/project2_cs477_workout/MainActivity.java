package com.example.mansourfaragalla.project2_cs477_workout;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.support.v4.widget.SimpleCursorAdapter;



import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;

    ListView MyListOfSports;

    BackgroundTask backgroundTask = new BackgroundTask(this);
    //String name;

    String listElement;
    String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = getIntent();
        //name = intent.getStringExtra("Name");


        MyListOfSports = findViewById(R.id.MyListOfSports);

        myDB = new DatabaseHelper(this);

        PopulateMyList();

        //if there was a long click!
        //then a popout window will appear and ask is user is sure about deleting this element
        MyListOfSports.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
              public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                  Toast.makeText(getApplicationContext(), "Long touched " + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();


                  listElement = ((TextView) view).getText().toString();


                  final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                  View popView = getLayoutInflater().inflate(R.layout.popup_window, null);

                  Button yesButton = popView.findViewById(R.id.ButtonYes);
                  Button noButton = popView.findViewById(R.id.ButtonNo);

                  builder.setView(popView);
                  final AlertDialog dialog = builder.create();

                  dialog.show();


                  //if user hits yes, then delete
                  yesButton.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {



                          Cursor data = myDB.getItemID(listElement); //get the ID that corresponds to this sport.

                          int sportID = -1;

                          while (data.moveToNext()) {

                              sportID = data.getInt(0);

                          }
                          Toast.makeText(MainActivity.this, "this: " + listElement + " will be deleted!",
                                  Toast.LENGTH_SHORT).show();

                          myDB.DeleteSport(sportID, listElement);

                          dialog.dismiss();
                          PopulateMyList();


                      }

                  });

                  //once the popup window appears: and user press no, then nothing happens
                  noButton.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          dialog.dismiss();
                          PopulateMyList();

                      }

                  });
                  PopulateMyList();

                  return true;

              }
          }
        );

        MyListOfSports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // your handler code here
                Intent intent = new Intent(MainActivity.this, ExtraInfo.class);
                String name = ((TextView) view).getText().toString();
                Log.d(TAG, "NAME IS " + name);
                intent.putExtra("Name", name);
                startActivity(intent);


            }
        });
}

//TODO: use AsyncTask to load the data.



    public void PopulateMyList(){


        new BackgroundTask(this).execute("get_info");
        //backgroundTask.execute("get_info");


//        //populate an arraylist with elements from the database, and then adapt with adapter
//        ArrayList<String> theList = new ArrayList<>();
//
//        //all content of the database the cursor will get;
//        Cursor data = myDB.getListContents();
//
//        if (data.getCount() == 0) {
//
//            Toast.makeText(MainActivity.this, "the database is empty",
//                    Toast.LENGTH_SHORT).show();
//        } else {
//
//            while (data.moveToNext()) {
//
//                theList.add(data.getString(1));
//
//                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
//                        theList);
//
//                MyListOfSports.setAdapter(listAdapter);
//
//            }
//
//        }
    }


    public void EditMoreExcersices(View view) {

        Intent intent = new Intent(this, EditExcersices.class);
        startActivity(intent);

    }



    private class BackgroundTask extends AsyncTask<String,Void,Cursor> {

        Context ctx;


        BackgroundTask(Context ctx){

            this.ctx = ctx;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(String... paramas) {
            String method = paramas[0];
            DatabaseHelper mydb = new DatabaseHelper(ctx);

            String id;
            String name;


            if(method.equals("get_info")) {

                SQLiteDatabase db = mydb.getReadableDatabase();

                Cursor cursor = mydb.getListContents();
                return cursor;
            }


            return null;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            //super.onPostExecute(aVoid);

            //populate an arraylist with elements from the database, and then adapt with adapter

            if(cursor == null) {
                Log.i("example-tag", "cursor  is null, not setting list");
                return;
            }
            ArrayList<String> theList = new ArrayList<>();
            theList.add("Shoulder lifts");
            theList.add("Bicep Curls");

            while (cursor.moveToNext()){

                theList.add(cursor.getString(1));

            }
            //prevents memorry leak
            try {
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ListAdapter listAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,
                    theList);

            MyListOfSports.setAdapter(listAdapter);

            backgroundTask = null;

            Toast.makeText(MainActivity.this, "done loading ",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
