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
    public void shouldNotAddItemOfEmptyValue() {
        ReactPage reactPage = new ReactPage(driver);
        reactPage.navigate();
        reactPage.createNewTodo("");

        assertEquals(Optional.empty(), reactPage.getIndividualTodoItem(0));
        System.out.println("No todo item added. Test 1 passed.");
    }


    @Test
    public void testTest() throws InterruptedException {
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
        reactPage.clickElement(reactPage.getDeleteButton(0));


        //reactPage.createNewTodo("");
        // AAAAAAAAAAA
    }

    // TODO: TEST 2 Can add an item with a single character
    @Test
    public void shouldAddItemWithSingleCharacter() {
        ReactPage reactPage = new ReactPage(driver);
        reactPage.navigate();
        reactPage.createNewTodo("a");
        System.out.println(reactPage.getIndividualTodoItem(0));
        assertEquals("a", reactPage.getLabel(0).get().getText());
    }

    // TODO: TEST 3 Can delete a single item
    @Test
    public void canDeleteSingleItem() {
        ReactPage reactPage = new ReactPage(driver);
        reactPage.navigate();
        reactPage.createNewTodo("task");
        reactPage.clickElement(reactPage.getDeleteButton(0));
        assertEquals(Optional.empty(), reactPage.getIndividualTodoItem(0));
    }


    // TODO: TEST 6 Can modify existing todo item
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
//            assertFalse(reactPage.isComplete(0));
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
//    @Test
//    @DisplayName("TEST 6.3: Can modify completed todo")
//    public void shouldModifyCompletedTodo() {
//        ReactPage reactPage = new ReactPage(driver);
//        reactPage.navigate();
//        reactPage.createNewTodo("Task");
//        reactPage.clickElement(reactPage.getToggleButton(0));
//        reactPage.editTodo(0, "Modified");
//
//        assertEquals("Modified", reactPage.getTodoText(0));
//        assertTrue(reactPage.isComplete(0));
//    }

// TODO: TEST 7 Pressing Escape during item modification cancels modification
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
//        assertFalse(reactPage.isComplete(0));
        assertTrue(reactPage.getItemsLeftText().contains("1 item left"));

    }


    // TODO: TEST 8 Can add another todo item to list
    // TODO: TEST 15 Can clear complete todo items when >0 completed todo items are listed
    // TODO: Status bar always displays a count of remaining todo items left to do


    // To automate later
    // TODO: TEST 4 Can add characters and symbols

    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "Test-with-dashes_and_underscores",
            "Test/with\\slashes",
            "Test with 'single' and \"double\" quotes",
            "Test with @#$%^&*()",
            "Test with brackets [square] {curly} (round)",
            "Test with <angle> brackets",
            "Test with | pipe",
            "Test with + plus = equals",
            "Test with ~ tilde ` backtick",
            "Test with spaces and punctuation!",
            "Café résumé naïve",
            "Mañana niño JALAPEÑO",
            "München Zürich ÜBER",
            "Bjørn Søren KØBENHAVEN",
            "Łódź Kraków GDAŃSK"
    })
    @DisplayName("TEST 4: Can add characters and symbols.")
    public void shouldAddCharactersAndSymbols(String newTodo) {

        ReactPage reactPage = new ReactPage(driver);
        reactPage.navigate();

        reactPage.createNewTodo(newTodo);
        assertEquals(newTodo, reactPage.getTodoText(0));
        assertTrue(reactPage.getItemsLeftText().contains("1 item left"));
        System.out.println(newTodo + " successfully added.");
    }


    // TODO: TEST 5 Can add emoji characters (with and without text + combo)
    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "✅✅✅",
            "✅☕",
            "Urgent task \uD83D\uDEA8",
            "Celebration \uD83C\uDF89",
            "In progress \uD83D\uDD04",
            "Bug found 🐛",
            "Coffee break ☕"
    })
    @DisplayName("TEST 5 Can add emoji characters (with and without text)")
    public void shouldAddEmojis(String newTodo) throws InterruptedException {

        ReactPage reactPage = new ReactPage(driver);
        reactPage.navigate();

        reactPage.createNewTodo(newTodo);
        Thread.sleep(2000);
        assertEquals(newTodo, reactPage.getTodoText(0));
        assertTrue(reactPage.getItemsLeftText().contains("1 item left"));
        System.out.println(newTodo + " successfully added.");
    }






    // TODO: TEST 11.1 Can filter incompleted items
    // TODO: TEST 11.2 Can filter completed items
    // TODO: TEST 11.3 Can filter by all items

    // TODO: TEST 13 Can mark all todo items as complete
    @Test
    @DisplayName("TEST 13 Can mark all todo items as complete")
    public void shouldMarkAllTodosAsCompleteWhenTogglePressed() {
        ReactPage reactPage = new ReactPage(driver);
        reactPage.navigate();

        reactPage.createNewTodo("Task 1");
        reactPage.createNewTodo("Task 2");
        reactPage.createNewTodo("Task 3");

        reactPage.clickElement(reactPage.getToggleButton(1));
        reactPage.clickElement(reactPage.getToggleAll());

        assertTrue(reactPage.isComplete(0));
        assertTrue(reactPage.isComplete(1));
        assertTrue(reactPage.isComplete(2));
    }
    // TODO: TEST 14 Can mark all todo items as incomplete
    @Test
    @DisplayName("TEST 14 Can mark all todo items as incomplete")
    public void shouldMarkAllTodosAsIncompleteWhenTogglePressed() {
        ReactPage reactPage = new ReactPage(driver);
        reactPage.navigate();

        reactPage.createNewTodo("Task 1");
        reactPage.createNewTodo("Task 2");
        reactPage.createNewTodo("Task 3");
        
        reactPage.clickElement(reactPage.getToggleButton(1));
        reactPage.clickElement(reactPage.getToggleAll());
        reactPage.clickElement(reactPage.getToggleAll());

        assertFalse(reactPage.isComplete(0));
        assertFalse(reactPage.isComplete(1));
        assertFalse(reactPage.isComplete(2));
    }
}

