package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

/**
 * Appium Driver Manager to initialize appium driver
 *
 * @author Somu
 * @since 14 Mar, 2025
 */

public class AppiumDriverManager {

    private AppiumDriver driver;

    public AppiumDriver getDriver() {
        return driver;
    }

    public void initializeDriver() throws Exception {
        // URL to the zipped app file in the GitHub release
        String appZipUrl = "https://github.com/saucelabs/my-demo-app-rn/releases/download/v1.3.0/iOS-Simulator-MyRNDemoApp.1.3.0-162.zip";

        // Local directory to save the downloaded and extracted files
        File appDir = new File("src/test/resources/apps");
        if (!appDir.exists()) {
            appDir.mkdirs(); // Create the directory if it doesn't exist
        }

        // Download the zip file
        File zipFile = FileDownloader.downloadFile(appZipUrl, appDir, "MyRNDemoApp.app.zip");

        // Extract the zip file
        System.out.println("Extracting zip file...");
        File appFile = ZipExtractor.extractZipFile(zipFile, appDir);
        System.out.println("App directory extracted successfully: " + appFile.getAbsolutePath());

        // Clean up the zip file
        System.out.println("Deleting zip file...");
        zipFile.delete();

        // Set up Appium capabilities
        DesiredCapabilities capabilities = AppiumCapabilities.getIOSCapabilities(appFile.getAbsolutePath());

        // Initialize the Appium driver
        URL url = new URL("http://localhost:4723");
        driver = new IOSDriver(url, capabilities);
        System.out.println("Appium driver initialized successfully.");
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void main(String[] args) {
        AppiumDriverManager manager = new AppiumDriverManager();
        try {
            // Initialize the driver (download and extract the app file)
            manager.initializeDriver();

            // Verify the driver is initialized
            if (manager.getDriver() != null) {
                System.out.println("Driver initialization successful!");
            } else {
                System.out.println("Driver initialization failed.");
            }

            // Add a small delay to keep the driver session active for a few seconds
            Thread.sleep(5000); // 5 seconds delay
        } catch (Exception e) {
            System.err.println("Error during initialization: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Tear down the driver
            manager.tearDown();
        }
    }
}
