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

    /**
     * DO NOT USE THIS CONSTRUCTOR FROM PRODUCT CODE. IT IS ONLY INTENDED FOR
     * USE IN UNIT TESTING.
     *
     *  This constructor is here only so that unit tests can de-serialize a String
     *  that's in JSON format to an object of this type. The library that performs
     *  this work needs a no-parameter constructor, but this constructor can be
     *  private (as this one is).
     */
    private TaxiQuery() {
        averageSeconds = 0;
        averageCost = 0;
    }

    public int getAverageSeconds() {
        return averageSeconds;
    }

    public float getAverageCost() {
        return averageCost;
    }

    public boolean equals(Object obj) {
        if (obj instanceof TaxiQuery) {
            TaxiQuery taxiQuery = (TaxiQuery)obj;

            return this.averageSeconds == taxiQuery.averageSeconds && this.averageCost == taxiQuery.averageCost;
        }

        return false;
    }
}
