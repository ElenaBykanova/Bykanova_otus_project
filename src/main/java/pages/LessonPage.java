package pages;

import org.openqa.selenium.WebDriver;

public class LessonPage extends AbsBasePage{

    public LessonPage(WebDriver driver, String endPartOfUrl){
       super(driver,String.format("/lessons/$s",endPartOfUrl));
    }
}
