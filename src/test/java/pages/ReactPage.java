package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ReactPage {
    protected WebDriver driver;

    // locators
    private By buttonDeleteBy = By.cssSelector("delete_button");
    private By inputFieldBy = By.cssSelector("input_field");
    private By editFieldBy = By.cssSelector("edit_field");
    private By listTodoItemsBy = By.cssSelector("todo_items_list");
    private By toggleCheckboxBy = By.cssSelector("toggle");
    private By toggleSelectAllBy = By.cssSelector("chevron");
    private By buttonFilterAll = By.cssSelector("all_button");
    private By buttonFilterActive = By.cssSelector("active_button");
    private By buttonFilterCompleted = By.cssSelector("completed_button");
    private By buttonClearCompleted = By.cssSelector("clear_completed_button");
    private By headingItemCounter = By.cssSelector("item_counter");

    // methods
    public void deleteItem() {
        WebElement deleteButton = driver.findElement(deleteButtonBy);
        deleteButton.click();
    }

}