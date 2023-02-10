package com.sdet.lmsApi.stepdefinition;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.sdet.lmsApi.utilities.ApiResources;
import com.sdet.lmsApi.utilities.TestDataPayload;
import com.sdet.lmsApi.utilities.Utils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class ProgramSteps extends Utils {

	RequestSpecification request;
	ResponseSpecification responseSpec;
	Response response;
	String num;
	public static String programId;
	public static String programName;
	
	public static ArrayList<ProgramData> programs = new ArrayList<ProgramData>();
		
	@Given("create new program payload with {string} {string} {string} with POST request")
	public void create_new_program_payload_with_with_post_request(String name, String desc, String status) throws IOException {
		
		int n = 4;
		num = getRandomNumber(n);
		
		TestDataPayload programPayload = new TestDataPayload();
			
		request = given().spec(requestSpecification())
				.body(programPayload.createProgramPayload(name+num, desc, status));
	}
	
	@When("user calls {string} api with post request")
	public void user_calls_saveprogram_api_with_post_request(String resource) {
		responseSpec = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.build();
		
		ApiResources resourceName = ApiResources.valueOf(resource);
		response = request.when().post(resourceName.getResource());
	}
	
	@Then("program is created with success code")
	public void program_is_created_with_success_code() {
		System.out.println("Code::"+response.getStatusCode());
		assertEquals(response.getStatusCode(), 201);
	   
	}
		
	@And("verify program_id created is mapped to {string} using {string}")
	public void verify_program_id_created_is_mapped_to_using(String actualProgramName, String method) throws IOException {
		
		ProgramData program = new ProgramData(Integer.valueOf(getJsonPath(response, "programId")), getJsonPath(response, "programName"));
		programs.add(program);
		System.out.println(program);
		
	
		//Assert response contains program name
		a_program_id_with_api(method);
		user_requests_with_get_method();
				
		assertEquals(program.getProgramName(), actualProgramName+num);
		
		response.then()
		.header("Content-Type","application/json").statusLine("HTTP/1.1 200 ")
		.assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(getProperties("JSONSchemaUpdateProgram")+"/"+"CreateProgramSchema.json")));
		
	}
	
	/**
	 * Get  programs by ID
	 * @throws IOException 
	 */
	 
	 
	  
	@Given("a program id with api {string}")
	public void a_program_id_with_api(String method) throws IOException {
	
		request = given().spec(requestSpecification());
		
		System.out.println("program Id ::"+programs.get(programs.size()-1).getProgramId()+"method::" +method+ " contains::"+method.contains("Get"));
		ApiResources resourceName = ApiResources.valueOf(method);
		System.out.println("resource name::"+resourceName.getResource());
		if(method.contains("Get")) {			
			request.basePath("{basePath}/{programId}")
		    .pathParam("basePath",resourceName.getResource());
		}else if(method.contains("Delete")) {
			request.basePath("{basePath}/{programId}")
			.pathParam("basePath",resourceName.getResource());
		} 
		request.pathParam("programId", programs.get(programs.size()-1).getProgramId());
	}
	
	@When("user requests with get method")
	public void user_requests_with_get_method() {
		response = request.when().get();
	}
	@Then("programs are displayed with success code")
	public void programs_are_displayed_with_success_code() {
		assertEquals(response.getStatusCode(), 200);
	    
	}
	@And("response has id")
	public void response_has_id() {
		int actualProgramId = Integer.valueOf(getJsonPath(response,"programId"));
		System.out.println("program Id::"+programs.get(programs.size()-1).getProgramId());
		assertEquals(actualProgramId, programs.get(programs.size()-1).getProgramId());
	}
	
	/**
	 * Delete by Id
	 * @param string
	 */
	@When("user requests with delete method")
	public void user_requests_with_delete_method() {
	    response = request.when().delete();
	    programs.remove(programs.size()-1);
	}
	@Then("program id is deleted with success code")
	public void program_id_is_deleted_with_success_code() {
	    assertEquals(response.getStatusCode(), 200);
	}
	
	/**
	 * Delete program by name
	 */
	@Given("program payload with in GET request")
	public void program_payload_with_in_get_request() throws IOException {
		request = given().spec(requestSpecification());	 
	}

	@When("user calls deletebyprogname api with in DELETE request")
	public void user_calls_deletebyprogname_api_with_in_delete_request() {		
	    response = request.when().delete("/deletebyprogname/"+programs.get(programs.size()-1).getProgramName());
	}

	@Then("program is deleted by program name with success code {int}")
	public void program_is_deleted_by_program_name_with_success_code(Integer int1) {
		response.then().assertThat().statusCode(int1).statusLine("HTTP/1.1 200 ");
	}
	
	/**
	 * Get all programs
	 * @throws IOException
	 */
	
	@Given("API URL to fetch all programs")
	public void api_url_to_fetch_all_programs() throws IOException {
		request = given().log().all().spec(requestSpecification());
	}

	
	@When("User sends GET request to {string}")
	public void user_sends_get_request(String resource) {
		ApiResources resourceName = ApiResources.valueOf(resource);
		response = request.when().get(resourceName.getResource());
	}
	
	@Then("All programs are displayed with success code")
	public void all_programs_are_displayed_with_success_code() {
		response.then().assertThat().statusCode(200);
	}
	
	@And("validate header in response")
	public void validate_header_in_response() {
		response.then().assertThat().header("Content-Type","application/json");
		
	}
	

	
}
