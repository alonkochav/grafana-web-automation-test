package pageObjects.calculator;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MainPage {

        @FindBy(how = How.NAME, using = "Clear")
        public WebElement btn_clear;

        @FindBy(how = How.NAME, using = "Zero")
        public WebElement btn_zero;

        @FindBy(how = How.NAME, using = "One")
        public WebElement btn_one;

        @FindBy(how = How.NAME, using = "Two")
        public WebElement btn_two;

        @FindBy(how = How.NAME, using = "Three")
        public WebElement btn_three;

        @FindBy(how = How.NAME, using = "Four")
        public WebElement btn_four;

        @FindBy(how = How.NAME, using = "Five")
        public WebElement btn_five;

        @FindBy(how = How.NAME, using = "Six")
        public WebElement btn_six;

        @FindBy(how = How.NAME, using = "Seven")
        public WebElement btn_seven;

        @FindBy(how = How.NAME, using = "Eight")
        public WebElement btn_eight;

        @FindBy(how = How.NAME, using = "Nine")
        public WebElement btn_nine;

        @FindBy(how = How.NAME, using = "Decimal Separator")
        public WebElement btn_point;

        @FindBy(how = How.NAME, using = "Positive Negative")
        public WebElement btn_polarity;


        // operations

        @FindBy(how = How.NAME, using = "Equals")
        public WebElement btn_equals;

        @FindBy(how = How.NAME, using = "Plus")
        public WebElement btn_plus;

        @FindBy(how = How.NAME, using = "Minus")
        public WebElement btn_minus;

        @FindBy(how = How.NAME, using = "Multiply by")
        public WebElement btn_multiply;

        @FindBy(how = How.NAME, using = "Divide by")
        public WebElement btn_divide;

        @FindBy(how = How.NAME, using = "Square")
        public WebElement btn_square;

        @FindBy(how = How.NAME, using = "Square Root")
        public WebElement btn_sqrt;

        @FindBy(how = How.NAME, using = "Percent")
        public WebElement btn_percent;

        @FindBy(how = How.XPATH, using = "//*[@AutomationId= 'CalculatorResults']")
        public WebElement field_result;

        // operations

        @FindBy(how = How.NAME, using = "Backspace")
        public WebElement btn_bkspc;




}