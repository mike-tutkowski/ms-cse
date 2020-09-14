package service;

import data.Location;
import data.TaxiQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import schema.LocationTable;
import schema.TransportType;
import schema.TripTable;

/**
 * For the purposes of this assignment, I decided to go with an embedded Derby DB. This makes configuration easy
 * (so I can spend more time on other aspects of the project), but it only allows for one active connection at a
 * time. As such, I have made the getLocations and getTaxiQuery methods synchronized so that only one of them
 * is accessing the DB at a time. In a real-world situation, we would want to spend time making sure the DB
 * can not only be accessed from multiple threads at the same time, but that our code can control access to
 * such resources properly.
 *
 * Since I elected for this project to keep the data-access layer relatively simple, I did not leverage
 * any of Java's Persistence APIs (JPA). Using tools like that, I could have generated classes that
 * represented my SQL queries. Doing such a thing in large projects tends to make maintenance of the
 * logic of SQL statements easier.
 *
 * The ServiceImpl class, which implements the Service interface, represents the business logic of the
 * system. In an effort to keep the design relatively simple for this project, I decided to merge
 * the business and data-access layers into one unit (so, there is both business as well as
 * data-access logic in this particular implementation of the Service interface). In larger projects,
 * I have often found it beneficial to separate the layers of a REST service into distinct API, business,
 * and data-access units.
 */
@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    private static final Logger LOGGER = Logger.getLogger(ServiceImpl.class);
    private static final String CONNECTION_URL = "jdbc:derby:nytripdb";

    private final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#.##");

    /**
     * This method retrieves locations from the LOCATION table. If startWith is populated,
     * I only include locations whose borough starts with the provided text.
     */
    public synchronized List<Location> getLocations(String startsWith) throws Exception {
        String sql = "SELECT * FROM " + LocationTable.LOCATION_TABLE_NAME;

        if (startsWith != null && startsWith.trim().length() > 0) {
            sql += " WHERE UPPER(" + LocationTable.LOCATION_TABLE_BOROUGH_COL + ") LIKE '" + startsWith.toUpperCase() + "%'";
        }

        sql += " ORDER BY " + LocationTable.LOCATION_TABLE_ID_COL;

        LOGGER.debug("SQL to execute = '" + sql + "'");

        java.util.List<Location> boroughs = new java.util.ArrayList<>();

        try (Connection conn = DriverManager.getConnection(CONNECTION_URL);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs= stmt.executeQuery()) {
            LOGGER.debug("Connected to database from 'getLocations' method");

            while (rs.next()) {
                int id = rs.getInt(LocationTable.LOCATION_TABLE_ID_COL);
                String borough = rs.getString(LocationTable.LOCATION_TABLE_BOROUGH_COL);
                String zone = rs.getString(LocationTable.LOCATION_TABLE_ZONE_COL);

                Location location = new Location(id, borough, zone);

                boroughs.add(location);
            }
        }

        return boroughs;
    }

    /**
     * This method calculates an average trip time (in seconds) and an average cost based on
     * provided location IDs (from and to) and - optionally - a transport type.
     */
    public synchronized Optional<TaxiQuery> getTaxiQuery(int fromLocationId, int toLocationId, TransportType type) throws Exception {
        String sql = "SELECT AVG({fn timestampdiff(SQL_TSI_SECOND, " + TripTable.TRIP_TABLE_PICK_UP_TIME_COL +
                ", " + TripTable.TRIP_TABLE_DROP_OFF_TIME_COL + ")}) as AverageSeconds, " +
                "AVG(" + TripTable.TRIP_TABLE_COST_COL + ") as AverageCost " +
                "FROM " + TripTable.TRIP_TABLE_NAME + " " +
                "WHERE " + TripTable.TRIP_TABLE_PICK_UP_LOC_ID_COL + " = " + fromLocationId + " and " +
                TripTable.TRIP_TABLE_DROP_OFF_LOC_ID_COL + " = " + toLocationId;

        if (type != null && !type.equals(TransportType.NONE)) {
            sql += " and " + TripTable.TRIP_TABLE_TRANSPORT_TYPE_COL + " = " + type.ordinal();
        }

        LOGGER.debug("SQL to execute = '" + sql + "'");

        try (Connection conn = DriverManager.getConnection(CONNECTION_URL);
             PreparedStatement s = conn.prepareStatement(sql);
             ResultSet rs= s.executeQuery()) {

            LOGGER.debug("Connected to database from 'getTaxiQuery' method");

            if (rs.next()) {
                int averageSeconds = rs.getInt(TaxiQuery.AVERAGE_SECONDS);
                float averageCost = rs.getFloat(TaxiQuery.AVERAGE_COST);

                averageCost = Float.parseFloat(DECIMAL_FORMATTER.format(averageCost));

                return Optional.of(new TaxiQuery(averageSeconds, averageCost));
            }
        }

        return Optional.empty();
    }
}
