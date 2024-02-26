package pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.util.List;

public class CatalogPage extends AbsBasePage {

    public CatalogPage(WebDriver driver) {
        super(driver, "/catalog/courses");
    }

    @FindBy(xpath = "//div[.='Каталог']/../../following-sibling::div//a")
    private List<WebElement> lessonsTiles;

    @FindBy(xpath = "//div[.='Каталог']/../../following-sibling::div//a//div[contains(text(),'месяц')]")
    private List<WebElement> lessonsDuration;

    public void lessonsTilesShouldBeAs(int number) {
        Assertions.assertEquals(
                number,
                lessonsTiles.size(),
                String.format("Count of lessons tiles not", number)
        );
    }

    public void clickRandomLessonsTiles() {
        faker.options().nextElement(lessonsTiles).click();
    }

    public int getTilesNumbers() {
        return lessonsTiles.size();
    }

    public String getLessonNameByIndex(int index) {
        return lessonsTiles.get(--index).findElement(By.xpath(".//h6")).getText();
    }

    public String getLessonDuration(int index) {
       String lessonDurationFull;
       String lessonDuration;
       int charIndex = 0;

       lessonDurationFull = lessonsDuration.get(--index).getText();
       charIndex = lessonDurationFull.indexOf("·") + 2;
       lessonDuration = lessonDurationFull.substring(charIndex);

       return lessonDuration;
    }

    private Document getDomPage(int index) throws IOException {
        String url = lessonsTiles.get(--index).getAttribute("href");
        return Jsoup.connect(url).get();
    }

    public void checkHeaderLessonByIndex(int index, String expectedHeader) throws IOException {
        Document doc = getDomPage(index);
        Element headerPageElement = doc.selectFirst("h1");
        Assertions.assertEquals(expectedHeader, headerPageElement.text(), "Header not equal expected");
    }

    public void checkDescriptionLessonByIndex(int index) throws IOException{
       // Elements descriptionLessonElement = getDomPage(index).selectXpath("//h1/following-sibling::div/p[1]").get(0);
        Elements elements = getDomPage(index).selectXpath("//h1/following-sibling::div/p[1]");

        if(elements.size() > 0){
           Element descriptionLessonElement = getDomPage(index).selectXpath("//h1/following-sibling::div/p[1]").get(0);
           Assertions.assertFalse(descriptionLessonElement.text().isEmpty(),"Description is empty");
        }
        else {
            Element descriptionLessonElement = getDomPage(index).selectXpath("//h1/following-sibling::div[text()]").get(0);
            }
        }




    public void checkLessonDurationByIndex(int index, String expectedLessonDuration) throws IOException{
        Element lessonDurationElement = getDomPage(index).selectXpath("//div/following-sibling::p[contains(text(),'месяц')]").get(0);
        Assertions.assertEquals(expectedLessonDuration, lessonDurationElement.text(),"Lesson duration not equal expected");
    }

    public void checkLessonFormat(int index, String format) throws IOException{
        Element lessonFormatElement = getDomPage(index).selectXpath(String.format("//p[contains(text(),'%s')]", format)).get(0);
        Assertions.assertEquals(format, lessonFormatElement.text(),String.format("Lesson format is not %s",format));
    }

}
