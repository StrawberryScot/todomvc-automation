package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    private By inputFieldBy = By.id("todo-input");
    private By listTodoItemsBy = By.className("todo-list");
    private By toggleSelectAllBy = By.id("toggle-all");
    private By buttonFilterAll = By.linkText("All");
    private By buttonFilterActive = By.linkText("Active");
    private By buttonFilterCompleted = By.linkText("Completed");
    private By buttonClearCompleted = By.cssSelector(".clear-completed");
    private By headingItemCounter = By.className("todo-count");

    // methods
    public void deleteItem() {
        WebElement deleteButton = driver.findElement(deleteButtonBy);
        deleteButton.click();
    }

}