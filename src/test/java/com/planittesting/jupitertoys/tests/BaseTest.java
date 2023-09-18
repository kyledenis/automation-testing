package com.planittesting.jupitertoys.tests;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.stream.Stream;

import com.planittesting.automation.driver.DriverFactory;
import com.planittesting.automation.driver.support.DriverLogListener;
import com.planittesting.jupitertoys.tests.support.AfterEachProcessor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * {@code BaseTestSuite} is an abstract class which contains common
 * methods for:
 * <ul>
 * <li>Setup of the test environment and logs</li>
 * <li>Clean up of test environment once completed</li>
 * <li>Other common methods that can be used for all tests</li>
 * </ul>
 * 
 * All tests for the project application must extend this class.
 * 
 * <p>
 * This class also contains a method for a cleaner
 * looking way to <b>open</b> page object classes.
 * </p>
 * 
 * <br>
 * <b>Example:</b>
 * 
 * <pre>
 * {
 * 	&#64;code
 * 	var message = open(HomePage.class).clickContactMenu()
 * 			.setContactData(contactData)
 * 			.clickSubmitButton()
 * 			.getSuccessMessage();
 * }
 * </pre>
 * 
 * @see AbstractDriverFactory
 * @see CustomWebDriverEventListener
 * @see AfterEachProcessor
 */
@ExtendWith(AfterEachProcessor.class)
@Execution(ExecutionMode.CONCURRENT)
public abstract class BaseTest {
	private static Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
	protected WebDriver driver;
	private String browser;

	public WebDriver getDriver() {
		return driver;
	}

	public String getBrowser() {
		return browser;
	}

	@BeforeAll
	public static void checkMandatoryEnvronmentVariables() throws Exception {
		var variables = dotenv.entries().stream().map(e -> e.getKey()).toList();
		Stream.of("SELENIUM_BROWSER", "SELENIUM_WAIT", "SELENIUM_URL")
				.filter(e -> !variables.contains(e))
				.reduce((a, b) -> a + ", " + b)
				.ifPresent(missing -> {
					throw new RuntimeException("Missing environment variables: " + missing);
				});
	}

	@BeforeEach
	public void setupTest(TestInfo testInfo) throws Exception {
		browser = dotenv.get("SELENIUM_BROWSER");
		var wait = Integer.parseInt(dotenv.get("SELENIUM_WAIT"));
		var url = dotenv.get("SELENIUM_URL");
		var gridUrl = dotenv.get("SELENIUM_GRID_URL");
		var isHeadless = Boolean.parseBoolean(dotenv.get("SELENIUM_HEADLESS", "true"));
		var version = dotenv.get("SELENIUM_BROWSER_VERSION", "latest");
		
		driver = DriverFactory.getFactory(browser).withGridUrl(gridUrl).withHeadless(isHeadless).withVersion(version).build();
		driver = new EventFiringDecorator<WebDriver>(new DriverLogListener(testInfo.getTestClass().get().getSimpleName(),testInfo.getTestMethod().get().getName())).decorate(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(wait));
		driver.manage().window().maximize();
		driver.navigate().to(url);
	}

	// The afterEach actions are performed in AfterEachProcessor
	// AfterEachProcessor allows us to determine if the test failed so a screenshot
	// can be taken
	@AfterEach
	public void shutdownTest() {
	}

	protected <T> T open(Class<T> page) {
		try {
			return page.getConstructor(WebDriver.class).newInstance(driver);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

}
