package sanity;

import extensions.Verifications;
import io.qameta.allure.Description;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;
import workflows.WebFlows;

import java.util.ArrayList;
import java.util.List;

@Listeners(utilities.Listeners.class)
public class TestGrafanaWeb extends CommonOps {

    @Test(description = "Test 01 - Verify Header")
    @Description ("This Test logs in and verifies the main header")
    public void test01_verifyHeader(){
        WebFlows.login("admin", "admin");
        Verifications.verifyTextInElement(grafanaMain.head_Dashboard,"Welcome to Grafana");
    }

    @Test (description = "Test 02 - Verify Default Users")
    @Description ("This Test verifies the default users")
    public void test02_verifyDefaultUsers(){
        WebFlows.showUsers();
//        Verifications.numberOfElements(grafanaServerAdminMain.rows,99);      // FAIL TEST
        Verifications.numberOfElements(grafanaServerAdminMain.rows,1);
    }

    @Test (description = "Test 03 - Verify New Users")
    @Description ("This Test verifies a new user has been added")
    public void test03_verifyNewUser(){
        WebFlows.showUsers();
        WebFlows.createNewUser("Digital", "digital@web.com", "digiweb", "12345678");
        Verifications.numberOfElements(grafanaServerAdminMain.rows,99); // FAIL TEST
//        Verifications.numberOfElements(grafanaServerAdminMain.rows,2);

    }

    @Test (description = "Test 04 - Verify User Deletion")
    @Description ("This Test verifies user has been deleted")
    public void test04_verifyUserDeletion(){
        WebFlows.showUsers();
        WebFlows.deleteLastUser();
        WebFlows.showUsers();
        Verifications.numberOfElements(grafanaServerAdminMain.rows,44);  // FAIL TEST
//        Verifications.numberOfElements(grafanaServerAdminMain.rows,1);

    }

    @Test (description = "Test 05 - Verify progress steps")
    @Description ("This Test verifies the progress steps are visible on main page using soft Assertion")
    public void test05_verifyProgressSteps(){
        List<WebElement> listOfAllWebElements = new ArrayList<WebElement>();
        listOfAllWebElements.add(grafanaMain.head_progressSteps);
        for (WebElement elem:grafanaMain.list_progressSteps){
            listOfAllWebElements.add(elem);
        }
//        Verifications.verifyTextInElement( grafanaMain.head_progressSteps,"Basic\n" +
//                "The steps below will guide you to quickly finish setting up your Grafana installation.");
        Verifications.visibilityOfElements(listOfAllWebElements);
    }
}
