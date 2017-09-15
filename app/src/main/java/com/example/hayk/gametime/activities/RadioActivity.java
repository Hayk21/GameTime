package com.example.hayk.gametime.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewTreeObserver;

import com.example.hayk.gametime.R;
import com.example.hayk.gametime.custom_views.ScorePaper;
import com.example.hayk.gametime.fragments.RadioMelodyFragment;

public class RadioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String RADIO_MELODY_FRAGMENT = "RadioMelodyFragment";
    public static final String RADIO_GAME_FRAGMENT = "RadioGameFragment";
    public static final String MELODY = "Melody";
    public static final String BOOLEAN_FOR_QUEUE_OF_TEAMS = "BooleanForQueueOfTeams";

    ScorePaper mScorePaper;
    boolean mWithTeams;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    RadioMelodyFragment mRadioMelodyFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mScorePaper = (ScorePaper) findViewById(R.id.score_place);
        mFragmentManager = getFragmentManager();
        mRadioMelodyFragment = new RadioMelodyFragment();

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mScorePaper.startDrawing();
                drawer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        setTitle("ՓՉԱՑԱԾ ՌԱԴԻՈ");
        if(getIntent() != null){
            if(getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_ONE) != null){
                mWithTeams = true;
                mScorePaper.setTeamOneName(getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_ONE));
                mScorePaper.setTeamTwoName(getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_TWO));
            }else {
                mWithTeams = false;
                toggle.setDrawerIndicatorEnabled(false);
                drawer.removeView(navigationView);
            }
        }
        Bundle args = new Bundle();
        args.putBoolean(BOOLEAN_FOR_QUEUE_OF_TEAMS,mWithTeams);
        mRadioMelodyFragment.setArguments(args);
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.radio_game_activity,mRadioMelodyFragment,RADIO_MELODY_FRAGMENT);
        mFragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(mFragmentManager.findFragmentByTag(RADIO_GAME_FRAGMENT)!=null){
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.you_realy_want_to_exit));
                builder.setPositiveButton("ԱՅՈ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RadioActivity.this.finish();
                    }
                });
                builder.setNegativeButton("ՈՉ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }else
            if(mFragmentManager.findFragmentByTag(RADIO_MELODY_FRAGMENT)!=null){
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.you_realy_want_to_exit));
                builder.setPositiveButton("ԱՅՈ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RadioActivity.this.finish();
                    }
                });
                builder.setNegativeButton("ՈՉ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
