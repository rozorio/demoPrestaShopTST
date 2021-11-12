package base;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;

import pages.HomePage;
//import pages.LoginPage;
//import pages.ProdutoPage;

/* 
 * Classe BaseTests: responsável por toda comunicação/configuração básica para automação
 */

public class BaseTests {

	/*
	 * WebDriver driver: variável do tipo WebDriver
	 * HomePage homePage: variável do tipo HomePage
	 */
		
	private static WebDriver driver; 
	protected HomePage homePage;
//	protected LoginPage loginPage;
//	protected ProdutoPage produtoPage;
		
	/*
	 * inicializar(): procedimentos de inicialização geral
	 * **.setProperty: define caminho do chromedriver.exe
	 * ** new ChromeDriver: cria objeto ChromeDriver
	 */
		
	@BeforeAll      //Anotação JUnit, inicialização geral
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver","C:\\Program Files\\Chrome Driver\\drivers\\95\\chromedriver.exe");
		driver = new ChromeDriver();	
	}
		
	/*
	 * carregarPagInic(): Carrega a home page
	 * **.get("https://...): método do Selenium que navegará até tal site
	 * **.manage().timeouts()...: método do Selenium que define tempo de espera
	 * ** new HomePage(driver): cria obj HomePage passando o driver como parâmetro
	 */
	
	@BeforeEach     //Anotação JUnit, inicialização para cada teste
	public void carregarPagInic() {
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60000, TimeUnit.MILLISECONDS);
		homePage = new HomePage(driver);
//		loginPage = new LoginPage(driver);
//		produtoPage = new ProdutoPage(driver);
		
	}
	
	
	/* MÉTODO PARA CAPTURA DE TELA
	 * - TakesScreenshot --> Selenium;
	 * - File --> java.io;
	 * - Files.move(dir origem, dir destino);
	 * - try {...} catch(){...}  --> caso dê erro ao gerar o arquivo, registrar msg de erro.
	 */
	public void capturarTela(String tipoTeste, String resultado) {
		TakesScreenshot camera = (TakesScreenshot) driver;
		File capturaDeTela = camera.getScreenshotAs(OutputType.FILE);
		try {
			Files.move(capturaDeTela, new File("resources/screenshots/" + tipoTeste + "_" + resultado + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/*
	 * finalizar(): procedimentos de finalização
	 * **.quit(): sai do driver
	 */
	
	@AfterAll      //Anotação JUnit, finalização geral
	public static void finalizar() {
		driver.quit();
	}
}
