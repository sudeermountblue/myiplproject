package com.sudeer;

public class Match {

    private String id;

    private String season;

    private String team1;

    private String team2;


    private String tossWinner;

    private String winner;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getSeason() {
        return season;
    }


    public void setSeason(String season) {
        this.season = season;
    }

    public String getTossWinner() {
        return tossWinner;
    }

    public void setTossWinner(String tossWinner) {
        this.tossWinner = tossWinner;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }
}
