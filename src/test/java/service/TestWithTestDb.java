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
    private static final int EXPECTED_TOTAL_NUMBER_OF_LOCATIONS = 265;

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
        List<Location> locations = service.getLocations(null);

        Assert.assertEquals(EXPECTED_TOTAL_NUMBER_OF_LOCATIONS, locations.size());
    }
}
