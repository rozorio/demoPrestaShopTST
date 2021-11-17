package steps;

//Por algum motivo, o assertThat do hamcrest parou de funcionar com cucumber após implementação do Runner.
//import static org.hamcrest.MatcherAssert.*;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
import java.util.concurrent.TimeUnit;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.HomePage;
import pages.LoginPage;
import pages.ModalPage;
import pages.ProdutoPage;
import util.Funcoes;

public class CompraProdutosSteps {

	private static WebDriver driver;
	private HomePage homePage = new HomePage(driver);
//	private static LocalDate dataHora = LocalDate.now();
	
	String endereco = "https://marcelodebittencourt.com/demoprestashop/";
	
	

	@Before // Before do Cucumber
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Program Files\\Chrome Driver\\drivers\\95\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(60000, TimeUnit.MILLISECONDS);

	}

	@Dado("usuario na homepage")
	public void usuario_na_homepage() {
		homePage.abrirPaginaInicio(endereco);
		String titulo = homePage.verificarTituloPagina().intern();
		System.out.println(titulo);
//		assertThat(titulo, is("Loja de Teste"));
		assertEquals(titulo, "Loja de Teste");
	}

	@Quando("usuario nao logado")
	public void usuario_nao_logado() {
		//assertThat(homePage.verificarUsuarioNaoLogado(), is(true));
		assertEquals(homePage.verificarUsuarioNaoLogado(), true);
	}

	@Entao("exibido listagem de {int} produtos")
	public void exibido_listagem_de_produtos(Integer qtdeProd) {
//		assertThat(homePage.contarProdsExibidos(), is(qtdeProd));
		assertEquals(homePage.contarProdsExibidos(), qtdeProd);
	}

	@Entao("verificado carrinho de compras com {int} item")
	public void verificado_carrinho_de_compras_com_item(Integer qtdeCar) {
//		assertThat(homePage.validCarVazio(), is(qtdeCar));
		assertEquals(homePage.validCarVazio(), qtdeCar);
	}

	LoginPage loginPage;
	String email = "rozorio@teste.com";
	String password = "testeTeste";
	String usuario = "Raphael Ozorio";

	@Dado("usuario esta logado")
	public void usuario_esta_logado() {
		loginPage = homePage.clicarSignIn();
		loginPage.preencherEmail(email);
		loginPage.preencherPassword(password);
		loginPage.clicarSignIn();
		assertEquals(loginPage.validarLogOn(usuario), true);
	}

	ProdutoPage produtoPage;
	String nomeHP;
	Double precoHP;

	@Dado("usuario clica no produto de posicao {int}")
	public void usuario_clica_no_produto_de_posicao(Integer posicao) {
		nomeHP = homePage.obterNomeProd(posicao).toUpperCase();
		precoHP = Funcoes.tratarPrecoToDouble(homePage.obterPrecoProd(posicao));
		produtoPage = homePage.clicarProduto(posicao);
	}

	@Dado("nome do produto na homepage e na produtopage eh {string}")
	public void nome_do_produto_na_homepage_e_na_produtopage_eh(String nomeProd) {
//		assertThat(nomeHP, is(nomeProd.toUpperCase()));
//		assertThat(produtoPage.obterNomeProd(), is(nomeProd.toUpperCase()));
		assertEquals(nomeHP, nomeProd.toUpperCase());
		assertEquals(produtoPage.obterNomeProd().toUpperCase(), nomeProd.toUpperCase());
	}

	@Dado("preco do produto na homepage e na produtopage eh {string}")
	public void preco_do_produto_na_homepage_e_na_produtopage_eh(String precoProd) {
		Double precoProdDbl = Funcoes.tratarPrecoToDouble(precoProd);
		assertEquals(precoHP, precoProdDbl);
		assertEquals(Funcoes.tratarPrecoToDouble(produtoPage.obterPrecoProd()), precoProdDbl);
	}

	String tamProdPP;
	@Dado("tamanho do produto selecionado na produtopage eh {string}")
	public void tamanho_do_produto_selecionado_na_produtopage_eh(String tamProd) {
		produtoPage.selecionarTamanho(tamProd);
		tamProdPP = produtoPage.obteTamanhoSelecionado().toString();
		tamProdPP = Funcoes.limparTexto(tamProdPP, "[");
		tamProdPP = Funcoes.limparTexto(tamProdPP, "]");
		assertEquals(tamProdPP, tamProd);
	}

	@Dado("cor do produto selecionado na produtopage eh {string}")
	public void cor_do_produto_selecionado_na_produtopage_eh(String corProd) {
		if(corProd.intern() != "N/A") {
			produtoPage.selecionarCor(corProd);
		}
	}

	ModalPage modalPage;
	@Dado("quantidade informada para aquisicao na produtopage eh {int}")
	public void quantidade_informada_para_aquisicao_na_produtopage_eh(Integer qtdeProd) {
		produtoPage.alterarQuantidade(qtdeProd);
		modalPage = produtoPage.adicionarCarrinho();
	}

	
	@Entao("eh exibido modalpage com {string}, {string}, {string}, {string}, {int}")
	public void eh_exibido_modalpage_com(String nomeProdMdl, String precoProdMdl, String tamProdMdl, String corProdMdl, Integer qtdeProdMdl) {
		assertTrue(modalPage.validarMsgSucesso().endsWith("Product successfully added to your shopping cart"));
		assertEquals(modalPage.obterNomeProduto().toUpperCase(), nomeProdMdl.toUpperCase());
		assertEquals(Funcoes.tratarPrecoToDouble(modalPage.obterPrecoProduto()), Funcoes.tratarPrecoToDouble(precoProdMdl));
		assertEquals(modalPage.obterTamanho(), tamProdMdl);
		if (corProdMdl.intern() != "N/A") {
			assertEquals(modalPage.obterCor(), corProdMdl);	
		}
		assertEquals(Integer.parseInt(modalPage.obterQuantidade()), qtdeProdMdl);
	}
	
	@After (order = 1)   //order: define ordem de execução (quanto maior o número, maior a prioridade)
	public void capturarTela(Scenario cenario) {
		TakesScreenshot camera = (TakesScreenshot) driver;
		File capturaDeTela = camera.getScreenshotAs(OutputType.FILE);
		String dataExecucao = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(Calendar.getInstance().getTime());   //Gera String com data e hora atual
		String nomeArq = "resources/screenshots/" + cenario.getName() + "_" + cenario.getStatus() + "_" + dataExecucao + ".png";
		try {
			Files.move(capturaDeTela, new File(nomeArq));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@After (order = 0) // After do Cucumber
	public static void finalizar() {
		driver.quit();
	}

}
