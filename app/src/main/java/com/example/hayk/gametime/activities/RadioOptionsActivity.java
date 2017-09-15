package com.example.hayk.gametime.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hayk.gametime.R;
import com.example.hayk.gametime.fragments.RadioTeamsFragment;

public class RadioOptionsActivity extends AppCompatActivity {

    public static final String RADIO_TEAMS_FRAGMENT = "RadioTeamsFragment";
    public static final String RADIO_OPTIONS_FRAGMENT = "RadioOptionsFragment";
    public static final String TEAM_NUMBER_ONE = "TeamNumberOne";
    public static final String TEAM_NUMBER_TWO = "TeamNumberTwo";
    public static final String POINTS_NUMBER = "pointsNumber";
    public static final String TIME_NUMBER = "TimeNumber";

    FragmentManager mFragmentManager = getFragmentManager();
    FragmentTransaction mFragmentTransaction;
    RadioTeamsFragment mRadioTeamsFragment = new RadioTeamsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_options);

        setTitle("ՓՉԱՑԱԾ ՌԱԴԻՈ");
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.radio_activity_container,mRadioTeamsFragment,RADIO_TEAMS_FRAGMENT);
        mFragmentTransaction.commit();
    }
}
