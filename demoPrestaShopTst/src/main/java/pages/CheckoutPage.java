package pages;

import org.openqa.selenium.By;
//import org.openqa.selenium.By.ByClassName;
//import org.openqa.selenium.By.ByCssSelector;
//import org.openqa.selenium.By.ByName;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.By.ByCssSelector;

public class CheckoutPage {

	private WebDriver driver;
	
	private By subTotalChkt = new By.ByCssSelector("#cart-subtotal-products .value") ;
	private By totalTIChkt = new By.ByCssSelector(".cart-total .value") ;
	private By shipValChkt = new By.ByCssSelector("#cart-subtotal-shipping .value") ;
	private By tituloAddress = new By.ByCssSelector("#checkout-addresses-step .step-title") ;
	private By addrRadio = new By.ByName("id_address_delivery") ;
	private By address = new By.ByClassName("address");
	private By btnAddrsCont = new By.ByName("confirm-addresses");
	private By tituloShipMet = new By.ById("checkout-delivery-step");
	private By carrRadio = new By.ByCssSelector(".delivery-options .custom-radio #delivery_option_2") ;
	private By tipoCarrier = new By.ByClassName("carrier-name") ;
	private By precoCarr = new By.ByClassName("carrier-price") ;
	private By btnShipCnt = new By.ByName("confirmDeliveryOption");
	private By titPayment = new By.ByCssSelector("#checkout-payment-step .step-title") ;
	private By radPayCheck = new By.ById("payment-option-1") ;
	private By valAmount = new By.ByXPath("//div[contains(@id,\"payment-option-1-additional-information\")]//dd[1]");
	private By termsAgree = new By.ByXPath("//span[contains(@class,'custom-checkbox')]//input");
	private By btnOrder = new By.ById("payment-confirmation");
	
	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String obterSubTotal() {
		return driver.findElement(subTotalChkt).getText();
	}
	
	public String obterShippingValChkt() {
		return driver.findElement(shipValChkt).getText();
	}
	
	public String obterTotalTI() {
		return driver.findElement(totalTIChkt).getText();
	}
	
	public String obterTituloAddresses() {
		return driver.findElement(tituloAddress).getText();
	}
	
	public Boolean obterAddrRadioChkd() {
		return driver.findElement(addrRadio).isSelected();
	}
	
	public String obterAddress() {
		return driver.findElement(address).getText();
	}
	
	public void clicarAddressContinue() {
		driver.findElement(btnAddrsCont).click();
	}
	
	public String obterTitShipMthd() {
		return driver.findElement(tituloShipMet).getText();
	}
	
	public String obterTipoCarrier() {
		return driver.findElement(tipoCarrier).getText();
	}
	
	public Boolean obterTipoCarrChkd() {
		return driver.findElement(carrRadio).isSelected();
	}
	
	public String obterPrecoCarrier() {
		return driver.findElement(precoCarr).getText();
	}
	
	public void clicarShippingMthdCont() {
		driver.findElement(btnShipCnt).click();
	}
	
	public String obterTitPayment() {
		return driver.findElement(titPayment).getText();
	}
	
	public void clicarPaymentCheck() {
		driver.findElement(radPayCheck).click();
	}
	
	public Boolean obterPaymentCheckChkd() {
		return driver.findElement(radPayCheck).isSelected();
	}
	
	public String obterAmountPayment() {
		return driver.findElement(valAmount).getText();
	}
	
	public void clicarIAgree() {
		driver.findElement(termsAgree).click();
	}
	
	public Boolean obterIAgreeChkd() {
		return driver.findElement(termsAgree).isSelected();
	}
	
	public OrderPage clicarBtnOrder() {
		driver.findElement(btnOrder).click();
		return new OrderPage(driver);
	}
	 
	
}
