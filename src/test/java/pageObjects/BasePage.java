package pageObjects;

import enums.ButtonColor;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import lombok.AllArgsConstructor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@AllArgsConstructor
public class BasePage {

    AndroidDriver<AndroidElement> driver;

    protected void clickTheMiddleOfThePage() {
        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();
        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(height / 2, width / 2)).release().perform();
    }

    protected void clickTheElementEnd(AndroidElement androidElement) throws InterruptedException {
            Thread.sleep(500);
        Point location = androidElement.getCenter();
        int elementWidth = androidElement.getSize().getWidth();

        System.out.println(elementWidth);

        TouchAction action = new TouchAction(driver);
        action.longPress(PointOption.point(location.getX(), location.getY()))
                .moveTo(PointOption.point(location.getX() + elementWidth / 2, location.getY()))
                .release().perform();
            Thread.sleep(500);
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

}
