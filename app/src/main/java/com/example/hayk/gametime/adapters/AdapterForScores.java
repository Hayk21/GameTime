package com.example.hayk.gametime.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hayk.gametime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hayk on 18.08.2017.
 */

public class AdapterForScores extends RecyclerView.Adapter<AdapterForScores.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView teamOneScore, teamTwoScore;

        ViewHolder(View itemView) {
            super(itemView);
            teamOneScore = (TextView) itemView.findViewById(R.id.team_one_scores);
            teamTwoScore = (TextView) itemView.findViewById(R.id.team_two_scores);
        }

    }

    private List<ScoreItem> list;
    private int teamOneFinalyScore = 0,teamTwoFinalyScore = 0;
    private Context context;

    public AdapterForScores(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.scores_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.teamOneScore.setText(list.get(position).getFirstTeamScore());
        holder.teamTwoScore.setText(list.get(position).getSecondTeamScore());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addScores(ScoreItem scoreItem) {
        if (scoreItem.getSecondTeamScore().equals("-")) {
            list.add(scoreItem);
            teamOneFinalyScore += Integer.parseInt(scoreItem.getFirstTeamScore());
        } else {
            list.remove(list.size() - 1);
            list.add(scoreItem);
            teamTwoFinalyScore += Integer.parseInt(scoreItem.getSecondTeamScore());
        }
        this.notifyDataSetChanged();
    }

    public ScoreItem getTeamsFinalyScores(){
        String teamOne = ((Integer)teamOneFinalyScore).toString();
        String teamTwo = ((Integer)teamTwoFinalyScore).toString();
        return new ScoreItem(teamOne,teamTwo);
    }

    public void clearList(){
        list.clear();
        this.notifyDataSetChanged();
        teamOneFinalyScore = 0;
        teamTwoFinalyScore = 0;
    }
}
