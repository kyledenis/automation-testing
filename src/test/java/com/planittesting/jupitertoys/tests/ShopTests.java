package com.planittesting.jupitertoys.tests;

import static java.util.Comparator.comparing;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.planittesting.jupitertoys.model.components.Product;
import com.planittesting.jupitertoys.model.pages.HomePage;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ShopTests extends BaseTest {
	@Test
	void validatePriceForProduct() {
		var price = open(HomePage.class)
			.clickShopMenu()
			.getProduct(p->p.getTitle().startsWith("Extinct") && p.getPrice()<30)
			.getPrice();
		assertEquals(20, price);
	}

	@Tag("buy")
	@Test
	void buyAProductWithGivenPrice() {
		var cartCount = open(HomePage.class)
			.clickShopMenu()
			.getProduct(p -> p.getPrice() >= 10.99)
			.clickBuyButton()
			.getCartCount();
		assertEquals(1, cartCount);
	}

	@Test
	void buyAllProductsWithStarRating() {
		var products = open(HomePage.class)
			.clickShopMenu()
			.getProducts(p -> p.getRating() == 1);
		products.forEach(Product::clickBuyButton);
		assertEquals(products.size(), products.get(0).getParentPage().getCartCount());
	}

	@Test
	void buyCheapestProduct() {
		var cartCount = open(HomePage.class)
			.clickShopMenu()
			.getProducts()
			.stream().min(comparing(Product::getPrice)).get()
			.clickBuyButton()
			.getCartCount();
		assertEquals(1, cartCount);
	}
}
