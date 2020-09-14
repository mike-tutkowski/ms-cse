package schema;

/**
 * The LocationTable interface contains constants related to the LOCATION table. I
 * did not make this into an enum type because I only need access to simple String
 * values here. Since I elected for this project to keep the data-access layer relatively
 * simple, I did not leverage any of Java's Persistence APIs (JPA). Using tools like that, I
 * could have generated interfaces (or enums) like the one below. When I have used such tools, I
 * incorporated invoking them during the build process (or ad hoc via make files
 * or whatever equivalent tool was common for the environment I was programming in).
 */
public interface LocationTable {
    public static final String LOCATION_TABLE_NAME = "LOCATION";
    public static final String LOCATION_TABLE_ID_COL = "ID";
    public static final String LOCATION_TABLE_BOROUGH_COL = "BOROUGH";
    public static final String LOCATION_TABLE_ZONE_COL = "ZONE";
}
