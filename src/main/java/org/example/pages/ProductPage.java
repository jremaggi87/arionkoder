package org.example.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage {

    @FindBy(css = ".name")
    private WebElement productNameElement;

    @FindBy(css = ".price-container")
    private WebElement productPriceElement;

    @FindBy(linkText = "Add to cart")
    private WebElement addToCartButton;

    @FindBy(css = ".hrefch")
    private WebElement productLink;

    public ProductPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(productNameElement));
    }

    public String getProductName() {
        return productNameElement.getText();
    }

    public double getProductPrice() {
        String priceText = productPriceElement.getText();
        String numericPart = priceText.replaceAll("[^0-9.]", "");
        if (numericPart.isEmpty()) {
            throw new RuntimeException("No se pudo extraer precio numérico de: " + priceText);
        }
        return Double.parseDouble(numericPart);
    }

    public void addToCart() {
        click(addToCartButton);
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }
}