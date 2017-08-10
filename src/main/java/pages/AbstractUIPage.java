package pages;

import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by Robert on 07/08/2017.
 */
public abstract class AbstractUIPage {
    protected ChromeDriver driver;

    public AbstractUIPage(ChromeDriver driver) {
        this.driver = driver;
    }

}
