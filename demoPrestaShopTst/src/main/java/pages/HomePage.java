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
 * HomePage: classe que re�ne m�todos de intera��o com a p�gina pr�priamente dita
 */

public class HomePage {

	/*
	 * private WebDriver (...): Cria vari�vel driver que ser� parametrizada pela classe BaseTest;
	 * List<WebElement> (...): Cria vetor que ir� registrar elementos encontrados na tela
	 * private By (...): Cria obj do tipo By para parametrizar o m�todo findElements
	 * 
	 */
	
	private WebDriver driver;
	List<WebElement> listaProds = new ArrayList<WebElement>();     //List: biblioteca que n�o faz parte do java padr�o (como Integer, Double...), integra a java.util
	private By produtos = new By.ByClassName("product-description");
	private By carrinho = new By.ByClassName("cart-products-count");
	private By descProd = new By.ByCssSelector(".product-description a");
	private By valProd = new By.ByCssSelector(".price");
	private By btnSignIn = new By.ByCssSelector("#_desktop_user_info .hidden-sm-down");
	private By btnSignOut = new By.ByCssSelector(".logout.hidden-sm-down");
	
	
	/*
	 * 
	 * public HomePage()...: M�todo da classe que constr�i objeto homePage com o driver parametrizado pela BaseTest
	 * public int contProds()...: M�todo que ir� retornar a quantidade de produtos encontrados na p�gina
	 * private void carregarListProds()...: M�todo que ir� preencher Array com produtos encontrados na p�gina
	 * 
	 */
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void abrirPaginaInicio(String endereco) {
		driver.get(endereco);
	}
	
	public String verificarTituloPagina() {
		return driver.getTitle();
	}
	
	public boolean verificarUsuarioNaoLogado() {
		String signIn = driver.findElement(btnSignIn).getText().intern();
		return signIn.equals("Sign in");
	}
	
	public int contarProdsExibidos() {
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
	
	public void clicarSignOut() {
		driver.findElement(btnSignOut).click();
	}
	
	public LoginPage clicarSignIn() {
		String txtSigIn = driver.findElement(btnSignIn).getText().intern();
		if(txtSigIn == "Sign in") {
			driver.findElement(btnSignIn).click();
		} else {
			clicarSignOut();
			driver.findElement(btnSignIn).click();
		}
		return new LoginPage(driver);
	}
	
	
}
