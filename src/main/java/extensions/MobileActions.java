package extensions;

import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Step;
import org.openqa.selenium.Dimension;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.CommonOps;

import java.time.Duration;

public class MobileActions extends CommonOps {

    @Step("Update Text Element")
    public static void updateText(AndroidElement elem, String text) {
        wait.until(ExpectedConditions.visibilityOf(elem));
        elem.sendKeys(text);
    }

/*    ====================================         TAP  =============================================== */
    @Step ("Tap on Element")
    public static void tap (AndroidElement elem) {
        wait.until(ExpectedConditions.elementToBeClickable(elem));
        AndroidTouchAction action = new AndroidTouchAction (mobileDriver);
        action.tap((new TapOptions())
                .withElement(ElementOption.element(elem)))
                .perform();
    }

/*    ===================================   Long Press =============================================== */
//    @Step ("Long press / longtap on Element")
//    public static void longPress(AndroidElement elem){
//        TouchAction action = new TouchAction (mobileDriver);
//        action.longPress(elem);
//        action.perform();
//    }

/*    =================================     SWIPE  =============================================== */

    @Step ("Swipe Element")
    public static void swipe(Direction dir) {
        System.out.println("swipeScreen(): dir: '" + dir + "'"); // always log your actions

        // Animation default time:
        //  - Android: 300 ms
        //  - iOS: 200 ms
        // final value depends on your app and could be greater
        final int ANIMATION_TIME = 200; // ms

        final int PRESS_TIME = 200; // ms

        int edgeBorder = 10; // better avoid edges
        PointOption pointOptionStart, pointOptionEnd;

        // init screen variables
        Dimension dims = mobileDriver.manage().window().getSize();

        // init start point = center of screen
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);

        switch (dir) {
            case DOWN: // center of footer
                pointOptionEnd = PointOption.point(dims.width / 2, dims.height - edgeBorder);
                break;
            case UP: // center of header
                pointOptionEnd = PointOption.point(dims.width / 2, edgeBorder);
                break;
            case LEFT: // center of left side
                pointOptionEnd = PointOption.point(edgeBorder, dims.height / 2);
                break;
            case RIGHT: // center of right side
                pointOptionEnd = PointOption.point(dims.width - edgeBorder, dims.height / 2);
                break;
            default:
                throw new IllegalArgumentException("swipeScreen(): dir: '" + dir + "' NOT supported");
        }

        // execute swipe using AndroidTouchAction
        try {
            new TouchAction(mobileDriver)
                    .press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeScreen(): AndroidTouchAction  FAILED\n" + e.getMessage());
            return;
        }

        // always allow swipe action to complete
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }
    }


    /*    ================================     ZOOM  =============================================== */

    @Step ("Zoom Element -- ZOOM IN")
    public static void zoom(AndroidElement elem) {
        wait.until(ExpectedConditions.visibilityOf(elem));

        int x = elem.getLocation().getX() + elem.getSize().getWidth() / 2;
        int y = elem.getLocation().getY() + elem.getSize().getHeight() / 2;
        AndroidTouchAction  finger1 = new AndroidTouchAction (mobileDriver);
        finger1.press(new ElementOption()
                .withElement(elem).withCoordinates(x, y - 10))
                .moveTo(new ElementOption().withElement(elem).withCoordinates(x, y -  100));

        AndroidTouchAction  finger2 = new AndroidTouchAction (mobileDriver);
        finger2.press(new ElementOption()
                        .withElement(elem).withCoordinates(x, y + 10))
                .moveTo(new ElementOption().withElement(elem).withCoordinates(x, y + 100));

        MultiTouchAction action = new MultiTouchAction(mobileDriver);
        action.add(finger1).add(finger2).perform();
    }

    /*    ===================================     PINCH  =============================================== */

    @Step ("Pinch Element - ZOOM OUT")
    public static void pinch(AndroidElement elem) {
        wait.until(ExpectedConditions.visibilityOf(elem));

        int x = elem.getLocation().getX() + elem.getSize().getWidth() / 2;
        int y = elem.getLocation().getY() + elem.getSize().getHeight() / 2;
        AndroidTouchAction  finger1 = new AndroidTouchAction (mobileDriver);
        finger1.press(new ElementOption()
                        .withElement(elem).withCoordinates(x, y - 100))
                .moveTo(new ElementOption().withElement(elem).withCoordinates(x, y -  10));

        AndroidTouchAction  finger2 = new AndroidTouchAction (mobileDriver);
        finger2.press(new ElementOption()
                        .withElement(elem).withCoordinates(x, y + 100))
                .moveTo(new ElementOption().withElement(elem).withCoordinates(x, y + 10));

        MultiTouchAction  action = new MultiTouchAction (mobileDriver);
        action.add(finger1).add(finger2).perform();
    }


    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

}
