package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = ".success td:nth-child(2)")
    private java.util.List<WebElement> productNames;

    @FindBy(css = ".success td:nth-child(3)")
    private List<WebElement> productPrices;

    @FindBy(css = "button[data-target='#orderModal']")
    private WebElement placeOrderButton;

    @FindBy(id = "totalp")
    private WebElement totalPrice;

    @FindBy(id = "name")
    private WebElement orderNameInput;

    @FindBy(id = "country")
    private WebElement orderCountryInput;

    @FindBy(id = "city")
    private WebElement orderCityInput;

    @FindBy(id = "card")
    private WebElement orderCardInput;

    @FindBy(id = "month")
    private WebElement orderMonthInput;

    @FindBy(id = "year")
    private WebElement orderYearInput;

    @FindBy(css = "button[onclick='purchaseOrder()']")
    private WebElement purchaseButton;

    @FindBy(css = ".sweet-alert h2")
    private WebElement orderConfirmationMessage;

    @FindBy(css = ".sweet-alert .lead")
    private WebElement orderDetails;

    @FindBy(css = ".sweet-alert h2")
    private WebElement orderResultMessage;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductInCart(String productName) {
        return productNames.stream()
                .anyMatch(p -> p.getText().equalsIgnoreCase(productName));
    }

    public double getProductPrice(String productName) {
        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).getText().equalsIgnoreCase(productName)) {
                return Double.parseDouble(productPrices.get(i).getText());
            }
        }
        return 0.0;
    }

    public double getTotalPrice() {
        return productPrices.stream()
                .mapToDouble(p -> Double.parseDouble(p.getText()))
                .sum();
    }

    public void placeOrder() {
        click(placeOrderButton);
        wait.until(ExpectedConditions.visibilityOf(orderNameInput));
    }

    public void completeOrder(String name, String country, String city,
                              String card, String month, String year) {
        type(orderNameInput, name);
        type(orderCountryInput, country);
        type(orderCityInput, city);
        type(orderCardInput, card);
        type(orderMonthInput, month);
        type(orderYearInput, year);
        click(purchaseButton);
    }

    public String getOrderConfirmationMessage() {
        wait.until(ExpectedConditions.visibilityOf(orderConfirmationMessage));
        return orderConfirmationMessage.getText();
    }

    public boolean isPlaceOrderButtonPresent() {
        wait.until(ExpectedConditions.visibilityOf(placeOrderButton));
        return true;
    }

    public boolean isTotalPricePresent() {
        wait.until(ExpectedConditions.visibilityOf(totalPrice));
        return true;
    }

    public boolean isCartEmpty() {
        return productNames.isEmpty();
    }

    public String getOrderDetails() {
        return orderDetails.getText();
    }

    public void clickPlaceOrder() {
        click(placeOrderButton);
    }

    public boolean isOrderModalVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(orderNameInput));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void attemptCompletePurchase(String name, String country, String city,
                                        String card, String month, String year) {
        type(orderNameInput, name);
        type(orderCountryInput, country);
        type(orderCityInput, city);
        type(orderCardInput, card);
        type(orderMonthInput, month);
        type(orderYearInput, year);
        click(purchaseButton);
    }
    public void deleteAllProducts() {
        try {
            // Implementar lógica para eliminar productos si existen
            if (!isCartEmpty()) {
                for (WebElement deleteButton : driver.findElements(By.cssSelector("a[onclick^='delete']"))) {
                    click(deleteButton);
                    wait.until(ExpectedConditions.stalenessOf(deleteButton));
                }
            }
        } catch (Exception e) {
            System.out.println("Error limpiando carrito: " + e.getMessage());
        }
    }

    public String getOrderResultMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(orderResultMessage));
            return orderResultMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
}