package steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.concurrent.TimeUnit;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.HomePage;

public class VisualizaProdutosSteps {
	
	private static WebDriver driver;
	private HomePage homePage = new HomePage(driver);
	String endereco = "https://marcelodebittencourt.com/demoprestashop/";

	@Before        //Before do Cucumber
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver","C:\\Program Files\\Chrome Driver\\drivers\\95\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(60000, TimeUnit.MILLISECONDS);
		
	}
	
	@Dado("usuario na homepage")
	public void usuario_na_homepage() {
		homePage.abrirPaginaInicio(endereco);
		String titulo = homePage.verificarTituloPagina();
		assertThat(titulo, is("Loja de Teste"));
	}

	@Quando("usuario nao logado")
	public void usuario_nao_logado() {
		assertThat(homePage.verificarUsuarioNaoLogado(), is(true));
	}

	@Entao("exibido listagem de {int} produtos")
	public void exibido_listagem_de_produtos(Integer qtdeProd) {
		assertThat(homePage.contarProdsExibidos(), is(qtdeProd));
	}

	@Entao("verificado carrinho de compras com {int} item")
	public void verificado_carrinho_de_compras_com_item(Integer qtdeCar) {
		assertThat(homePage.validCarVazio(), is(qtdeCar));  
	}
	
	@After      //After do Cucumber
	public static void finalizar() {
		driver.quit();
	}
	
}
