package pageObjects;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ActionPopUp extends BasePage {

    private AndroidDriver<AndroidElement> driver;

    public ActionPopUp(AndroidDriver<AndroidElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }


    @AndroidFindBy(xpath = "//*[@resource-id = 'android:id/text1']")
    private List<AndroidElement> listOfActions;


    // Simple code listOfActions.stream().map(RemoteWebElement::getText).collect(Collectors.toList()); is not working
    // because the last element is not visible
    public List<String> getListOfActionsText() {
        List<String> actionNames = new ArrayList<>();
        AndroidElement firstAction = listOfActions.get(0);
        actionNames.add(firstAction.getText());
        actionNames.addAll(getNextActionText(firstAction));
        System.out.println(actionNames);
        return actionNames;
    }

    private List<String> getNextActionText(AndroidElement previousAction) {
        List<String> actions = new ArrayList<>();
        String previousActionText = previousAction.getText();
        String nextActionXpath = "//*[@text='" + previousActionText + "']//following-sibling::*[1]";
        AndroidElement prevAction = previousAction;
        TouchAction action = new TouchAction(driver);

        while (driver.findElementsByXPath(nextActionXpath).size() == 1) {
            AndroidElement nextAction = driver.findElementByXPath(nextActionXpath);
            String nextActionText = nextAction.getText();
            actions.add(nextActionText);

            action.
                    press(PointOption.point(nextAction.getCenter()))
                    .moveTo(PointOption.point(prevAction.getCenter()))
                    .release()
                    .perform();

            prevAction = nextAction;
            nextActionXpath = "//*[@text='" + nextActionText + "']//following-sibling::*[1]";
        }

        return actions;
    }

  //  public List<String> getListOfActionsText() {
  //      return listOfActions.stream().map(RemoteWebElement::getText).collect(Collectors.toList());
  //  }

}
