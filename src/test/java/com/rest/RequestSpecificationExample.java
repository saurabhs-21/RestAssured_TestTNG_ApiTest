package com.rest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RequestSpecificationExample {

    @BeforeClass
public void beforeClass(){
     /* requestSpecification= with().
             baseUri("https://api.postman.com").
            header("X-API-Key", "PMAK-680e2a7c74781300017751ae-9cea1b89e639b94188a6534e32a2c898ae"); */

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com")
                .addHeader("X-API-Key", "PMAK-680e2a7c74781300017751ae-9cea1b89e639b94188a6534e32a2c898ae");

//        requestSpecBuilder.setBaseUri("https://api.postman.com");
//        requestSpecBuilder.addHeader("X-API-Key", "PMAK-680e2a7c74781300017751ae-9cea1b89e639b94188a6534e32a2c898ae");

        requestSpecBuilder.log(LogDetail.ALL);

    requestSpecification = requestSpecBuilder.build();
    }

    @Test
    public void queryTest(){
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.
                query(requestSpecification);
        System.out.println(queryableRequestSpecification.getBaseUri());
        System.out.println(queryableRequestSpecification.getHeaders());
    }

    @Test
    public void validate_status_code() {
        //BDD approach
     /*  given().spec(requestSpecification).
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200); */

        //Non BDD approach
        Response response =
//                given(requestSpecification).
                get("/workspaces").then().log().all().extract().response();
        assertThat(response.getStatusCode(), is(equalTo(200)));
    }
@Test
    public  void  validate_response_body(){
        //BDD Approach
    /* given().spec(requestSpecification).
            when().
            get("/workspaces").
            then().
            log().all().
            assertThat().
            statusCode(200).body("workspaces[0].name", equalTo("My Workspace")); */

    //Non-BDD Approach
    Response response =
//            given(requestSpecification).
            get("/workspaces").then().log().all().extract().response();
    assertThat(response.getStatusCode(), is(equalTo(200)));
    assertThat(response.path("workspaces[0].name").toString(), equalTo("My Workspace"));

    }
}
