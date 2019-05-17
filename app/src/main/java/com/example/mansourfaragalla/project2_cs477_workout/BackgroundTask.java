package com.example.mansourfaragalla.project2_cs477_workout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
//
//public class BackgroundTask extends AsyncTask<String,Void,Void> {
//
//    Context ctx;
//
//
//    BackgroundTask(Context ctx){
//
//        this.ctx = ctx;
//
//
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected Void doInBackground(String... paramas) {
//        String method = paramas[0];
//        DatabaseHelper mydb = new DatabaseHelper(ctx);
//
//        String id;
//        String name;
//
//
//        if(method.equals("get_info")){
//
//            SQLiteDatabase db = mydb.getReadableDatabase();
//
//            Cursor cursor = mydb.getListContents();
//
//            //populate an arraylist with elements from the database, and then adapt with adapter
//            ArrayList<String> theList = new ArrayList<>();
//
//            while (cursor.moveToNext()){
//
//                theList.add(cursor.getString(1));
//
//                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
//                        theList);
//
//                MyListOfSports.setAdapter(listAdapter);
//            }
//
//
//        }
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
//        super.onPostExecute(aVoid);
//    }
//
//    @Override
//    protected void onProgressUpdate(Void... values) {
//        super.onProgressUpdate(values);
//    }
//}
