package com.planittesting.jupitertoys.model.pages;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import com.planittesting.automation.driver.support.CleanWebDriverWait;
import com.planittesting.jupitertoys.model.components.Product;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShopPage extends BasePage<ShopPage> {

	public ShopPage(WebDriver driver) {
		super(driver);
		new CleanWebDriverWait(driver, Duration.ofSeconds(30))
			.until(d -> !d.findElements(By.cssSelector("[data-testid='product']")).isEmpty());
	}

	public Product<ShopPage> getProduct(Function<Product<ShopPage>, Boolean> strategy) {
		return getProducts(strategy).stream().findFirst().orElseThrow();
	}
	
	public List<Product<ShopPage>> getProducts(Function<Product<ShopPage>, Boolean> strategy) {
		return driver.findElements(By.cssSelector("[data-testid='product']"))
				.stream()
				.map(e -> new Product<ShopPage>(e, this))
				.filter(strategy::apply)
				.toList();	
	}
	
	public List<Product<ShopPage>> getProducts() {
		return getProducts(p->true);		
	}
}