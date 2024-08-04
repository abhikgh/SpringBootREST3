package com.example.SpringBootREST3;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;


import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class GatlingSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:9100")
                    .acceptHeader("application/json");

    ChainBuilder read = exec(http("Read Spring Rest Controller").get("/v3/rest/home"));
    ScenarioBuilder users = scenario("Users Read").exec(read);

    {
        setUp(
                users.injectOpen(
                        atOnceUsers(1),
                        nothingFor(2),
                        atOnceUsers(50),
                        rampUsers(50).during(10),
                        constantUsersPerSec(100.0).during(20),
                        constantUsersPerSec(30.0).during(30)
                )
        ).protocols(httpProtocol);
    }
}
