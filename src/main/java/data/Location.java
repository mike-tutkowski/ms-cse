package data;

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

    public long getId() {
        return id;
    }

    public String getBorough() {
        return borough;
    }

    public String getZone() {
        return zone;
    }
}
