package runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class) // do JUnit 4
@CucumberOptions(
		features = "src\\test\\resources\\features\\compra_produtos.feature", 
		glue = "steps", 
		tags = "@fluxopadrao", 
		plugin = "pretty", 
		monochrome = true)

public class Runner {

}
