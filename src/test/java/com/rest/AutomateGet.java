package com.rest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.config.LogConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class AutomateGet {

    @Test
    public void validate_get_status_code() {
        given().
                baseUri("https://api.postman.com").
                header("X-API-Key", "PMAK-680e2a7c74781300017751ae-9cea1b89e639b94188a6534e32a2c898ae").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name", hasItems("My Workspace", "My Workspace1"),
                        "workspaces.type", hasItems("personal", "personal"),
                        "workspaces[0].name", equalTo("My Workspace"),
                        "workspaces[0].name", is(equalTo("My Workspace")),
                        "workspaces.size()", equalTo(2),
                        "workspaces.name", hasItem("My Workspace"));

    }

    @Test
    public void extract_response() {

        Response res = given().
                baseUri("https://api.postman.com").
                header("X-API-Key", "PMAK-680e2a7c74781300017751ae-9cea1b89e639b94188a6534e32a2c898ae").
                when().
                get("/workspaces").
                then().
                //log().all().
                        assertThat().
                statusCode(200).
                extract().
                response();
        System.out.println(" response : " + res.asString());

    }

    @Test
    public void extract_single_value_from_response() {
        String name = given().
                baseUri("https://api.postman.com").
                header("X-API-Key", "PMAK-680e2a7c74781300017751ae-9cea1b89e639b94188a6534e32a2c898ae").
                when().
                get("/workspaces").
                then().
                // log().all().
                        assertThat().
                statusCode(200).
                extract().
                response().path("workspaces[0].name");
        System.out.println(" workspace name : " + name);
//        assertThat(name,equalTo("My Workspace"));
        Assert.assertEquals(name, "My Workspace");
//        response();
//                response().asString();
//
//        System.out.println(" workspace name : " + JsonPath.from(res).getString("workspaces[0].name"));
//        JsonPath jsonPath=new JsonPath(res.asString());
//        System.out.println(" workspace name : " + jsonPath.getString("workspaces[0].name"));
//        System.out.println(" workspace name : " + jsonPath.getString("workspaces[0].name"));
//        System.out.println(" workspace name : " + res.path("workspaces[0].name"));
    }

    @Test
    public void validate_response_body_hamcrest_learnings() {
        given().
                baseUri("https://api.postman.com").
                header("X-API-Key", "PMAK-680e2a7c74781300017751ae-9cea1b89e639b94188a6534e32a2c898ae").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name",
                        //   contains("My Workspace", "My Workspace1"));
                        containsInAnyOrder("My Workspace1", "My Workspace"),
                        "workspaces.name",
                        //is(not(empty()))
                        is(not(emptyArray())),
                        "workspaces.name", hasSize(2),
                        "workspaces.name", everyItem(startsWith("My")),
                        "workspaces[0]", hasKey("id"),
                        "workspaces[0]", hasValue("My Workspace"),
                        "workspaces[0]", hasEntry("id", "8702d760-fd1c-4265-8033-8a693ed8a909"),
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)),
                        "workspaces[0].name", allOf(startsWith("My"), containsString("Workspace")),
                        "workspaces[0].name", anyOf(startsWith("Team"), containsString("Workspace"))
                );

    }

    @Test
    public void request_response_logging() {
        given().
                baseUri("https://api.postman.com").
                header("X-API-Key", "PMAK-680e2a7c74781300017751ae-9cea1b89e639b94188a6534e32a2c898ae").
                // log().all().
                // log().headers().
                //     log().body().
                    log().parameters().
               // log().params().
                //  log().cookies().
                        when().
                get("/workspaces").
                then().
                // log().all().
                // log().body().
                        log().headers().
                        log().cookies().
                assertThat().
                statusCode(200).
                body("workspaces.name", hasItems("My Workspace", "My Workspace1"),
                        "workspaces.size()", equalTo(2),
                        "workspaces.name", hasItem("My Workspace"));

    }
    @Test
    public void log_Only_if_error() {
        given().
                baseUri("https://api.postman.com").
                header("X-API-Key", "PMAK-680e2a7c74781300017751ae-9cea1b89e639b94188a6534e32a2c898ae").
                log().all().
                when().
                get("/workspaces").
                then().
                log().ifError().
                assertThat().
                statusCode(200).
                body("workspaces.name", hasItems("My Workspace", "My Workspace1"),
                        "workspaces.size()", equalTo(2),
                        "workspaces.name", hasItem("My Workspace"));

    }
    @Test
    public void log_Only_if_validation_fails() {
        given().
                baseUri("https://api.postman.com").
                header("X-API-Key", "PMAK-680e2a7c74781300017751ae-9cea1b89e639b94188a6534e32a2c898ae").
                config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())).
                //log().ifValidationFails().
                when().
                get("/workspaces").
                then().
                //log().ifValidationFails().
                assertThat().
                statusCode(200).
                body("workspaces.name", hasItems("My Workspace", "My Workspace1"),
                        "workspaces.size()", equalTo(2),
                        "workspaces.name", hasItem("My Workspace"));

    }
    @Test
    public void log_blacklist_header() {
        Set<String> headers=new HashSet<String>();
        headers.add("X-API-KEY");
        headers.add("Accept");

        given().
                baseUri("https://api.postman.com").
                header("X-API-Key", "PMAK-680e2a7c74781300017751ae-9cea1b89e639b94188a6534e32a2c898ae").
               // config(config.logConfig(LogConfig.logConfig().blacklistHeader("X-API-KEY"))).
                config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))).
                log().all().
                        when().
                get("/workspaces").
                then().
                        assertThat().
                statusCode(200).
                body("workspaces.name", hasItems("My Workspace", "My Workspace1"),
                        "workspaces.size()", equalTo(2),
                        "workspaces.name", hasItem("My Workspace"));

    }
}
