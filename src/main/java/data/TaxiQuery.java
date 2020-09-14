package data;

/**
 * The TaxiQuery class is a representation of what gets returned from a particular DB query.
 * Since I elected for this project to keep the data-access layer relatively simple,
 * I did not leverage any of Java's Persistence APIs (JPA). Using tools like that, I
 * could have generated classes like the one below. When I have used such tools, I
 * incorporated invoking them during the build process (or ad hoc via make files
 * or whatever equivalent tool was common for the environment I was programming in).
 */
public class TaxiQuery {
    public static final String AVERAGE_SECONDS = "AverageSeconds";
    public static final String AVERAGE_COST = "AverageCost";

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
