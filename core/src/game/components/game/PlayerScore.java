package game.components.game;

public class PlayerScore {
    private String startTime;
    private int distanceTravel;
    private int aliensKilled;

    public PlayerScore(String startTime, int distanceTravel, int aliensKilled) {
        this.startTime = startTime;
        this.distanceTravel = distanceTravel;
        this.aliensKilled = aliensKilled;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getDistanceTravel() {
        return distanceTravel;
    }

    public void setDistanceTravel(int distanceTravel) {
        this.distanceTravel = distanceTravel;
    }

    public int getAliensKilled() {
        return aliensKilled;
    }

    public void setAliensKilled(int aliensKilled) {
        this.aliensKilled = aliensKilled;
    }

    @Override
    public String toString() {
        return String.format("| %-30s | %20s km | %15s |%n", startTime, distanceTravel, aliensKilled);
//        return String.format("%-30s", startTime) +
//                String.format("%-20s", distanceTravel + " km ") +
//                String.format("%-15s", aliensKilled) +
//                '\n';
    }
}
