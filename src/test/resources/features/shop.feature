@web
Feature: Shopping

@shop
Scenario: Product Price
	Given A product with name Extinct Capybara is in the shop catalog
	Then The price is 20.00

#	Examples:
#		| ProductName 		| ProductPrice |
#		| Extinct Capybara  | 15.00        |

@shop
Scenario Outline: Buy Product
	Given A customer buys the product with name <ProductName>
	Then The product with name <ProductName> must be added to the cart

	Examples:
		| ProductName    |
		| Copacetic Capybara |

# @shop
# Scenario: Buy all products with cost less than 10
# 	Given A customer buys all products cheaper than 10.00
# 	Then The products must be added to the cart

# @shop
# Scenario: Buy cheapest product
# 	Given A customer buys the cheapest product
# 	Then The product must be added to the cart