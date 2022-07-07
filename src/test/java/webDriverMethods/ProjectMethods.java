package webDriverMethods;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class ProjectMethods extends SeleniumMethods {

    public String browser;
    public String testCaseName;
    public String testDescription;

    @BeforeSuite
    public void beforeSuite() {
        startResult();
    }

    @BeforeClass
    public void beforeClass() {

    }

    @Parameters({ "browser", "platform", "url", "runIn"})
    @BeforeMethod
    public void beforeMethod(String browser, String platform, String applicationUrl, String runIn) {
        test = startTestModule(testCaseName, testDescription);
        test = startTestCase(testNodes);
        test.assignCategory(category);
        test.assignAuthor(authors);
        startBrowser(browser, platform, applicationUrl, testCaseName, runIn);

    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() throws IOException {
        endResult();
        closeAllBrowsers();
    }

    @AfterTest
    public void afterTest() {

    }

//    @AfterClass
//    public void afterClass() {
//
//    }

    @AfterSuite
    public void afterSuite() {
        endResult();

    }

}
