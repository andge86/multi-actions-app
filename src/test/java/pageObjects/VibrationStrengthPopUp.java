package pageObjects;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class VibrationStrengthPopUp extends BasePage {

    private AndroidDriver<AndroidElement> driver;

    public VibrationStrengthPopUp(AndroidDriver<AndroidElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }


    @AndroidFindBy(id = "com.home.button.bottom:id/seekbar")
    private AndroidElement vibrationStrengthTab;
    @AndroidFindBy(id = "com.home.button.bottom:id/ok_textview")
    private AndroidElement okButton;


    public VibrationStrengthPopUp put100Percents() throws InterruptedException {
        clickTheElementEnd(vibrationStrengthTab);
        return this;
    }

    public DashboardPage clickOkButton(){
        okButton.click();
        return new DashboardPage(driver);
    }

}
