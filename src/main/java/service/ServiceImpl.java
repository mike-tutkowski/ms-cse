package service;

import data.Location;
import data.TaxiQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import schema.LocationTable;
import schema.TransportType;

/** For the purposes of this assignment, I decided to go with an embedded Derby DB. This makes configuration easy
 *  (so I can spend more time on other aspects of the project), but it only allows for one active connection at a
 *  time. As such, I have made the getLocations and getTaxiQuery methods synchronized so that only one of them
 *  is accessing the DB at a time. In a real-world situation, we would want to spend time making sure the DB
 *  can not only be accessed from multiple threads at the same time, but that our code can control access to
 *  such resources properly.
 */

/**
 * The ServiceImpl class, which implements the Service interface, represents
 * the business logic of the system.
 */
@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    private static final Logger LOGGER = Logger.getLogger(ServiceImpl.class);
    private static final String CONNECTION_URL =
            "jdbc:derby:/Users/tutkowski/Downloads/db-derby-10.14.2.0-bin/lib/nytripdb";

    public synchronized List<Location> getLocations(String startsWith) throws Exception {
        String columnNameId = LocationTable.ID.toString();
        String columnNameBorough = LocationTable.BOROUGH.toString();
        String columnNameZone = LocationTable.ZONE.toString();

        Connection conn = DriverManager.getConnection(CONNECTION_URL);

        LOGGER.debug("Connected to database ");

        String sql = "SELECT * FROM " + LocationTable.LOCATION_TABLE_NAME;

        if (startsWith != null && startsWith.trim().length() > 0) {
            sql += " WHERE " + columnNameBorough + " LIKE '" + startsWith + "%'";
        }

        sql += " ORDER BY " + columnNameId;

        LOGGER.debug("SQL to execute = '" + sql + "'");

        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs= s.executeQuery();

        java.util.List<Location> boroughs = new java.util.ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(columnNameId);
            String borough = rs.getString(columnNameBorough);
            String zone = rs.getString(columnNameZone);

            Location location = new Location(id, borough, zone);

            boroughs.add(location);
        }

        return boroughs;
    }

    public synchronized TaxiQuery getTaxiQuery(int fromLocationId, int toLocationId, TransportType type) throws Exception {
        /** @// TODO: 9/13/20 Replace with access to the DB */
        return new TaxiQuery(500, 10.50f);
    }
}
