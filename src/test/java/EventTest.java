import data.EventTypeData;
import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.CalendarEventsPage;

public class EventTest {

    private WebDriver driver;
    private CalendarEventsPage eventsPage;
    private final Logger logger = LogManager.getLogger(EventTest.class);


    @BeforeEach
    public void init() {
        this.driver = new DriverFactory().create();

        this.eventsPage = new CalendarEventsPage(driver);
        eventsPage.open();
        logger.info("Page with events opened");

    }

    @AfterEach
    public void stopDriver(){
        if (driver != null){
            driver.quit();
            logger.info("Driver closed");
        }
    }

    @Test
    public void checkEventDateIsActual() {
        eventsPage.checkEventTilesPresent()
                  .checkEventDateIsActual();
        logger.info("Date check completed. All date is actual");
    }

    @Test
    public void selectSortedType() {
        eventsPage.selectSortedEventTypes(EventTypeData.OPEN)
                  .checkEventsType(EventTypeData.OPEN);
        logger.info("Event sorted by Open Webinar");
    }

}
