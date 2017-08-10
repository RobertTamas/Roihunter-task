package pages.tmobilePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.AbstractUIPage;

/**
 * Created by Robert on 09/08/2017.
 */
public class TMobileSupportPage extends AbstractUIPage {

    public WebElement contactFormButton;

    private final String URL = "https://www.t-mobile.cz/podpora/obratte-se-na-nas";

    public TMobileSupportPage(ChromeDriver driver) {
        super(driver);
    }

    public void initElements() {
        contactFormButton = driver.findElement(
                By.xpath("//h2/a[contains(text(), 'Kontaktní formulář')]"));
    }

    public void goTo() {
        TMobilePage parent = new TMobilePage(driver);
        parent.goTo();
        parent.closePopUp();
        parent.initElements();
        parent.goThroughMenu();
        initElements();
    }

    public void goToDirect(){
        driver.get(URL);
        initElements();
    }
}
