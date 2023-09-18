package com.planittesting.jupitertoys.tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.planittesting.jupitertoys.model.pages.HomePage;
import com.planittesting.jupitertoys.tests.support.dataproviders.WithJson;
import com.planittesting.jupitertoys.tests.support.dataproviders.apiMocks.ProductListMock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

// BiDi is only supported in chrome at the moment. Browsers != chrome fail to run
// https://i.pinimg.com/originals/f8/54/49/f85449b90cb8cfb593fb4d8af5d80393.jpg
@ExtendWith(ProductListMock.class)
@WithJson("""
    [{
        "quantity": "2000",
        "visible": "true",
        "image": "https://c2.staticflickr.com/4/3042/2762123727_db6d650cf2_b.jpg",
        "category": "Stuffed Toys",
        "price": "-999",
        "id": "9ba9a21a-2b78-49e3-9a55-fbfc4a15ccf6",
        "stars": "4",
        "title": "Mocked Quokka"
    }, 
    {
        "quantity": "2000",
        "visible": "true",
        "image": "https://static01.nyt.com/images/2017/08/01/science/29TB-PLATYPUS1/29TB-PLATYPUS1-videoSixteenByNineJumbo1600.jpg",
        "category": "Stuffed Toys",
        "price": "-1",
        "id": "9ba9a21a-2b78-49e3-9a55-fbfc4a15ccf7",
        "stars": "4",
        "title": "PLATYPUS"
    }]
    """)
public class ShopBidiTests extends BaseTest {
    @Test
	void validatePriceForProductBidi() {
		var price = open(HomePage.class)
			.clickShopMenu()
			.getProduct(p->p.getTitle().equals("Mocked Quokka"))
			.getPrice();
        assertThat(price).isEqualTo(-999);
        // assertThat(price).isNot(new Condition<Double>(p -> p<=0, "negative or zero"));
	}
}
