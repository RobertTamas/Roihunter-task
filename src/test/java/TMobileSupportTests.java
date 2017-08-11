import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.tmobilePages.TMobileFormPage;

import java.util.concurrent.TimeUnit;

/**
 * Created by Robert on 08/08/2017.
 */
public class TMobileSupportTests {

    private ChromeDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
    }

    @Test(description = "Searches the google, opens T-Mobile page and goes to" +
            "contact form page which is filled and send.")
    public void searchSiteAndFillFormTest() {
        //Arrange
        String subject = "Pokusný dotaz";
        String content = "Pokusný dotaz";
        String phoneNumber = "123456789";
        String email = "pokus@pokus.cz";
        Boolean checkbox = true;
        String filepath = "src\\main\\resources\\image.jpg";

        //Act
        TMobileFormPage formPage = new TMobileFormPage(driver);
        formPage.goTo();
        formPage.fillElements(subject, content, phoneNumber, email, checkbox, filepath);
        formPage.getSendButton().click();

        //Assert
        Assert.assertTrue(formPage.isMessageDisplayed());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
