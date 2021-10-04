package shadiHomePage;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class HomePage {

	    String CSV_PATH = "/Users/reenasequeira/Desktop/Selenium/Shadi/src/test/resources/TestdataRes/DataTest.csv";
	    static WebDriver driver;
	    static WebDriverWait wait;
	    String[] csvCell;
	    
	    @BeforeTest
	    public void setup() throws Exception {
	 
	       	System.setProperty("webdriver.chrome.driver", "/Users/reenasequeira/Desktop/Selenium/Shadi/Driver/chromedriver");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver,30);
			Reporter.log("Chrome browser is opened");
	    }
	    
	    
	    @Test
	    public void dataRead_CSV() throws IOException, CsvException {
	    	CSVReader csvReader = new CSVReader(new FileReader(CSV_PATH));

	        while ((csvCell = csvReader.readNext()) != null) {
	        	int i =0;
		            String websitURL = csvCell[i];
		            String CustomerEmail = csvCell[i+1];
		            String CustomerPassword = csvCell[i+2];
		            String CommunityName = csvCell[i+3];
			        driver.get(websitURL);
					driver.findElement(By.xpath("//button[@class=\"btn-primary form-control\"]")).click();
					driver.findElement(By.xpath("//input[@data-testid=\"email\"]")).sendKeys(CustomerEmail);
					driver.findElement(By.xpath("//input[@data-testid=\"password1\"]")).sendKeys(CustomerPassword);
					driver.findElement(By.xpath("//div[@class=\"Dropdown-placeholder\"]")).click();
					driver.findElement(By.xpath("//div[@class=\"Dropdown-menu postedby_options\"]/div[text()=\"Self\"]")).click();
					driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
					driver.findElement(By.id("gender_female")).click();
					driver.findElement(By.xpath("//button[@data-testid=\"next_button\"]")).click();
					driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
					
					//Verification of Community and Mother tongue
					if(websitURL.contains("marathishaadi")) {
						String Marathi = driver.findElement(By.xpath("//div[text()='Marathi']")).getText();
						Assert.assertEquals(CommunityName, Marathi);
						Reporter.log("Community Name and mother tongue are same for Marathi");
					}else {
						String Gujarati = driver.findElement(By.xpath("//div[text()='Gujarati']")).getText();
						Assert.assertEquals(CommunityName, Gujarati);
						Reporter.log("Community Name and mother tongue are same for Gujarati");
					}
	        	}
	        }
	    
	    @AfterTest
	    public void exit() {
	        driver.close();
	        driver.quit();
	    }
	  

}
