package com.sdet.lmsApi.stepdefinition;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;
import org.testng.Assert;

import com.sdet.lmsApi.utilities.ApiResources;
import com.sdet.lmsApi.utilities.Utils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BatchSteps extends Utils{
	
	RequestSpecification request;
	ResponseSpecification responseSpec;
	Response response;
	RequestSpecification reqSpeci;
	public  static String batchId;
	public JSONObject reqPayLoad = new JSONObject();
     String num;
	@Given("create new batch  with {string} {string} {string} {int} {int} with POST request")
	public void create_new_batch_with_with_post_request(String batchname, String batchdesc, String batchStatus, Integer batchnoofclass, Integer programid) {
	
		try
		{
			int n = 3;
			num = getRandomNumber(n);
		System.out.println(" Req: "+batchname+batchdesc+batchnoofclass+programid);
	
		reqPayLoad.put("batchName",batchname+num);
		reqPayLoad.put("batchDescription", batchdesc);
		reqPayLoad.put("batchStatus", batchStatus);
		reqPayLoad.put("batchNoOfClasses",batchnoofclass);
		reqPayLoad.put("programId",programid);
		reqSpeci= requestSpecification();
		
		request =given().log().all().spec(reqSpeci).body(reqPayLoad.toString());
		System.out.println(" Req: "+request.toString());
		
		}
		catch(Exception ex)
		{
			
		}
		}

	@When("user calls {string} api with post method")
	public void user_calls_createbatch_api_with_post_request(String resource) {
	
		responseSpec = new ResponseSpecBuilder()
				      .expectStatusCode(201).expectContentType(ContentType.JSON).build();
		
		ApiResources resourceName = ApiResources.valueOf(resource);
		response = request.when().post(resourceName.getResource());
		
	}
	@Then("batch is created with success code {int}")
	public void batch_is_created_with_success_code(Integer statuscode ) {
		System.out.println(response.statusCode());
		assertEquals(response.getStatusCode(), 201);
		
		
	}

	@Then("verify batch_id created is mapped to given programId using PosttBatchAPI")
	public void verify_batch_id_created_is_mapped_to_given_program_id_using_postt_batch_api() {
	  
	   
	try {
		batchId = getJsonPath(response,"batchId");
		System.out.println(" response: "+batchId);
		System.out.println(" response: Success ");
		if (batchId!=null && !batchId.isEmpty())
			Assert.assertTrue(true);
		else 
			Assert.assertTrue(false);
	}
	catch (Exception Ex)
	{
		
	}
	}

	//get batches By ID  
	
	
	@Given("get batch with get request by Id")
	public void get_batch_with_get_request_by_id() {
		try 
		{
		reqSpeci= requestSpecification();
		//batchId= bId;
		request = given().log().all().spec(reqSpeci)
			   .basePath("{basePath}/{basePath2}/{id}")
			    .pathParam("basePath","batches")
			    .pathParam("basePath2","batchId")
			    .pathParam("id", batchId);
		}catch(Exception ex)
		{
			
		}
	}

	@When("user calls get batch api with get request")
	public void user_calls_get_batch_api_with_get_request() {
		responseSpec = new ResponseSpecBuilder()
			      .expectStatusCode(200).expectContentType(ContentType.JSON).build();
	
	response = request.when().get();
	}

	@Then("get batch details with success code {int}")
	public void get_batch_details_with_success_code(Integer int1) {
		System.out.println(response.statusCode());
		assertEquals(response.getStatusCode(), 200);
	}

	@Then("verify the given batch_id")
	public void verify_the_given_batch_id() {
		try {
			String actBatchId = getJsonPath(response,"batchId");
			System.out.println(" response: "+batchId+" actBatchId :"+actBatchId);
			assertEquals(actBatchId, batchId);
		}
		catch (Exception Ex)
		{
			
		}
	}
	
	// delete batch
	@Given("delete batch with delete request")
	public void delete_batch_with_delete_request() throws IOException {
		
		reqSpeci= requestSpecification();
		//batchId= bId;
		request = given().log().all().spec(reqSpeci)
			   .basePath("{basePath}/{id}")
			    .pathParam("basePath","batches")
			    .pathParam("id", batchId);
		
		System.out.println(" Request: "+request.given().log().toString());
		
	}

	@When("user calls delete batch api with Delete request")
	public void user_calls_delete_batch_api_with_delete_request() {
		responseSpec = new ResponseSpecBuilder()
			      .expectStatusCode(200).expectContentType(ContentType.JSON).build();
	
	response = request.when().delete();
	}

	@Then("batch is deleted with success code {int}")
	public void batch_is_deleted_with_success_code(Integer int1) {
		System.out.println(response.statusCode());
		assertEquals(response.getStatusCode(), 200);
	}

	@Then("verify the given batch_id deleted")
	public void verify_the_given_batch_id_deleted() {
		String msg = response.asString();
		System.out.println(" response: "+ msg);
		String expMsg= "Message: Batch with Id-"+batchId+" deleted Successfully!";
		assertEquals(msg,expMsg);
		
	}

	/**
	 * Get all batches
	 * @param string
	 * @throws IOException 
	 */
	@Given("a api url")
	public void a_service() throws IOException {
		request = given().spec(requestSpecification());			
	}
	@When("user calls {string} with get method")
	public void user_calls_get_method(String resource) {
		ApiResources resourceName = ApiResources.valueOf(resource);
		response = request.when().get(resourceName.getResource());
	}
	@Then("resquest is successfull with  status code {int}")
	public void resquest_is_successfull_with_status_code(int code) {
	    response.then().assertThat().statusCode(code); 
	}
	@Then("verify response size is greater than zero")
	public void verify_response_size_is_greater_than_zero() throws IOException {
		System.out.println("response length::"+response.asString().length());
		Assert.assertTrue(response.asString().length() > 0);
	}

	/**
	 * get batch by ID
	 * @param batchname
	 * @param batchdesc
	 * @param batchStatus
	 * @throws IOException
	 */
	@Given("Update existing batch payload with {string} {string} {string} with Put request")
	public void update_existing_batch_with_with_put_request(String batchname, String batchdesc, String batchStatus) throws IOException {
		reqPayLoad.put("batchName",batchname);
		reqPayLoad.put("batchDescription", batchdesc);
		reqPayLoad.put("batchStatus", batchStatus);
		reqPayLoad.put("batchNoOfClasses",12);
		reqPayLoad.put("programId",305);
		reqSpeci= requestSpecification();
		
		request =given().log().all().spec(reqSpeci).body(reqPayLoad.toString());
		System.out.println(" Req: "+request.toString());
	}
	@When("user calls {string} api with {string}")
	public void user_calls_batch_api_with_request(String resource, String batchId) {
		responseSpec = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.build();
		ApiResources resourceName = ApiResources.valueOf(resource);
		response = request.log().all().expect().spec(responseSpec).when().put(resourceName.getResource()+batchId);
	}

	
	@Then("verify batch is updated with {string} {string}")
	public void verify_batch_id_created_is_mapped_to_using(String name, String desc) {
		/*String batchName = getJsonPath(response,"batchName");
		System.out.println(" response: "+batchName+" batchDesc :"+string);
		assertEquals(batchName, string);
		
		
		String batchDescription = getJsonPath(response,"batchDescription");
		System.out.println(" response: "+string2+" batchDescription :"+batchDescription);
		assertEquals(batchDescription, string2);*/
		
		response.then().assertThat().body("batchName", equalTo(name))
		.body("batchDescription", equalTo(desc));
		
	}

		
	
}
