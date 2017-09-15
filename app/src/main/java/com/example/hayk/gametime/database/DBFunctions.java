package com.example.hayk.gametime.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hayk.gametime.objects.DatabaseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hayk on 27.07.2017.
 */

public class DBFunctions {
    private DBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public DBFunctions(Context context) {
        mDBHelper = new DBHelper(context, "/data/data/com.example.hayk.gametime/databases/gametime.db");
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }

    public List<DatabaseObject> getData(String table, String column,String count) {
        Integer ID;
        String Work;
        List<DatabaseObject> list = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.query(table, null, DBHelper.USED + " =?", new String[]{"0"}, null, null, null, count);
        if (cursor.moveToFirst()) {
            do{
            ID = cursor.getInt(cursor.getColumnIndex(DBHelper.ID));
            Work = cursor.getString(cursor.getColumnIndex(column));
            DatabaseObject databaseObject = new DatabaseObject(ID, Work);
            list.add(databaseObject);}while (cursor.moveToNext());
            cursor.close();
            return list;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.USED, 0);
            mSQLiteDatabase.update(table, contentValues, null, null);
            cursor.close();
            Cursor cursor1 = mSQLiteDatabase.query(table, null, DBHelper.USED + " =?", new String[]{"0"}, null, null, null, count);
            if (cursor1.moveToFirst()) {
                do{
                ID = cursor1.getInt(cursor.getColumnIndex(DBHelper.ID));
                Work = cursor1.getString(cursor.getColumnIndex(column));
                DatabaseObject databaseObject = new DatabaseObject(ID, Work);
                list.add(databaseObject);}while (cursor1.moveToNext());
                cursor1.close();
                return list;
            } else return null;
        }
    }

    public void updateBombGameWorks(List<DatabaseObject> list, String table) {
        ContentValues contentValues = new ContentValues();
        for(DatabaseObject databaseObject :list) {
            String ID = databaseObject.getID().toString();
            contentValues.put(DBHelper.USED, 1);
            mSQLiteDatabase.update(table, contentValues, DBHelper.ID + " =?", new String[]{ID});
        }
    }
}
