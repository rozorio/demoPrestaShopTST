package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import util.Funcoes;

public class OrderPage {

	private WebDriver driver;

	private By titPedido = new By.ByCssSelector("#content-hook_order_confirmation .h1.card-title");
	private By email = new By.ByCssSelector("#content-hook_order_confirmation p");
	private By totProds = new By.ByCssSelector("div.order-confirmation-table div.bold");
	private By totTxInc = new By.ByCssSelector(".total-value td:nth-child(2)");
	private By pymntMethod = new By.ByCssSelector("#order-details li:nth-child(2)");

	public OrderPage(WebDriver driver) {
		this.driver = driver;
	}

	public String obterTitPedido() {
		return driver.findElement(titPedido).getText();
	}

	public String obterEmail() {
		String strEmail = driver.findElement(email).getText();
		strEmail = Funcoes.limparTexto(strEmail, "An email has been sent to the ");
		strEmail = Funcoes.limparTexto(strEmail, " address.");
		return strEmail;
	}

	public Double obterTotProds() {
		return Funcoes.tratarPrecoToDouble(driver.findElement(totProds).getText());
	}

	public Double obterTotTxInc() {
		return Funcoes.tratarPrecoToDouble(driver.findElement(totTxInc).getText());
	}

	public String obterPymntMethod() {
		String pymnt = driver.findElement(pymntMethod).getText();
		pymnt = Funcoes.limparTexto(pymnt, "Payment method: Payments by ");
		return pymnt;
	}

}
