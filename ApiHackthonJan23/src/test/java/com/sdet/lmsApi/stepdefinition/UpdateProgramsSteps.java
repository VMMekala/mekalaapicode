package com.sdet.lmsApi.stepdefinition;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

import com.sdet.lmsApi.utilities.ApiResources;
import com.sdet.lmsApi.utilities.Utils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;

public class UpdateProgramsSteps extends Utils {
	
	RequestSpecification request;
	ResponseSpecification responseSpec;
	Response response;
	POJO_UpdateProgram data = new POJO_UpdateProgram(); 
	
	
	
	private void createProgramForTest(String programDescription, String programStatus) throws IOException
	{
		POJO_UpdateProgram cpData = new POJO_UpdateProgram();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		int random = (int)(Math.random()*1000);
		
		
		cpData.setProgramName(getProperties("programName")+random);
		cpData.setProgramDescription(programDescription+random);
		cpData.setProgramStatus(getProperties("programStatusInactive"));
		cpData.setCreationTime(dtf.format(now));
		cpData.setLastModTime(dtf.format(now));
		
		request = given()
    			.contentType(ContentType.JSON)
    			.body(cpData).log().all();
		
		data.setProgramName(request.when().post(getProperties("baseUrl")+getProperties("createProgram"))
					.jsonPath().getString("programName"));
		
		
	}
	
	
//	@Given("Sets the request payload data as {string} {string}")
//	public void sets_the_request_payload_data_as(String description, String status) throws IOException {

	@Given("Sets the request payload data with program {string} {string}")
	public void sets_the_request_payload_data_with_program(String programDescription, String programStatus) throws IOException {
		int random = (int)(Math.random()*1000);
		
		createProgramForTest(programDescription,programStatus);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		data.setProgramDescription(programDescription+random);
		data.setProgramStatus(programStatus);
		data.setCreationTime(dtf.format(now));
		data.setLastModTime(dtf.format(now));
				
		request = given().log().all().spec(requestSpecification())
				.body(data).log().all();
	}

	@When("User gives PUT Request to the update program api {string}")
	public void user_gives_put_request_to_the_update_program_api(String resource) throws IOException {
	
		responseSpec = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.build();
		//String resource = "UpdateProgramByName";
		
		ApiResources resourceName = ApiResources.valueOf(resource);
		//response = request.when().put(getProperties("updateProgramByName")+"/"+data.getProgramName());
		response = request.when().put(resourceName.getResource()+"/"+data.getProgramName());
		
	}

	@Then("Program should be updated with status code {int}")
	public void program_should_be_updated_with_status_code(Integer statusCode) {
		response.then().statusCode(statusCode).log().all();
	}

	@Then("validate response body and schema")
	public void validate_response_body_and_schema() throws IOException {
		response.then()
			.body("programDescription",equalTo(data.getProgramDescription()))
			.body("programStatus",equalTo(data.getProgramStatus()))
			//.body("lastModTime", equalTo(data.getLastModTime()))
			.header("Content-Type","application/json")
			//.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemaUpdateProgram.json"))
			.assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(getProperties("JSONSchemaUpdateProgram")+"/"+"schemaUpdateProgram.json")))
			.log().all();
		
		
		System.out.println("***********Delete the created program");
		
		given().when().delete(getProperties("baseUrl")+getProperties("deleteProgramByname")+"/"+data.getProgramName())
		.then().statusCode(200);
	}
	
	@Given("Update existing program payload with {string} {string} {string} with Put request")
	public void update_existing_program_payload_with_with_put_request(String string, String string2, String string3) throws IOException {

		JSONObject reqPayLoad = new JSONObject();
		reqPayLoad.put("programId", Integer.parseInt(string));
		reqPayLoad.put("programName", string2);
		reqPayLoad.put("programDescription", string3);
		reqPayLoad.put("programStatus", "Active" );
		reqPayLoad.put("creationTime", "2023-01-07T04:13:00.000+00:00");		
		reqPayLoad.put("lastModTime", "2023-01-07T04:13:00.000+00:00");

		request = given().log().all().spec(requestSpecification())
				.body(reqPayLoad.toString());
	}
	@When("user calls {string} api with {string} request")
	public void user_calls_api_with_request(String string, String string2) {
		responseSpec = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.build();

		response = request.expect().spec(responseSpec).when().put("/"+string+"/"+Integer.parseInt(string2));
	}
	
	@Then("program is updated with success code {int}")
	public void program_is_created_with_success_code(Integer code) {
		response.then().assertThat().statusCode(code);
	    
	}
	
	@And("verify {string} exists in response")
	public void verify_exists_in_response(String string) {		
		response.then().assertThat().body("programName", equalTo(string));
	    
	}
}
