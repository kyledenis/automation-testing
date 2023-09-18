package com.planittesting.jupitertoys.bdd.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;

import com.planittesting.jupitertoys.bdd.steps.support.ObjectContainer;
import com.planittesting.jupitertoys.bdd.steps.support.WebBaseSteps;
import com.planittesting.jupitertoys.model.components.Product;
import com.planittesting.jupitertoys.model.data.ProductData;
import com.planittesting.jupitertoys.model.data.ShopData;
import com.planittesting.jupitertoys.model.pages.HomePage;
import com.planittesting.jupitertoys.model.pages.ShopPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ShoppingSteps extends WebBaseSteps {
	
	public ShoppingSteps(ObjectContainer container) {
		super(container);
    }

    @Given("^A product with name (.*) is in the shop catalog$")
    public void givenAProductWithNameIsInTheShopCatalog(String productName) {
        var product = open(HomePage.class)
            .clickShopMenu()
            .getProduct(p -> p.getTitle().equals(productName));
        this.container.register("product", new ProductData(0, true, "", "", product.getPrice(), "", 0, product.getTitle()));
    }

    @Then("^The price (is|is not) ([+-?][0-9]+\\.?[0-9]*|\\.[0-9]+)$") //Ideally it should match ([+-?][0-9]+\.?[0-9]*|\.[0-9]+)
    public void thenPriceShouldBe(String is, double productPrice) {
        ProductData product = this.container.retrieve("product");
        switch(is) {
            case "is" -> assertEquals(productPrice, product.price(), FLOATING_POINT_ERROR);
            case "is not" -> assertThat(product.price()).isNotEqualTo(productPrice);
        }
    }

    @When("^A customer buys the product with name (.*)$")
    public void whenACustomerBuysTheProductWithName(String productName) {
        var shopPage = open(HomePage.class).clickShopMenu();
        this.container.register("shopData", new ShopData(null, shopPage.getCartCount()));
		var testProduct = shopPage.getProduct(p -> p.getTitle().equals(productName));
        testProduct.clickBuyButton();
    }

    @Then("^The product with name (.*) must be added to the cart$")
    public void thenTheProductWithNameMustBeAddedToTheCart(String productName) throws Exception {
        ShopData shopData = this.container.retrieve("shopData");
        var shopPage = open(ShopPage.class);
        assertEquals(shopData.cartCount() + 1, shopPage.getCartCount());
    }

    @When("A customer buys all products cheaper than {double}")
    public void whenACustomerBuysAllProductsCheaperThan(double amount) {
        var producDataList = open(HomePage.class)
            .clickShopMenu()
            .getProducts(p -> p.getPrice() < amount).stream().map(product -> {
                product.clickBuyButton();
                return new ProductData(0, true, "", "", product.getPrice(), "", 0, product.getTitle());
            }).toList();
        var shopData = new ShopData(producDataList, producDataList.size());
        this.container.register("shopData", shopData);
    }

    @Then("The product(s) must be added to the cart")
    public void thenTheProductMustBeAddedToTheCart() {
        ShopData shopData = this.container.retrieve("shopData");
        var count = open(ShopPage.class).getCartCount();
        assertEquals(shopData.products().size(), count);
    }

    @When("A customer buys the cheapest product")
    public void whenACustomerBuysTheCheapestProduct() {
        var product = open(HomePage.class)
			.clickShopMenu()
			.getProducts()
			.stream()
			.min(Comparator.comparing(Product::getPrice)).orElseThrow();
        var productData = new ProductData(0, true, "", "", product.getPrice(), "", 0, product.getTitle());
        product.clickBuyButton();
        var shopData = new ShopData(List.of(productData), 1);
        this.container.register("shopData", shopData);
    }
}
