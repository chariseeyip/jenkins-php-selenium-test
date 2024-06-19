package com.mycompany.app;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppTest {
    WebDriver driver; 
    WebDriverWait wait; 
    String url = "http://localhost";
    String validEmail = "user@example.com";
    String validPassword = "password1234";
    String invalidEmail = "none@example.com";
    String invalidPassword = "password";

    @Before
    public void setUp() { 
        driver = new HtmlUnitDriver();
        wait = new WebDriverWait(driver, 10); 
    } 

    @After
    public void tearDown() { 
        driver.quit(); 
    }   

    @Test
    public void testLoginWithValidEmailValidPassword() throws InterruptedException { 
        System.out.println("Navigating to login page");
        driver.get(url);
        System.out.println("Waiting for login page title");
        wait.until(ExpectedConditions.titleContains("Login Page |")); 

        System.out.println("Entering valid credentials");
        driver.findElement(By.name("email")).sendKeys(validEmail);
        driver.findElement(By.name("password")).sendKeys(validPassword);
        driver.findElement(By.name("submit")).submit();

        System.out.println("Waiting for dashboard page title");
        String expectedResult = "Dashboard |"; 
        boolean isResultCorrect = wait.until(ExpectedConditions.titleContains(expectedResult)); 
        assertTrue(isResultCorrect); 
    }

    @Test
    public void testLoginWithValidEmailInvalidPassword() throws InterruptedException { 
        System.out.println("Navigating to login page");
        driver.get(url);
        System.out.println("Waiting for login page title");
        wait.until(ExpectedConditions.titleContains("Login Page |")); 

        System.out.println("Entering valid email and invalid password");
        driver.findElement(By.name("email")).sendKeys(validEmail);
        driver.findElement(By.name("password")).sendKeys(invalidPassword);
        driver.findElement(By.name("submit")).submit();

        System.out.println("Waiting for error message");
        By errorMsgId = By.className("error-msg");
        String expectedResult = "Login failed"; 
        boolean isResultCorrect = wait.until(ExpectedConditions.textToBe(errorMsgId, expectedResult)); 
        assertTrue(isResultCorrect); 
    }
}