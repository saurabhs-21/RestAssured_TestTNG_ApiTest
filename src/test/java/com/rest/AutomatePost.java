package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomatePost {
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
    public void validate_post_request_bdd_style() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"8702d760-fd1c-4265-8033-8a693ed8a909\",\n" +
                "        \"name\": \"myFirstWorkspace\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"Rest Assured created this\",\n" +
                "        \"visibility\": \"personal\"\n" +
                "      \n" +
                "    }\n" +
                "}";
        given().
                body(payload).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", equalTo("myFirstWorkspace"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));

        ;
    }

    @Test
    public void validate_post_request_non_bdd_style() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"8702d760-fd1c-4265-8033-8a693ed8a909\",\n" +
                "        \"name\": \"myFirstWorkspace1\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"Rest Assured created this\",\n" +
                "        \"visibility\": \"personal\"\n" +
                "      \n" +
                "    }\n" +
                "}";


        Response response = with().
                body(payload).
                when().
                post("/workspaces");
        assertThat(response.path("workspace.name"), equalTo("myFirstWorkspace1"));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));


        ;
    }

    @Test
    public void validate_post_request_payload_from_file() {
        File file = new File("src/main/resources/CreateWorkspacePayload.json");
        given().
                body(file).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", equalTo("mySecondWorkspace"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));

    }

    @Test
    public void validate_post_request_payload_as_map() {
        HashMap<String, Object> mainObject = new HashMap<String, Object>();
        HashMap<String, String> nestedObject = new HashMap<String, String>();
        nestedObject.put("name", "myThirdWorkspace");
        nestedObject.put("type", "personal");
        nestedObject.put("description", "Rest Assured created this");
        nestedObject.put("visibility", "personal");

        mainObject.put("workspace", nestedObject);

        given().
                body(mainObject).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", equalTo("myThirdWorkspace"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));

    }


}
