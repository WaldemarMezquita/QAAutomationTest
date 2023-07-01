package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.CheckOutPage;
import pages.HomePage;
import pages.ShoppingCartPage;

public class LoginTest {
	WebDriver driver;
	HomePage homepage;
	ShoppingCartPage shoppinCartPage;
	CheckOutPage checkoutPage;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://advantageonlineshopping.com/");
		homepage = new HomePage(driver);
		shoppinCartPage =  new ShoppingCartPage(driver);
		checkoutPage = new CheckOutPage(driver);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void loginSuccesfully() throws InterruptedException {
		homepage.login();
	}
	
	@Test
	public void addProductToCart() throws InterruptedException {
		homepage.login();
		homepage.addProductToCart();
	}
	
	@Test
	public void removeProductFromCart() throws InterruptedException {
		homepage.login();
		homepage.addProductToCart();
		homepage.GoToShoppingCart();
		shoppinCartPage.removeProductFromCart();
	}
	
	@Test
	public void completePurchase() throws InterruptedException {
		homepage.login();
		homepage.addProductToCart();
		homepage.GoToShoppingCart();
		shoppinCartPage.validateProductInCart();
		shoppinCartPage.goToCheckout();
		checkoutPage.completeOrder();
	}

}
