package webDriverMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Reports;


public class SeleniumMethods extends Reports {

    public WebDriver driver;
    private Logger log = Logger.getLogger(this.getClass().getName());
    public String AUTOMATE_USERNAME = "";
    public String AUTOMATE_ACCESS_KEY = "";
    public String URL;
    public DesiredCapabilities dc;
    public String version = "";

    public SeleniumMethods() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("/Users/manojs/Documents/Automation/Messy/src/test/resources/config.properties")));
            AUTOMATE_USERNAME = prop.getProperty("USERNAME");
            AUTOMATE_ACCESS_KEY = prop.getProperty("PASSWORD");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DesiredCapabilities getBrowser(String browser) {
        DesiredCapabilities dc = new DesiredCapabilities();

        switch (browser) {
            case "Chrome":
                version = "latest";
                dc = new DesiredCapabilities();
                break;

            case "Firefox":
                version = "latest";
                dc = new DesiredCapabilities();
                break;

            case "Edge":
                version = "latest";
                dc = new DesiredCapabilities();
                break;

            case "InternetExplorer":
                version = "latest";
                dc = new DesiredCapabilities();
                break;

            case "Safari":
                version = "latest";
                dc = new DesiredCapabilities();
                break;
        }
        dc.setCapability("version", version);
        return dc;
    }

    public void startBrowser(String browser, String platform, String applicationUrl, String tcname, String runIn) {

        if (runIn.equalsIgnoreCase("localGrid")) {

            String url = "http://10.15.11.51:4444";
            if(browser.equalsIgnoreCase("chrome")){
                ChromeOptions chrome = new ChromeOptions();
                try {
                    driver = new RemoteWebDriver(new URL(url), chrome);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else if(browser.equalsIgnoreCase("firefox")){
                FirefoxOptions firefox = new FirefoxOptions();
                try {
                    driver = new RemoteWebDriver(new URL(url), firefox);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else if(browser.equalsIgnoreCase("safari")){
                SafariOptions safari = new SafariOptions();
                try {
                    driver = new RemoteWebDriver(new URL(url), safari);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

//            ChromeOptions chrome = new ChromeOptions();
//
//            try {
//                driver = new RemoteWebDriver(new URL(url), chrome);
//            } catch (MalformedURLException e) {
//                 TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        }else if(runIn.equalsIgnoreCase("local")) {
            if(browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
        }else {

            URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";

            try {

                DesiredCapabilities dc = getBrowser(browser);
                log.warning(tcname);
                dc.setCapability("os", "Windows");
                dc.setCapability("os_version", "11");
                dc.setCapability("browser_version", version);
                dc.setCapability("name", tcname);
                dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
                dc.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
                driver = new RemoteWebDriver(new URL(URL), dc);

            } catch (Exception e) {

            }
        }
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get(applicationUrl);
            driver.manage().window().maximize();
            reportStep("[" + browser + "] launched successfully", "INFO");
        } catch (Exception e) {
            reportStep("[" + browser + "]: could not be launched", "FAIL");
        }
    }


    public long takeScreenShot() {

        long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
        try {
            File srcFiler = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFiler,
                    new File(System.getProperty("user.dir") + "/reports/images/" + number + ".png"));
        } catch (WebDriverException e) {
            log.warning("The snapshot has been taken.");
        } catch (IOException e) {
            log.warning("The snapshot has't been taken");
        }
        return number;
    }

    public void closeAllBrowsers() throws IOException {
        try {
            if (driver == null) {
                return;
            }
            driver.quit();
            driver = null;
            reportStep("The opened browsers are closed", "PASS", false);
        } catch (Exception e) {
            reportStep("Unexpected error occured in Browser: \n Error: " + e.getMessage(), "INFO", false);
        }
    }


    public void type(WebElement ele, String data) throws IOException {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(ele)));
            ele.clear();
            ele.sendKeys(data);
            if (data.matches("^[\\w_*^)!]*$")) {
                data = "****";
            }
            reportStep("The data: " + data + " entered successfully in field :" + "", "PASS");
        } catch (InvalidElementStateException e) {
        } catch (WebDriverException e) {
            reportStep("WebDriverException" + e.getMessage(), "INFO");
        }
    }

    public void click(WebElement ele) {

        String text = "";
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(ele)));
            text = ele.getText();
            ele.click();
            reportStep("The element : " + text + " is clicked ", "PASS");
        } catch (InvalidElementStateException e) {
            reportStep("The element: " + ele + " is not interactable", "FAIL");
        } catch (WebDriverException e) {
            reportStep("WebDriverException" + e.getMessage(), "FAIL");
        }
    }

    public static void sleep(int mSec) {
        try {
            Thread.sleep(mSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}