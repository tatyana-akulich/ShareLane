package signup.registration;

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

public class RegistrationFormTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @DataProvider
    public Object[][] registrationDataAndValidationResult() {
        return new Object[][]{
                {new RegistrationForm("Tatyana", "123@tmmcv.com", "12345"), true}, //tests for First Name
                {new RegistrationForm("Tatyana1", "124@tmmcv.com", "12345"), false},
                {new RegistrationForm("", "125@tmmcv.com", "12345"), false},
                {new RegistrationForm("Ab Vd", "126@tmmcv.com", "12345"), true},
                {new RegistrationForm("A", "127@tmmcv.com", "12345"), true},
                {new RegistrationForm(" ", "128@tmmcv.com", "12345"), false}, //fails - space as a name is a bug
                {new RegistrationForm(" Ann", "129@tmmcv.com", "12345"), false}, //fails - name starting from whitespace is a bug
                {new RegistrationForm("***", "1210@tmmcv.com", "12345"), false},
                {new RegistrationForm("t_a", "1211@tmmcv.com", "12345"), false},
                {new RegistrationForm("Tatyana", "1212@tmmcv.com", "12345"), true}, // tests for email
                {new RegistrationForm("Tatyana", "1212@tmmcv.com", "12345"), false}, //fails - previously used email
                {new RegistrationForm("Tatyana", "1213tmmcv.com", "12345"), false},
                {new RegistrationForm("Tatyana", "1214@tmmcv", "12345"), false},
                {new RegistrationForm("Tatyana", "1215@tmmcv.", "12345"), false}, //fails - email without top level domain
                {new RegistrationForm("Tatyana", "1216 @tmmcv.com", "12345"), false}, //fails - email with whitespace
                {new RegistrationForm("Tatyana", "", "12345"), false},
                {new RegistrationForm("Tatyana", " ", "12345"), false},
                {new RegistrationForm("Tatyana", " 1217@tmmcv.com", "12345"), false}, // fails - email starts with whitespace
                {new RegistrationForm("Tatyana", "1218@tmmcv.com", "1"), false}, //tests for password
                {new RegistrationForm("Tatyana", "1219@tmmcv.com", "1234"), true},
                {new RegistrationForm("Tatyana", "1220@tmmcv.com", "bbbbb"), true},
                {new RegistrationForm("Tatyana", "1221@tmmcv.com", ""), false},
                {new RegistrationForm("Tatyana", "1222@tmmcv.com", "\"asd\""), true},
                {new RegistrationForm("Tatyana", "1223@tmmcv.com", "    "), true}, // any symbols in password are accepted
                {new RegistrationForm("Tatyana", "1224@tmmcv.com", "абвгд"), true},
                {new RegistrationForm("Tatyana", "1225@tmmcv.com", "1234а"), true},
                {new RegistrationForm("Tatyana", "1226@tmmcv.com", "aaaa*"), true},
                {new RegistrationForm("Tatyana", "1227@tmmcv.com", "12345", "1234"), false}, //test for confirmation - fails
        };
    }

    @Test(dataProvider = "registrationDataAndValidationResult")
    public void testRegistrationForm(RegistrationForm registrationFormData, boolean validationResult) {
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345");
        WebElement firstNameInput = driver.findElement(By.name("first_name"));
        firstNameInput.sendKeys(registrationFormData.getFirstName());
        WebElement emailInput = driver.findElement(By.name("email"));
        emailInput.sendKeys(registrationFormData.getEmail());
        WebElement passwordInput = driver.findElement(By.name("password1"));
        passwordInput.sendKeys(registrationFormData.getPassword());
        WebElement passwordConfirmationInput = driver.findElement(By.name("password2"));
        passwordConfirmationInput.sendKeys(registrationFormData.getPasswordConfirmation());
        driver.findElement(By.cssSelector("input[value='Register']")).click();
        Assert.assertEquals(UtilMethods.isElementDisplayed(driver, By.className("confirmation_message")), validationResult,
                "Input data is invalid");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

