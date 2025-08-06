package org.example.tests;

import org.example.pages.CartPage;
import org.example.pages.HomePage;
import org.example.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CompleteOrderTest extends TestBase {

    private static final String PRODUCT_NAME = "Samsung galaxy s6";
    private static final String ORDER_NAME = "Jose";
    private static final String COUNTRY = "Argentina";
    private static final String CITY = "Mendoza";
    private static final String CARD = "424242424242";
    private static final String MONTH = "12";
    private static final String YEAR = "2025";

    @Test(description = "Completar una orden de compra exitosamente")
    public void testCompleteOrder() {
        HomePage homePage = new HomePage(driver);

        // 1. Seleccionar un producto
        ProductPage productPage = homePage.selectProduct(PRODUCT_NAME);
        String productName = productPage.getProductName();
        double productPrice = productPage.getProductPrice();
        int productPriceInt= (int) Math.floor(productPrice);
        // 2. Agregar al carrito
        productPage.addToCart();
        // 3. Ir al carrito
        CartPage cartPage = homePage.goToCart();
        cartPage.isTotalPricePresent();
        // 4. Verificar producto en carrito
        Assert.assertTrue(cartPage.isProductInCart(PRODUCT_NAME),
                "El producto debe estar en el carrito");
        Assert.assertEquals(cartPage.getProductPrice(PRODUCT_NAME), productPrice,
                "El precio debe coincidir");

        // 5. Iniciar el proceso de orden
        cartPage.placeOrder();

        // 6. Completar formulario de orden
        cartPage.completeOrder(ORDER_NAME, COUNTRY, CITY, CARD, MONTH, YEAR);

        // 7. Verificar confirmación de orden
        String confirmationMessage = cartPage.getOrderConfirmationMessage();
        Assert.assertEquals(confirmationMessage, "Thank you for your purchase!",
                "Debería mostrar el mensaje de compra exitosa");

        // 8. Verificar detalles de la orden
        String orderDetails = cartPage.getOrderDetails();
        Assert.assertTrue(orderDetails.contains(String.valueOf(ORDER_NAME)),
                "Los detalles deben incluir el nombre del comprador");
        Assert.assertTrue(orderDetails.contains(String.valueOf(productPriceInt)),
                "Los detalles deben incluir el precio del producto");
        Assert.assertTrue(orderDetails.contains(CARD),
                "Los detalles deben incluir el numero de tarjeta de credito");
    }
}