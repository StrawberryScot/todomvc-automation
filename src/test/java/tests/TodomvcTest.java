package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ReactPage;
import pages.SveltePage;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TodomvcTest {
    private static WebDriver driver;
    private static SveltePage page;
    // private static ReactPage page;

    @BeforeAll
    static void setUp() {
        driver = new ChromeDriver();
        page = new SveltePage(driver);
        //  page = new ReactPage(driver);
    }

    @AfterAll
    static void tearDown() {
        driver.close();
        driver.quit();
    }

    // 'Tests To Automate Now'


    // TODO: TEST 1 Can't add an item with an empty value
    @Test
    public void shouldNotAddItemOfEmptyValue() {

        page.navigate();
        page.createNewTodo("");

        assertEquals(Optional.empty(), page.getIndividualTodoItem(0));
        System.out.println("No todo item added. Test 1 passed.");
    }


    // TODO: TEST 2 Can add an item with a single character
    @Test
    public void shouldAddItemWithSingleCharacter() {
        page.navigate();
        page.createNewTodo("a");
        System.out.println(page.getIndividualTodoItem(0));
        assertEquals("a", page.getLabel(0).get().getText());
    }

    // TODO: TEST 3 Can delete a single item
    @Test
    public void canDeleteSingleItem() {
        page.navigate();
        page.createNewTodo("task");
        page.clickElement(page.getDeleteButton(0));
        assertEquals(Optional.empty(), page.getIndividualTodoItem(0));
        System.out.println("Item deleted. Test 3 passed.");
    }


    // TODO: TEST 6 Can modify existing todo item

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
        page.navigate();

        page.createNewTodo(original);
        page.editTodo(0, modified);

        assertEquals(modified, page.getTodoText(0));
//            assertFalse(page.isComplete(0));
        assertTrue(page.getItemsLeftText().contains("1 item left"));
    }

    // Specific edge case - multiple edits on single todo
    @Test
    @DisplayName("TEST 6.2: Can modify same todo multiple times")
    public void shouldModifyMultipleTimes() {
        page.navigate();
        page.createNewTodo("V1");
        page.editTodo(0, "V2");
        page.editTodo(0, "V3");
        assertEquals("V3", page.getTodoText(0));
    }

    // Specific edge case - editing completed todo
    @Test
    @DisplayName("TEST 6.3: Can modify completed todo")
    public void shouldModifyCompletedTodo() {
        page.navigate();

        page.createNewTodo("Original task");
        // Mark it complete
        page.clickElement(page.getToggleButton(0));
        assertTrue(page.isComplete(0), "Todo should be marked complete");
        page.editTodo(0, "Modified task");
        assertEquals("Modified task", page.getTodoText(0),
                "Text should be updated");
        assertTrue(page.isComplete(0),
                "Todo should remain complete after editing");
    }


// TODO: TEST 7 Pressing Escape during item modification cancels modification

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

        page.navigate();

        page.createNewTodo(originalTodo);
        assertEquals(originalTodo, page.getTodoText(0));

        page.editTodoWithEscapeAt(0, attemptedEdit, escapePoint);

        assertEquals(originalTodo, page.getTodoText(0),
                scenario + ": Todo should remain unchanged after Escape");
//        assertFalse(page.isComplete(0));
        assertTrue(page.getItemsLeftText().contains("1 item left"));

    }


    // TODO: TEST 8: Can add another todo item to list
    @DisplayName("TEST 8: Can add another todo item to list")
    @Test
    public void shouldAddItemToPopulatedList() {
        page.navigate();
        int initialCount = page.getListTodoItems().size();
        System.out.println("The initial count is " + initialCount);
        page.createNewTodo("Walk the cat");
        System.out.println("Now the count is " + page.getListTodoItems().size());
        page.createNewTodo("Groom Fish");

        assertEquals(2, page.getListTodoItems().size());
        assertEquals("Groom Fish", page.getTodoText(1));

    }

    // TODO TEST 15: Can clear complete todo items when >0 completed todo items are listed
    @DisplayName("TEST 15: Can clear complete todo items when >0 completed todo items are listed")
    @Test
    public void shouldClearCompletedItemsWhenMoreThanZeroCompleted() {
        page.navigate();

        page.createNewTodo("Walk the cat");
        page.createNewTodo("Groom the fish");
        page.createNewTodo("Feed the dragon");

        page.clickElement(page.getToggleButton(0));
        page.clickElement(page.getToggleButton(1));

        page.clickElement(page.getClearButton());

        int remainingTodoCount = page.getListTodoItems().size();
        assertEquals(1, remainingTodoCount);

        String remainingText = page.getTodoText(0);
        assertEquals("Feed the dragon", remainingText);
    }


    // 'Tests To Automate Later'

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

        page.navigate();

        page.createNewTodo(newTodo);
        assertEquals(newTodo, page.getTodoText(0));
        assertTrue(page.getItemsLeftText().contains("1 item left"));
        System.out.println(newTodo + " successfully added.");
    }


    // TODO: TEST 5 Can add emoji characters (with and without text + combo)
    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "✅✅✅",
            "Coffee break ☕",
            "✅☕",
            "Bug found 🐛",
    })
    @DisplayName("TEST 5: Can add emoji characters (with and without text)")
    public void shouldAddEmojis(String newTodo) {

        page.navigate();

        page.createNewTodo(newTodo);
        assertEquals(newTodo, page.getTodoText(0));
        assertTrue(page.getItemsLeftText().contains("1 item left"));
        System.out.println(newTodo + " successfully added.");
    }


    // TODO: TEST 11.1 Can filter incompleted items
    @Test
    @DisplayName("TEST 11.1: Can filter incompleted items")
    public void shouldFilterIncompleteItems() {
        page.navigate();

        page.createNewTodo("Task 1");
        page.createNewTodo("Task 2");
        page.createNewTodo("Task 3");

        page.clickElement(page.getToggleButton(0));
        page.clickElement(page.getToggleButton(2));

        page.clickElement(page.getFilterActiveButton());

        assertEquals("Task 2", page.getTodoText(0));
    }

    // TODO: TEST 11.2 Can filter completed items
    @Test
    @DisplayName("TEST 11.2: Can filter completed items")
    public void shouldFilterCompletedItems() {
        page.navigate();

        page.createNewTodo("Task 1");
        page.createNewTodo("Task 2");
        page.createNewTodo("Task 3");

        page.clickElement(page.getToggleButton(1));

        page.clickElement(page.getFilterCompletedButton());

        assertEquals("Task 2", page.getTodoText(0));
    }

    // TODO: TEST 11.3 Can filter by all items
    @Test
    @DisplayName("TEST 11.3: Can filter by all items")
    public void shouldFilterAllItems() {
        page.navigate();

        page.createNewTodo("Task 1");
        page.createNewTodo("Task 2");
        page.createNewTodo("Task 3");

        page.clickElement(page.getToggleButton(1));

        page.clickElement(page.getFilterAllButton());

        assertEquals("Task 1", page.getTodoText(0));
        assertEquals("Task 2", page.getTodoText(1));
        assertEquals("Task 3", page.getTodoText(2));
    }

    // TODO: TEST 13 Can mark all todo items as complete
    @Test
    @DisplayName("TEST 13: Can mark all todo items as complete")
    public void shouldMarkAllTodosAsCompleteWhenTogglePressed() {
        page.navigate();

        page.createNewTodo("Task 1");
        page.createNewTodo("Task 2");
        page.createNewTodo("Task 3");

        page.clickElement(page.getToggleButton(1));
        page.clickElement(page.getToggleAll());

        assertTrue(page.isComplete(0));
        assertTrue(page.isComplete(1));
        assertTrue(page.isComplete(2));
    }

    // TODO: TEST 14 Can mark all todo items as incomplete
    @Test
    @DisplayName("TEST 14: Can mark all todo items as incomplete")
    public void shouldMarkAllTodosAsIncompleteWhenTogglePressed() {
        page.navigate();

        page.createNewTodo("Task 1");
        page.createNewTodo("Task 2");
        page.createNewTodo("Task 3");

        page.clickElement(page.getToggleButton(1));
        page.clickElement(page.getToggleAll());
        page.clickElement(page.getToggleAll());

        assertFalse(page.isComplete(0));
        assertFalse(page.isComplete(1));
        assertFalse(page.isComplete(2));
    }
}

