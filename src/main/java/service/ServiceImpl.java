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

/**
 * The ServiceImpl class, which implements the Service interface, represents
 * the business logic of the system.
 */
@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    private static final Logger LOGGER = Logger.getLogger(ServiceImpl.class);
    private static final String CONNECTION_URL =
            "jdbc:derby:/Users/tutkowski/Downloads/db-derby-10.14.2.0-bin/lib/nytripdb";

    public List<Location> getLocations(String startsWith) throws Exception {
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

    public TaxiQuery getTaxiQuery(int fromLocationId, int toLocationId, TransportType type) throws Exception {
        /** @// TODO: 9/13/20 Replace with access to the DB */
        return new TaxiQuery(500, 10.50f);
    }
}
