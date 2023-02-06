package signup.zipcode;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.UtilMethods;

public class ZipCodeTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @DataProvider
    public Object[][] zipCodesAndValidationResults() {

        return new Object[][]{
                {"12345", true},
                {"00000", true},
                {"1234", false},
                {"123456", false}, //fails
                {"", false},
                {" ", false},
                {"abc", false},
                {"рус", false},
                {"1234567890123456789012345678901234567890", false}, //fails
                {"12f34", false},
                {"12 34", false},
                {"1234 ", false},
                {"123456 ", false}};
    }

    @Test(dataProvider = "zipCodesAndValidationResults")
    public void testZipCode(String zipCode, boolean isValid) {
        driver.get("https://sharelane.com/cgi-bin/register.py");
        WebElement zipCodeInput = driver.findElement(By.name("zip_code"));
        zipCodeInput.sendKeys(String.valueOf(zipCode));
        driver.findElement(By.cssSelector("input[value='Continue']")).click();
        Assert.assertEquals(UtilMethods.isElementDisplayed(driver, By.cssSelector("input[value='Register']")), isValid,
                "Wrong reaction for this type of ZIP code");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
