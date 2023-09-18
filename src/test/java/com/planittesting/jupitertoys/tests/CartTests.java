package com.planittesting.jupitertoys.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import com.planittesting.jupitertoys.model.pages.HomePage;

class CartTests extends BaseTest {

	@Test()
	void validateSumsInCartPage() throws Exception {
		var product = open(HomePage.class)
			.clickShopMenu()
			.getProduct(p -> p.getTitle().equalsIgnoreCase("poodle"));
		var price = product.getPrice();
		var cartPage = product
			.clickBuyButton(3)
			.clickCartMenu();
		assertEquals(price, cartPage.getPrice("poodle"), "compare price in cart page with price from shop page");
		assertEquals(cartPage.getPrice("poodle") * cartPage.getQuantity("poodle"),cartPage.getSubtotal("poodle"),"price * quantity should equal subtotal");
		assertEquals(cartPage.getSubtotal("poodle"), cartPage.getTotal(), "subtotals sum equals total");
	}

}
