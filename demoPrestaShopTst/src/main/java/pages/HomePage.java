package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
//import org.openqa.selenium.By.ByCssSelector;
//import org.openqa.selenium.By.ByCssSelector;
//import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/*
 * HomePage: classe que reúne métodos de interação com a página própriamente dita
 */

public class HomePage {

	/*
	 * private WebDriver (...): Cria variável driver que será parametrizada pela classe BaseTest;
	 * List<WebElement> (...): Cria vetor que irá registrar elementos encontrados na tela
	 * private By (...): Cria obj do tipo By para parametrizar o método findElements
	 * 
	 */
	
	private WebDriver driver;
	List<WebElement> listaProds = new ArrayList<WebElement>();     //List: biblioteca que não faz parte do java padrão (como Integer, Double...), integra a java.util
	private By produtos = new By.ByClassName("product-description");
	private By carrinho = new By.ByClassName("cart-products-count");
	private By descProd = new By.ByCssSelector(".product-description a");
	private By valProd = new By.ByCssSelector(".price");
	private By btnSignIn = new By.ByCssSelector("#_desktop_user_info .hidden-sm-down");
	private By btnSignOut = new By.ByCssSelector(".logout.hidden-sm-down");
	
	
	/*
	 * 
	 * public HomePage()...: Método da classe que constrói objeto homePage com o driver parametrizado pela BaseTest
	 * public int contProds()...: Método que irá retornar a quantidade de produtos encontrados na página
	 * private void carregarListProds()...: Método que irá preencher Array com produtos encontrados na página
	 * 
	 */
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public int contProds() {
		carregarListProds();
		return listaProds.size();
	}
	
	private void carregarListProds() {
		listaProds = driver.findElements(produtos);
	}
	
	public int validCarVazio() {
		String txtCarrinho = driver.findElement(carrinho).getText();
		txtCarrinho = txtCarrinho.replace("(", "");
		txtCarrinho = txtCarrinho.replace(")", "");
		Integer qtdCarrinho = Integer.parseInt(txtCarrinho);
		return qtdCarrinho;		
	}
	
	public String obterNomeProd(int indice) {
		String nomeProd = driver.findElements(descProd).get(indice).getText();
		return nomeProd;
	}
	
	public String obterPrecoProd(int indice) {
		String precoProd = driver.findElements(valProd).get(indice).getText();
		return precoProd;
	}
	
	
	public ProdutoPage clicarProduto(int indice) {
		driver.findElements(descProd).get(indice).click();
		return new ProdutoPage(driver);
	}
	
	public LoginPage clicarSignIn() {
		String txtSigIn = driver.findElement(btnSignIn).getText().intern();
		if(txtSigIn == "Sign in") {
			driver.findElement(btnSignIn).click();
		} else {
			driver.findElement(btnSignOut).click();
		}
		return new LoginPage(driver);
	}
	
	
}
