package com.planittesting.jupitertoys.bdd.steps.hooks;

import java.time.Duration;
import java.util.stream.Stream;

import com.planittesting.automation.driver.support.DriverLogListener;

import org.openqa.selenium.WebDriver;

import com.planittesting.automation.driver.DriverFactory;
import com.planittesting.jupitertoys.bdd.steps.support.ObjectContainer;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

import org.openqa.selenium.support.events.EventFiringDecorator;

import io.github.cdimascio.dotenv.Dotenv;

public class WebStepHooks {
	private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
	private final ObjectContainer objectContainer;
	

	public WebStepHooks(ObjectContainer objectContainer) {
		this.objectContainer = objectContainer;
	}

	@BeforeAll
	public static void beforeAll() {
		var variables = dotenv.entries().stream().map(e -> e.getKey()).toList();
		Stream.of("SELENIUM_BROWSER", "SELENIUM_WAIT", "SELENIUM_URL")
				.filter(e -> !variables.contains(e))
				.reduce((a, b) -> a + ", " + b)
				.ifPresent(missing -> {
					throw new RuntimeException("Missing environment variables: " + missing);
				});
	}

	@Before("@web")
	public void setup(Scenario scenario) throws Exception {
		var browser = dotenv.get("SELENIUM_BROWSER");
		var wait = Integer.parseInt(dotenv.get("SELENIUM_WAIT"));
		var url = dotenv.get("SELENIUM_URL");
		var gridUrl = dotenv.get("SELENIUM_GRID_URL");
		var isHeadless = Boolean.parseBoolean(dotenv.get("SELENIUM_HEADLESS","true"));
		
		WebDriver driver = DriverFactory.getFactory(browser)
			.withGridUrl(gridUrl)
			.withHeadless(isHeadless)
			.build();
		driver = new EventFiringDecorator<WebDriver>(
			new DriverLogListener(
				scenario.getClass().getName(), 
				scenario.getName())).decorate(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(wait));
		driver.manage().window().maximize();
		driver.navigate().to(url);

		this.objectContainer.setDriver(driver);
		this.objectContainer.register("browser", browser);
	}

	@After("@web")
	public void cleanup() {
		objectContainer.getDriver().ifPresent(driver -> driver.quit());
	}
}
