package pageObjects.grafana;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class MainPage {


    @FindBy(how = How.CSS, using = "section div:nth-of-type(2) h1")
    public WebElement head_Dashboard;

    @FindBy(how = How.CSS, using = "div.panel-content div:nth-child(2) > div:first-of-type > div")
    public static WebElement head_progressSteps;

    @FindBy(how = How.CSS, using = "div.panel-content div:nth-child(2) > div > div + div > a, a + div, a + div + div")
    public static List<WebElement> list_progressSteps;

//    @FindBy(how = How.XPATH, using = "//*[@id='2']//section//*")
//    public List<WebElement> list_progressSteps;

}
