package com.example.hayk.gametime.custom_views;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hayk.gametime.R;
import com.example.hayk.gametime.adapters.AdapterForScores;
import com.example.hayk.gametime.adapters.ScoreItem;

/**
 * Created by Hayk on 18.08.2017.
 */

public class ScorePaper extends FrameLayout {

    private ScorePlace mScorePlace;
    private LinearLayout mTeamsLinear,mScoresLinear;
    private TextView mTeamOne,mTeamTwo,mTeamOneFinalyScore,mTeamTwoFinalyScore;
    RecyclerView mListOfScores;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterForScores mAdapterForScores;
    int mTeamOneFinaly,mTeamTwoFinaly;

    public ScorePaper(@NonNull Context context) {
        this(context,null);
    }

    public ScorePaper(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScorePaper(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.score_place,this,true);
        mScorePlace = (ScorePlace)findViewById(R.id.finaly_score);
        mTeamsLinear = (LinearLayout)findViewById(R.id.teams_linear);
        mScoresLinear = (LinearLayout)findViewById(R.id.scores_linear);
        mTeamOne = (TextView)findViewById(R.id.team_one_name);
        mTeamTwo = (TextView)findViewById(R.id.team_two_name);
        mTeamOneFinalyScore = (TextView)findViewById(R.id.team_one_finaly_score);
        mTeamTwoFinalyScore = (TextView)findViewById(R.id.team_two_finaly_score);
        mListOfScores = (RecyclerView) findViewById(R.id.teams_scores_side);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapterForScores = new AdapterForScores(context);
        mListOfScores.setLayoutManager(mLayoutManager);
        mListOfScores.setAdapter(mAdapterForScores);
    }

    public void startDrawing(){
        int top = mTeamsLinear.getBottom();
        int bottom = mScoresLinear.getTop();
        int start = mTeamsLinear.getTop();
        int end = mScoresLinear.getBottom();
        mScorePlace.setParameters(top,bottom,start,end);
    }

    public void setTeamOneName(String name){
        mTeamOne.setText(name);
    }

    public void setTeamTwoName(String name){
        mTeamTwo.setText(name);
    }

    public void addAdapterItem(ScoreItem scoreItem){
        mAdapterForScores.addScores(scoreItem);
        setFinalyScores(mAdapterForScores.getTeamsFinalyScores());
    }

    public boolean getCount(){
        return mAdapterForScores.getItemCount() % 2 == 0;
    }

    public void setFinalyScores(ScoreItem scoreItem){
        mTeamOneFinalyScore.setText(scoreItem.getFirstTeamScore());
        mTeamTwoFinalyScore.setText(scoreItem.getSecondTeamScore());
        mTeamOneFinaly = Integer.parseInt(mTeamOneFinalyScore.getText().toString());
        mTeamTwoFinaly = Integer.parseInt(mTeamTwoFinalyScore.getText().toString());
    }

    public int[] getFinalScores(){
        return new int[]{mTeamOneFinaly,mTeamTwoFinaly};
    }

    public void restartGame(){
        mAdapterForScores.clearList();
        mTeamOneFinaly = 0;
        mTeamTwoFinaly = 0;
        mTeamOneFinalyScore.setText("");
        mTeamTwoFinalyScore.setText("");
    }
}
