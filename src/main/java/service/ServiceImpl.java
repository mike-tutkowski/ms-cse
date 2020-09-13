package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import schema.LocationTable;

/**
 * The ServiceImpl class, which implements the Service interface, represents
 * the business logic of the system.
 */
@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    private static final Logger LOGGER = Logger.getLogger(ServiceImpl.class);
    private static final String CONNECTION_URL =
            "jdbc:derby:/Users/tutkowski/Downloads/db-derby-10.14.2.0-bin/lib/nytripdb";

    public List<String> getBoroughs(final String startsWith) throws Exception {
        String columnNameBorough = LocationTable.BOROUGH.toString();
        Connection conn = DriverManager.getConnection(CONNECTION_URL);

        LOGGER.debug("Connected to database ");

        String sql = "SELECT DISTINCT " + columnNameBorough + " FROM " + LocationTable.LOCATION_TABLE_NAME;

        if (startsWith != null && startsWith.trim().length() > 0) {
            sql += " WHERE " + columnNameBorough + " LIKE '" + startsWith + "%'";
        }

        sql += " ORDER BY " + columnNameBorough;

        LOGGER.debug("SQL to execute = '" + sql + "'");

        PreparedStatement s = conn.prepareStatement(sql);
        ResultSet rs= s.executeQuery();

        java.util.List<String> boroughs = new java.util.ArrayList<>();

        while (rs.next()) {
            String borough = rs.getString(columnNameBorough);

            boroughs.add(borough);
        }

        return boroughs;
    }
}
