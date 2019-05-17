package com.example.mansourfaragalla.project2_cs477_workout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class EditExcersices extends AppCompatActivity {

    DatabaseHelper myDB;

    Button updateButton;

    EditText nameOfSport;

    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_excersices);



        nameOfSport = findViewById(R.id.EditTextNameOfActivity);

        updateButton = findViewById(R.id.UpdateButton);

        myDB = new DatabaseHelper(this);

        //name = getIntent().getStringExtra("Name");



    }



    //add to the database the user's new sport:
    public void AddToMySchedule(View v){

        //getting the entry of the user:
        String newSport = nameOfSport.getText().toString();

        //checks if the edit text field is empty:
        if (nameOfSport.length() != 0){

            nameOfSport.setText("");
        }

        else{
            Toast.makeText(EditExcersices.this, "Please enter the type of sport you" +
                    "you are practicing", Toast.LENGTH_LONG).show();

        }

        //now to add to the actual database and check if it was added:
        boolean isitdone = myDB.addData(newSport);

        if (isitdone == true){

            Toast.makeText(EditExcersices.this, newSport + " has been added to the Database",
                    Toast.LENGTH_LONG).show();

        }

        else{

            Toast.makeText(EditExcersices.this, "Something went wrong :(",
                    Toast.LENGTH_LONG).show();

        }


        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Name", nameOfSport.getText());
        startActivity(intent);



    }



}
