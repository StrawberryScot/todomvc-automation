package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.sql.Array;
import java.util.List;
import java.util.Optional;

public class ReactPage {
    protected WebDriver driver;

    // locators
    private By singleButtonDeleteBy = By.cssSelector(".destroy");
    private By singleToggleCheckboxBy = By.cssSelector(".toggle");
     private By singleLabelItemBy = By.cssSelector(".view > label");
    // individual items above

    private By buttonDeleteBy = By.cssSelector("li:nth-child(2) .destroy");
    private By toggleCheckboxBy = By.cssSelector("li:nth-child(2) .toggle");
    private By labelItemBy = By.cssSelector("li:nth-child(2) label");
    private By editItemBy = By.cssSelector(".input-container:nth-child(1) > #todo-input");
    // above is index specific. MUST CHANGE

    private final By inputFieldBy = By.id("todo-input");
    private final By listTodoItemsBy = By.className("todo-list");
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
        opt.ifPresent(WebElement::click);
    }

    //public void deleteItem() {
    //    WebElement deleteButton = driver.findElement(deleteButtonBy);
    //    deleteButton.click();
    //}

    public void createNewTodo(String newTodo) {
        WebElement inputField = driver.findElement(inputFieldBy);
        inputField.sendKeys(newTodo);
        inputField.sendKeys(Keys.ENTER);
    }

    // GET methods

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