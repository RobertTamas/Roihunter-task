package pages.googlePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.AbstractUIPage;

import java.util.List;

/**
 * Created by Robert on 07/08/2017.
 */
public class GoogleResultPage extends AbstractUIPage {

    private List<WebElement> results;

    public GoogleResultPage(ChromeDriver driver) {
        super(driver);
    }

    public void initElements() {
        results = driver.findElements(By.xpath("//*[@id='rso']//h3/a"));
    }

    public void selectCorrectResult(String regex) {
        for (WebElement element : results) {
            if (element.getAttribute("href").matches(regex)) {
                element.click();
                break;
            }
        }
    }

    public void goTo(String query) {
        GoogleSearchPage parent = new GoogleSearchPage(driver);
        parent.goTo();
        parent.googleSearch(query);
        initElements();
    }
}
