package pages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckOutPage {
	//Variables
	WebDriver driver;
	WebDriverWait wait;
	String nombreCliente = "Waldemar Mezquita";
	String direcciónDeEnvio = "Ejemplo de dirección";
	String ciudad = "San Salvador";
	String pais = "United States";
	String estado = "San Salvad";
	String codigoPostal = "123456";
	String teléfono = "484844545";
	String numeroTarjeta = "2544 4444 4444";
	String cvv = "256";
	String anioExp = "2028";
	String mesExp = "11";
	String  NombreTarjeta= "Waldemar";
	
	
	//localizadores
	By orderPaymentLbl = By.xpath("//h3[contains(text(),'ORDER PAYMENT')]");
	
	//Shipping details
	By shippingDetailsLocator;
	By clientsNameLocator = By.xpath("//div[@id='userDetails']/div[1]/label");
	By clientsAddressLocator = By.xpath("//div[@id='userDetails']/div[2]/label[1]");
	By clientsCityLocator = By.xpath("//div[@id='userDetails']/div[2]/label[2]");
	By clientsCountryLocator = By.xpath("//div[@id='userDetails']/div[2]/label[3]");
	By clientsProvidenceLocator = By.xpath("//div[@id='userDetails']/div[2]/label[4]");
	By clientsZipCodeLocator = By.xpath("//div[@id='userDetails']/div[2]/label[5]");
	By clientsPhoneLocator = By.xpath("//div[@id='userDetails']/div[3]/label");
	
	By nextButnLocator = By.id("next_btn");
	
	//payment
	By editPaymentMethodLocator =  By.xpath("//div[@class='roboto-regular']/label[contains(text(),'Edit')]");
	By choosePaymentMthdLblLocator =  By.xpath("//label[@class='Choose_payment roboto-medium ng-scope']");
	By masterCardMethodLocator = By.name("masterCredit");
	By cardNumberLocator = By.id("creditCard");
	By cvvNumberLocator = By.name("cvv_number");
	By expMonthLocator = By.name("mmListbox");
	By expYearLocator = By.name("yyyyListbox"); 
	By payNowManualBtnLocator = By.id("pay_now_btn_ManualPayment");
	By payNowMasterCardBtnLocator = By.id("pay_now_btn_MasterCredit");
	
	By successOrderLocator = By.xpath("//div[@id='orderPaymentSuccess']/h2/span");

	public CheckOutPage(WebDriver driver) {
		this.driver =  driver;
	}
	
	public void completeOrder() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.presenceOfElementLocated(orderPaymentLbl));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(clientsNameLocator)));
		assertEquals("El nombre del cliente mostrado no corresponde a: " + nombreCliente, nombreCliente, driver.findElement(clientsNameLocator).getText());
		assertEquals("La dirección no coincide",direcciónDeEnvio,driver.findElement(clientsAddressLocator).getText());
		//assertEquals("La ciudad no coincide",ciudad,driver.findElement(clientsCityLocator));
		//assertEquals("El país no coincide",pais,driver.findElement(clientsCountryLocator));
		//assertEquals("El estado no coincide",estado,driver.findElement(clientsProvidenceLocator));
		//assertEquals("El código postal no coincide",codigoPostal,driver.findElement(clientsZipCodeLocator));
		driver.findElement(nextButnLocator).click();
		assertTrue("No se ha cargado pantalla de metodo de pago", driver.findElement(choosePaymentMthdLblLocator).isDisplayed());
		wait.until(ExpectedConditions.presenceOfElementLocated(masterCardMethodLocator));
		driver.findElement(masterCardMethodLocator).click();
		if(driver.findElement(editPaymentMethodLocator).isDisplayed()) {
			wait.until(ExpectedConditions.presenceOfElementLocated(payNowMasterCardBtnLocator));
			assertTrue("El botón de pagar no está habilitado o no se encuentra",driver.findElement(payNowMasterCardBtnLocator).isEnabled());
			driver.findElement(payNowMasterCardBtnLocator).click();
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(successOrderLocator)));
			assertTrue("no se muestra mensaje de exito",driver.findElement(successOrderLocator).isDisplayed());
			assertEquals("No se muestra el mensaje de exito esperado", "Thank you for buying with Advantage",driver.findElement(successOrderLocator).getText());
			
		}else {
			wait.until(ExpectedConditions.presenceOfElementLocated(cardNumberLocator));
			assertTrue("no se muestra campos par aingresar información de la tarjeta",driver.findElement(cardNumberLocator).isDisplayed());
			driver.findElement(cardNumberLocator).sendKeys(numeroTarjeta);
			driver.findElement(cvvNumberLocator).sendKeys(cvv);
			driver.findElement(expMonthLocator).sendKeys(mesExp);
			driver.findElement(expYearLocator).sendKeys(anioExp);
			assertTrue("El botón de pagar no está habilitado o no se encuentra",driver.findElement(payNowManualBtnLocator).isEnabled());
			driver.findElement(payNowManualBtnLocator).click();
			assertTrue(driver.findElement(successOrderLocator).isDisplayed());
			assertEquals("", "Thank you for buying with Advantage",driver.findElement(successOrderLocator).getText());
		}
	}
	
}
