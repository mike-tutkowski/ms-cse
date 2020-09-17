package service;

import data.Location;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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
}
