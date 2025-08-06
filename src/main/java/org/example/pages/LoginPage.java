package org.example.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    @FindBy(id = "loginusername")
    private WebElement usernameInput;

    @FindBy(id = "loginpassword")
    private WebElement passwordInput;

    @FindBy(css = "button[onclick='logIn()']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(usernameInput));
    }

    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public boolean isAlertPresentWithText(String expectedText) {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            String alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            return alertText.equals(expectedText);
        } catch (Exception e) {
            return false;
        }
    }
}