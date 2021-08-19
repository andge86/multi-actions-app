package stepDefinitions;

import enums.ButtonColor;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pageObjects.*;
import utils.BaseUtil;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StepDefinitions extends BaseUtil {

    private final BaseUtil base;

    public StepDefinitions(BaseUtil base) {
        this.base = base;
    }

    private DashboardPage dashboardPage;
    private ActionPopUp actionPopUp;
    private VibrationStrengthPopUp vibrationStrengthPopUp;
    private ButtonColorPopUp buttonColorPopUp;
    private AndroidDriver<AndroidElement> driver;


    @Given("I open the App first time")
    public void openTheAppFirstTime() {
        driver = base.driver;
   //     driver.launchApp();
        dashboardPage = new DashboardPage(driver);
    }

    @Given("I install new app")
    public void installTheApp() throws Exception {
        // checks if it is first test and not re-installs the app because the new app was already installed by @Before
        // and still was not launched

        // Reinstalling the app to have fresh newly installed version
        if (!Hook.IS_FIRST_TEST) {
            Utils.uninstallApp();
            Utils.installApp();
        }

    }

    @Given("I open the App")
    public void openTheApp() throws InterruptedException {
        driver = base.driver;
        dashboardPage = new DashboardPage(driver);
        if (Hook.IS_FIRST_TEST) addPermissions(); // to handle first app launch by adding permissions
    }

    @When("I add permissions")
    public void addPermissions() throws InterruptedException {
        dashboardPage.addAppPermissions();
    }

    @And("I close tutorial screen by clicking on it")
    public void closeTutorial() {
        dashboardPage.clickOnTutorialToSkip();
    }

    @And("I close rate pop up if present")
    public void closeStartInstruction() throws InterruptedException {
        if (dashboardPage.isRatePopUpPresent()) dashboardPage.clickOnCancelRatePopUpButton();
    }

    @Then("Dashboard page is opened")
    public void verifyThatDashboardIsOpened() {
        Assert.assertEquals("Top App toolbar is not present, seems the app is not opened",
                true, dashboardPage.isTopMenuToolbarPresent());
    }

    @And("I click on Action on Click tab")
    public ActionPopUp clickOnActionsOnClickTab() throws InterruptedException {
        actionPopUp = dashboardPage.clickOnActionsOnClickTab();
        return new ActionPopUp(driver);
    }

    @And("I click on Vibration strength tab")
    public VibrationStrengthPopUp clickOnVibrationStrengthTab() throws InterruptedException {
        vibrationStrengthPopUp = dashboardPage.clickOnVibrationStrengthTab();
        return new VibrationStrengthPopUp(driver);
    }

    @Then("All Actions should be shown")
    public void getActionsList() {
        final List<String> ALL_ACTIONS_LIST =
                Arrays.asList("None", "Home", "Recent applications", "Pull down notification", "Device settings",
                        "Selected application", "[Experimental] Back", "[PRO] Screenshot", "[PRO] Lock the screen",
                        "[PRO] Quick settings", "[PRO] Power menu", "[PRO] Split screen", "[PRO] Open Google Assistant",
                        "[PRO] Start multi task 2 times (open last app on some devices)");

        List<String> actions = actionPopUp.getListOfActionsText();
        List<String> allActionsList1 = new ArrayList<>(ALL_ACTIONS_LIST);
        List<String> allActionsList2 = new ArrayList<>(ALL_ACTIONS_LIST);

        allActionsList1.removeAll(actions);
        Assert.assertEquals("Some actions are not shown in the app:" + allActionsList1, 0, allActionsList1.size());
        actions.removeAll(allActionsList2);
        Assert.assertEquals("Some more actions are shown in the app:" + actions, 0, actions.size());

    }

    @And("I put vibration strength to 100")
    public DashboardPage putVibrationStrengthTo100() throws InterruptedException {
        vibrationStrengthPopUp
                .put100Percents()
                .clickOkButton();
        return new DashboardPage(driver);
    }

    @Then("Vibration strength should be 100 in the Dashboard")
    public void vibrationStrengthShouldBeInTheDashboard() {
        Assert.assertEquals("Vibration strength is not 100 in the Dashboard",
                "100", dashboardPage.getVibrationStrengthText());
    }

    @When("I click on Button color tab")
    public ButtonColorPopUp iClickOnButtonColorTab() throws InterruptedException {
        buttonColorPopUp = dashboardPage.clickOnButtonColorTab();
        return new ButtonColorPopUp(driver);
    }

    @And("I click on {string} circle")
    public DashboardPage iClickOnColor(String color) throws InterruptedException {
        buttonColorPopUp.clickONColorButton(ButtonColor.valueOf(color.toUpperCase()));
        return new DashboardPage(driver);
    }

    @Then("Color button should be {string} in the Dashboard")
    public void colorButtonShouldBeVioletInTheDashboard(String color) throws IOException {
        Assert.assertEquals("Color of the circle at Dashboard is not clicked one",
                ButtonColor.valueOf(color.toUpperCase()), dashboardPage.getButtonColor());
    }

    @When("I click on Show Notification checkbox")
    public DashboardPage iClickOnShowNotificationCheckbox() {
        dashboardPage.checkNotificationCheckbox();
        return new DashboardPage(driver);
    }

    @Then("Show Notification checkbox should be unchecked")
    public void showNotificationCheckboxShouldBeUnchecked() {
        Assert.assertEquals("Notification checkbox is checked", false, dashboardPage.isNotificationCheckboxChecked());

    }

}
