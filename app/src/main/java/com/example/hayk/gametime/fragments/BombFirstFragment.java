package com.example.hayk.gametime.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hayk.gametime.R;

public class BombFirstFragment extends android.app.Fragment {

    ImageView mHumanUp,mHumanDown;
    TextView mNumberOfPlayers;
    Button mStartGame;
    int mCurrentNumber;
    onStartClickListener mStartClickListener;
    String mNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bomb_first, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mHumanUp = (ImageView)view.findViewById(R.id.human_plus);
        mHumanDown = (ImageView)view.findViewById(R.id.human_minus);
        mNumberOfPlayers = (TextView)view.findViewById(R.id.number_players);
        mStartGame = (Button) view.findViewById(R.id.start_bomb_game);
        mCurrentNumber = Integer.parseInt(mNumberOfPlayers.getText().toString());

        mHumanUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNumber = Integer.parseInt(mNumberOfPlayers.getText().toString());
                if(mCurrentNumber < 12){
                    mCurrentNumber ++;
                    mNumber  = ((Integer) mCurrentNumber).toString();
                    mNumberOfPlayers.setText(mNumber);
                }
            }
        });

        mHumanDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNumber = Integer.parseInt(mNumberOfPlayers.getText().toString());
                if(mCurrentNumber > 2){
                    mCurrentNumber --;
                    mNumber = ((Integer) mCurrentNumber).toString();
                    mNumberOfPlayers.setText(mNumber);
                }
            }
        });

        mStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mStartClickListener != null){
                    mStartClickListener.startClicked(mCurrentNumber);
                }
            }
        });
    }

    public interface onStartClickListener{
        void startClicked(int number);
    }

    public void setOnStartListener(onStartClickListener startListener){
        mStartClickListener = startListener;
    }
}
