package org.example.tests;

import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogoutTest extends TestBase {  // Asegúrate de extender TestBase

    private static final String VALID_USER = "testuser_logout";
    private static final String VALID_PASSWORD = "Test1234";

    @BeforeMethod
    public void loginUser() {
        // Crear usuario y hacer login antes de cada prueba
        HomePage homePage = new HomePage(driver);

        // Registrar usuario si no existe
        if (!homePage.isUserLoggedIn(VALID_USER)) {
            homePage.clickSignUp().signUp(VALID_USER, VALID_PASSWORD);
            driver.navigate().refresh();

            // Hacer login
            LoginPage loginPage = homePage.clickLogin();
            loginPage.login(VALID_USER, VALID_PASSWORD);

            // Verificar que el login fue exitoso
            Assert.assertTrue(homePage.isUserLoggedIn(VALID_USER),
                    "El usuario debería estar logueado antes de probar logout");
        }
    }

    @Test
    public void testLogout() {
        HomePage homePage = new HomePage(driver);

        // 1. Verificar estado PRE-logout
        Assert.assertTrue(homePage.isUserLoggedIn(VALID_USER),
                "El usuario debería estar logueado antes de logout");
        Assert.assertTrue(homePage.isLogoutLinkVisible(),
                "El link de logout debería ser visible cuando el usuario está logueado");

        // 2. Ejecutar logout
        homePage.clickLogout();

        // 3. Verificar estado POST-logout
        Assert.assertTrue(homePage.isLoginLinkVisible(),
                "El link de login debería ser visible después de logout");
        Assert.assertFalse(homePage.isUserLoggedIn(VALID_USER),
                "El mensaje de bienvenida debería desaparecer después de logout");
        Assert.assertFalse(homePage.isLogoutLinkVisible(),
                "El link de logout debería desaparecer después de logout");

        // 4. Verificar que no se puede acceder a páginas protegidas
        driver.get("https://www.demoblaze.com/cart.html");
        Assert.assertTrue(driver.getCurrentUrl().contains("index"),
                "Debería redirigir a la página principal cuando se accede a una ruta protegida sin login");
    }
}