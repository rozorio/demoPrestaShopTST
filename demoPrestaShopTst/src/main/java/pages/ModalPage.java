package pages;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
//import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class ModalPage {

	private WebDriver driver;
	private By titModal = new By.ById("myModalLabel");
	private By nomProd = new By.ByCssSelector("#blockcart-modal .modal-body .col-md-6:nth-child(2) h6");
	private By precoProd = new By.ByCssSelector("#blockcart-modal .modal-body .col-md-6:nth-child(2) .product-price");
	private By outrasInfos = new By.ByCssSelector("#blockcart-modal .modal-body .col-md-6:nth-child(2) strong");
	private By subTotal = new By.ByCssSelector(".cart-content p:nth-child(2) span.value");
	private By btnChckOut = new By.ByCssSelector(".cart-content-btn a");
	int i;
	
	public ModalPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String validarMsgSucesso() {
		/* FluentWait: espera pelo surgimento do modal
		 * withTimeout() --> tempo de espera;
		 * pollingEvery() --> tempo para check;
		 * ignoring() --> o que ignorar enquanto espera;
		 * until() --> evento esperado.
		 */
		
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(titModal));
		
		String msgSucesso = driver.findElement(titModal).getText();
		return msgSucesso;
	}
	
	public String obterNomeProduto() {
		return driver.findElement(nomProd).getText();
	}
	
	public String obterPrecoProduto() {
		return driver.findElement(precoProd).getText();
	}
	
	public String obterTamanho(){
		return driver.findElements(outrasInfos).get(0).getText();	
	}
	
	public String obterCor(){
			return driver.findElements(outrasInfos).get(1).getText();
	}
	
	public String obterQuantidade(){
		i = driver.findElements(outrasInfos).size();
		if(i == 3) {
			return driver.findElements(outrasInfos).get(2).getText();
		} else {
			return driver.findElements(outrasInfos).get(1).getText();
		}
	}
	
	public String obterSubTotal() {
		return driver.findElement(subTotal).getText();
	}
	
	public CarrinhoPage fazerChckOutPedido() {
		driver.findElement(btnChckOut).click();
		return new CarrinhoPage(driver);
	}
}
