@web
Feature: Verify Items on the cart and price

	@cart
	Scenario: Verify the items on the cart and price
		Given a customer adds following items to the cart
			| cute puppy | 2 |
			| Copacetic Capybara  | 5 |
			| Carp Streamers     | 3 |
		Then sub total should be correct for all items
		And total price of items should be correct
		And cart page should have the following
			| Quantity | ProductName        | Price | SubTotal |
			| 2        | cute puppy 		| 10.99 | 21.98    |
			| 5        | Copacetic Capybara |  5    | 25       |
			| 3        | Carp Streamers     | 30    | 90       |