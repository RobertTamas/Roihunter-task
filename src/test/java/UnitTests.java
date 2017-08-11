import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.googlePages.GoogleResultPage;
import pages.googlePages.GoogleSearchPage;
import pages.tmobilePages.TMobileFormPage;
import pages.tmobilePages.TMobilePage;
import pages.tmobilePages.TMobileSupportPage;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by Robert on 08/08/2017.
 */
public class UnitTests {
    private ChromeDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
    }

    @Test(description = "opens the google page and fill input field")
    public void goToGoogle() {
        //Arrange
        GoogleSearchPage page = new GoogleSearchPage(driver);
        String expectedURL = "https://www.google.cz/";
        String expectedInputValue = "Skuska";
        String actualInputValue;

        //Act
        page.goTo();
        page.getSearchBox().sendKeys(expectedInputValue);
        actualInputValue = page.getSearchBox().getAttribute("value");

        //Assert
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
        Assert.assertEquals(actualInputValue, expectedInputValue);
    }

    @Test(description = "searches the google")
    public void googleSearch() {
        //Arrange
        GoogleSearchPage page = new GoogleSearchPage(driver);

        //Act
        page.goTo();
        page.googleSearch("skuska");

        //Assert
        Assert.assertTrue(driver.getTitle().matches("skuska.*"));
    }

    @Test(description = "selects wiki page from results")
    public void searchTextSelectWikipediaLink(){
        //Arrange
        GoogleResultPage page = new GoogleResultPage(driver);

        //Act
        page.goTo("skuska");
        page.selectCorrectResult(".*wiki.*");

        //Assert
        Assert.assertTrue(driver.getTitle().matches(".*Wiki.*"));
    }

    @Test(description = "navigates through menu from main page to support page")
    public void navigateThroughTMobileMenu() {
        //Arrange
        TMobilePage page = new TMobilePage(driver);
        String expectedURL = "https://www.t-mobile.cz/podpora/obratte-se-na-nas";
        String actualURL;

        //Act
        page.goToDirect();
        page.closePopUp();
        page.goThroughMenu();
        actualURL = driver.getCurrentUrl();

        //Assert
        Assert.assertEquals(actualURL, expectedURL);
    }

    @Test(description = "navigates from support page to contact form")
    public void navigateThroughSupportPage() {
        //Arrange
        TMobileSupportPage page = new TMobileSupportPage(driver);
        String expectedURL = "https://www.t-mobile.cz/podpora/kontaktujte-nas";
        String actualURL;

        //Act
        page.goToDirect();
        page.getContactFormButton().click();
        actualURL = driver.getCurrentUrl();

        //Assert
        Assert.assertEquals(actualURL, expectedURL);
    }

    @Test(description = "fills text inputs in form")
    public void fillFormsTextFields() {
        //Arrange
        TMobileFormPage page = new TMobileFormPage(driver);
        page.goToDirect();
        String expectedSubject = "SubjectTest";
        String actualSubject;
        String expectedContent = "ContentTest";
        String actualContent;
        String expectedPhoneNumber = "987654321";
        String actualPhoneNumber;
        String expectedEmail = "test@test.com";
        String actualEmail;
        Boolean checkboxValue = false;
        String filepath = null;

        //Act
        page.fillElements(expectedSubject, expectedContent, expectedPhoneNumber,
                expectedEmail, checkboxValue, filepath);
        actualSubject = page.getSubject().getAttribute("value");
        actualContent = page.getContent().getAttribute("value");
        actualPhoneNumber = page.getPhoneNumber().getAttribute("value");
        actualEmail = page.getEmail().getAttribute("value");

        //Assert
        Assert.assertEquals(actualSubject, expectedSubject);
        Assert.assertEquals(actualContent, expectedContent);
        Assert.assertEquals(actualPhoneNumber, expectedPhoneNumber);
        Assert.assertEquals(actualEmail, expectedEmail);
    }

    @Test(description = "test all possible conditions of checkbox:" +
            "unchecked -> checked, checked -> checked, checked -> unchecked," +
            "unchecked -> unchecked")
    public void checkboxTest() {
        //Arrange
        TMobileFormPage page = new TMobileFormPage(driver);
        page.goToDirect();
        String expectedUncheckedToCheckedClassName;
        String expectedCheckedToCheckedClassName;
        String expectedCheckedToUncheckedClassName;
        String expectedUncheckedToUncheckedClassName;

        //Act
        page.setCheckboxValue(true);
        expectedUncheckedToCheckedClassName = page.getCheckbox().getAttribute("class");
        page.setCheckboxValue(true);
        expectedCheckedToCheckedClassName = page.getCheckbox().getAttribute("class");
        page.setCheckboxValue(false);
        expectedCheckedToUncheckedClassName = page.getCheckbox().getAttribute("class");
        page.setCheckboxValue(false);
        expectedUncheckedToUncheckedClassName = page.getCheckbox().getAttribute("class");

        //Assert
        Assert.assertTrue(expectedUncheckedToCheckedClassName.contains("active"));
        Assert.assertTrue(expectedCheckedToCheckedClassName.contains("active"));
        Assert.assertFalse(expectedCheckedToUncheckedClassName.contains("active"));
        Assert.assertFalse(expectedUncheckedToUncheckedClassName.contains("active"));
    }

    @Test(description = "attach file to form")
    public void attachFile() {
        //Arrange
        TMobileFormPage page = new TMobileFormPage(driver);
        page.goToDirect();
        String filePath = "src\\main\\resources\\image.jpg";
        String expectedFileName = "image.jpg";
        String actualFileName;

        //Act
        page.dropFile(new File(filePath), page.getDropAreaForAttachments(), 0, 0);
        actualFileName = driver.findElement(
                By.xpath("//div[@class=\"upload-list\"]//li"))
                .getAttribute("data-filename");

        //Assert
        Assert.assertEquals(expectedFileName, actualFileName);
    }

    @Test(expectedExceptions = WebDriverException.class,
            description = "attach nonexisting file")
    public void attachMissingFile() {
        //Arrange
        TMobileFormPage page = new TMobileFormPage(driver);
        page.goToDirect();
        String filePath = "nonexisting\\path\\to\\something.extension";

        //Act
        page.dropFile(new File(filePath), page.getDropAreaForAttachments(), 0, 0);
    }

    @Test(description = "sends filled form")
    public void sendForm() {
        //Arrange
        TMobileFormPage page = new TMobileFormPage(driver);
        page.goToDirect();
        String subject = "SubjectTest";
        String content = "ContentTest";
        String phoneNumber = "987654321";
        String email = "test@test.com";
        Boolean checkboxValue = false;
        String filepath = null;

        //Act
        page.fillElements(subject, content, phoneNumber,
                email, checkboxValue, filepath);
        page.getSendButton().click();

        //Assert
        Assert.assertTrue(page.isMessageDisplayed());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
