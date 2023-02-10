package com.sdet.lmsApi.testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = {"src/test/resources/Features/batch.feature"},
		glue = {"com.sdet.lmsApi.stepdefinition"},
		tags = "not @ignore",
		plugin = { "pretty", "html:target/cucumber-reports/LmsAPi.html"}
	)
public class TestRunner extends AbstractTestNGCucumberTests {

}
