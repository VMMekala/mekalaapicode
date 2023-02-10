package com.sdet.lmsApi.stepdefinition;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import com.sdet.lmsApi.utilities.Utils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ExtraScenariosSteps extends Utils{

	RequestSpecification request;
	Response response;
	
	/**
	 * Authenticate user using basic authentication
	 * @param username
	 * @param password
	 */
	@Given("Add payload with valid {string} with valid {string}")
	public void add_payload_with_valid_username_with_valid_password(String username,String password) {
		request = given().auth().basic(username,password);
	}
	@When("user call login api with get method")
	public void user_call_login_api_with_get_method() {
	    response = request.when().get("login");
	}
	@Then("user is logged to system with success code {int}")
	public void user_is_logged_to_system_with_success_code(Integer code) {
	    response.then().assertThat().statusCode(code);
	}
	
	/**
	 * Limit result set 
	 * @param string
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	@Given("Add payload with integer limit {string}")
	public void add_payload_with_integer_limit(String string) throws NumberFormatException, IOException {
	   request = given().spec(requestSpecification()).queryParam("limit", Integer.parseInt(string));
	}
	@When("user calls getAllPrograms with get method")
	public void user_calls_get_all_programs_with_get_method() {
	    response = request.when().get("allprograms");
	}
	@Then("latest {string} number of records should display")
	public void latest_number_of_records_should_display(String string) {
	   assertEquals(response.asString().length(), Integer.parseInt(string)); 
	}
	
	
	/**
	 * sear by name description date
	 * @param string
	 * @param string2
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	@Given("add payload with {string} {string}")
	public void add_payload_with(String string, String string2) throws NumberFormatException, IOException {
		 request = given().spec(requestSpecification()).queryParam("limit", Integer.parseInt(string));
	}
	@When("user calls getProgramByName with get method")
	public void user_calls_get_program_by_name_with_get_method() {
		 response = request.when().get("allprograms");
	}
	@Then("All programs with given description must display with success code")
	public void all_programs_with_given_description_must_display_with_success_code() {
		response.then().assertThat().statusCode(200);
	}
	
	
}
