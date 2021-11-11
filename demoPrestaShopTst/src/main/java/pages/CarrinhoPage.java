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
	
	public String obterQtdeProd() {
		return driver.findElement(qtdeProd).getAttribute("value");
	}
	
	public String obterSubTotProd() {
		return driver.findElement(subTotProd).getText();
	}
}
