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
import android.widget.Button;
import android.widget.EditText;

import com.example.hayk.gametime.R;
import com.example.hayk.gametime.activities.RadioActivity;
import com.example.hayk.gametime.activities.RadioOptionsActivity;

public class RadioTeamsFragment extends Fragment {

    EditText mTeamOne;
    EditText mTeamTwo;
    Button mWithoutTeams;
    Button mNext;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    RadioOptionsFragment mRadioOptionsFragment = new RadioOptionsFragment();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_radio_teams, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTeamOne = (EditText) view.findViewById(R.id.team_number_one);
        mTeamTwo = (EditText) view.findViewById(R.id.team_number_two);
        mWithoutTeams = (Button) view.findViewById(R.id.play_without_teams);
        mNext = (Button) view.findViewById(R.id.radio_next);
        mFragmentManager = getActivity().getFragmentManager();

        if(getArguments() != null){
            if(getArguments().getString(RadioOptionsActivity.TEAM_NUMBER_ONE) != null){
                mTeamOne.setText(getArguments().getString(RadioOptionsActivity.TEAM_NUMBER_ONE));
            }if(getArguments().getString(RadioOptionsActivity.TEAM_NUMBER_TWO) != null){
                mTeamTwo.setText(getArguments().getString(RadioOptionsActivity.TEAM_NUMBER_TWO));
            }
        }

        mWithoutTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RadioActivity.class);
                startActivity(intent);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentTransaction = mFragmentManager.beginTransaction();
                String team1, team2;
                if (!mTeamOne.getText().toString().equals("")) {
                    team1 = mTeamOne.getText().toString();
                } else {
                    team1 = mTeamOne.getHint().toString();
                }
                if(!mTeamTwo.getText().toString().equals("")) {
                    team2 = mTeamTwo.getText().toString();
                }else {
                    team2 = mTeamTwo.getHint().toString();
                }
                Bundle args = new Bundle();
                args.putString(RadioOptionsActivity.TEAM_NUMBER_ONE, team1);
                args.putString(RadioOptionsActivity.TEAM_NUMBER_TWO, team2);
                mRadioOptionsFragment.setArguments(args);
                mFragmentTransaction.replace(R.id.radio_activity_container, mRadioOptionsFragment, RadioOptionsActivity.RADIO_OPTIONS_FRAGMENT);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
            }
        });
    }
}
