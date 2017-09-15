package com.example.hayk.gametime.fragments;



import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hayk.gametime.R;
import com.example.hayk.gametime.activities.RadioActivity;
import com.example.hayk.gametime.activities.RadioOptionsActivity;

public class RadioMelodyFragment extends Fragment {

    Button mArmenian,mRussian,mForeign,mMixed;
    TextView mTextOfChoose;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    RadioGameFragment mRadioGameFragment;
    boolean mFirstTeam;
    String mTextForTeams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_radio_melody, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        onMelodyItemClickListener melodyItemClickListener = new onMelodyItemClickListener();

        mFragmentManager = getActivity().getFragmentManager();
        mRadioGameFragment = new RadioGameFragment();
        mArmenian = (Button)view.findViewById(R.id.armenian_music);
        mRussian = (Button)view.findViewById(R.id.russian_music);
        mForeign = (Button)view.findViewById(R.id.foreign_music);
        mMixed = (Button)view.findViewById(R.id.mixed_music);
        mTextOfChoose = (TextView)view.findViewById(R.id.choose_melody_text);

        mArmenian.setOnClickListener(melodyItemClickListener);
        mRussian.setOnClickListener(melodyItemClickListener);
        mForeign.setOnClickListener(melodyItemClickListener);
        mMixed.setOnClickListener(melodyItemClickListener);

        if(getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_ONE) != null){
            mFirstTeam = getArguments().getBoolean(RadioActivity.BOOLEAN_FOR_QUEUE_OF_TEAMS);
            if(mFirstTeam){
                mTextForTeams = getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_ONE) + " " + getString(R.string.team_choose_melody);
                mTextOfChoose.setText(mTextForTeams);
            }else {
                mTextForTeams = getActivity().getIntent().getStringExtra(RadioOptionsActivity.TEAM_NUMBER_TWO) + " " + getString(R.string.team_choose_melody);
                mTextOfChoose.setText(mTextForTeams);
            }
        }else {
            mTextOfChoose.setText(getString(R.string.choose_melody));
        }
    }

    private class onMelodyItemClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Bundle args = new Bundle();
            args.putString(RadioActivity.MELODY,v.getTag().toString());
            args.putBoolean(RadioActivity.BOOLEAN_FOR_QUEUE_OF_TEAMS,mFirstTeam);
            mRadioGameFragment.setArguments(args);
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.radio_game_activity,mRadioGameFragment,RadioActivity.RADIO_GAME_FRAGMENT);
            mFragmentTransaction.commit();
        }
    }
}
