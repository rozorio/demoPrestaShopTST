package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
//import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.By.ByCssSelector;

public class ProdutoPage {

	private WebDriver driver;
	private By descProd = new By.ByClassName("h1");
	private By valProd = new By.ByCssSelector(".current-price span:nth-child(1)"); //nth-child(1) --> garantir que será retornado o 1º span
	private By tamProd = new By.ById("group_1");
	private By corPreta = new By.ByXPath("//ul[@id='group_2']//input[@value='11']"); //  //elemento[@atributo]
	private By inputQtde = new By.ById("quantity_wanted");
	private By addToCar = new By.ByCssSelector(".add-to-cart");
	
	public ProdutoPage(WebDriver driver) {
		/* Construtor de instância de ProdutoPage
		 * Receberá o driver de quem o invoca para continuar os testes
		 */
		this.driver = driver;
	}
	
	public String obterNomeProd() {
		String nomeProd = driver.findElement(descProd).getText();
		return nomeProd;
	}
	
	public String obterPrecoProd() {
		String precoProd = driver.findElement(valProd).getText();
		return precoProd;
	}
	
	public Select instanciarSelectTam() {
		return new Select(driver.findElement(tamProd)); 
	}
	
	public List<String> obteTamanhoSelecionado(){
		List<WebElement> opcoesTam = instanciarSelectTam().getAllSelectedOptions();
		List<String> listaTamanhos = new ArrayList<String>();
		
		/*Enhanced-for
		 * (tipo_do_array:array)
		 * obrigado percorrer array
		 * a cada iteração será armazenado o elemento do array
		 */
		for(WebElement elemento : opcoesTam) {
			listaTamanhos.add(elemento.getText());
		}
		
		return listaTamanhos;
	}
	
	public void selecionarTamanho(String opcao) {
		instanciarSelectTam().selectByVisibleText(opcao);
	}
	
	public void selecionarCor() {
		driver.findElement(corPreta).click();
	}
	
	public void alterarQuantidade(int qtde) {
		driver.findElement(inputQtde).clear();
		driver.findElement(inputQtde).sendKeys(Integer.toString(qtde));
	}
	
	public ModalProduto adicionarCarrinho() {
		driver.findElement(addToCar).click();
		return new ModalProduto(driver);
	}
}
