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

public class AutomateDelete {

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
    public void validate_delete_request_bdd_style() {
        String workspaceId = "d0801fa1-a14d-4c67-a32e-bddd8277e05d";
        given().
                pathParam("workspaceId", workspaceId).

                when().
                delete("/workspaces/{workspaceId}").

                then().
                log().all().
                assertThat().
                body("workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId));
        ;

    }
    @Test
    public void validate_delete_request_non_bdd_style(){
        String workspaceId = "514b528e-4ddc-4fdd-8ef1-b33907ef8019";


        Response response =  with().
                pathParam("workspaceId", workspaceId).
                when().
                delete("/workspaces/{workspaceId}");

        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
        assertThat(response.path("workspace.id"),equalTo("514b528e-4ddc-4fdd-8ef1-b33907ef8019"));


        ;
    }

}
