# personalized-data

Personalized data API

Implemented following interfaces on Personalized data API

# Interface with eCommerce:

●   Get Products by shopper (with filters)
○ Shopper ID - String, required
○ Category - String, optional
○ Brand - String, optional
○ Limit - Integer, optional, default = 10, max = 100

●   Get shopper by product
○ Product ID - String, required
○ Limit - Integer, optional, default = 10, max = 1000

# Interface with dataTeam:

●   receiveShopperData
●   receiveProductMetadata

## Postman collection is attached under /resources for testing purposes