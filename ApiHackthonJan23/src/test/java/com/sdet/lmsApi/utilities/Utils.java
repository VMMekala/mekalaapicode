package com.sdet.lmsApi.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	
	public static RequestSpecification reqSpec;
	
	public RequestSpecification requestSpecification() throws IOException {
		
		if(reqSpec==null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			reqSpec = new RequestSpecBuilder()
				.setBaseUri(getProperties("baseUrl"))
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
				.setContentType(ContentType.JSON).build();
		}
		return reqSpec;
	}
	
	public String getProperties(String key) throws IOException {
		
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("./src/test/resources/Config.properties");
		prop.load(fis);
		System.out.println("Key:"+key);
		return prop.getProperty(key);
		
	}
	
	public String getJsonPath(Response response, String key) {
		String res = response.asString();
		JsonPath js = new JsonPath(res);
		//System.out.println("json key # "+key +" # "+js.get(key));
		return js.get(key).toString();
	}
	
	public String getRandomNumber(int n) {
		String AlphaNumericString = "0123456789";

		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {		
			int index = (int)(AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
			}
		return sb.toString();
		}	
}
