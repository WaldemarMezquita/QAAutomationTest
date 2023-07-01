package pages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShoppingCartPage {
	//decaración de variables
	WebDriver driver;
	String product = "HP Pro Tablet 608 G1";
	String price = "$479.00";
	String quantity = "1";
	
	WebDriverWait wait;
	/*Properties props;
	FileReader reader;*/
	
	
	//declaración de localizadores
	By shoppingCartLabelLocator = By.xpath("//h3[contains(text(),'SHOPPING CART')]");
	By checkoutBtnLocator = By.id("checkOutButton");
	By quantityOfProductsLocator = By.xpath("//span[@ng-show='cart.productsInCart.length > 0']");
	//By removeProductLocator = By.xpath("//a[contains(text(),'REMOVE')]");
	By removeProductFromCartLocator = By.xpath("//td[label[contains(text(),'" + product.toUpperCase() + "')]]/following-sibling::td/child::span[@class='actions']/a[contains(text(),'REMOVE')]");
	By productInCartLocator = By.xpath("//label[contains(text(),'"+product.toUpperCase()+"')]");
	By emptyCartLabelLocator = By.xpath("//div[@id='shoppingCart']/div/label[contains(text(),'Your shopping cart is empty')]");
	 
	
	By productNameLocator = By.xpath("//label[@class='roboto-regular productName ng-binding']");
	By quantityLocator = By.xpath("//td[@class='smollCell quantityMobile']/label[2]");
	By priceLocator = By.xpath("//td[@class='smollCell']/p[@class='price roboto-regular ng-binding']");
	
	By orderPaymentLbl = By.xpath("//h3[contains(text(),'ORDER PAYMENT')]");
	
	//constructor
	public ShoppingCartPage(WebDriver driver) throws IOException {
		this.driver=driver;	
	}
		
	//Métodos y funciones
	public void removeProductFromCart() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		assertFalse("No hay productos agregados", driver.findElement(emptyCartLabelLocator).isDisplayed());
		assertTrue("No se encontró botón para eliminar el producto " + product, driver.findElement(removeProductFromCartLocator).isDisplayed());
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(removeProductFromCartLocator)));
		driver.findElement(removeProductFromCartLocator).click();
		//assertFalse("No se ha eliminado el producto: " + product,driver.findElement(productInCartLocator).isDisplayed());
		System.out.println("Se eliminó el producto: " + product);
	}
	
	public void validateProductInCart() {
		assertEquals("El nombre del producto no es el mismo",product.toUpperCase(), driver.findElement(productNameLocator).getText().toUpperCase());
		//assertEquals("La cantidad del producto no es la mismo",quantity, driver.findElement(quantityLocator).getText());
		//assertEquals("El precio del producto no es el mismo",price, driver.findElement(priceLocator).getText());
	}
	
	public void goToCheckout() {
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		assertTrue("No se muestra botón checkout",driver.findElement(checkoutBtnLocator).isDisplayed());
		driver.findElement(checkoutBtnLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(orderPaymentLbl));
		assertTrue("No se ha cargado la página de checkout", driver.findElement(orderPaymentLbl).isDisplayed());
	}
	
}
