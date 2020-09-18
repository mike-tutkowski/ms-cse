package service;

import data.Location;
import data.TaxiQuery;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import schema.TransportType;

/**
 * Tests that make use of a test database that consists of data that's in a known state.
 * I execute commands against the business layer and pass in parameters that result in known results.
 */
@RunWith(MockitoJUnitRunner.class)
class TestWithTestDb {
    private static final String TEST_CONNECTION_URL = "jdbc:derby:testnytripdb";

    private Service service;

    @BeforeEach
    void init() {
        service = new ServiceImpl();

        // I do not want to advertise the setConnectionUrl method from
        // the Service interface because this method is only intended
        // for use in testing. As such, I perform a cast to the known
        // implementation type (ServiceImpl) and invoke setConnectionUrl,
        // passing in the test connection URL.
        ((ServiceImpl)service).setConnectionUrl(TEST_CONNECTION_URL);
    }

    @Test
    void testLocations() throws Exception {
        int expectedTotalNumberOfLocations = 265;

        String startsWith = "";
        testLocationsStartsWith(startsWith, expectedTotalNumberOfLocations);

        // This should pass, as well, as the start-with string should be
        // trimmed (if it is not equal to null).
        startsWith = " ";
        testLocationsStartsWith(startsWith, expectedTotalNumberOfLocations);

        startsWith = null;
        testLocationsStartsWith(startsWith, expectedTotalNumberOfLocations);
    }

    @Test
    void testLocationsStartsWithB() throws Exception {
        int expectedNumberOfLocationsStartingWithB = 104;

        String startsWith = "B";
        testLocationsStartsWith(startsWith, expectedNumberOfLocationsStartingWithB);

        startsWith = "b";
        testLocationsStartsWith(startsWith, expectedNumberOfLocationsStartingWithB);
    }

    @Test
    void testLocationsStartsWithSt() throws Exception {
        int expectedNumberOfLocationsStartingWithSt = 20;

        String startsWith = "St";
        testLocationsStartsWith(startsWith, expectedNumberOfLocationsStartingWithSt);

        startsWith = "sT";
        testLocationsStartsWith(startsWith, expectedNumberOfLocationsStartingWithSt);

        startsWith = "st";
        testLocationsStartsWith(startsWith, expectedNumberOfLocationsStartingWithSt);

        startsWith = "ST";
        testLocationsStartsWith(startsWith, expectedNumberOfLocationsStartingWithSt);
    }

    private void testLocationsStartsWith(String startsWith, int expectedNumber) throws Exception {
        List<Location> locations = service.getLocations(startsWith);

        Assert.assertEquals(expectedNumber, locations.size());
    }

    @Test
    void testTaxiQueryTransportTypeNone() throws Exception {
        int fromLocationId = 41;
        int toLocationId = 24;
        int expectedAverageSeconds = 911;
        float expectedAverageCost = 4.6f;

        testTaxiQuery(fromLocationId, toLocationId, TransportType.NONE, expectedAverageSeconds, expectedAverageCost);
    }

    @Test
    void testTaxiQueryTransportTypeYellow() throws Exception {
        int fromLocationId = 161;
        int toLocationId = 146;
        int expectedAverageSeconds = 677;
        float expectedAverageCost = 14.97f;

        testTaxiQuery(fromLocationId, toLocationId, TransportType.YELLOW, expectedAverageSeconds, expectedAverageCost);
    }

    @Test
    void testTaxiQueryTransportTypeGreen() throws Exception {
        int fromLocationId = 129;
        int toLocationId = 82;
        int expectedAverageSeconds = 718;
        float expectedAverageCost = 11.3f;

        testTaxiQuery(fromLocationId, toLocationId, TransportType.GREEN, expectedAverageSeconds, expectedAverageCost);
    }

    @Test
    void testTaxiQueryTransportTypeForHire() throws Exception {
        int fromLocationId = 123;
        int toLocationId = 77;
        int expectedAverageSeconds = 1227;
        float expectedAverageCost = 0f;

        testTaxiQuery(fromLocationId, toLocationId, TransportType.FOR_HIRE, expectedAverageSeconds, expectedAverageCost);
    }

    private void testTaxiQuery(int fromLocationId, int toLocationId, TransportType transportType,
                               int expectedAverageSeconds, float expectedAverageCost) throws Exception {
        Optional<TaxiQuery> opt = service.getTaxiQuery(fromLocationId, toLocationId, transportType);

        Assert.assertTrue(opt.isPresent());

        TaxiQuery taxiQuery = opt.get();

        Assert.assertEquals(expectedAverageSeconds, taxiQuery.getAverageSeconds());
        Assert.assertEquals(0, Float.compare(expectedAverageCost, taxiQuery.getAverageCost()));
    }
}
