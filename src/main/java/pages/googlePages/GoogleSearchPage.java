package pages.googlePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.AbstractUIPage;


/**
 * Created by Robert on 07/08/2017.
 */
public class GoogleSearchPage extends AbstractUIPage {

    private WebElement searchBox;
    private final String URL = "https://google.cz";

    public GoogleSearchPage(ChromeDriver driver) {
        super(driver);
    }

    public void googleSearch(String query) {
        searchBox.sendKeys(query);
        searchBox.submit();
    }

    public void initElements() {
        searchBox = driver.findElement(By.id("lst-ib"));
    }

    public void goTo() {
        driver.get(URL);
        initElements();
    }

    public WebElement getSearchBox() {
        return searchBox;
    }
}
