package service;

import data.Location;
import data.TaxiQuery;

import java.util.List;

import schema.TransportType;

public interface Service {
    List<Location> getLocations(String startsWith) throws Exception;
    TaxiQuery getTaxiQuery(int fromLocationId, int toLocationId, TransportType type) throws Exception;
}
