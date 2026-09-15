# Shefo Store

**Shefo Store** is a Spring Boot REST API developed as **Capstone 1** during the **Tuwaiq Academy Java Web Development & AI Bootcamp**.

The project simulates an online store where customers can purchase products from merchants, manage their purchases, request discounts, and request Admin access.

## Technologies

- Java
- Spring Boot
- REST API
- Jakarta Validation
- Lombok
- Maven
- Postman

## Project Structure

The project follows a layered architecture:

```text
Client / Postman
       ↓
   Controller
       ↓
    Service
       ↓
 Model / Data
       ↓
   Response
```

### Main Models

- User
- Product
- Category
- Merchant
- MerchantStock

## Validation

- Required fields cannot be empty.
- IDs must contain exactly 3 digits.
- IDs must be unique within their class.
- IDs are checked before update operations.
- Protected fields such as `role` and `balance` cannot be changed directly.

## Main Features

- User management
- Product management
- Category management
- Merchant management
- Merchant stock management
- Product purchasing
- Customer purchase history
- Admin purchase history
- Product refunds
- Product information lookup
- Products by merchant
- Admin request system
- Admin request approval
- Discount request system
- Discount request approval
- 5% approved customer discount
- 10% Admin discount
- Merchant asset calculation
- All merchants asset report

## Additional Endpoints

### Customer History

Returns the purchase history of a specific customer.

### Admin History

Returns the complete purchase history for Admin users.

### Refund

Refunds a purchased item, restores the stock, and returns the appropriate amount to the user.

### Get Product Info

Returns product and merchant information using the MerchantStock ID.

### Get Merchant Products

Returns all products belonging to a specific merchant.

### Request Admin

Allows eligible customers with more than 10 purchases to request Admin access.

### Show Admin Requests

Allows an Admin to view pending Admin requests.

### Approve Admin Request

Promotes an approved customer to Admin.

### Request Discount

Allows eligible customers with more than 5 purchases to request a discount.

### Show Discount Requests

Allows an Admin to view pending discount requests.

### Approve Discount Request

Approves the request and gives the customer a 5% discount.

### Get Merchant Asset

Calculates the total inventory value of one merchant.

### Get All Assets

Allows an Admin to view the inventory value of all merchants.

## Run the Project

Clone the repository and open the project in your IDE.

Run the Spring Boot application, then use **Postman** to test the REST API.

## Author

**Abdulaziz Shafae**  
Computer Information Systems Graduate  
Tuwaiq Academy — Java Web Development & AI

---

**Capstone 1 — Shefo Store**
