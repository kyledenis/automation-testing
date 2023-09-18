@web @bidi @shop
Feature: Shopping

Background: Mock Network Calls
	Given product API mock with payload
		"""
		[{
			"quantity": "2000",
			"visible": "true",
			"image": "https://i.pinimg.com/originals/f8/54/49/f85449b90cb8cfb593fb4d8af5d80393.jpg",
			"category": "Stuffed Toys",
			"price": "-5",
			"id": "9ba9a21a-2b78-49e3-9a55-fbfc4a15ccf6",
			"stars": "4",
			"title": "Mocked Quokka"
		}]
		"""

Scenario Outline: Product Price With Mock Data
	Given A product with name <ProductName> is in the shop catalog
	Then The price is <ProductPrice>

	Examples:
		| ProductName 		| ProductPrice |
		| Mocked Quokka	    | -5           |

