package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

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

    // navigation
    public void navigate() {
        driver.get("https://todomvc.com/examples/react/dist");
    }

    // methods
    public void deleteItem() {
        WebElement deleteButton = driver.findElement(deleteButtonBy);
        deleteButton.click();
    }

    public String fetchItemCountText() {
        WebElement headingItemCounter = driver.findElement(headingItemCounterBy);
        return headingItemCounter.getText();
    }

    public void clickClearButton() {
        WebElement buttonClearCompleted = driver.findElement(buttonClearCompletedBy);
        buttonClearCompleted.click();
    }


    public void clickFilterCompletedButton() {
        WebElement buttonFilterCompleted = driver.findElement(buttonFilterCompletedBy);
        buttonFilterCompleted.click();
    }

    public void clickFilterActiveButton() {
        WebElement buttonFilterActive = driver.findElement(buttonFilterActiveBy);
        buttonFilterActive.click();
    }

    public void clickFilterAllButton() {
        WebElement buttonFilterAll = driver.findElement(buttonFilterAllBy);
        buttonFilterAll.click();
    }

    public void toggleAll() {
        WebElement toggleSelectAll = driver.findElement(toggleSelectAllBy);
        toggleSelectAll.click();
    }

    public List<String> listTodoItems() {
        WebElement listTodoItems = driver.findElement(listTodoItemsBy);
        listTodoItems.

    }


    public void createNewTodo(String newTodo) {
        WebElement inputField = driver.findElement(inputFieldBy);
        inputField.sendKeys(newTodo);
        inputField.sendKeys(Keys.ENTER);
    }




}