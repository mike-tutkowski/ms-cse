
*** Running the application ***

To run the REST service from a command prompt:
    * Navigate to the folder that contains this README file.
    * On Windows, execute the following command: mvnw.cmd spring-boot:run
      On Linux (including Mac OS X), execute the following command: ./mvnw spring-boot:run
    * Execute CTRL-C to terminate the REST service.

Alternatively, you can run the REST service from a command prompt as follows:
    * Navigate to the folder that contains this README file.
    * On Windows, execute the following command to build a JAR file: mvnw.cmd clean package
      On Linux (including Mac OS X), execute the following command: ./mvnw clean package
    * java -jar target/random-name-joke-service-1.0.0.jar
    * Execute CTRL-C to terminate the REST service.

*** Running the tests for the application ***

To run the unit tests for the REST service from a command prompt:
    * Navigate to the folder that contains this README file.
    * On Windows, execute the following command: mvnw.cmd clean test
      On Linux (including Mac OS X), execute the following command: ./mvnw clean test

*** Calling into the REST service from a client ***

You can call the REST service's endpoints from any tool capable of making the necessary
HTTP calls. Here is an example using curl:

Example call:
$ curl 'http://localhost:5000/locations'

I have provided a series of example invocations (with output) in the file "Sample API Invocations.txt",
which is in the same directory as this README file.

*** Controlling the logging behavior ***

A log file, called nytripdata.log, is created (if not already present) in the logs folder
when running the REST service. You can control the characteristics of logging by editing the
log4j.properties file located in src/main/resources (requires a restart of the REST service
for the changes to take effect).

*** Organization of the code ***

The entrypoints for the system are effectively controller.NyTripData's getLocations and getTaxiQuery
methods (technically, Spring starts the system up through the standard main method in the
controller.Application class, but this class primarily consists of a bit of boilerplate code).

*** Future ideas ***

* Make use of Aspect-oriented programming (AoP) to enable simplified logging for when each
  function is entered and before it is exited. This information would be written to the log
  when logging is set to DEBUG or higher.

* Increase the code coverage of the unit tests.

* Develop integration/system tests for this REST service. At the time, I have some degree of unit
  testing, but no integration/system testing. All of my integration/system testing was performed
  manually (example: making sure the system returned data in a timely fashion while the API was
  being invoked from multiple instances of curl at the same time).

* Define the REST APIs with OpenAPI and document them with Swagger.
