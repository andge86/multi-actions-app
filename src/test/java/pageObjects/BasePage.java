package pageObjects;

import enums.ButtonColor;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import lombok.AllArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class BasePage {

    AndroidDriver<AndroidElement> driver;
    WebDriverWait wait;
    private static final int TIMEOUT = 10;
    private static final int POLLING = 100;

    public BasePage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT, POLLING);
    }

    protected void waitAndTap(AndroidElement androidElement){
    //    waitForBetterTestsVisualization();
        wait.until(ExpectedConditions.visibilityOf(androidElement)).click();
    }

    protected void clickTheElementEnd(AndroidElement androidElement) throws InterruptedException {
        Point location = androidElement.getCenter();
        int elementWidth = androidElement.getSize().getWidth();

        System.out.println(elementWidth);

        TouchAction action = new TouchAction(driver);
        action.longPress(PointOption.point(location.getX(), location.getY()))
                .moveTo(PointOption.point(location.getX() + elementWidth / 2, location.getY()))
                .release().perform();
    }


    protected ButtonColor getColorOfElement(AndroidElement elem) {
        org.openqa.selenium.Point point = elem.getCenter();
        int centerX = point.getX();
        int centerY = point.getY();
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage image = null;
        try {
            image = ImageIO.read(scrFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Getting pixel color by position x and y
        int clr = image.getRGB(centerX, centerY);
        int red = (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue = clr & 0x000000ff;
        return Arrays.stream(ButtonColor.values())
                .filter(color -> color.getRed() == red && color.getGreen() == green && color.getBlue() == blue)
                .findFirst()
                .map(color -> ButtonColor.valueOf(color.toString()))
                .orElse(ButtonColor.UNDEFINED);
    }

/*
    private void waitForBetterTestsVisualization() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
 */
}
