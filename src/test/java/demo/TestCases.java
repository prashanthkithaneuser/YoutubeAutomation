package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest(alwaysRun = true)
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

       @Test(priority=1)
        public void testCase01() throws InterruptedException{
                try {
                        // Navigate to YouTube
                        driver.get("https://www.youtube.com");
            
                        // Get the current URL
                        String currentpageUrl = driver.getCurrentUrl();
            
                        // Assert the URL is correct
                        Assert.assertEquals(currentpageUrl, "https://www.youtube.com/", "Alert! You are not on Youtube page!");
            
                        System.out.println("You are on the Youtube page");
                } catch (Exception e) {
                        System.out.println("Test Failed: " + e.getMessage());
                } finally {
                        // Close the browser
                        //driver.quit();
                }
                //Thread.sleep(5000);
                Wrappers.clickOnElementWrapper(driver, By.xpath("//a[text()='About']")); 
                Thread.sleep(7000);
                WebElement abouttext=driver.findElement(By.xpath("//h1[@class='lb-font-display-1 lb-font-weight-700 lb-font-color-text-primary lb-font--no-crop']"));
                System.out.println(abouttext.getText());
        }

        @Test(priority=2)
        public void testCase02() throws InterruptedException{
                try {
                        // Navigate to YouTube
                        driver.get("https://www.youtube.com");
                        Thread.sleep(3000);
                       
                } catch (Exception e) {
                        System.out.println("Test Failed: " + e.getMessage());
                } 
                Wrappers.clickOnElementWrapper(driver, By.xpath("//a[@title='Movies']"));
                Thread.sleep(3000);
                Wrappers.clickOnRightScrollOnElementUntillLast(driver, By.xpath("(//button[@aria-label='Next'])[1]"));
                Thread.sleep(3000); 
                String certificationtext=Wrappers.extractElementText(driver, By.xpath("(//p[@class='style-scope ytd-badge-supported-renderer'])[last()]"));
                System.out.println("Movie Certificate Text: "+certificationtext);
                SoftAssert sassert=new SoftAssert();
                sassert.assertEquals(certificationtext,"U");
                String genretextfull=Wrappers.extractElementText(driver, By.xpath("(//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer'])[last()]"));
                System.out.println("Full Text: " + genretextfull);
                // Define the list of categories
                String[] categories = {"Indian cinema", "Comedy", "Action & adventure", "Animation"};

                // Flag to track if any category matches
                boolean isCategoryFound = false;

                for (String category : categories) {
                if (genretextfull.contains(category)) {
                isCategoryFound = true;  // Mark that we found a matching category
                System.out.println("Category found: " + category);
                break;  // Exit the loop since we found a match
                }
                }

                // Use soft assertion to check if any category was found
                if (!isCategoryFound) {
                 sassert.fail("None of the categories match the genre text: " + genretextfull);
                }
                sassert.assertAll();
                Thread.sleep(2000);

        }


       @Test(priority=3)
        public void testCase03() throws InterruptedException{
                try {
                        // Navigate to YouTube
                        driver.get("https://www.youtube.com");
                        Thread.sleep(3000);
                       
                } catch (Exception e) {
                        System.out.println("Test Failed: " + e.getMessage());
                } 
                Wrappers.clickOnElementWrapper(driver, By.xpath("//a[@title='Music']"));
                Thread.sleep(3000);
                Wrappers.clickOnRightScrollOnElementUntillLast(driver, By.xpath("(//button[@aria-label='Next'])[1]"));
                Thread.sleep(3000);
                String noofvideostext=Wrappers.extractElementText(driver, By.xpath("(//p[@id='video-count-text'])[11]"));
                System.out.println("Text of video count: "+noofvideostext);
                String intpart=noofvideostext.substring(0,2);
                System.out.println("Split video count: "+intpart);
                int videocount=Integer.parseInt(intpart);
                System.out.println("Video Count: "+videocount);
                SoftAssert s1=new SoftAssert();
                s1.assertTrue(videocount<=50,"Video count exceeds 50");
                s1.assertAll();
                Thread.sleep(2000);
        }

        @Test(priority=4)
        public void testCase04() throws InterruptedException{
                try {
                        // Navigate to YouTube
                        driver.get("https://www.youtube.com");
                        Thread.sleep(3000);
                       
                } catch (Exception e) {
                        System.out.println("Test Failed: " + e.getMessage());
                } 
                Wrappers.clickOnElementWrapper(driver, By.xpath("//a[@title='News']"));
                Thread.sleep(3000);
                //WebElement latestnewselem=driver.findElement(By.xpath("(//div[@id='contents'])[18]"));
                List<WebElement> lnews=driver.findElements(By.xpath("//div[@class='style-scope ytd-post-renderer' and @id='dismissible']"));
                int count=0;
                int likescount=0;
                for(WebElement elem:lnews){
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].scrollIntoView(true);", elem);
                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                        wait.until(ExpectedConditions.elementToBeClickable(elem));
                        String newstitle=elem.findElement(By.xpath(".//span[@class='style-scope ytd-post-renderer']")).getText();
                        System.out.println("Trending news title is: "+newstitle);
                        String newsdescription=elem.findElement(By.xpath(".//span[@dir='auto']")).getText();
                        //System.out.println("Trending news description is: "+newsdescription);
                        String likestext=elem.findElement(By.xpath(".//span[@id='vote-count-middle']")).getText();
                        System.out.println("Likes String: "+likestext);
                        int likes=Integer.parseInt(likestext.trim());
                        System.out.println("Likes count of news: "+likes);
                        likescount+=likes;

                        count++;
                        if(count>=3){
                                break;
                        }

                }
                System.out.println("Total Likes: "+likescount);
        }


        @Test(priority=5,dataProvider = "excelData", dataProviderClass = ExcelDataProvider.class)
        public void testCase05(String searchTerm) throws InterruptedException{
                try {
                        // Navigate to YouTube
                        driver.get("https://www.youtube.com");
                        Thread.sleep(3000);
                       
                } catch (Exception e) {
                        System.out.println("Test Failed: " + e.getMessage());
                } 
        WebElement searchBox = driver.findElement(By.name("search_query"));
        searchBox.sendKeys(searchTerm);
        searchBox.submit();

        Thread.sleep(2000);  // Wait for the results to load

        // Initialize view count sum
        long totalViews = 0;

        JavascriptExecutor js = (JavascriptExecutor) driver;
        while (totalViews < 100_000_000) {  // Stop when total views exceed 10 crore (100 million)
            // Find all video elements with view count
            List<WebElement> videoElements = driver.findElements(By.xpath("//span[contains(@class,'style-scope ytd-video-meta-block')]"));

            for (WebElement video : videoElements) {
                String viewText = video.getText();
                totalViews += Wrappers.extractViewCount(viewText);
                if (totalViews >= 100_000_000) {
                    break;
                }
            }

            // Scroll down to load more results
            js.executeScript("window.scrollBy(0,1000);");
            Thread.sleep(2000);  // Allow new results to load
        }
        SoftAssert  s1=new SoftAssert();
        // Assert the total views exceeded 10 crore
        s1.assertTrue(totalViews >= 100_000_000, "Total views for '" + searchTerm + "' did not reach 10 crore!");

        // Log the result
        System.out.println("Search term: " + searchTerm + ", Total views: " + totalViews);

        // Assert all
        s1.assertAll();


        }



       @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}