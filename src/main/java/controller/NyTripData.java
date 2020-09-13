package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import data.Location;

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
            List<Location> boroughs = service.getBoroughs(startsWith);

            ObjectMapper mapper = new ObjectMapper();

            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            String json = mapper.writeValueAsString(boroughs);
            String msg = String.format("Successful response to client: '%s'", json);

            LOGGER.debug(msg);

            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        catch (Exception ex) {
            String errMsg = ex.getMessage() + System.lineSeparator();
            String formattedMsg = String.format("Error response to client: '%s'", ex.getMessage());

            LOGGER.error(formattedMsg, ex);

            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
