package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import data.Location;
import data.TaxiQuery;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import schema.TransportType;

import service.Service;

@RestController
class NyTripData {
    private static final Logger LOGGER = Logger.getLogger(NyTripData.class);

    @Autowired private Service service;

    @RequestMapping(value = "/locations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getLocations(@RequestParam(value = "startsWith", required = false) String startsWith) {
        try {
            List<Location> locations = service.getLocations(startsWith);

            String json = getAsJson(locations);
            String msg = getSuccessfulLogMsg(json);

            LOGGER.debug(msg);

            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        catch (Exception ex) {
            String errorLogMsg = getErrorLogMsg(ex);

            LOGGER.error(errorLogMsg, ex);

            return new ResponseEntity<>(getErrorJson(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/taxiquery", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getTaxiQuery(@RequestParam(value = "fromLocationId") int fromLocationId,
                                        @RequestParam(value = "toLocationId") int toLocationId,
                                        @RequestParam(value = "transportType", required = false) String transportType) {
        try {
            Optional<TaxiQuery> query = service.getTaxiQuery(fromLocationId, toLocationId, getTransportType(transportType));

            final String json;
            final String msg;

            if (query.isPresent()) {
                json = getAsJson(query.get());
                msg = getSuccessfulLogMsg(json);
            }
            else {
                json = "{}";
                msg = "Successful response to client, but no applicable data exists";
            }

            LOGGER.debug(msg);

            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        catch (Exception ex) {
            String errorLogMsg = getErrorLogMsg(ex);

            LOGGER.error(errorLogMsg, ex);

            return new ResponseEntity<>(getErrorJson(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static String getAsJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return mapper.writeValueAsString(obj);
    }

    private static TransportType getTransportType(String transportType) {
        if (transportType == null || transportType.trim().length() == 0) {
            return TransportType.NONE;
        }

        try {
            return TransportType.valueOf(transportType);
        }
        catch (IllegalArgumentException ex) {
            String msg = "Invalid format for Transport Type: " + transportType;

            LOGGER.error(msg, ex);

            throw new RuntimeException(msg);
        }
    }

    private static String getErrorJson(String errorMsg) {
        return "{\"error\": \"" + errorMsg + "\"}";
    }

    private static String getSuccessfulLogMsg(String json) {
        return String.format("Successful response to client:" + System.lineSeparator() + "'%s'", json);
    }

    private static String getErrorLogMsg(Exception ex) {
        return String.format("Error response to client: '%s'", ex.getMessage());
    }
}
