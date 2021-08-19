package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

// Used to inject driver instance from Hook to StepDefinitions
public class BaseUtil {

    public AndroidDriver<AndroidElement> driver;

}
