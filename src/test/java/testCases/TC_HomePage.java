package testCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageFields.LoginPage;
import webDriverMethods.ProjectMethods;

public class TC_HomePage extends ProjectMethods {

    @BeforeTest
    public void setValues() {

        testCaseName = "FoodHub Login";
        testDescription = "To verify FoodHub Login";
        testNodes = "Nodes";
        category = "Regression";
        authors = "vinoth";
    }


    @Test
    public void HomePage() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("/Users/manojs/Documents/Automation/Messy/src/test/resources/config.properties")));

            new LoginPage(driver, test)

                    .enterPostcode("AA11AA")
                    .clickFindtakeaway();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
