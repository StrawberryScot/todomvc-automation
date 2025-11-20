package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
            // Detect OS for correct modifier key
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("mac")) {
                element.sendKeys(Keys.chord(Keys.COMMAND, "a"));
            } else {
                element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            }

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
    // TODO: TEST 3 Can delete a single item
    // TODO: TEST 6 Can modify existing todo item
    // TODO: TEST 7 Pressing Escape during item modification cancels modification


// Item Modification Tests (6 + 7):

//    @Test
//    @DisplayName("TEST 6: Can modify existing todo item")
//    public void shouldSuccessfullyModifyExistingTodo() {
//        ReactPage reactPage = new ReactPage(driver);
//        reactPage.navigate();
//        reactPage.createNewTodo("make a sandwich");
//        assertEquals("make a sandwich", reactPage.getTodoText(0));
//
//        reactPage.editTodo(0, "make a cake");
//
//        assertEquals("make a cake", reactPage.getTodoText(0));
//        assertFalse(reactPage.isComplete(0));
//        assertTrue(reactPage.getItemsLeftText().contains("1 item left"));
//    }

    // TEST 6
    // Main parameterized test - covers common scenarios
    @ParameterizedTest(name = "Modify: ''{0}'' to ''{1}''")
    @CsvSource({
            "make a sandwich, make a cake",
            "Short, Much longer task with more details",
            "Very long task description, Short",
            "Buy milk, Buy milk and eggs",
            "Task123, Task456",
            "meeting, MEETING"
    })
    @DisplayName("TEST 6.1: Can modify existing todo item")
    public void shouldSuccessfullyModifyExistingTodo(String original, String modified) {
        ReactPage reactPage = new ReactPage(driver);
        reactPage.navigate();

        reactPage.createNewTodo(original);
            reactPage.editTodo(0, modified);

            assertEquals(modified, reactPage.getTodoText(0));
            assertFalse(reactPage.isComplete(0));
            assertTrue(reactPage.getItemsLeftText().contains("1 item left"));
        }

    // Specific edge case - multiple edits on single todo
    @Test
    @DisplayName("TEST 6.2: Can modify same todo multiple times")
    public void shouldModifyMultipleTimes() {
        ReactPage reactPage = new ReactPage(driver);
        reactPage.navigate();
        reactPage.createNewTodo("V1");
        reactPage.editTodo(0, "V2");
        reactPage.editTodo(0, "V3");
        assertEquals("V3", reactPage.getTodoText(0));
    }

    // Specific edge case - editing completed todo
    @Test
    @DisplayName("TEST 6.3: Can modify completed todo")
    public void shouldModifyCompletedTodo() {
        ReactPage reactPage = new ReactPage(driver);
        reactPage.navigate();
        reactPage.createNewTodo("Task");
        reactPage.clickElement(reactPage.getToggleButton(0));
        reactPage.editTodo(0, "Modified");

        assertEquals("Modified", reactPage.getTodoText(0));
        assertTrue(reactPage.isComplete(0));
    }


//TEST 7

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "Immediate cancel, Buy milk, Buy bread, immediately",
            "After typing fully, Clean kitchen, Wash dishes, after_full_edit",
            "After typing partially, Call dentist, Call d, after_partial_edit",
            "After clearing, Water plants, '', after_clear",
            "After appending, Submit report, ' today', after_append"
    })
    @DisplayName("TEST 7: Escape cancels modification")
    public void shouldCancelModificationWhenEscapePressed(
            String scenario,
            String originalTodo,
            String attemptedEdit,
            String escapePoint) {

        ReactPage reactPage = new ReactPage(driver);
        reactPage.navigate();

        reactPage.createNewTodo(originalTodo);
        assertEquals(originalTodo, reactPage.getTodoText(0));

        reactPage.editTodoWithEscapeAt(0, attemptedEdit, escapePoint);

        assertEquals(originalTodo, reactPage.getTodoText(0),
                scenario + ": Todo should remain unchanged after Escape");
        assertFalse(reactPage.isComplete(0));
        assertTrue(reactPage.getItemsLeftText().contains("1 item left"));
    }
}


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

