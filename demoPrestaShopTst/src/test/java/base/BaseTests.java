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
 * Classe BaseTests: respons�vel por toda comunica��o/configura��o b�sica para automa��o
 */

public class BaseTests {

	/*
	 * WebDriver driver: vari�vel do tipo WebDriver
	 * HomePage homePage: vari�vel do tipo HomePage
	 */
		
	private static WebDriver driver; 
	protected HomePage homePage;
//	protected LoginPage loginPage;
//	protected ProdutoPage produtoPage;
		
	/*
	 * inicializar(): procedimentos de inicializa��o geral
	 * **.setProperty: define caminho do chromedriver.exe
	 * ** new ChromeDriver: cria objeto ChromeDriver
	 */
		
	@BeforeAll      //Anota��o JUnit, inicializa��o geral
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver","C:\\Program Files\\Chrome Driver\\drivers\\95\\chromedriver.exe");
		driver = new ChromeDriver();	
	}
		
	/*
	 * carregarPagInic(): Carrega a home page
	 * **.get("https://...): m�todo do Selenium que navegar� at� tal site
	 * **.manage().timeouts()...: m�todo do Selenium que define tempo de espera
	 * ** new HomePage(driver): cria obj HomePage passando o driver como par�metro
	 */
	
	@BeforeEach     //Anota��o JUnit, inicializa��o para cada teste
	public void carregarPagInic() {
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60000, TimeUnit.MILLISECONDS);
		homePage = new HomePage(driver);
//		loginPage = new LoginPage(driver);
//		produtoPage = new ProdutoPage(driver);
		
	}
	
	
	/* M�TODO PARA CAPTURA DE TELA
	 * - TakesScreenshot --> Selenium;
	 * - File --> java.io;
	 * - Files.move(dir origem, dir destino);
	 * - try {...} catch(){...}  --> caso d� erro ao gerar o arquivo, registrar msg de erro.
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
	 * finalizar(): procedimentos de finaliza��o
	 * **.quit(): sai do driver
	 */
	
	@AfterAll      //Anota��o JUnit, finaliza��o geral
	public static void finalizar() {
		driver.quit();
	}
}
