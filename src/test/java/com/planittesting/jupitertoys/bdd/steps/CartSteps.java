package com.planittesting.jupitertoys.bdd.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.planittesting.jupitertoys.bdd.steps.support.ObjectContainer;
import com.planittesting.jupitertoys.bdd.steps.support.WebBaseSteps;
import com.planittesting.jupitertoys.model.data.CartData;
import com.planittesting.jupitertoys.model.pages.CartPage;
import com.planittesting.jupitertoys.model.pages.HomePage;
import com.planittesting.jupitertoys.model.pages.ShopPage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CartSteps extends WebBaseSteps {

	public CartSteps(ObjectContainer container) {
		super(container);
	}

	@DataTableType
	public CartData cartDataTransformer(Map<String, String> cartTable) {
		return new CartData(
				Integer.parseInt(cartTable.get("Quantity")),
				cartTable.get("ProductName"),
				Double.parseDouble(cartTable.get("Price")),
				Double.parseDouble(cartTable.get("SubTotal"))
			);
	}

	@Given("^a customer adds following items to the cart$")
	public void addItemsToCart(DataTable dataTable) {
		var productsWithPrice = new HashMap<String, Double>();
		var shopPage = open(HomePage.class).clickShopMenu();
		dataTable.asMap(String.class, Integer.class).entrySet().forEach(entry -> {
			var product = shopPage.getProduct(p -> p.getTitle().equalsIgnoreCase(entry.getKey()));
			product.clickBuyButton(entry.getValue());
			productsWithPrice.put(entry.getKey(), product.getPrice());
		});
		container.register("products", productsWithPrice);
	}

	@Then("^sub total should be correct for all items$")
	public void verifySubTotal() {
		Map<String, Double> items = container.retrieve("products");
		var cartPage = open(ShopPage.class).clickCartMenu();
		double total = 0.0;
		for (var entry : items.entrySet()) {
			assertEquals(entry.getValue(), cartPage.getPrice(entry.getKey()));
			assertEquals(cartPage.getPrice(entry.getKey()) * cartPage.getQuantity(entry.getKey()), cartPage.getSubtotal(entry.getKey()), 0.00);
			total += cartPage.getSubtotal(entry.getKey());
		}
		container.register("TotalPrice", total);
	}
	
	@Then("^total price of items should be correct$")
	public void verifyTotal() {
		var cartPage = open(CartPage.class);
		double totalPrice = container.retrieve("TotalPrice");
		assertEquals(totalPrice, cartPage.getTotal(), 0.00);
	}

	@Then("^cart page should have the following$")
	public void ThenCartPageShouldHaveTheFollowing(List<CartData> expectedCartData) {
		var actualCartData = open(CartPage.class).getCartDataList();
		assertEquals(expectedCartData, actualCartData);
	}
}
