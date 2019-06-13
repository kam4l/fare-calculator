README 
======

Overview
--------

* Rest API to calculate fare for a automobile rental application.

* Fare is calculated using the rules as mentioned below:

  * Vehicle can be any one of the following:
    * Swift
    * Audi
    * Benz
    * Volvo
    * Toyota Coaster

  * The standard rate for a petrol vehicle for a standard trip is 15 Rs/Km. Additional 2 Rs/Km charge is applicable for AC vehicles. Diesel vehicles cost a rupee less than standard rate.

  * 2% discount is applicable for bus on standard rate.

  * Additional charges of 1 Rs/Km/Person are charged if number of passengers exceeds the max limit of a vehicle.


Dependencies
------------

* http://openjdk.java.net/ [Java 8] 
* https://gradle.org/install/ [Gradle] 

Running
-------

 To clean and run all tests, use `gradle clean test`
 
 To run, use `gradle run`
 
 Sample query
 `curl -X POST "http://localhost:8080/calculateFare" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"vehicleName\": \"volvo\", \"vehicleFuel\": \"petrol\", \"travelRoute\": [ \"Pune\", \"Mumbai\", \"Bangalore\", \"Pune\" ], \"passengerCount\": 5, \"airConditioned\": true}"`
 
Swagger
-------

 * <http://localhost:8080/swagger-ui.html>
