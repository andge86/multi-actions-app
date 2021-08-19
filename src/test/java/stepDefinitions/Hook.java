package stepDefinitions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Hook extends BaseUtil {

    private final BaseUtil base;
    DesiredCapabilities capabilities;
    //  private AppiumDriverLocalService service;
    //  private String serviceUrl;
    public static final String APP_PACKAGE = "com.home.button.bottom";
    public static final String APP_REL_PATH = "Multi-action_Home_Button_base.apk";
    public static String PLATFORM_VERSION = "11";

    public Hook(BaseUtil base) {
        this.base = base;
    }

    @Before
    public void startTest() throws Exception {
        if (System.getProperty("PLATFORM_VERSION") != null) PLATFORM_VERSION = System.getProperty("PLATFORM_VERSION");
        if (!isAppInstalled()) installApp();
        if (capabilities == null) initCapabilities();
        //   if (service == null) startService();
        if (driver == null) initDriver();
        startVideoRecording();
    }

    @After
    public void closeDriver(Scenario scenario) throws IOException {
        addScreenshotIfTestFailed(scenario, true, true);
        stopAndAddVideoRecording(scenario, false, true);
        // driver.resetApp();
        // driver.removeApp("com.home.button.bottom");
        driver.quit();
        System.out.println("Driver is quit");
    }

    private void initCapabilities() throws MalformedURLException {
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        // capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        // capabilities.setCapability(MobileCapabilityType.APP, new File(APP_REL_PATH).getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.home.button.activity.MainActivity");
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
    }

    /*
    private void startService() {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
        serviceUrl = service.getUrl().toString();
        System.out.println("Appium Service Address: " + serviceUrl);
    }
     */

    private void initDriver() throws MalformedURLException {
        //  driver = new AndroidDriver<>(new URL(serviceUrl), capabilities);
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        base.driver = driver;
    }

    private void addScreenshotIfTestFailed(Scenario scenario, Boolean addToReport, Boolean writeToFile) throws IOException {
        if (scenario.isFailed()) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            // attach screenshot to Cucumber base Report
            if (addToReport) scenario.attach(screenshot, "image/png", timestamp);
            // add screenshot to target folder
            if (writeToFile) Files.write(Paths.get("target/" + scenario.getName() + ".png"), screenshot);
        }
    }

    private void startVideoRecording() {
        driver.startRecordingScreen(new AndroidStartScreenRecordingOptions());
    }

    private void stopAndAddVideoRecording(Scenario scenario, Boolean addToReport, Boolean writeToFile) throws IOException {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String base64String = driver.stopRecordingScreen();
        byte[] data = Base64.decodeBase64(base64String);
        // attach video to Cucumber base Report
        if (addToReport) scenario.attach(data, "video/mp4", timestamp);
        // add video to target folder
        if (writeToFile) Files.write(Paths.get("target/" + scenario.getName() + ".mp4"), data);
    }

    private void installApp() throws IOException, InterruptedException {
        System.out.println("Installing the app");
        ProcessBuilder pb = new ProcessBuilder("adb", "install", "-r", APP_REL_PATH);
        Process pc = pb.start();
        pc.waitFor();
    }

    private Boolean isAppInstalled() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("adb", "shell", "pm", "list", "packages", APP_PACKAGE);
        Process process = pb.start();
        process.waitFor();
        String line = "";
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {
                if (line.contains(APP_PACKAGE)) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}

