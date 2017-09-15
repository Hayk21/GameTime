package com.example.hayk.gametime.adapters;

/**
 * Created by Hayk on 19.08.2017.
 */

public class ScoreItem {

    private String FirstTeamScore,SecondTeamScore;

    public ScoreItem(){}

    public ScoreItem(String FirstTeamScore,String SecondTeamScore){
        this.FirstTeamScore = FirstTeamScore;
        this.SecondTeamScore = SecondTeamScore;
    }

    public String getFirstTeamScore() {
        return FirstTeamScore;
    }

    public void setFirstTeamScore(String firstTeamScore) {
        FirstTeamScore = firstTeamScore;
    }

    public String getSecondTeamScore() {
        return SecondTeamScore;
    }

    public void setSecondTeamScore(String secondTeamScore) {
        SecondTeamScore = secondTeamScore;
    }
}
