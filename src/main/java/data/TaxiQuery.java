package data;

public class TaxiQuery {
    private final int averageSeconds;
    private final float averageCost;

    // Note: We could use a library like Lombok (https://projectlombok.org) instead of manually adding getters here.

    public TaxiQuery(int averageSeconds, float averageCost) {
        this.averageSeconds = averageSeconds;
        this.averageCost = averageCost;
    }

    public int getAverageSeconds() {
        return averageSeconds;
    }

    public float getAverageCost() {
        return averageCost;
    }
}
