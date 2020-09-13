package service;

import data.Location;

import java.util.List;

public interface Service {
    List<Location> getBoroughs(String startsWith) throws Exception;
}
