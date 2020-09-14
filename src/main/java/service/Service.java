package service;

import data.Location;
import data.TaxiQuery;

import java.util.List;
import java.util.Optional;

import schema.TransportType;

/**
 * This is the interface that represents the business of the system.
 */
public interface Service {
    List<Location> getLocations(String startsWith) throws Exception;
    Optional<TaxiQuery> getTaxiQuery(int fromLocationId, int toLocationId, TransportType type) throws Exception;
}
