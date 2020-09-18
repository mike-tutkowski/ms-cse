package data;

/**
 * The Location class is a representation of what exists in the Location table.
 * Since I elected for this project to keep the data-access layer relatively simple,
 * I did not leverage any of Java's Persistence APIs (JPA). Using tools like that, I
 * could have generated classes like the one below. When I have used such tools, I
 * incorporated invoking them during the build process (or ad hoc via make files
 * or whatever equivalent tool was common for the environment I was programming in).
 */
public class Location {
    private final long id;
    private final String borough;
    private final String zone;

    // Note: We could use a library like Lombok (https://projectlombok.org) instead of manually adding getters here.

    public Location(long id, String borough, String zone) {
        this.id = id;
        this.borough = borough;
        this.zone = zone;
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
    private Location() {
        id = 0;
        borough = "";
        zone = "";
    }

    public long getId() {
        return id;
    }

    public String getBorough() {
        return borough;
    }

    public String getZone() {
        return zone;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            Location location = (Location)obj;

            return this.id == location.id &&
                    this.borough.equals(location.borough) &&
                    this.zone.equals(location.zone);
        }

        return false;
    }
}
