package org.example.tests;

import org.example.pages.CartPage;
import org.example.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EmptyCartTest extends TestBase {

    @Test(description = "Verificar que no se puede completar pedido con carrito vacío")
    public void testCannotPlaceOrderWithEmptyCart() {
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = homePage.goToCart();

        // 1. Verificar que el carrito está vacío
        Assert.assertTrue(cartPage.isCartEmpty(), "El carrito debería estar vacío");

        // 2. Intentar hacer clic en Place Order
        cartPage.clickPlaceOrder();

        // 3. Verificar que el modal de pedido NO se abre
        Assert.assertFalse(cartPage.isOrderModalVisible(),
                "El modal de pedido no debería abrirse cuando el carrito está vacío");
    }

}