package common;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import tools.WaitTools;

public abstract class AbsCommon {
    protected WebDriver driver;
    protected WaitTools waitTools;
    protected Faker faker;

     public AbsCommon(WebDriver driver) {
        this.driver = driver;
        this.waitTools = new WaitTools(driver);
        this.faker = new Faker();
    }

}
