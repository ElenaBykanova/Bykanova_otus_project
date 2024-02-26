package pages;

import data.EventTypeData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import java.lang.Character.*;

public class CalendarEventsPage extends AbsBasePage {

    public CalendarEventsPage(WebDriver driver) {
        super(driver, "/events/near/");
    }


    @FindBy(css = ".dod_new-event-content")
    private List<WebElement> eventTiles;

    @FindBy(css = ".dod_new-event__calendar-icon ~.dod_new-event__date-text")
    private List<WebElement> eventDates;
    @FindBy(css = ".dod_new-event-content .dod_new-type__text")
    private List<WebElement> eventsTypes;

    private String dropdownSortEventListSelector = ".dod_new-events-dropdown";
    private String dropdownEventListSelector = dropdownSortEventListSelector + " .dod_new-events-dropdown__list";
    private String dropDownSortEventItemSelectorTemplate = dropdownEventListSelector + " [title='%s']";

    public CalendarEventsPage checkEventTilesPresent() {
        Assertions.assertTrue(waitTools.waitForCondition(ExpectedConditions.visibilityOfAllElements(eventTiles)));

        return this;
    }


    public CalendarEventsPage checkEventDateIsActual() {
        ////span[contains(@class,'calendar-icon')]/following-sibling::span
        for (WebElement eventDateElement : eventDates) {
            LocalDate currentDate = LocalDate.now();

            String eventDateStr = Character.isDigit(eventDateElement.getText().charAt(1)) ?
                    eventDateElement.getText() + String.format(" %d", currentDate.getYear()) :
                    "0" + eventDateElement.getText() + String.format(" %d", currentDate.getYear());
            Locale locale = new Locale("ru");
            LocalDate eventDate = LocalDate.parse(eventDateStr, DateTimeFormatter.ofPattern("dd MMMM yyyy", locale));
            Assertions.assertTrue(eventDate.isAfter(currentDate) || eventDate.isEqual(currentDate), "Date of event not actual ");
        }

        return this;

    }

    private CalendarEventsPage dropDownSortEventShouldNotBeOpened() {
        WebElement startingDropDown = driver.findElement(By.cssSelector(dropdownSortEventListSelector));
        Assertions.assertTrue(
                waitTools.waitForCondition
                        (ExpectedConditions.not
                                (ExpectedConditions.attributeContains
                                        (startingDropDown, "class", "dod_new-events-dropdown_opened")
                                )
                        )
        );

        return this;

    }

    private CalendarEventsPage dropDownSortEventShouldBeOpened() {
        WebElement startingDropDown = driver.findElement(By.cssSelector(dropdownSortEventListSelector));
        Assertions.assertTrue(
                waitTools.waitForCondition
                        (ExpectedConditions.attributeContains
                                (startingDropDown, "class", "dod_new-events-dropdown_opened")
                        )
        );

        return this;

    }

    private CalendarEventsPage dropDownSortEventMenuOpen() {
        WebElement startingDropDown = driver.findElement(By.cssSelector(dropdownSortEventListSelector));
        startingDropDown.click();
        return this;
    }

    private CalendarEventsPage sortItemsShouldBeVisible() {
        WebElement startingDropDownList = driver.findElement(By.cssSelector(dropdownEventListSelector));
        Assertions.assertTrue(waitTools.waitElementVisible(startingDropDownList));
        return this;
    }

    private CalendarEventsPage sortItemClick(EventTypeData eventTypeData) {
        WebElement sortItemElement = driver.findElement(By.cssSelector(String.format(dropDownSortEventItemSelectorTemplate, eventTypeData.getName())));
        sortItemElement.click();
        return this;
    }

    public CalendarEventsPage selectSortedEventTypes(EventTypeData eventTypeData) {
        this.dropDownSortEventShouldNotBeOpened()
                .dropDownSortEventMenuOpen()
                .dropDownSortEventShouldBeOpened()
                .sortItemsShouldBeVisible()
                .sortItemClick(eventTypeData);

        return this;
    }

    public CalendarEventsPage checkEventsType(EventTypeData eventTypeData) {
        for (WebElement element : eventsTypes) {
            Assertions.assertEquals(eventTypeData.getName(), element.getText());
        }

        return this;

    }


}
