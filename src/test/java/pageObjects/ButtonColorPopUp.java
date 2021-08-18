package pageObjects;

import enums.ButtonColor;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class ButtonColorPopUp extends BasePage {

    private AndroidDriver<AndroidElement> driver;

    public ButtonColorPopUp(AndroidDriver<AndroidElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }


 //   @AndroidFindBy(xpath = "//*[@resource-id='com.home.button.bottom:id/palette']//android.widget.FrameLayout")
 //   private List<AndroidElement> colorCircles;


    public DashboardPage clickONColorButton(ButtonColor color) throws InterruptedException {
        Thread.sleep(500);

        // To avoid exception "Cached elements do not exist in DOM anymore"
        String xpath = "//*[@resource-id='com.home.button.bottom:id/palette']//android.widget.FrameLayout";
        for (int i = 1; i <= driver.findElementsByXPath(xpath).size(); i++) {
            AndroidElement circle = driver.findElementByXPath(xpath + "[" + i + "]");
            if (getColorOfElement(circle).equals(color)) circle.click();
        }
        Thread.sleep(500);
        return new DashboardPage(driver);
    }

}
