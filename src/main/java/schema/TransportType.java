package schema;

/**
 * The TransportType enum contains constants related to the TRANSPORT_TYPE column of the
 * TRIP table. Since I elected for this project to keep the data-access layer relatively
 * simple, I did not leverage any of Java's Persistence APIs (JPA). Using tools like that, I
 * could have generated enums like the one below. When I have used such tools, I
 * incorporated invoking them during the build process (or ad hoc via make files
 * or whatever equivalent tool was common for the environment I was programming in).
 */
public enum TransportType {
    // Note: The order of these enum constants is important. NONE = 0, YELLOW = 1, GREEN = 2, and FOR_HIRE = 3.
    NONE, YELLOW, GREEN, FOR_HIRE
}
