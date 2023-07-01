package pages;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

public class HomePage {
	//decaración de variables
	WebDriver driver;
	WebDriverWait wait;
	WebElement e;
	Actions actions;
	/*Properties props;
	FileReader reader;*/
	String username = "testsv01";
	String password = "Test123";
	String product2 = "HP Pro Tablet 608 G1";
	
	
	
	
	//declaración de localizadores
	//localizadores del home
	By loginMenuLocator = By.id("menuUserLink");
	By userLabelLocator = By.xpath("//a[@id='menuUserLink']/span[contains(text(),'" + username + "')]");
	By loaderLocator = By.xpath("//body/div[@class='loader']");
	
	
	//localizadores en el tooltip del carrito
	By shoppinCartLocator = By.id("shoppingCartLink");
	By checkoutBtnTooltipLocator = By.name("check_out_btn");
	By removeProductLocator = By.xpath("//ul[@class='roboto-light desktop-handler']/li[@data-ng-mouseenter='enterCart()']/ul/li/tool-tip-cart/div/table/tbody/tr/td[child::a/h3[contains(text(),'" + product2.toUpperCase() + "')]]/following-sibling::td/child::div/div[@class='removeProduct iconCss iconX']");
	By tooltipCartLocator = By.id("toolTipCart");
	By productInTooltipLocator = By.xpath("//h3[contains(text(),'" + product2.toUpperCase() + "')]");
	
	//localizadores en carrito de compra
	By shoppingCartLabelLocator = By.xpath("//h3[contains(text(),'SHOPPING CART')]");
	
	//localizadores del login
	By usernameLoginLocator = By.name("username");
	By passwordLoginLocator = By.name("password");
	By rememberMeLoginLocator = By.name("remember_me");
	By signInBtnLocator = By.id("sign_in_btn");
	By signInResultMessageLocator = By.id("signInResultMessage");
	By loginLoaderLocator = By.xpath("//login-modal/div[@class='PopUp']/div/div[@class='loader']");
	
	//localizadores de items
	By tabletsCategoryLocator = By.id("tabletsImg");
	By categoryLabelLocator = By.xpath("//h3[contains(text(),'TABLETS')]");
	By producToAddLocator = By.xpath("//li[p/a[contains(text(),'" + product2 + "')]]");
	By productToAddLabelLocator = By.xpath("//div[@id='Description']/h1[contains(text(),'" + product2.toUpperCase() +"')]");
	
	By addToCartBtn = By.xpath("//button[@name='save_to_cart']");
	
	
	//constructor
	public HomePage(WebDriver driver) throws IOException {
		this.driver=driver;
		/*props = new Properties();
		reader = new FileReader("./src/test/resources/resources/Data.properties");
		props.load(reader);*/
		
		
	}
	
	//Métodos y funciones
	
	//Método para mostrar pantalla de login
	public void login() throws InterruptedException {
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		//espera a que se oculte el loader y valida que se muestre el icono del login y luego le hace clic
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(loaderLocator)));
		assertTrue("no se muestra el icono para abrir el login. ",driver.findElement(loginMenuLocator).isDisplayed());
		driver.findElement(loginMenuLocator).click();
		
		//espera que se oculte loader 
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(loginLoaderLocator)));
		
		//espera hasta que se muestre el campo de username y valide que se estre mostrando dicho campo
		wait.until(ExpectedConditions.visibilityOfElementLocated(usernameLoginLocator));
		assertTrue(driver.findElement(usernameLoginLocator).isDisplayed());
		
		//una vez se muestra el campo de username ingresa los valores de username y password
		driver.findElement(usernameLoginLocator).sendKeys(username);
		driver.findElement(passwordLoginLocator).sendKeys(password);
		
		//Espera que se oculte loader, después valida que esté habilitado el botón SignIn y después hace clic en el botón
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(loginLoaderLocator)));
		assertTrue(driver.findElement(signInBtnLocator).isEnabled());
		driver.findElement(signInBtnLocator).click();
	
		//Valida si hay error al iniciar sesión
		if(driver.findElement(signInResultMessageLocator).isDisplayed()) {
			//valida si el signInResultLocator tiene un mensaje de error
			if(driver.findElement(signInResultMessageLocator).getText().equalsIgnoreCase("Incorrect user name or password.")) {
				//si tiene mensaje de error, imprime en consola que el usuario no está registrado
				System.out.println("Usuario no registrado, por favor registrarlo primero");
				
			}else {
				//si no hay mensaje de error espera que se oculte la ventana de login y valide que se muestre el label con el nombre del usuario
				wait.until(ExpectedConditions.invisibilityOfElementLocated(signInBtnLocator));
				assertTrue(driver.findElement(userLabelLocator).isDisplayed());
				System.out.println("El usuario logueado es: " + driver.findElement(userLabelLocator).getText());
				assertTrue(driver.findElement(userLabelLocator).getText().equalsIgnoreCase(username));
				
			}
		}else {
			System.out.println("No se encuentra el label con id=signInResultMessage");
			
		}
			
		
	}
	
	//método para agregar un producto al carrito
	public void addProductToCart() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		actions =  new Actions(driver);
		
		driver.findElement(tabletsCategoryLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(categoryLabelLocator));
		assertTrue("No se muestra label de la categoria", driver.findElement(categoryLabelLocator).isDisplayed());
		assertTrue("No se encuentra el producto: " + product2, driver.findElement(producToAddLocator).isDisplayed());
		System.out.println("producto label: " + productToAddLabelLocator);
		actions.moveToElement(driver.findElement(producToAddLocator)).perform();
		System.out.println("usr: " + userLabelLocator);
		driver.findElement(producToAddLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(productToAddLabelLocator));
		assertTrue("Ha habido un error y no se ha cargado la página del producto: " + product2, driver.findElement(productToAddLabelLocator).isDisplayed());
		assertTrue("No se encuentra el botón 'Add to Cart'",driver.findElement(addToCartBtn).isDisplayed());
		driver.findElement(addToCartBtn).click();
		wait.until(ExpectedConditions.attributeContains(tooltipCartLocator, "display", "block"));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(productInTooltipLocator)));
		assertTrue("No se muestra producto en tooltip del carrito", driver.findElement(productInTooltipLocator).isDisplayed());
	}
	
	//método para quitar un producto del carrito
	public void GoToShoppingCart() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(shoppinCartLocator));
		assertTrue("No se muestra icono de carrito de compra.",driver.findElement(shoppinCartLocator).isDisplayed());
		driver.findElement(shoppinCartLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(shoppingCartLabelLocator));
		assertTrue("No se cargó pantalla de carrito de compra.", driver.findElement(shoppingCartLabelLocator).isDisplayed());
		
	}
	
}
