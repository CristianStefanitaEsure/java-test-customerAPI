package com.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
                , "tests.cucumber.MyTestListener"
        }
        , features = {"src/test/resources/features"}
        , glue = {"steps"}
        // , dryRun = false
        , monochrome = true
        , snippets = SnippetType.CAMELCASE
        //, tags = "@Test"
        , tags = "@Regression"
        //, tags = "@PutUpdate"
        //  , publish = true
)
public class TestRunner {

}