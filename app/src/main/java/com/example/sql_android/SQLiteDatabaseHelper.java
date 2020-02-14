package com.example.sql_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "university";

    public SQLiteDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*
        Initialize database for when first entering application
         */
        String sqlCommand = "CREATE TABLE students (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT);";
        sqLiteDatabase.execSQL(sqlCommand);

        String sqlInsert = "INSERT INTO students (name, email) VALUES (\"David\", \"david@yahoo.com\");";
        sqLiteDatabase.execSQL(sqlInsert);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "Brad");
        contentValues.put("email", "brad@yahoo.com");
        sqLiteDatabase.insert("students", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*
        Useful for updating a database
         */
        String sqlStatement = "ALTER TABLE students ADD COLUMN grade DOUBLE";
        sqLiteDatabase.execSQL(sqlStatement);
    }

    public void insert(SQLiteDatabase sqLiteDatabase, String tableName, String name, String email){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        sqLiteDatabase.insert(tableName, null, contentValues);
    }

    public Cursor read(SQLiteDatabase sqLiteDatabase, String tableName){
        Cursor cursor = sqLiteDatabase.query(tableName, null, null, null, null, null, null);
        return cursor;
    }

    public void updateName(SQLiteDatabase sqLiteDatabase, String tableName, String oldName, String newName){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", newName);
        sqLiteDatabase.update(tableName, contentValues, "name = ?", new String[]{oldName});

        /* This is similar to:
        String sqlStatement = "UPDATE students SET name = \"TOM\" WHERE name = \"David\"";
        sqLiteDatabase.execSQL(sqlStatement);
        */
    }

    public void deleteName(SQLiteDatabase sqLiteDatabase, String tableName, String name){
        sqLiteDatabase.delete(tableName, "name = ?", new String[]{name});
    }
}
