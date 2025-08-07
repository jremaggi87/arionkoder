package org.example.tests;

import org.example.pages.HomePage;
import org.example.pages.LoginPage;import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {

    private static final String VALID_USER = "jremaggi";
    private static final String VALID_PASSWORD = "asdf";

    @Test(description = "Login test")
    @Description("Login test")
    public void testSuccessfulLogin() {
        HomePage homePage = new HomePage(driver);
        System.out.println("URL antes de login: " + driver.getCurrentUrl());

        LoginPage loginPage = homePage.clickLogin();
        System.out.println("Haciendo clic en login...");

        loginPage.login(VALID_USER, VALID_PASSWORD);
        System.out.println("Credenciales ingresadas...");

        System.out.println("URL después de login: " + driver.getCurrentUrl());
        System.out.println("Verificando estado de login...");

        boolean isLoggedIn = homePage.isUserLoggedIn(VALID_USER);
        System.out.println("Resultado de isUserLoggedIn: " + isLoggedIn);

        Assert.assertTrue(isLoggedIn, "El usuario debería estar logueado");
    }

    @Test(priority = 2, description = "Error al intentar login sin datos")
    public void testLoginWithEmptyFields() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickLogin();
        loginPage.clickLoginButton(); // Clic sin ingresar datos

        Assert.assertTrue(loginPage.isAlertPresentWithText("Please fill out Username and Password."),
                "Debería mostrar error por campos vacíos");
    }

    @Test(priority = 3, description = "Error al intentar login con usuario inexistente")
    public void testLoginWithNonExistingUser() {
        String nonExistingUser = "usuario_inexistente_" + System.currentTimeMillis();

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickLogin();
        loginPage.login(nonExistingUser, "anypassword");

        Assert.assertTrue(loginPage.isAlertPresentWithText("User does not exist."),
                "Debería mostrar error por usuario inexistente");
    }

    @Test(priority = 4, description = "Error al intentar login con contraseña incorrecta")
    public void testLoginWithWrongPassword() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickLogin();
        loginPage.login(VALID_USER, "password_incorrecta");

        Assert.assertTrue(loginPage.isAlertPresentWithText("Wrong password."),
                "Debería mostrar error por contraseña incorrecta");
    }
}
