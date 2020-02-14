package com.example.sql_android;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ListView listView;
    private SQLiteDatabase database;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.textView);

        DatabaseAsyncTask  databaseAsyncTask = new DatabaseAsyncTask();
        databaseAsyncTask.execute();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
    }

    public class DatabaseAsyncTask extends AsyncTask<Void, Void, String>{

        private SQLiteDatabaseHelper databaseHelper;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new SQLiteDatabaseHelper(MainActivity.this);
        }

        @Override
        protected String doInBackground(Void... voids) {

            try{
                database = databaseHelper.getReadableDatabase();
               // databaseHelper.insert(database, "students", "Sarah", "sarah@yahoo.com");
                databaseHelper.updateName(database, "students", "Brad", "Tom");
                //databaseHelper.deleteName(database, "students", "Sarah");
                //Cursor cursor = database.query("students", null, null, null,
                  //      null, null, "_ID");

                //Cursor cursor = databaseHelper.read(database, "students");

                //String sqlStatement = "SELECT * FROM students WHERE name = ?;";

                //Cursor cursor = database.rawQuery(sqlStatement, new String[]{"David"});

                String sqlStatement = "SELECT * FROM STUDENTS;";
                cursor = database.rawQuery(sqlStatement, null);

                String returnString = "";
                if(cursor.moveToFirst()){

                    for(int i=0; i<cursor.getCount(); i++){

                        for(int j=0; j<cursor.getColumnCount(); j++){
                            returnString += cursor.getColumnName(j) + ": " + cursor.getString(j) + "\n";
                        }
                        returnString += "*****\n";
                        cursor.moveToNext();
                    }
                }

//                adapter = new SimpleCursorAdapter(
//                        MainActivity.this,
//                        android.R.layout.simple_list_item_1,
//                        cursor,
//                        new String[]{"name"},
//                        new int[]{android.R.id.text1},
//                        0
//                );
//                listView.setAdapter(adapter);

                return returnString;
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            textView.setText(s);
        }
    }
}


