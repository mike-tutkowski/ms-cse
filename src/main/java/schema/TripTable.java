package schema;

/**
 * The TripTable interface contains constants related to the TRIP table. I
 * did not make this into an enum type because I only need access to simple String
 * values here. Since I elected for this project to keep the data-access layer relatively
 * simple, I did not leverage any of Java's Persistence APIs (JPA). Using tools like that, I
 * could have generated interfaces (or enums) like the one below. When I have used such tools, I
 * incorporated invoking them during the build process (or ad hoc via make files
 * or whatever equivalent tool was common for the environment I was programming in).
 */
public interface TripTable {
    public static final String TRIP_TABLE_NAME = "TRIP";
    public static final String TRIP_TABLE_PICK_UP_TIME_COL = "PICK_UP_TIME";
    public static final String TRIP_TABLE_DROP_OFF_TIME_COL = "DROP_OFF_TIME";
    public static final String TRIP_TABLE_PICK_UP_LOC_ID_COL = "PICK_UP_LOC_ID";
    public static final String TRIP_TABLE_DROP_OFF_LOC_ID_COL = "DROP_OFF_LOC_ID";
    public static final String TRIP_TABLE_COST_COL = "COST";
    public static final String TRIP_TABLE_TRANSPORT_TYPE_COL = "TRANSPORT_TYPE";
}
