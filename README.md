
*** Running the application ***

To run the REST service from a command prompt:
    * Navigate to the folder that contains this README file.
    * Execute the following command: ./mvnw spring-boot:run
    * Execute CTRL-C to terminate the REST service.

Alternatively, you can run the REST service from a command prompt as follows:
    * Navigate to the folder that contains this README file.
    * Execute the following command to build a JAR file: ./mvnw clean package
    * java -jar target/random-name-joke-service-1.0.0.jar
    * Execute CTRL-C to terminate the REST service.

Note: To build and run this code, I have included mvnw (and a private folder
  called .mvn). This particular wrapper for Maven functions on Linux-based
  OSes (including Mac OS X). I have not included here the equivalent Maven
  wrapper for Windows.

*** Running the tests for the application ***

To run the unit tests for the REST service from a command prompt:
    * Navigate to the folder that contains this README file.
    * Execute the following command: ./mvnw clean test

*** Calling into the REST service from a client ***

Example call:
$ curl 'http://localhost:5000'

Example output:
Θεόδοτος Μακρής can instantiate an abstract class.

*** Controlling the logging behavior ***

A log file, called randomnamejoke.log, is created (if not already present) in the logs folder
when running the REST service. You can control the characteristics of logging by editing the
log4j.properties file located in src/main/resources (requires a restart of the REST service
for the changes to take effect).

*** Organization of the code ***

The entrypoint for the system is effectively the controller.RandomNameJoke.getRandomNameJoke
method (technically, Spring starts the system up through the standard main method in the
controller.Application class, but this class primarily consists of a bit of boilerplate code).

*** Future ideas ***

* Make use of Aspect-oriented programming (AoP) to enable simplified logging for when each
  function is entered and before it is exited. This information would be written to the log
  when logging is set to DEBUG or higher.

* Make use of MockServer (http://www.mock-server.com/) to intercept HTTP calls for unit testing.
  For example, this system makes calls to two REST services to retrieve data. With MockServer, we
  could tell the testing infrastructure to intercept those calls and instead return data that we
  specify in the tests. This would be a supplement for the existing Mockito tests, not a
  replacement.

* Increase the code coverage of the unit tests. I did not run any code-coverage tools against this
  software, but there are plenty of areas where code coverage could be increased.

* Develop integration/system tests for this REST service. At the time, I have some degree of unit (L1)
  testing, but no integration/system (L2/L3) testing. All of my integration/system testing was performed
  manually (example: making sure the system returned data in a timely fashion while the API was being
  invoked from multiple instances of curl at the same time).

