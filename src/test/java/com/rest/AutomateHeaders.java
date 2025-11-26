package com.rest;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.*;

public class AutomateHeaders {
    @Test
    public void multiple_headers(){

        Header header = new Header("header", "value2");
        Header matchHeader = new Header("x-mock-match-request-headers","header");

        given().
                baseUri("https://e3528176-1f88-41a0-bf10-9e7423380c15.mock.pstmn.io").
//                header("header", "value2").
//                header("x-mock-match-request-headers","header").
                header(header).
                header(matchHeader).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }

@Test
    public void multiple_headers_using_headers(){

        Header header = new Header("header", "value2");
        Header matchHeader = new Header("x-mock-match-request-headers","header");
        Headers headers = new Headers(header,matchHeader);

        given().
                baseUri("https://e3528176-1f88-41a0-bf10-9e7423380c15.mock.pstmn.io").
        headers(headers).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }
    @Test
    public void multiple_headers_using_Map(){
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers","header");

        given().
                baseUri("https://e3528176-1f88-41a0-bf10-9e7423380c15.mock.pstmn.io").
        headers(headers).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }
    @Test
    public void multi_value_headers_in_the_request(){

        Header header1 = new Header("multiValueHeader", "value1");
        Header header2 = new Header("multiValueHeader","value2");
        Headers headers = new Headers(header1,header2);

        given().
                baseUri("https://e3528176-1f88-41a0-bf10-9e7423380c15.mock.pstmn.io").
                headers(headers).
                log().headers().
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }
    @Test
    public void assert_response_headers(){
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers","header");

        given().
                baseUri("https://e3528176-1f88-41a0-bf10-9e7423380c15.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200).
                // header("responseHeader","resValue1");
                headers("responseHeader","resValue1","X-RateLimit-Limit","120");
    }

    @Test
    public void extract_response_headers(){
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers","header");

      Headers extractedHeaders =  given().
                baseUri("https://e3528176-1f88-41a0-bf10-9e7423380c15.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200).
                extract().
                headers();
      for(Header  header : extractedHeaders){

          System.out.println("header name = " + header.getName() + ",");
          System.out.println("header value = " + header.getValue());
      }
//        System.out.println("header name = " + extractedHeaders.get("responseHeader").getName());
//        System.out.println("header value = " + extractedHeaders.get("responseHeader").getValue());
//        System.out.println("header value = " + extractedHeaders.getValue("responseHeader"));
    }
    @Test
    public void extract_multi_value_response_header(){
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers","header");

        Headers extractedHeaders =  given().
                baseUri("https://e3528176-1f88-41a0-bf10-9e7423380c15.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                //log().all().
                assertThat().
                statusCode(200).
                extract().
                headers();
        List<String> values = extractedHeaders.getValues("multiValueHeader");
        for (String value : values){
            System.out.println(value);
        }
    }
}
