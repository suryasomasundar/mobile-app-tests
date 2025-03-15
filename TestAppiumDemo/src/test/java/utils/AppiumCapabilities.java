package utils;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Setting Desired Capabilities for iOS platform
 *
 * @author Somu
 * @since 14 Mar, 2025
 */

public class AppiumCapabilities {

    public static DesiredCapabilities getIOSCapabilities(String appPath) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "18.3");
        capabilities.setCapability("deviceName", "iPhone 16");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("app", appPath);
        return capabilities;
    }
}
