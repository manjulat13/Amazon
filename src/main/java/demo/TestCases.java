package demo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;


public class TestCases {
    ChromeDriver driver;
    public TestCases()
    {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

    }

    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    
    public  void testCase01(){
        System.out.println("Start Test case: testCase01");
        driver.get("https://www.makemytrip.com/");
        String URL=driver.getCurrentUrl();
        if(URL.contains("makemytrip."))
        {
            System.out.println("The URL of the Make My Trip homepage contains makemytrip.");
        }else{
            System.out.println("Url doesnt contains makemytrip");
        }
        System.out.println("end Test case: testCase02");
    }
    public void testCase02() throws InterruptedException{
        driver.get("https://www.makemytrip.com/");
        Thread.sleep(3000);

        //Select Bengalore as depature location
        driver.findElement(By.id("fromCity")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@class='autoSuggestPlugin hsw_autocomplePopup']/div/input")).sendKeys("blr");
        List<WebElement> departureOption=driver.findElements(By.xpath("//p[@class='font14 appendBottom5 blackText']"));
        for(int i=0;i<departureOption.size();i++){
            String textDep=departureOption.get(i).getText();
            if(textDep.contains("Bengaluru")){
                departureOption.get(i).click();
                break;
            }
        }
        
        //Select Delhi as arrival Location
        driver.findElement(By.id("toCity")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@class='autoSuggestPlugin hsw_autocomplePopup makeFlex column spaceBetween']/div/input")).sendKeys("del");
        List<WebElement> arrivalOption=driver.findElements(By.xpath("//p[@class='font14 appendBottom5 blackText']"));
        for(int i=0;i<arrivalOption.size();i++){
            String textArr=arrivalOption.get(i).getText();
            if(textArr.contains("New Delhi")){
                arrivalOption.get(i).click();
                break;
            }
        }
       
        //Select next month 29th date
        
        WebElement dateInput = driver.findElement(By.xpath("//span[contains(text(),'Departure')]"));
        dateInput.click();
        Thread.sleep(3000);
        WebElement dateElement = driver.findElement(By.xpath("//div[@class='DayPicker-Months']/div[2]//p[contains(text(),'29')]"));
        dateElement.click();
        //Search for Flights
        WebElement search=driver.findElement(By.xpath("//a[contains(text(),'Search')]"));
        search.click();
        Thread.sleep(10000);
       
        //Store Flight Price
        List<WebElement> perAdultPPrice=driver.findElements(By.xpath("//div[@class='textRight flexOne']"));
        for(int i=0;i<perAdultPPrice.size();i++){
            String flightPrice=perAdultPPrice.get(i).getText();
            System.out.println("Flight Price: ["+flightPrice+"]");
        }
       
    }
    public void testCase03() throws InterruptedException{
        
        driver.get("https://www.makemytrip.com/");
        Thread.sleep(3000);
        //Select YPR(Bangalore) as the departure location for the train.
        WebElement train=driver.findElement(By.xpath("//li[@data-cy='menu_Trains']//a"));
        train.click();
        WebElement depaturLoc=driver.findElement(By.id("fromCity"));
        depaturLoc.click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@class='autoSuggestPlugin hsw_autocomplePopup']/div/input")).sendKeys("ypr");
        List<WebElement> departureOption=driver.findElements(By.xpath("//span[@class='sr_city blackText']"));
        for(int i=0;i<departureOption.size();i++){
            String textDep=departureOption.get(i).getText();
            if(textDep.contains("Bangalore")){
                departureOption.get(i).click();
                break;
            }
        }
        
      // Select NDLS(New Delhi) as the arrival location for the train.
        WebElement toTrain=driver.findElement(By.id("toCity"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        
       executor.executeScript("arguments[0].click();", toTrain);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@class='autoSuggestPlugin hsw_autocomplePopup']/div/input")).sendKeys("ndls");
       
        List<WebElement> arrivalOption=driver.findElements(By.xpath("//span[@class='sr_city blackText']"));
        for(int i=0;i<arrivalOption.size();i++){
            String textArr=arrivalOption.get(i).getText();
            if(textArr.contains("Delhi")){
                arrivalOption.get(i).click();
                break;
            }
        }
        
        // Select the correct date (29th of next Month) for the train.
        WebElement travelDate=driver.findElement(By.id("travelDate"));
        travelDate.click();
        WebElement dateElement = driver.findElement(By.xpath("//div[@class='DayPicker-Months']//div[2]//div[contains(text(),'29')]"));
        dateElement.click();
        
        // Select the class as 3AC
        WebElement classType=driver.findElement(By.xpath("//span[@data-cy='class']"));
        classType.click();
        
        WebElement thirdAC=driver.findElement(By.xpath("//ul[@class='travelForPopup']/li[@data-cy='3A']"));
        thirdAC.click();

        // Click on the search button for the train.
        WebElement search=driver.findElement(By.xpath("//a[@data-cy='submit']"));
        search.click();
        Thread.sleep(5000);

        // Store the train price for 3AC.
        List<WebElement> thirdACPrice=driver.findElements(By.xpath("//div[contains(text(),'3A')]/../following-sibling::div"));
        for(int i=0;i<thirdACPrice.size();i++){
        
        String price=thirdACPrice.get(i).getText();
        System.out.println("Train Price: ["+price+"]");
        }

    }
    public void testcase04() throws InterruptedException{
        driver.get("https://www.makemytrip.com/");
        Thread.sleep(3000);

        //Select Bangalore(search for bangl) as the departure location for buses.
        WebElement bus=driver.findElement(By.xpath("//li[@class='menu_Buses']//a"));
        bus.click();
        WebElement depaturLoc=driver.findElement(By.id("fromCity"));
        depaturLoc.click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@class='autoSuggestPlugin hsw_autocomplePopup']/div/input")).sendKeys("bangl");
        List<WebElement> departureOption=driver.findElements(By.xpath("//p[@class='searchedResult font14 darkText']"));
        for(int i=0;i<departureOption.size();i++){
            String textDep=departureOption.get(i).getText();
            if(textDep.contains("Bangalore")){
                departureOption.get(i).click();
                break;
            }
        }

        // Select New Delhi(search for del) as the arrival location for buses.
        WebElement arrLoc=driver.findElement(By.id("toCity"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", arrLoc);
        
        driver.findElement(By.xpath("//div[@class='autoSuggestPlugin hsw_autocomplePopup']/div/input")).sendKeys("del");
       
        List<WebElement> arrivalOption=driver.findElements(By.xpath("//p[@class='searchedResult font14 darkText']"));
        for(int i=0;i<arrivalOption.size();i++){
            String textArr=arrivalOption.get(i).getText();
            if(textArr.contains("Delhi")){
                arrivalOption.get(i).click();
                break;
            }
        }
        

        // Select the correct date (29th of next Month) for buses.
        WebElement travelDate=driver.findElement(By.id("travelDate"));
        executor.executeScript("arguments[0].click();", travelDate);
        WebElement dateElement = driver.findElement(By.xpath("//div[@class='DayPicker-Months']//div[2]//div[contains(text(),'29')]"));
        dateElement.click();
        
        
        // Click on the search button for buses.
        WebElement search=driver.findElement(By.id("search_button"));
        search.click();
        Thread.sleep(5000);
        // Verify that text displayed is equal to No buses found for 29 Mar.
        WebElement text=driver.findElement(By.xpath("//span[@class='error-title']"));
        String errorTitle=text.getText();
        if(errorTitle.equalsIgnoreCase("No buses found for 29 Mar")){
            System.out.println(errorTitle);
        }
    }


}
