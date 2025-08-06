package org.example.tests;

import org.example.pages.CartPage;
import org.example.pages.HomePage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestBase {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected HomePage homePage;

    // 1. Configuración inicial del driver
    @BeforeClass
    public void globalSetup() {
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-dev-shm-usage"); // Evita problemas de memoria
        options.addArguments("--no-sandbox"); // Necesario para CI

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Aumentado a 20 segundos
    }

    // 2. Precondiciones antes de cada test - MEJORADO
    @BeforeMethod
    public void methodSetup() {
        driver.manage().deleteAllCookies();
        driver.get("https://www.demoblaze.com");
        waitForPageToLoad();
        if (!(this instanceof EmptyCartTest)) {  // No limpiar carrito para EmptyCartTest
            clearCart();
        }
        homePage = new HomePage(driver);
    }
    private void clearCart() {
        try {
            driver.get("https://www.demoblaze.com/cart.html");
            new CartPage(driver).deleteAllProducts();
            driver.navigate().back();
        } catch (Exception e) {
            System.out.println("No se pudo limpiar el carrito: " + e.getMessage());
        }
    }
    // 3. Métodos de utilidad reutilizables - MEJORADOS
    private void waitForPageToLoad() {
        // Esperar a que document.readyState sea "complete"
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));

        // Esperar adicional para estabilización
        try {
            Thread.sleep(500); // Espera de medio segundo
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // 4. Manejo de screenshots en fallos - CORREGIDO
    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                // Crear directorio si no existe
                new File("./screenshots").mkdirs();

                // Tomar screenshot
                File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(
                        srcFile,
                        new File("./screenshots/" + result.getName() + "_" + System.currentTimeMillis() + ".png")
                );
                System.out.println("Screenshot capturado: " + result.getName());
            } catch (IOException e) {
                System.err.println("Error al capturar screenshot: " + e.getMessage());
            }
        }
    }

    // 5. Cierre completo - MEJORADO
    @AfterClass
    public void globalTeardown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver cerrado correctamente");
        }
    }
}