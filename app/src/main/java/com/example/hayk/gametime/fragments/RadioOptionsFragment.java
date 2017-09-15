package com.example.hayk.gametime.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hayk.gametime.R;
import com.example.hayk.gametime.activities.RadioActivity;
import com.example.hayk.gametime.activities.RadioOptionsActivity;


public class RadioOptionsFragment extends Fragment {

    TextView mPointsNumber;
    TextView mTimeNumber;
    ImageView mPointsPlus;
    ImageView mPointsMinus;
    ImageView mTimePlus;
    ImageView mTimeMinus;
    ImageView mBack;
    ImageView mPlay;
    Integer mCurrentNumberPoints;
    Integer mCurrentNumberTime;
    String mCurrentStringPoints;
    String mCurrentStringTime;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_radio_options, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPointsNumber = (TextView)view.findViewById(R.id.points_number);
        mTimeNumber = (TextView)view.findViewById(R.id.time_number);
        mPointsPlus = (ImageView)view.findViewById(R.id.points_plus);
        mPointsMinus = (ImageView)view.findViewById(R.id.points_minus);
        mTimePlus = (ImageView)view.findViewById(R.id.time_plus);
        mTimeMinus = (ImageView)view.findViewById(R.id.time_minus);
        mBack = (ImageView)view.findViewById(R.id.back_to_teames);
        mPlay = (ImageView)view.findViewById(R.id.play_radio_game);

        mFragmentManager = getActivity().getFragmentManager();

        mPointsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNumberPoints = Integer.parseInt(mPointsNumber.getText().toString());
                if(mCurrentNumberPoints<300){
                    mCurrentNumberPoints = mCurrentNumberPoints + 5;
                    mCurrentStringPoints = mCurrentNumberPoints.toString();
                    mPointsNumber.setText(mCurrentStringPoints);
                }
            }
        });

        mPointsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNumberPoints = Integer.parseInt(mPointsNumber.getText().toString());
                if(mCurrentNumberPoints>20) {
                    mCurrentNumberPoints = mCurrentNumberPoints - 5;
                    mCurrentStringPoints =  mCurrentNumberPoints.toString();
                    mPointsNumber.setText(mCurrentStringPoints);
                }
            }
        });

        mTimePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNumberTime = Integer.parseInt(mTimeNumber.getText().toString());
                if(mCurrentNumberTime<120){
                    mCurrentNumberTime = mCurrentNumberTime + 5;
                    mCurrentStringTime = mCurrentNumberTime.toString();
                    mTimeNumber.setText(mCurrentStringTime);
                }
            }
        });

        mTimeMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNumberTime = Integer.parseInt(mTimeNumber.getText().toString());
                if(mCurrentNumberTime>30){
                    mCurrentNumberTime = mCurrentNumberTime - 5;
                    mCurrentStringTime = mCurrentNumberTime.toString();
                    mTimeNumber.setText(mCurrentStringTime);
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentTransaction = mFragmentManager.beginTransaction();
                RadioOptionsFragment radioOptionsFragment = (RadioOptionsFragment)mFragmentManager.findFragmentByTag(RadioOptionsActivity.RADIO_OPTIONS_FRAGMENT);
                RadioTeamsFragment radioTeamsFragment = new RadioTeamsFragment();
                Bundle args = new Bundle();
                if(!getArguments().getString(RadioOptionsActivity.TEAM_NUMBER_ONE).equals("Թիմ համար 1")){
                    args.putString(RadioOptionsActivity.TEAM_NUMBER_ONE,getArguments().getString(RadioOptionsActivity.TEAM_NUMBER_ONE));
                }if(!getArguments().getString(RadioOptionsActivity.TEAM_NUMBER_TWO).equals("Թիմ համար 2")){
                    args.putString(RadioOptionsActivity.TEAM_NUMBER_TWO,getArguments().getString(RadioOptionsActivity.TEAM_NUMBER_TWO));
                }
                radioTeamsFragment.setArguments(args);
                mFragmentTransaction.remove(radioOptionsFragment);
                mFragmentTransaction.add(R.id.radio_activity_container,radioTeamsFragment,RadioOptionsActivity.RADIO_TEAMS_FRAGMENT);
                mFragmentTransaction.commit();
            }
        });

        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RadioActivity.class);
                intent.putExtra(RadioOptionsActivity.TEAM_NUMBER_ONE,getArguments().getString(RadioOptionsActivity.TEAM_NUMBER_ONE));
                intent.putExtra(RadioOptionsActivity.TEAM_NUMBER_TWO,getArguments().getString(RadioOptionsActivity.TEAM_NUMBER_TWO));
                intent.putExtra(RadioOptionsActivity.POINTS_NUMBER,Integer.parseInt(mPointsNumber.getText().toString()));
                intent.putExtra(RadioOptionsActivity.TIME_NUMBER,mTimeNumber.getText().toString());
                getActivity().finish();
                startActivity(intent);
            }
        });
    }
}
