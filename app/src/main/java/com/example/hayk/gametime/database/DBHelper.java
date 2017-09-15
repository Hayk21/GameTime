package com.example.hayk.gametime.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Hayk on 27.07.2017.
 */

public class DBHelper extends SQLiteAssetHelper {
    public static final String DATABASE_NAME = "gametime.db";
    public static final int DATABASE_VERSION = 1;

    public static final String BOMB_TABLE = "BombGame";
    public static final String BOMB_TABLE_WORKS = "BombWorks";
    public static final String RADIO_VOICE_TABLE = "RadioVoice";
    public static final String RADIO_ARMENIAN_TABLE = "RadioArmenian";
    public static final String RADIO_RUSSIAN_TABLE = "RadioRussian";
    public static final String RADIO_FOREIGN_TABLE = "RadioForeign";
    public static final String ID = "ID";
    public static final String USED = "Used";
    public static final String BOMB_QUESTION= "Question";
    public static final String BOMB_WORK = "Work";
    public static final String RADIO_VOICE = "Voice";
    public static final String RADIO_SONG = "Song";

    public DBHelper(Context context, String storageDirectory) {
        super(context, DATABASE_NAME, storageDirectory, null, DATABASE_VERSION);
    }
}
