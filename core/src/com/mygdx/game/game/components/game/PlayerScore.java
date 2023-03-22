package com.mygdx.game.game.components.game;

public class PlayerScore implements Comparable<PlayerScore>{
    private String startTime;
    private int distanceTravelled;
    private int aliensKilled;

    public PlayerScore(String startTime, int distanceTravel, int aliensKilled) {
        this.startTime = startTime;
        this.distanceTravelled = distanceTravel;
        this.aliensKilled = aliensKilled;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(int distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public int getAliensKilled() {
        return aliensKilled;
    }

    public void setAliensKilled(int aliensKilled) {
        this.aliensKilled = aliensKilled;
    }

    @Override
    public String toString() {
        return String.format("| %-30s | %20s km | %15s |%n", startTime, distanceTravelled, aliensKilled);
//        return String.format("%-30s", startTime) +
//                String.format("%-20s", distanceTravel + " km ") +
//                String.format("%-15s", aliensKilled) +
//                '\n';
    }

    @Override
    public int compareTo(PlayerScore score) {
        int compare_distance = score.getDistanceTravelled();
        /* For Ascending order*/
        //return this.distanceTravelled-compare_distance;

        /* For Descending order do like this */
        return compare_distance-this.distanceTravelled;
    }
}
