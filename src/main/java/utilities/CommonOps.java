package utilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.windows.WindowsDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import io.restassured.RestAssured;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.sikuli.script.Screen;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import workflows.ElectronFlows;

import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;



public class CommonOps extends Base {

    public boolean isWeb() {  return platform.equalsIgnoreCase("web"); }
    public boolean isMobile() { return platform.equalsIgnoreCase("mobile");    }
    public boolean isAPI() {
        return platform.equalsIgnoreCase("api");
    }
    public boolean isElectron(){
        return platform.equalsIgnoreCase("electron");
    }
    public boolean isDesktop() {
        return platform.equalsIgnoreCase("desktop");
    }

    /**
     * ##############################################################################################
     * Method Name: getData
     * Method Description: This method gets the data from the xml configuration file
     * Method Parameters: String
     * Method Return: String
     * #################################################################################
     **/
    public static String getData(String nodeName) {
        File fXmlFile;
        DocumentBuilder dBuilder;
        Document doc = null;
        DocumentBuilderFactory dbFactory;

        try {
            fXmlFile = new File(".\\Configuration\\DataConfig.xml");
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

        } catch (Exception e) {
            Log.info("Exception in reading XML file: " + e);
        }
        return doc.getElementsByTagName(nodeName).item(0).getTextContent();
    }

    /**
     * ##############################################################################################
     * Method Name: initBrowser
     * Method Description: This method gets the browser type while using the web platform
     * Method Parameters: String
     * Method Return: void
     * #################################################################################
     **/

    public void initBrowser(String browserType) {
        if (browserType.equalsIgnoreCase("chrome"))
            driver = initChromeDriver();
        else if (browserType.equalsIgnoreCase("firefox"))
            driver = initFirefoxDriver();
//        else if (browserType.equalsIgnoreCase("tortor"))
//            driver = initTorDriver();
        else if (browserType.equalsIgnoreCase("ie/edge"))
            driver = initIEDriver();
        else
            throw new RuntimeException("Invalid browser type");

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Long.parseLong(getData("Timeout")), TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Long.parseLong(getData("Timeout")));
        driver.get(getData("url"));
        ManagePages.initGrafana();
    }

    /**
     * ##############################################################################################
     * Method Name: initChromeDriver
     * Method Description: This method gets the Chrome browser using the web platform
     * Method Parameters: None
     * Method Return: WebDriver
     * #################################################################################
     **/
    public static WebDriver initChromeDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        return driver;
    }
    /**
     * ##############################################################################################
     * Method Name: initFirefoxDriver
     * Method Description: This method gets the Firefox browser using the web platform
     * Method Parameters: None
     * Method Return: WebDriver
     * #################################################################################
     **/
    public static WebDriver initFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        return driver;
    }

    /**
     * ##############################################################################################
     * Method Name: initIEDriver
     * Method Description: This method gets the Internet Explorer browser using the web platform
     * Method Parameters: None
     * Method Return: WebDriver
     * #################################################################################
     **/
    public static WebDriver initIEDriver() {
        WebDriverManager.iedriver().setup();
        driver = new InternetExplorerDriver();
        return driver;
    }

    /**
     * ##############################################################################################
     * Method Name: initMobile
     * Method Description: This method runs the Appium server using the mobile platform
     * Method Parameters: None
     * Method Return: void
     * #################################################################################
     **/
    // Mobile driver methods
    public static void initMobile(){
        dc.setCapability(MobileCapabilityType.UDID, getData("UDID"));
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, getData("AppPackage"));
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getData("AppActivity"));
        try {
            mobileDriver = new AndroidDriver(new URL(getData("AppiumServer")+ "wd/hub"), dc);

        } catch (Exception e) {
            Log.info("Cannot Connect to Appium Server. See details: " + e);
        }
        mobileDriver.manage().timeouts().implicitlyWait(Long.parseLong(getData("Timeout")), TimeUnit.SECONDS);
        wait = new WebDriverWait(mobileDriver, Long.parseLong(getData("Timeout")));
        ManagePages.initMortgage();
    }
    /**
     * ##############################################################################################
     * Method Name: initAPI
     * Method Description: This method runs the API platform using Rest Assured
     * Method Parameters: None
     * Method Return: void
     * #################################################################################
     **/
    //   Rest API
    public static void initAPI() {
        RestAssured.baseURI = getData("urlAPI");
        httpRequest = RestAssured.given().auth().preemptive().basic(getData("username"),getData("password"));
    }
    /**
     * ##############################################################################################
     * Method Name: initElectron
     * Method Description: This method runs the Electron platform using the Electron Driver
     * Method Parameters: None
     * Method Return: void
     * #################################################################################
     **/

    //  Electron Driver
    public static void initElectron() {
        System.setProperty("webdriver.chrome.driver",getData("ElectronDriverPath"));
        ChromeOptions opt = new ChromeOptions();
        opt.setBinary(getData("ElectronAppPath"));
        dc.setCapability("chromeOptions",opt);
        dc.setBrowserName("chrome");
        driver = new ChromeDriver(dc);
        driver.manage().timeouts().implicitlyWait(Long.parseLong(getData("Timeout")), TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Long.parseLong(getData("Timeout")));
        ManagePages.initToDo();
    }

    /**
     * ##############################################################################################
     * Method Name: initDesktop
     * Method Description: This method runs the desktop platform using Windows Application Driver
     * Method Parameters: None
     * Method Return: void
     * #################################################################################
     **/
    public static void initDesktop() {
        dc.setCapability("app",getData("CalculatorApp"));
        try {
            driver = new WindowsDriver(new URL( getData("AppiumServer")), dc);
        } catch (MalformedURLException e) {
            Log.info("Could not connect to Appium Server, See Details : " + e);
        }
        driver.manage().timeouts().implicitlyWait(Long.parseLong(getData("Timeout")), TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Long.parseLong(getData("Timeout")));
        ManagePages.initCalculator();
    }

    //  ---    BeforeClass
    @BeforeClass
    @Parameters({"PlatformName"})
    public void startSession(String PlatformName) {
        platform = PlatformName;
        if (isWeb())
            initBrowser(getData("BrowserName"));
        else if (isMobile())
            initMobile();
        else if (isAPI())
            initAPI();
        else if (isElectron())
            initElectron();
        else if (isDesktop())
            initDesktop();
        else
            throw new RuntimeException("Invalid platform name");
        softAssert = new SoftAssert();
        screen = new Screen();


        if (!isDesktop() && !isAPI())
            action = new Actions(driver);
        String dbURL = getData("DBURL");
        String dbUser = getData("DBUsername");
        String dbPass = getData("DBPassword");
        ManageDB.openConnection(dbURL,dbUser,dbPass);
    }

    //  ---    BeforeMethod

    @BeforeMethod
    public void beforeMethod(Method method) {
        try {
            if (!isAPI()){
                if (!isMobile()){
                    MonteScreenRecorder.startRecord(method.getName());
                }
                else if (isMobile()){
                    ((JavascriptExecutor) mobileDriver).executeScript("window.focus();");
                    mobileDriver.startRecordingScreen();
                }
                else {
                    ((JavascriptExecutor) driver).executeScript("window.focus();");
                    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("html"), 0));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //   --      @AfterMethod
    @AfterMethod
    public void afterMethod() {
        if (isWeb()) {
            driver.get(getData("url"));
            ((JavascriptExecutor) driver).executeScript("window.focus();");
        } else if (isElectron()){
            ElectronFlows.emptyList();
        }
    }

    //   ---            @AfterClass
    @AfterClass
    public void closeSession(){
        if (!isAPI()) {
            if (isMobile())
                mobileDriver.quit();
            else
                driver.quit();
        }
        ManageDB.closeConnection();

    }

}
