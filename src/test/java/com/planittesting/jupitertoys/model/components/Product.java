package com.planittesting.jupitertoys.model.components;

import java.time.Duration;
import java.util.stream.IntStream;

import com.planittesting.automation.driver.support.CleanWebDriverWait;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

public class Product<T> {

	private WebElement element;
	private T parentPage;
	private WebDriver driver;

	public Product(WebElement element, T parentPage) {
		this.element = element;
		this.parentPage = parentPage;
		driver = ((WrapsDriver)element).getWrappedDriver();
		new CleanWebDriverWait(driver, Duration.ofSeconds(10))
			.until(d -> element.findElement(By.cssSelector("[data-locator='product-image']")).isDisplayed());
	}

	public T getParentPage() {
		return parentPage;
	}

	public String getTitle() {
		return element.findElement(By.cssSelector("[data-locator='product-title']")).getText();
	}

	public double getPrice() {
		return Double.parseDouble(element.findElement(By.cssSelector("[data-locator='product-price']")).getText().replaceAll("[^0-9\\-\\.]+", ""));
	}

	public T clickBuyButton() {
		clickBuyButton(1);
		return this.parentPage;
	}

	public T clickBuyButton(int times) {
		var clickElement = element.findElement(By.cssSelector("[data-locator='add-to-cart-button']"));
		IntStream.range(0, times).forEach(i -> clickElement.click());
		return this.parentPage;
	}

	public int getRating() {
		return Integer.parseInt(element.findElement(By.cssSelector("[data-locator='rating-value']")).getText());
	}

}
