package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class ScalaSimulation extends Simulation {

  // Define the HTTP protocol configuration
  val httpProtocol = http
    .baseUrl("http://localhost:8081") // Update with your application base URL
    .acceptHeader("application/json")

  // Define the scenario
  val scn = scenario("App2 Simulation")
    .exec(
      http("Get All Employees using Feign")
        .get("/employee-client/feign") // Replace with the actual endpoint
        .check(status.is(200))
    )
    .pause(1) // Add a pause between requests for realism
    .exec(
      http("Get Employee by ID using Feign")
        .get("/employee-client/feign/1") // Replace with the actual endpoint
        .check(status.is(200))
    )
    .pause(1)
    .exec(
      http("Create Employee using Feign")
        .post("/employee-client/feign")
        .body(StringBody("""{"emp_Name":"John Doe", "emp_EmailId":"john.doe@example.com", "emp_Age":30}""")).asJson
        .check(status.is(200))
    )
    .pause(1)
    .exec(
      http("Get All Employees using RestTemplate")
        .get("/employee-client/rest-template") // Replace with the actual endpoint
        .check(status.is(200))
    )
    .pause(1)
    .exec(
      http("Get Employee by ID using RestTemplate")
        .get("/employee-client/rest-template/1") // Replace with the actual endpoint
        .check(status.is(200))
    )
    .pause(1)
    .exec(
      http("Create Employee using RestTemplate")
        .post("/employee-client/rest-template")
        .body(StringBody("""{"emp_Name":"Jane Doe", "emp_EmailId":"jane.doe@example.com", "emp_Age":25}""")).asJson
        .check(status.is(200))
    )

  // Set up the simulation
  setUp(
    scn.inject(
      atOnceUsers(2) // Number of users to simulate at once
    )
  ).protocols(httpProtocol)
}
