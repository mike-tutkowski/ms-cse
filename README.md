
# ms-cse (Taxi Trips)

## Requirements
* [JDK 1.14](https://www.oracle.com/java/technologies/javase-jdk14-downloads.html)
* Set the JAVA_HOME environment variable to the root folder of your JDK (ex. on Windows: JAVA_HOME=C:\Program Files\Java\jdk-14.0.2)

## Running the application

To run the REST service from a command prompt:
* Navigate to the folder that contains this README file.
* On Windows, execute the following command: mvnw.cmd spring-boot:run<br/>
  On Linux (including Mac OS X), execute the following command: ./mvnw spring-boot:run
* Note: The first time you run this command, it may take longer than normal to execute
  as supporting software packages may need to be downloaded.<br/>
  Once you see the word "Spring" output in ASCII art (followed by a handful of INFO messages),
  the REST service is up and running.
* Execute CTRL-C to terminate the REST service.

## Calling into the REST service from a client

You can call the REST service's endpoints from any tool capable of making the necessary
HTTP calls. Here is an example using curl:

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
  the database. If you use the startsWith query parameter, then such locations are
  filtered using the borough name.

  The data is returned as an array of JSON objects. Here is an example of what a single location looks like:

  [ {
      "id" : 2,
      "borough" : "Queens",
      "zone" : "Jamaica Bay"
    }
  ]

  You can then make use of the returned "id" value in the taxiquery API command. This "id" field serves as
  a valid value for the fromLocationId and toLocationId query parameters of the taxiquery API call.

* http://localhost:5000/taxiquery

  This API supports three query parameters:
  * fromLocationId (required) - valid values comes from the "id" of a location returned by the locations API call (described above)
  * toLocationId (required) - valid values comes from the "id" of a location returned by the locations API call (described above)
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
  * One of three values (noted above) to filter based on taxi type. --> transportType (optional)

  Note: All query parameter names (ex. startsWith) are case sensitive. Query parameter names that
  are not recognized get ignored (more about this in the "Future ideas" section below).

To help the user acquire a solid understanding of the supported REST calls, I have provided
a series of example invocations (with output) in the file [Sample API Invocations.txt](https://github.com/mike-tutkowski/ms-cse/blob/master/Sample%20API%20Invocations.txt).

## Running the unit tests for the application

To run the unit tests for the REST service from a command prompt:
* Navigate to the folder that contains this README file.
* On Windows, execute the following command: mvnw.cmd clean test<br/>
  On Linux (including Mac OS X), execute the following command: ./mvnw clean test
* Note: The first time you run this command, it may take longer than normal to execute
  as supporting software packages may need to be downloaded.

The unit tests consist of two styles of testing:
  * Tests that make use of mocking. At the time being, these are focused on testing the API
  layer's interactions with the business layer.
  * Tests that make use of a test database that consists of data that's in a known state. I
  execute commands against the business layer and pass in parameters that result in known results.

## Controlling the logging behavior

A log file, called nytripdata.log, is created (if not already present) in the logs folder
when running the REST service. You can control the characteristics of logging by editing the
[log4j.properties file](https://github.com/mike-tutkowski/ms-cse/blob/master/src/main/resources/log4j.properties)
(requires a restart of the REST service for the changes to take effect).

## Raw Data
The raw data from the yellow and green taxis was in the same format. The raw data for the for-hire vehicles varied
in one important way: it did not contain a trip cost. I only required a subset of the raw data (detailed below):

- Location/Zone:
  - `LocationID`: ID of a zone within a borough
  - `Borough`: the name of the borough
  - `Zone`: the name of the zone
- Yellow and Green Trips
  - `tpep_pickup_datetime`: date and time of passenger pickup
  - `tpep_dropoff_datetime`: date and time of passenger dropoff
  - `PULocationID`: ID of pickup location (references the LocationID in the Location/Zone above)
  - `DOLocationID`: ID of dropoff location (references the LocationID in the Location/Zone above)
  - `Total_amount`: amount the passenger was charged for the trip
- FHV Trips
  - `Pickup_datetime`: date and time of passenger pickup
  - `DropOff_datetime`: date and time of passenger pickup
  - `PULocationID`: ID of pickup location (references the LocationID in the Location/Zone above)
  - `DOLocationID`: ID of pickup location (references the LocationID in the Location/Zone above)

Notes:

I noticed some of the raw data may have certain inaccuracies. For example, I saw that at least one trip
had a negative value for its cost. In a real-world system, we would want to perform some kind of data
scrubbing to make sure that the data going into the database met certain standards.

In an similar vein, I noticed some trips did not have location IDs associated with them. When I designed
the TRIP database table, I made sure to default the PICK_UP_LOC_ID and DROP_OFF_LOC_ID columns to the value
265, which is associated with the "Unknown" location/zone in the LOCATION table.

Also, in order to keep the download time (from GitHub) quick for this project, I decided to include an
abridged set of data for each of the three vehicle types (yellow taxis, green taxis, and for-hire vehicles).
I included 300,000 records of each type in the database. This should provide a nice sample size of data.

If you would like to use an input set that will definitely return (non-zero) data&ast;, I have confirmed that the
follow ten pairs of from-location IDs and to-location-IDs will do so for the taxiquery (regardless of transport type):

| From Location ID | To Location ID |
| :---: | :---: |
| 7 | 145 |
| 41 | 74 |
| 42 | 75 |
| 43 | 142 |
| 49 | 97 |
| 74 | 244 |
| 75 | 48 |
| 146 | 7 |
| 181 | 61 |
| 255 | 112 |

&ast; Note: Reminder that "Average Cost" when the transportType query parameter is set to FOR_HIRE will always be 0.0
because total cost does not exist in the raw data set of for-hire vehicles.

## Database Schema
The database is composed of [two tables](https://github.com/mike-tutkowski/ms-cse/blob/master/sql/create_taxi_derby_db.sql).

Note: Due to the fact that the raw data contained columns referred to in one way or another as location IDs, I decided to name
the table that contains these locations as LOCATION rather than ZONE. In a real-world scenario, I would work with the customer to
figure out the most useful terminology to use (for example, perhaps 'zone' is more commonly used than 'location' in this domain).

I also decided to keep the borough name inside of the LOCATION table rather than splitting it out into its own table (and then
referencing the applicable borough via a foreign key in the LOCATION table). Here are some of my thoughts as to why:

* In a real-world system, it is likely these database tables would be populated by some application. This application could be
written in such a way as to make sure that the borough names that get injected into the LOCATION table always meet certain
standards (for example, 'Manhattan' would never be populated as 'manhattan').
* The LOCATION table is likely to be quite static (unlike the TRIP table). As such, once we see that each row is populated
in a way that we like, it is not likely that this state is going to change much, if at all (new taxi zones are probably not
created often).
* If we moved the borough names to their own table, that table would only consist of two columns: an ID and a borough name. This
would be OK to do, but I believe it's not necessary here.
* It's not a big deal here since the LOCATION table doesn't contain much data, but you will save a slight amount of storage as well
as benefit slightly performance wise by storing the data as I have decided to for this project. You must weight those benefits
against the costs of not fully normalizing the database. Per my reasons above, I decided to put this data all in the LOCATION table.

## Organization of the code

The entrypoints for the system are effectively controller.NyTripData's getLocations and getTaxiQuery
methods (technically, Spring starts the system up through the standard main method in the
controller.Application class, but this class primarily consists of a bit of boilerplate code).

## Future ideas

* Make use of Aspect-oriented programming (AoP) to enable simplified logging for when each
  function is entered and before it is exited. This information would be written to the log
  when logging is set to DEBUG or higher.

* Increase the code coverage of the unit tests.

* Develop integration/system tests. Currently, I have some degree of unit-test coverage, but
  no integration/system testing. All of my integration/system testing was performed manually
  (executing the REST API in question against a set of known data and verifying the output
  was as expected).

* Possibly include indexes in the database. It would be a good idea in a real-world system to run
  scalability testing to determine if such indexing is necessary when the data reaches a certain quantity.

* Define the REST APIs using the OpenAPI specification and document them with Swagger.

* Consider making query parameter names case insensitive and/or returning an error if the user passes in
  a query parameter name that is not recognized.

* Since the "for hire" trips do not include the total cost, this field ends up as 0.0 in the database.
  Treating these trips as $0.00 throws off the average cost when you run a taxiquery API call that
  includes more than just "for hire" trips. In the future, consider excluding those trips when figuring
  out the average cost for the taxiquery API call.

