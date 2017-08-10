package pages.tmobilePages;

import scripts.Scripts;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AbstractUIPage;

import java.io.File;

/**
 * Created by Robert on 08/08/2017.
 */
public class TMobileFormPage extends AbstractUIPage {

    public WebElement subject;
    public WebElement content;
    public WebElement phoneNumber;
    public WebElement email;
    public WebElement checkbox;
    public WebElement dropAreaForAttachments;
    public WebElement sendButton;
    public WebElement successfulMessage;

    private final String URL = "https://www.t-mobile.cz/podpora/kontaktujte-nas";

    public TMobileFormPage(ChromeDriver driver) {
        super(driver);
    }

    public void initElements() {
        subject = driver.findElement(By.name("subject"));
        content = driver.findElement(By.name("content"));
        phoneNumber = driver.findElement(By.name("phoneNumber"));
        email = driver.findElement(By.name("email"));
        checkbox = driver.findElement(
                By.xpath("//span[@class=\"like-checkbox ico-check-mark\" " +
                        "or @class=\"like-checkbox ico-check-mark active\"]"));
        dropAreaForAttachments = driver.findElement(
                By.xpath("//button[contains(text(), 'Vybrat')]"));
        sendButton = driver.findElement(By.name("submit"));


    }

    public void fillElements(String subject, String content, String phoneNumber,
                             String email, Boolean checkboxValue, String filepath) {
        this.subject.sendKeys(subject);
        this.content.sendKeys(content);
        this.phoneNumber.sendKeys(phoneNumber);
        this.email.sendKeys(email);
        setCheckboxValue(checkboxValue);
        if (filepath != null) {
            dropFile(new File(filepath), dropAreaForAttachments, 0, 0);
        }
    }

    public void goTo() {
        TMobileSupportPage parent = new TMobileSupportPage(driver);
        parent.goTo();
        parent.contactFormButton.click();
        initElements();
    }

    public void goToDirect() {
        driver.get(URL);
        initElements();
    }

    /**
     * Sets checkbox to checked if it is required or otherwise.
     *
     * @param value true if checkbox should be check, false otherwise
     */
    public void setCheckboxValue(Boolean value) {
        String checked = checkbox.getAttribute("class");
        if (checked.contains("active") != value) {
            checkbox.click();
        }
    }

    /**
     * Checks if there's successful message after sending form.
     *
     * @return true if message is displayed, false otherwise
     */
    public Boolean isMessageDisplayed() {
        try {
            successfulMessage = driver.findElement(
                    By.xpath("//div[@class=\"portlet-msg-success\"]"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Drops the file to drag@&drop element.
     * https://sqa.stackexchange.com/questions/22191/is-it-possible-to-automate-
     * drag-and-drop-from-a-file-in-system-to-a-website-in-s
     *
     * @param filePath file to be dropped
     * @param target   element where the file should be dropped
     * @param offsetX  x axis
     * @param offsetY  y axis
     */
    public static void dropFile(File filePath, WebElement target,
                                int offsetX, int offsetY) {
        if (!filePath.exists())
            throw new WebDriverException("File not found: "
                    + filePath.toString());

        WebDriver driver = ((RemoteWebElement) target).getWrappedDriver();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, 30);

        WebElement input = (WebElement) jse.executeScript(
                Scripts.JS_DROP_FILE, target, offsetX, offsetY);
        input.sendKeys(filePath.getAbsoluteFile().toString());
        wait.until(ExpectedConditions.stalenessOf(input));
    }
}
