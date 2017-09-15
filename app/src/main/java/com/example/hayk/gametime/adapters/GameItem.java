package com.example.hayk.gametime.adapters;

import android.net.Uri;


public class GameItem {
    private int mIcon;
    private String mName;

    public GameItem(int id, String name) {
        mIcon = id;
        mName = name;
    }

    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
