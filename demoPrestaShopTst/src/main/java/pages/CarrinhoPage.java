package pages;

import org.openqa.selenium.By;
//import org.openqa.selenium.By.ByClassName;
//import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.WebDriver;

public class CarrinhoPage {

	private WebDriver driver;
	private By nomeProd = new By.ByCssSelector("div.product-line-info a");
	private By precoProd = new By.ByCssSelector("div.current-price span");
	private By tamProd = new By.ByXPath("//div[contains(@class,'product-line-grid-body')]//div[3]//span[contains(@class,'value')]") ;
	private By corProd = new By.ByXPath("//div[contains(@class,'product-line-grid-body')]//div[4]//span[contains(@class,'value')]") ;
	private By qtdeProd = new By.ByCssSelector("input.js-cart-line-product-quantity");
	private By subTotProd = new By.ByCssSelector("div.col-md-6 span.product-price strong") ;
	private By qtdeTP = new By.ByClassName("js-subtotal") ;
	private By subTotTP = new By.ByCssSelector("div.cart-summary div#cart-subtotal-products span.value");
	private By shipTP = new By.ByCssSelector("div.cart-summary div#cart-subtotal-shipping span.value") ;
	private By totTaxETP = new By.ByCssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(1) span.value") ;
	private By totTaxITP = new By.ByCssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(2) span.value") ;
	private By taxesTP = new By.ByCssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(3) span.value") ;
	
	
	
	
	public CarrinhoPage (WebDriver driver) {
		this.driver = driver;
	}
	
	public String obterNomeProd() {
		return driver.findElement(nomeProd).getText();
	}
	
	public String obterPrecoProd() {
		return driver.findElement(precoProd).getText();
	}
	
	public String obterTamProd() {
		return driver.findElement(tamProd).getText();
	}
	
	public String obterCorProd() {
		return driver.findElement(corProd).getText();
	}
	
	public Integer obterQtdeProd() {
		String qtdeProdStr = driver.findElement(qtdeProd).getAttribute("value");
		Integer qtdeProdInt = Integer.parseInt(qtdeProdStr);
		return qtdeProdInt;
	}
	
	public String obterSubTotProd() {
		return driver.findElement(subTotProd).getText();
	}
	
	
	public String obterQtdeTP() {
		return driver.findElement(qtdeTP).getText();
	}
	
	public String obterSubTotTP() {
		return driver.findElement(subTotTP).getText();
	}
	
	public String obterShipTP() {
		return driver.findElement(shipTP).getText();
	}
	
	public String obterTotTaxETP() {
		return driver.findElement(totTaxETP).getText();
	}
	
	public String obterTotTaxITP() {
		return driver.findElement(totTaxITP).getText();
	}
	
	public String obtertaxesTP() {
		return driver.findElement(taxesTP).getText();
	}
	
}
