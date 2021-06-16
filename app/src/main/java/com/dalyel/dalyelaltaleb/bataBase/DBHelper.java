package com.dalyel.dalyelaltaleb.bataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;



public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="data.db";
    private static final int DB_VERSION=6;


    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBContract.Question.CREATE_STATMENT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
if(i1>i){
    sqLiteDatabase.execSQL(DBContract.Question.DELEATE_STATMENT);
    onCreate(sqLiteDatabase);
}

    }
    public void insertData(String question,  byte[] image ){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO "+DBContract.Question.TABLE_NAME+" VALUES(null,?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, question);
        statement.bindBlob(2, image);
        statement.executeInsert();
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
}
