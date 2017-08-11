package pages.tmobilePages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import pages.AbstractUIPage;
import pages.googlePages.GoogleResultPage;

/**
 * Created by Robert on 08/08/2017.
 */
public class TMobilePage extends AbstractUIPage {

    private WebElement supportButton;
    private WebElement contactUsButton;
    private WebElement popUpCloseButton;
    private Actions action = new Actions(driver);

    private final String URL = "https://www.t-mobile.cz";


    private final String TMOBILEQUERY = "t-mobile";

    public TMobilePage(ChromeDriver driver) {
        super(driver);
    }

    public void initElements() {
        supportButton = driver.findElement(By.xpath("//ul/li/a[@tabindex='8']"));
        contactUsButton = driver.findElement(
                By.xpath("//a[contains(text(), 'Obraťte se na nás')]"));
    }

    /**
     * Mouses over the support menu, then clicks contact us link.
     */
    public void goThroughMenu() {
        action.moveToElement(supportButton).perform();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
        contactUsButton.click();
    }

    /**
     * If add shows up, closes the add.
     */
    public void closePopUp() {
        try {
            popUpCloseButton = driver.findElement(
                    By.xpath("//span[@class=\"btn-close\"]"));
            popUpCloseButton.click();
        } catch (NoSuchElementException e) {
        }
    }

    public void goTo() {
        GoogleResultPage parent = new GoogleResultPage(driver);
        parent.goTo(TMOBILEQUERY);
        parent.selectCorrectResult(".*www\\.t-mobile\\.cz\\/");
    }

    public void goToDirect() {
        driver.get(URL);
        initElements();
    }

    public WebElement getSupportButton() {
        return supportButton;
    }

    public WebElement getContactUsButton() {
        return contactUsButton;
    }

    public WebElement getPopUpCloseButton() {
        return popUpCloseButton;
    }
}
