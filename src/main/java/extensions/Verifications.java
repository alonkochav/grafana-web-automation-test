package extensions;

import io.qameta.allure.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.sikuli.script.*;
import utilities.CommonOps;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class Verifications extends CommonOps {

    @Step ("Verify Text in Elements")
    public static void verifyTextInElement(WebElement elem, String expected){
        wait.until(ExpectedConditions.visibilityOf(elem));
        assertEquals(elem.getText(), expected);
    }

    @Step ("Verify Number of Elements")
    public static void numberOfElements(List<WebElement> elems, int expected){
        wait.until(ExpectedConditions.visibilityOf(elems.get(elems.size()-1)));
        assertEquals(elems.size(),expected);
    }

    @Step("Verify Visibility of Elements (softAssertion)")
    public static void visibilityOfElements(List<WebElement> elems){
        for (WebElement elem:elems) {
            wait.until(ExpectedConditions.visibilityOfAllElements(elems));
            softAssert.assertTrue(elem.isDisplayed(),"Sorry, the element "+ elem.getText() + "is not displayed." );
        }
        softAssert.assertAll("Some elements were not displayed");
    }

    @Step("Verify Element Visually")
    public static void  visualElement(String expectedImageName){
        try{
            screen.find(getData("ImageRepo")+expectedImageName+".png");
        } catch (FindFailed findFailed){
            System.out.println("Error comparing Image File " + findFailed);
            fail("Error comparing Image File " + findFailed);
        }
    }
}
