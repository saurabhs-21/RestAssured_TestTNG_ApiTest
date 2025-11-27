package com.rest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ResponseSpecificationExample {

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

        RestAssured.requestSpecification = requestSpecBuilder.build();

       /*  responseSpecification = RestAssured.expect().
                statusCode(200).
                contentType(ContentType.JSON);  */
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);

        RestAssured.responseSpecification  = responseSpecBuilder.build();

    }

    @Test
    public void validate_status_code() {
        //Non BDD approach
                get("/workspaces");
                        //then().spec(responseSpecification);
    }

@Test
    public  void  validate_response_body(){
    //Non-BDD Approach
    Response response =
            get("/workspaces").
                    then().
                    extract().response();
    assertThat(response.path("workspaces[0].name").toString(), equalTo("My Workspace"));

    }
}
