package pageFields;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import webDriverMethods.ProjectMethods;

public class LoginPage extends ProjectMethods{

    //private Logger log = Logger.getLogger(this.getClass().getName());

    public LoginPage(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;

        PageFactory.initElements(driver, this);
    }

    @FindBy(id="home-postcode")
    private WebElement elePostcode;
    public LoginPage enterPostcode(String postCode) {
        try {
            type(elePostcode, postCode);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this;
    }

    @FindBy(id="home-findtakeaway")
    private WebElement eleFindtakeaway;
    public LoginPage clickFindtakeaway() {
        click(eleFindtakeaway);
        return this;
    }


}
