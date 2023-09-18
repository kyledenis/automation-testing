package com.planittesting.jupitertoys.model.pages;

import com.planittesting.jupitertoys.model.components.ui.Table;
import com.planittesting.jupitertoys.model.data.CartData;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage<CartPage> {

	private Table cartItems;

	public CartPage(WebDriver driver) {
		super(driver);
	}

	public double getPrice(String productTitle) throws RuntimeException {
		return Double.parseDouble(getCartItems().getValue("Item", productTitle, "Price").getText().replaceAll("[^0-9\\.]+", ""));
	}

	public int getQuantity(String productTitle) throws RuntimeException {
		return Integer.parseInt(getCartItems().getValue("Item", productTitle, "Quantity").getText());//findElement(By.tagName("input")).getAttribute("value"));
	}

	public double getSubtotal(String productTitle) throws RuntimeException {
		return Double.parseDouble(getCartItems().getValue("Item", productTitle, "Subtotal").getText().replaceAll("[^0-9\\.]+", ""));
	}

	public double getTotal() {
		return Double.parseDouble(driver.findElement(By.id("footer-total")).getText().replaceAll("[^0-9\\.]+", ""));
	}
	
	public void clickRemoveButton(String productTitle) throws RuntimeException {
		getCartItems().getValue("Item", productTitle, "Actions").findElement(By.className("remove-item")).click();
	}
	
	public void clcikOnShopLink() {
		driver.findElement(By.linkText("Shopping")).click();
	}

	public List<CartData> getCartDataList() {
		return getCartItems().getTableMap().stream().map(row -> {
			return new CartData(
				Integer.parseInt(row.get("QUANTITY").getText()),
				row.get("ITEM").getText(),
				Double.parseDouble(row.get("PRICE").getText().replace("$", "")),
				Double.parseDouble(row.get("SUBTOTAL").getText().replace("$", "")));
		}).collect(Collectors.toList());
	}

	/* 
	 * Lazy instantiation of cart items
	 * Could return a new Table every time to avoid stale element situations 
	 * but in this particular application this will not happen
	 */
	private Table getCartItems() {
		if (this.cartItems == null) 
			this.cartItems = new Table(driver.findElement(By.id("cart-items")));
		return this.cartItems;
	}
}
