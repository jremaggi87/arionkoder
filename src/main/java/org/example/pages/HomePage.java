package org.example.pages;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    // Elementos
    @FindBy(id = "signin2")
    private WebElement signUpLink;

    @FindBy(id = "login2")
    private WebElement loginLink;

    @FindBy(id = "cartur")
    private WebElement cartLink;

    @FindBy(css = ".hrefch")
    private List<WebElement> productLinks;

    @FindBy(id = "nameofuser")
    private WebElement welcomeUserLabel;

    @FindBy(id = "logout2")
    private WebElement logoutLink;

    @FindBy(partialLinkText = "Samsung galaxy s6")
    private WebElement samsungGalaxyLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Métodos
    public SignUpPage clickSignUp() {
        click(signUpLink);
        return new SignUpPage(driver);
    }

    public LoginPage clickLogin() {
        click(loginLink);
        return new LoginPage(driver);
    }

    public CartPage goToCart() {
        click(cartLink);
        return new CartPage(driver);
    }
    public ProductPage selectProduct(String productName) {
        click(samsungGalaxyLink);
        return new ProductPage(driver);
    }

    public boolean isUserLoggedIn(String username) {
        try {
            wait.until(ExpectedConditions.visibilityOf(welcomeUserLabel));
            System.out.println(welcomeUserLabel.getText());
            return welcomeUserLabel.getText().contains(username);
        } catch (Exception e) {
            return false;
        }
    }
    public void clickLogout() {
        click(logoutLink);
        wait.until(ExpectedConditions.invisibilityOf(welcomeUserLabel));
    }

    public boolean isLogoutLinkVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(logoutLink)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginLinkVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(loginLink)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}