package utilities;

import io.appium.java_client.android.AndroidDriver;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;
import org.testng.asserts.SoftAssert;
import pageObjects.calculator.MainPage;
import pageObjects.grafana.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Base {

    // General
    protected static WebDriverWait wait;
    protected static Actions action;
    protected static SoftAssert softAssert;
    protected static Screen screen;
    protected static String platform;

     // Web
    protected static WebDriver driver;


    // Mobile
    protected static AndroidDriver mobileDriver;
    protected static DesiredCapabilities dc = new DesiredCapabilities();

    // Rest API

    protected static RequestSpecification httpRequest;
    protected static Response response;
    protected static JSONObject params = new JSONObject();
    protected static JsonPath jp;

    // Database
    protected static Connection con;
    protected static Statement stmt;
    protected static ResultSet rs;

    // Page Objects - Web
    protected static LoginPage grafanaLogin;
    protected static pageObjects.grafana.MainPage grafanaMain;
    protected static LeftMenuPage grafanaLeftMenu;
    protected static ServerAdminMenuPage grafanaServerAdmin;
    protected static ServerAdminMainPage grafanaServerAdminMain;
    protected static AddNewUserPage grafanaAddNewUser;
    protected static EditUserPage grafanaEditUser;

    // Page Objects - Mobile
    protected static pageObjects.mortgage.MainPage mortgageMain;

    // Page Objects - Electron
    protected static pageObjects.todo.MainPage todoMain;

    // Page Objects - Desktop
    protected static MainPage calcMain;




}
