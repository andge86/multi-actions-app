package pageObjects;

import enums.ButtonColor;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import stepDefinitions.Hook;

import java.time.Duration;
import java.util.List;

public class DashboardPage extends BasePage {


    private AndroidDriver<AndroidElement> driver;

    public DashboardPage(AndroidDriver<AndroidElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);

    }

    @AndroidFindBy(id = "toolbar")
    private AndroidElement topMenuToolbar;
    @AndroidFindBy(id = "android:id/button1")
    private AndroidElement openAppPermissionsButton;
    @AndroidFindBy(accessibility = "Multi-action Home Button")
    private AndroidElement appPermissionsSectionTab;
    @AndroidFindBy(id = "android:id/switch_widget")
    private AndroidElement permissionsToggle;
    @AndroidFindBy(id = "layout_tuto")
    private AndroidElement tutorialElement;
    @AndroidFindBy(id = "cancel_button")
    private List<AndroidElement> cancelRatePopUpButtons; // list is used to check if rate pop up is present or not
    @AndroidFindBy(id = "click_layout")
    private AndroidElement actionOnClickTab;
    @AndroidFindBy(id = "vibration_strength_subtitle_textview")
    private AndroidElement vibrationStrengthValue;
    @AndroidFindBy(id = "color_selected_imageview")
    private AndroidElement buttonColorImage;

    // find AndroidElement and scroll  by UIAutomator
    private AndroidElement getElementByUiAutomator(String id) {
        String script = "new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().resourceId(\"com.home.button.bottom:id/" + id + "\"));";
        return driver.findElementByAndroidUIAutomator(script);
    }


    public DashboardPage addAppPermissions() {
        waitAndTap(openAppPermissionsButton);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Hook.PLATFORM_VERSION.equals("11")) waitAndTap(appPermissionsSectionTab);
        waitAndTap(permissionsToggle);
        pressBackPhoneButton();
        if (Hook.PLATFORM_VERSION.equals("11")) pressBackPhoneButton();
        return this;
    }

    public void clickOnTutorialToSkip(){
        waitAndTap(tutorialElement);
    }

    public boolean isRatePopUpPresent() {
        return (cancelRatePopUpButtons.size() == 1);
    }

    public boolean isTopMenuToolbarPresent() {
        return (topMenuToolbar.isDisplayed());
    }

    public DashboardPage pressBackPhoneButton() {
        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        return this;
    }

    public ActionPopUp clickOnActionsOnClickTab() {
        waitAndTap(actionOnClickTab);
        return new ActionPopUp(driver);
    }

    public DashboardPage clickOnCancelRatePopUpButton() {
        cancelRatePopUpButtons.get(0).click();
        return this;
    }

    public VibrationStrengthPopUp clickOnVibrationStrengthTab() {
        getElementByUiAutomator("vibration_strength_layout").click();
        return new VibrationStrengthPopUp(driver);
    }

    public String getVibrationStrengthText() {
        return vibrationStrengthValue.getText();
    }

    public ButtonColorPopUp clickOnButtonColorTab() throws InterruptedException {
        getElementByUiAutomator("color_layout").click();
        return new ButtonColorPopUp(driver);
    }

    public ButtonColor getButtonColor() {
        return getColorOfElement(buttonColorImage);
    }

    public DashboardPage checkNotificationCheckbox() {
        getElementByUiAutomator("notification_checkbox").click();
        return this;
    }

    public Boolean isNotificationCheckboxChecked() {
        return Boolean.getBoolean(getElementByUiAutomator("notification_checkbox").getAttribute("checked"));
    }
}
