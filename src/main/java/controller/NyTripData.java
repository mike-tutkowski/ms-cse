package controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import service.Service;

@RestController
class NyTripData {
    private static final Logger LOGGER = Logger.getLogger(NyTripData.class);

    @Autowired private Service service;

    @RequestMapping(value = "/boroughs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getBoroughs(@RequestParam(value = "startsWith", required = false) final String startsWith) {
        try {
            final List<String> boroughs = service.getBoroughs(startsWith);

            final String joined = String.join(", ", boroughs);
            final String msg = String.format("Successful response to client: '%s'", joined);

            LOGGER.debug(msg);

            return new ResponseEntity<>(msg, HttpStatus.OK);
        }
        catch (Exception ex) {
            final String errMsg = ex.getMessage() + System.lineSeparator();
            final String formattedMsg = String.format("Error response to client: '%s'", ex.getMessage());

            LOGGER.error(formattedMsg, ex);

            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
