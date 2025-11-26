package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomatePut {

    @BeforeClass
    public void beforeClass() {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com")
                .addHeader("X-API-Key", "PMAK-680e2a7c74781300017751ae-9cea1b89e639b94188a6534e32a2c898ae");
        requestSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();


        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void validate_put_request_bdd_style() {
        String workspaceId = "c2310d78-e505-4b83-b4d2-171939871aa2";
        String payload = "{\n" +
                "    \"workspace\": \n" +
                "        { \n" +
                "            \"name\": \"My Workspace3\",\n" +
                "            \"type\": \"personal\",\n" +
                "            \"visibility\": \"personal\",\n" +
                "            \"createdBy\": \"42375952\"\n" +
                "        }  \n" +
                "}";
        given().
                body(payload).
                pathParam("workspaceId", workspaceId).

                when().
                put("/workspaces/{workspaceId}").

                then().
                log().all().
                assertThat().
                body("workspace.name", equalTo("My Workspace3"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId));
        ;

    }
    @Test
    public void validate_put_request_non_bdd_style(){
        String workspaceId = "99906b1a-1ac4-44be-934a-ec26f714a01e";
        String payload = "{\n" +
                "    \"workspace\": \n" +
                "        { \n" +
                "            \"name\": \"myFirstWorkspace4\",\n" +
                "            \"type\": \"personal\",\n" +
                "            \"visibility\": \"personal\",\n" +
                "            \"createdBy\": \"42375952\"\n" +
                "        }  \n" +
                "}";


        Response response =  with().
                body(payload).
                pathParam("workspaceId", workspaceId).
                when().
                put("/workspaces/{workspaceId}");

        assertThat(response.path("workspace.name"), equalTo("myFirstWorkspace4"));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
        assertThat(response.path("workspace.id"),equalTo("99906b1a-1ac4-44be-934a-ec26f714a01e"));


        ;
    }

}
