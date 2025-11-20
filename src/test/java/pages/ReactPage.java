package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.sql.Array;
import java.util.List;
import java.util.Optional;

public class ReactPage {
    protected WebDriver driver;

    // locators

    private By buttonDeleteBy = By.cssSelector(".destroy");
    private By toggleCheckboxBy = By.cssSelector(".toggle");
    private By labelItemBy = By.cssSelector("label");
    private By editItemBy = By.cssSelector("#todo-input");
    // above is index specific. MUST CHANGE

    private final By inputFieldBy = By.id("todo-input");
    private final By listTodoItemsBy = By.cssSelector(".view");
    private final By toggleSelectAllBy = By.id("toggle-all");
    private final By buttonFilterAllBy = By.linkText("All");
    private final By buttonFilterActiveBy = By.linkText("Active");
    private final By buttonFilterCompletedBy = By.linkText("Completed");
    private final By buttonClearCompletedBy = By.cssSelector(".clear-completed");
    private final By headingItemCounterBy = By.className("todo-count");

    // constructor
    public ReactPage(WebDriver driver) {
        this.driver = driver;
    }

    // navigation
    public void navigate() {
        driver.get("https://todomvc.com/examples/react/dist");
    }

   // ACTION methods

    public void clickElement(Optional<WebElement> opt) {
        opt.ifPresent(element -> element.click());
    }

    public void doubleClickElement(Optional<WebElement> opt) {
        opt.ifPresent(element -> new Actions(driver).doubleClick(element).perform());
    }

    public void createNewTodo(String newTodo) {
        WebElement inputField = driver.findElement(inputFieldBy);
        inputField.sendKeys(newTodo);
        inputField.sendKeys(Keys.ENTER);
    }

    // GET methods

    public Optional<WebElement> getEditField(Integer index) {
        doubleClickElement(getLabel(index));
        Optional<WebElement> opt = getIndividualTodoItem(index);
        return opt.flatMap(todoItem -> todoItem
                .findElements(editItemBy)
                .stream()
                .findFirst());
    }

    public Optional<WebElement> getDeleteButton(Integer index) {
        Optional<WebElement> opt = getIndividualTodoItem(index);
        opt.ifPresent(element -> new Actions(driver).moveToElement(element).perform());

        return opt.flatMap(todoItem -> todoItem
                .findElements(buttonDeleteBy)
                .stream()
                .findFirst());
    }

    public Optional<WebElement> getToggleButton(Integer index) {
        Optional<WebElement> opt = getIndividualTodoItem(index);
        opt.ifPresent(element -> new Actions(driver).moveToElement(element).perform());

        return opt.flatMap(todoItem -> todoItem
                .findElements(toggleCheckboxBy)
                .stream()
                .findFirst());
    }

    public Optional<WebElement> getLabel(Integer index) {
        Optional<WebElement> opt = getIndividualTodoItem(index);
        opt.ifPresent(element -> new Actions(driver).moveToElement(element).perform());

        return opt.flatMap(todoItem -> todoItem
                .findElements(labelItemBy)
                .stream()
                .findFirst());
    }

    public Optional<WebElement> getIndividualTodoItem(Integer index) {
        List<WebElement> todoItems = getListTodoItems();
        if (index >= todoItems.size()) {
            return Optional.empty();
        }
        else {
            return Optional.of(todoItems.get(index));
        }
    }

    public Optional<WebElement> getItemCountElement() {
        List<WebElement> elements = driver.findElements(headingItemCounterBy);
        return elements.stream().findFirst();
    }

    public Optional<WebElement> getClearButton() {
        List<WebElement> elements = driver.findElements(buttonClearCompletedBy);
        return elements.stream().findFirst();
    }

    public Optional<WebElement> getFilterCompletedButton() {
        List<WebElement> elements = driver.findElements(buttonFilterCompletedBy);
        return elements.stream().findFirst();
    }

    public Optional<WebElement> getFilterActiveButton() {
        List<WebElement> elements = driver.findElements(buttonFilterActiveBy);
        return elements.stream().findFirst();
    }

    public Optional<WebElement> getFilterAllButton() {
        List<WebElement> elements = driver.findElements(buttonFilterAllBy);
        return elements.stream().findFirst();
    }

    public Optional<WebElement> getToggleAll() {
        List<WebElement> elements = driver.findElements(toggleSelectAllBy);
        return elements.stream().findFirst();
    }

    public List<WebElement> getListTodoItems() {
         return driver.findElements(listTodoItemsBy);
     }
     
}