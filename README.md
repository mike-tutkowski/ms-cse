
# ms-cse (Taxi Trips)

## Requirements
* [JDK 1.14](https://www.oracle.com/java/technologies/javase-jdk14-downloads.html)
* Set the JAVA_HOME environment variable to the root folder of your JDK (ex. on Windows: C:\Program Files\Java\jdk-14.0.2)

## Running the application

To run the REST service from a command prompt:
* Navigate to the folder that contains this README file.
* On Windows, execute the following command: mvnw.cmd spring-boot:run
  On Linux (including Mac OS X), execute the following command: ./mvnw spring-boot:run
* Note: The first time you run this command, it may take longer than normal to execute
  as supporting software packages may need to be downloaded.
* Execute CTRL-C to terminate the REST service.

## Running the tests for the application

To run the unit tests for the REST service from a command prompt:
* Navigate to the folder that contains this README file.
* On Windows, execute the following command: mvnw.cmd clean test
  On Linux (including Mac OS X), execute the following command: ./mvnw clean test
* Note: The first time you run this command, it may take longer than normal to execute
  as supporting software packages may need to be downloaded.

## Calling into the REST service from a client

You can call the REST service's endpoints from any tool capable of making the necessary
HTTP calls. Here is an example using curl:

Example call:
$ curl 'http://localhost:5000/locations'

An easy way to execute the API commands is to launch the web browser of your choice
and paste the applicable command in the search text field. For example, you can paste
http://localhost:5000/locations in the search text field and hit the ENTER button.

The REST service supports the following two commands:

* http://localhost:5000/locations

  This API supports an optional query parameter: startsWith.

  Here is an example of making use of this API command with its query parameter:
  http://localhost:5000/locations?startsWith=B

  Note that the path, locations, is followed by a '?' and then the key=value pair.

  The purpose of this API is to retrieve information about all of the locations in
  the database. If you use the startWith query parameter, then such locations are
  filtered using the borough name.

  The data is returned as an array of JSON objects. Here is an example of what a single location looks like:

  [ {
      "id" : 2,
      "borough" : "Queens",
      "zone" : "Jamaica Bay"
    }
  ]

* http://localhost:5000/taxiquery

  This API supports three query parameters:
  * fromLocationId (required)
  * toLocationId (required)
  * transportType (optional) - valid values are YELLOW, GREEN, and FOR_HIRE

  Here is an example showing only the use of the two required query parameters:
  http://localhost:5000/taxiquery?fromLocationId=100&toLocationId=200

  Note that the path, taxiquery, is followed by a '?' and then key=value pairs
  that are each separated by a '&'.

  Here is an example showing the use of all three query parameters:
  http://localhost:5000/taxiquery?fromLocationId=100&toLocationId=200&transportType=YELLOW

  The purpose of this API is to provide the "Average Seconds" and the "Average Cost"
  related to a set of taxi trips that meet the following criteria:
  * The location ID where the taxi trips begin. --> fromLocationId (required)
  * The location ID where the taxi trips end. --> toLocationId (required)
  * One of three valid values (noted above) to filter based on taxi type. --> transportType (optional)

  Note: All query parameter names (ex. startsWith) are case sensitive. Query parameter names that
  are not recognized are ignored (more about this in the "Future ideas" section below).

To help the user acquire a solid understanding of the supported REST calls, I have provided
a series of example invocations (with output) in the file "Sample API Invocations.txt", which
is in the same directory as this README file.

## Controlling the logging behavior

A log file, called nytripdata.log, is created (if not already present) in the logs folder
when running the REST service. You can control the characteristics of logging by editing the
log4j.properties file located in src/main/resources (requires a restart of the REST service
for the changes to take effect).

## Organization of the code

The entrypoints for the system are effectively controller.NyTripData's getLocations and getTaxiQuery
methods (technically, Spring starts the system up through the standard main method in the
controller.Application class, but this class primarily consists of a bit of boilerplate code).

## Future ideas

* Make use of Aspect-oriented programming (AoP) to enable simplified logging for when each
  function is entered and before it is exited. This information would be written to the log
  when logging is set to DEBUG or higher.

* Increase the code coverage of the unit tests.

* Develop integration/system tests. Currently, I have some degree of unit testing, but no
  integration/system testing. All of my integration/system testing was performed manually
  (executing the REST API in question against a set of known data and verifying the output
  was as expected).

* Possibly include indexes in the database. It would be a good idea in a real-world system to run
  scalability testing to determine if such indexing is necessary when the data reaches a certain quantity.

* Define the REST APIs using the OpenAPI specification and document them with Swagger.

* Consider making query parameter names case insensitive and/or returning an error if the user passes in
  a query parameter name that is not recognized.

