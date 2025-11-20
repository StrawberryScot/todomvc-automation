package tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ReactPage;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TodomvcTest {
    private static WebDriver driver;

    @BeforeAll
    static void setUp() {
        driver = new ChromeDriver();
    }

    @AfterAll
    static void tearDown() {
        driver.close();
        driver.quit();
    }

    // To automate now
    // TODO: TEST 1 Can't add an item with an empty value
    @Test
    public void shouldNotAddItemOfEmptyValue() throws InterruptedException {
        ReactPage reactPage = new ReactPage(driver);
        reactPage.navigate();
        reactPage.createNewTodo("Walk the cat");
        // Optional<WebElement> clearButton = reactPage.getClearButton();
        // assertTrue(clearButton.isEmpty());
        reactPage.clickElement(reactPage.getToggleButton(0));
        reactPage.clickElement(reactPage.getLabel(0));
        Optional<WebElement> editField = reactPage.getEditField(0);
        editField.ifPresent(element -> {
            element.sendKeys(Keys.COMMAND, "a");
            element.sendKeys(Keys.BACK_SPACE);
            element.sendKeys("Walk the dog");
            element.sendKeys(Keys.ENTER);
        });
        Thread.sleep(2000);
        reactPage.clickElement(reactPage.getDeleteButton(0));


        //reactPage.createNewTodo("");
        // AAAAAAAAAAA
    }
    // TODO: TEST 2 Can add a value with a single character
    // test
    // TODO: TEST 3 Can delete a single item
    // TODO: TEST 6 Can modify existing todo item
    // TODO: TEST 7 Pressing Escape during item modification cancels modification
    // TODO: TEST 8 Can add another todo item to list
    // TODO: TEST 15 Can clear complete todo items when >0 completed todo items are listed
    // TODO: Status bar always displays a count of remaining todo items left to do


    // To automate later
    // TODO: TEST 4 Can add characters and symbols
    // TODO: TEST 5 Can add emoji characters
    // TODO: TEST 9 Can reorder items
    // TODO: TEST 11.1 Can filter incompleted items
    // TODO: TEST 11.2 Can filter completed items
    // TODO: TEST 11.3 Can filter by all items
    // TODO: TEST 13 Can mark all todo items as complete
    // TODO: TEST 14 Can mark all todo items as incomplete
}
