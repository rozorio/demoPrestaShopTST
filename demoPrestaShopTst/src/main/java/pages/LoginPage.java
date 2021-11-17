package pages;

import org.openqa.selenium.By;
//import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	
	private WebDriver driver;
	private By emailLog = new By.ByName("email");
	private By passwLog = new By.ByName("password");
	private By btnSigIn = new By.ById("submit-login");
	private By logoHome = new By.ByCssSelector("#_desktop_logo a");
	private By usuarioLogado = new By.ByCssSelector("#_desktop_user_info span");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void preencherEmail(String email) {
		driver.findElement(emailLog).sendKeys(email);
	}
	
	public void preencherPassword(String password) {
		driver.findElement(passwLog).sendKeys(password);
	}
	
	public void clicarSignIn() {
		driver.findElement(btnSigIn).click();
		driver.findElement(logoHome).click();
	}
	
	public Boolean validarLogOn(String usuario) {
		String logOn = driver.findElement(usuarioLogado).getText();
		return logOn.contentEquals(usuario);
	}

}
