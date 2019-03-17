# checkout-codekata-java
supermarket checkout code kata implementation in Java



The code is written in pure Java for the problem given in docs/CheckoutKata - Principal.pdf. 

Assumptions
 The solution of the problem replicate the checkout in  a super market

Notes: 
 No database is used and data is provided using CheckoutTestDataRepository

We have the following Domain Objects
 1) Item
 2) Promotion

Service Class 'CheckoutService' handles the check out using a Basket

Calculation is done in 'PriceCalculator' (Logic could possibly be improved)

Integration Test is provided by 'CheckOutIntegrationTest'

