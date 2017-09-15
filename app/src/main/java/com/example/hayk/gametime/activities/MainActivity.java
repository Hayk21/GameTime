package com.example.hayk.gametime.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hayk.gametime.adapters.AdapterForRecycler;
import com.example.hayk.gametime.adapters.GameItem;
import com.example.hayk.gametime.R;
import com.example.hayk.gametime.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mListOfGames;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterForRecycler mAdapterForRecycler;
    SQLiteDatabase sqLiteDatabase;
    Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DBHelper dbHelper = new DBHelper(this, "/data/data/com.example.hayk.gametime/databases/gametime.db");

        mListOfGames = (RecyclerView) findViewById(R.id.list_of_games);
        mAdapterForRecycler = new AdapterForRecycler(this);
        mLayoutManager = new LinearLayoutManager(this);
        mListOfGames.setLayoutManager(mLayoutManager);
        mListOfGames.setAdapter(mAdapterForRecycler);
        AdapterForRecycler.onAdapterItemClickListener adapterItemClickListener = new AdapterForRecycler.onAdapterItemClickListener() {
            @Override
            public void onItemClicked(GameItem gameItem) {
                switch (gameItem.getmName()) {
                    case "Բոմբ խաղ":
                        mIntent = new Intent(MainActivity.this, BombActivity.class);
                        startActivity(mIntent);
                        break;
                    case "Փչացած ռադիո":
                        mIntent = new Intent(MainActivity.this, RadioOptionsActivity.class);
                        startActivity(mIntent);
                        break;
                }
            }
        };
        mAdapterForRecycler.setOnAdapterListener(adapterItemClickListener);

        sqLiteDatabase = dbHelper.getReadableDatabase();
        List<String> list = getTaskList();
        for (String word1 : list) {
            Log.d("WORD", word1);
        }

    }

    public List<String> getTaskList() {
        List<String> list = new ArrayList<>();
        Integer ID;
        String name;
        String date;
        String time;
        String desc;
        String[] line;
        Cursor cursor = sqLiteDatabase.query("BombWorks", null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex("Work"));
                list.add(name);
            } while (cursor.moveToNext());
            cursor.close();
            return list;
        } else {
            cursor.close();
            return null;
        }
    }
}
