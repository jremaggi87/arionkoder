package org.example.tests;
import org.example.pages.HomePage;
import org.example.pages.SignUpPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignUpTest extends TestBase {

    @Test(priority = 1, description = "Registro exitoso de nuevo usuario")
    public void testSuccessfulSignUp() {
        String username = "user_" + System.currentTimeMillis(); // Usuario único
        String password = "Pass1234";

        HomePage homePage = new HomePage(driver);
        SignUpPage signUpPage = homePage.clickSignUp();
        signUpPage.signUp(username, password);
        Assert.assertTrue(signUpPage.isAlertPresentWithText("Sign up successful."));
    }

    @Test(priority = 2, description = "Intento de registro con usuario existente")
    public void testDuplicateUserSignUp() {
        String existingUser = "admin"; // Usuario previamente registrado
        String password = "admin";
        HomePage homePage = new HomePage(driver);
        SignUpPage signUpPage = homePage.clickSignUp();
        signUpPage.signUp(existingUser, password);
        Assert.assertTrue(signUpPage.isAlertPresentWithText("This user already exist."));
    }

    @Test(priority = 3, description = "Intento de registro sin completar campos")
    public void testEmptyFieldsSignUp() {
        HomePage homePage = new HomePage(driver);
        SignUpPage signUpPage = homePage.clickSignUp();
        signUpPage.clickSignUpButton(); // Hacer clic sin ingresar datos
        Assert.assertTrue(signUpPage.isAlertPresentWithText("Please fill out Username and Password"));
    }
}