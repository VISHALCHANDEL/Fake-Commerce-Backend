# FakeCommerce

Small Spring Boot e-commerce sample API (FakeCommerce).

**Contents**
- Features
- Technology stack
- Project structure
- Public APIs (endpoints)
- DTOs and models (high-level)
- Setup & run instructions
- Example curl requests
- Troubleshooting & notes

---

## Features
- Product CRUD and listing
- Category listing
- Order lifecycle: create, update, fetch, delete, order summary
- Review creation and retrieval (by product / by order)
- DB migrations with Flyway
- JPA entities with soft-delete support
- Global exception handling and ApiResponse wrapper

## Technology stack
- Java 21 (toolchain configured)
- Spring Boot 4.x
- Spring Data JPA (Hibernate)
- MySQL (Connector/J)
- Flyway for migrations
- Lombok for DTOs/entities
- Gradle build
- New Relic optional agent integration

## Project structure (selected)
- `src/main/java/com/example/FakeCommerce/` — application code
  - `controllers/` — REST controllers (Orders, Products, Categories, Reviews, ...)
  - `services/` — business logic
  - `repositories/` — Spring Data JPA repositories
  - `schema/` — JPA entities
  - `dtos/` — request/response DTOs
  - `adapters/` — entity -> DTO mapping helpers
  - `exceptions/` — application exceptions and global handler
- `src/main/resources/application.yml` — configuration
- `src/main/resources/db/migration/` — Flyway migrations

## Public APIs (summary)
Base path: `/api/v1`

Orders
- `GET  /api/v1/orders` — list orders
- `GET  /api/v1/orders/{id}` — get order details
- `POST /api/v1/orders` — create order
- `PUT  /api/v1/orders/{id}` — update order (add/remove/increment/decrement items, change status)
- `DELETE /api/v1/orders/{id}` — delete order
- `GET  /api/v1/orders/{id}/summary` — get order summary (items, totals)

Products
- `GET  /api/v1/products` — list products
- `GET  /api/v1/products/{id}` — product by id
- `POST /api/v1/products` — create product
- `DELETE /api/v1/products/{id}` — delete product

Categories
- `GET /api/v1/categories` — list categories

Reviews (new)
- `GET  /api/v1/reviews` — list all reviews (returns `GetReviewResponseDto` list)
- `POST /api/v1/reviews` — create a review (accepts `CreateReviewRequestDto`)
- `GET  /api/v1/reviews/{id}` — get review by id
- `GET  /api/v1/reviews/product/{productId}` — reviews for a product
- `GET  /api/v1/reviews/order/{orderId}` — reviews for an order
- `DELETE /api/v1/reviews/{id}` — delete review

Responses use a common wrapper: `{ success, message, error, data }`.

## DTOs (selected)
- `GetReviewResponseDto`: `id`, `productId`, `orderId`, `rating`, `comment`, `createdAt`.
- `CreateReviewRequestDto`: `orderId`, `productId`, `rating`, `comment`.
- Order/product DTOs live in `src/main/java/.../dtos/`.

## Setup & run
Prerequisites
- Java 21 JDK installed
- MySQL running and accessible
- Gradle wrapper (project includes `gradlew`)

1. Create a database (example):

```sql
CREATE DATABASE fakecommerce;
-- ensure user and privileges are configured
```

2. Configure DB connection in `src/main/resources/application.yml` (or use env vars):
- `spring.datasource.url: jdbc:mysql://localhost:3306/fakecommerce`
- `spring.datasource.username` and `spring.datasource.password`

3. (Optional) New Relic agent: `newrelic/newrelic.jar` is referenced in `build.gradle` bootRun arguments. Remove or configure if not used.

4. Build the project:

```bash
./gradlew build -x test
```

5. Run the application:

```bash
./gradlew bootRun
```

Default started port: `8083` (see `application.yml` or logs). If port is in use, modify `server.port` in `application.yml`.

## Example curl requests
Create a review (replace ids/values as needed):

```bash
curl -X POST \
  http://localhost:8083/api/v1/reviews \
  -H "Content-Type: application/json" \
  -d '{"orderId": 1, "productId": 1, "rating": 4.5, "comment": "Nice product"}'
```

Get all reviews:

```bash
curl -X GET http://localhost:8083/api/v1/reviews
```

Get reviews by product:

```bash
curl -X GET http://localhost:8083/api/v1/reviews/product/1
```

Get reviews by order:

```bash
curl -X GET http://localhost:8083/api/v1/reviews/order/1
```

Get single review:

```bash
curl -X GET http://localhost:8083/api/v1/reviews/10
```

Delete a review:

```bash
curl -X DELETE http://localhost:8083/api/v1/reviews/10
```

Notes for Windows PowerShell: use `Invoke-WebRequest` or `curl` alias carefully; prefer `Invoke-RestMethod` or `curl.exe` if `curl` is mapped to `Invoke-WebRequest`.

## Troubleshooting
- Port conflicts: check which process is using the port and stop it, or change `server.port`.
- Flyway migrations: schema migrations are in `src/main/resources/db/migration/` and run at startup.
- If you see `New Relic` errors, either configure the agent or remove the `-javaagent` bootRun JVM arg in `build.gradle`.

## Next steps / TODOs
- Add comprehensive unit & integration tests for controllers and services.
- Add validation for request DTOs (`@Valid`) and better error payloads.
- Add pagination for list endpoints.

---

File: [README.md](README.md)

If you want, I can also add quick Postman collection or more detailed API reference (example payloads and response samples).