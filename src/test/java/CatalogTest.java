import catalog.LessonsCategoryData;
import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.CatalogPage;
import pages.LessonPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CatalogTest {

    private WebDriver driver;
    private final Logger logger = LogManager.getLogger(CatalogTest.class);

    private CatalogPage catalogPage = null;


    @BeforeEach
    public void init() {
        this.driver = new DriverFactory().create();

        List<String> queryParams = new ArrayList<>();
        queryParams.add(String.format("categories=%s", LessonsCategoryData.TESTING.name().toLowerCase(Locale.ROOT)));
        this.catalogPage = new CatalogPage(driver);
        catalogPage.open(queryParams);


    }

    @AfterEach
    public void stopDriver(){
        if (driver != null){
            driver.quit();
            logger.info("Driver closed");
        }
    }

    @Test
    public void catalogTest(){
        catalogPage.lessonsTilesShouldBeAs(10);
        logger.info("Proverka proidena. Kolichestvo plitok = 10");
    }

    @Test
    public void checkDataOnLessonPage() throws IOException {
        for (int i = 1; i<catalogPage.getTilesNumbers();i++){
            String expectedHeader = catalogPage.getLessonNameByIndex(i);
            String expectedLessonDuration = catalogPage.getLessonDuration(i);

            catalogPage.checkHeaderLessonByIndex(i, expectedHeader);
            catalogPage.checkDescriptionLessonByIndex(i);
            catalogPage.checkLessonDurationByIndex(i, expectedLessonDuration);
            catalogPage.checkLessonFormat(i,"Онлайн");

            logger.info(String.format("Proverili %s QA curs",i));

        }

            catalogPage.clickRandomLessonsTiles();
            LessonPage lessonPage = new LessonPage(driver,"");
    }



}
