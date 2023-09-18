package com.planittesting.jupitertoys.tests.support;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.planittesting.jupitertoys.tests.BaseTest;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AfterEachProcessor implements AfterEachCallback {
	
	private final Logger logger = LoggerFactory.getLogger(AfterEachProcessor.class);
	
	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		var driver = ((BaseTest)context.getTestInstance().get()).getDriver();
		if (context.getExecutionException().isPresent()) {
			var fileName = context.getDisplayName().replace("()", "");
			fileName = fileName.length()>50 ? fileName.substring(0, 50) : fileName;
			var filePath = System.getProperty("user.dir")+File.separator+"target"+File.separator+fileName+".png";
			saveScreenshot(driver, filePath);
		}
		driver.quit();
	}
	
	private void saveScreenshot(WebDriver driver, String filePath) {
		try {
			var screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File destination = new File(filePath);
			Files.move(screenshotFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
			logger.info(destination.getAbsolutePath());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
