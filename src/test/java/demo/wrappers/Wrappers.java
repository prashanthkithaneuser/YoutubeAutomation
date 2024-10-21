package demo.wrappers;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

     public static void clickOnElementWrapper(WebDriver driver, By locator) {
        System.out.println("Clicking on Element");
        Boolean success;
        try {
            // Locate the element first
            WebElement element = driver.findElement(locator);
    
            // Use JavaScript to scroll the element into view
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            System.out.println("Scrolled to the element successfully!");
    
            // Wait until the element is visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    
            // Click the element
            Thread.sleep(3000); // Optional delay before clicking
            element.click();
            Thread.sleep(3000); // Optional delay after clicking
            System.out.println("Successfully clicked on element.");
            success = true;
    
        } catch (Exception e) {
            System.out.println("Exception Occurred! " + e.getMessage());
            success = false;
        }
    }
    

    public static void clickOnRightScrollOnElementUntillLast(WebDriver driver, By locator) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement rightScrollButton = driver.findElement(locator);
        System.out.println("Trying to scroll to the right:");
    
        while (true) {
            try {
                // Scroll into view to make sure the button is visible
                js.executeScript("arguments[0].scrollIntoView(true);", rightScrollButton);
                
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.elementToBeClickable(rightScrollButton));

                // Check if the button is enabled and clickable before clicking
                if (rightScrollButton.isEnabled() && rightScrollButton.isDisplayed()) {
                    // Click the right scroll button
                    rightScrollButton.click();
                    System.out.println("Clicked on the scroller.");
    
                    // Wait a little before the next click to avoid issues
                    Thread.sleep(1000);
                } else {
                    System.out.println("Reached the end of the scrollable area (button disabled or hidden).");
                    break; // Exit the loop if the button is no longer clickable
                }
    
                // Re-locate the button in case the DOM changes after scrolling
                rightScrollButton = driver.findElement(By.xpath("(//button[@aria-label='Next'])[1]"));
    
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                // Handle click interception, retry scrolling or adjust the interaction
                System.out.println("ElementClickInterceptedException occurred: " + e.getMessage());
                js.executeScript("window.scrollBy(0,0);"); // Slightly scroll the window to avoid interception
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                // Handle stale element, re-locate the button and continue
                System.out.println("StaleElementReferenceException occurred. Re-locating the button...");
                rightScrollButton = driver.findElement(By.xpath("(//button[@aria-label='Next'])[1]"));
            } catch (org.openqa.selenium.NoSuchElementException e) {
                // Exit if the button is no longer found (end of scrollable area)
                System.out.println("NoSuchElementException: Scroll completed, no further interaction possible.");
                break;
            } catch (Exception e) {
                // Catch any other unexpected exceptions
                System.out.println("Unexpected exception occurred: " + e.getMessage());
                break;
            }
        }
    }
    
    

    public static String extractElementText(WebDriver driver, By locator) {
        String elementText = "";
        try {
            // Locate the element using the provided locator
            WebElement element = driver.findElement(locator);
            Thread.sleep(2000);
            // Extract the text from the element
            elementText = element.getText().trim();
        } catch (Exception e) {
            System.out.println("An error occurred while getting the element's text: " + e.getMessage());
        }
        return elementText;
    }

    

     // Helper method to extract view count from string
     public static  long extractViewCount(String viewText) {
        long views = 0;
        try {
            // Example: "1.2M views", "534K views", "10K views"
            if (viewText.contains("M")) {
                views = (long) (Double.parseDouble(viewText.replace("M views", "")) * 1_000_000);
            } else if (viewText.contains("K")) {
                views = (long) (Double.parseDouble(viewText.replace("K views", "")) * 1_000);
            } else if (viewText.contains("views")) {
                views = Long.parseLong(viewText.replace(" views", "").replace(",", ""));
            }
        } catch (Exception e) {
            System.out.println("Failed to parse view count: " + e.getMessage());
        }
      
        return views;
    }



}
