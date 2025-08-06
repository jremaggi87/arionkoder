package org.example.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignUpPage extends BasePage {

    @FindBy(id = "sign-username")
    private WebElement usernameInput;

    @FindBy(id = "sign-password")
    private WebElement passwordInput;

    @FindBy(css = "button[onclick='register()']")
    private WebElement signUpButton;

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public void signUp(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(signUpButton);
    }

    // Método nuevo para hacer clic en el botón
    public void clickSignUpButton() {
        click(signUpButton);
    }

    // Método nuevo para verificar alertas
    public boolean isAlertPresentWithText(String expectedText) {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            String alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            return alertText.contains(expectedText);
        } catch (Exception e) {
            return false;
        }
    }
}