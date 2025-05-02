# Product Management System

A Spring Boot-based RESTful API for managing products with user roles and authentication. The system includes admin/user role management, product CRUD operations, and API documentation via Swagger. Data is persisted in a MySQL database.

---

## 🛠 Technologies Used

- Java 8  
- Spring Boot  
- Spring Data JPA  
- Spring Security  
- MySQL  
- Swagger (for API documentation)  
- Maven  
- RESTful APIs

---

## ✅ Features

- 🔐 Role-based access using Spring Security  
- 🌱 On first run, the application seeds:
  - Two roles: `ADMIN` and `USER`
  - One admin user with credentials:
    - **Username:** `admins`
    - **Password:** `admin123`
- 👤 Admin can:
  - Register new users (default role: USER)
  - Access all product APIs
- 👥 Registered users (with USER role) can:
  - Access only permitted endpoints
- 📦 Product Management:
  - Add product
  - Get all products
  - Get product by ID
  - Update product
  - Delete product

## ⚙️ How to Run

### 1. **Clone the Repository**

```bash
git clone <https://github.com/farhaaaa/ProductManagementSystem.git>
cd ProductManagementSystem
```

---

### 2. **Configure MySQL Database**

1. Create the database:

```sql
CREATE DATABASE productmanagementsystem;
```

2. Update the `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/productmanagementsystem
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 3. **Build and Run the Application**

```bash
mvn clean install
mvn spring-boot:run
```

---

### 4. **Access the Application**

- **Swagger UI** (interactive API docs):  
  `http://localhost:8080/swagger-ui.html`

---

## ✅ Testing

The project includes a unit test for the `createProduct` endpoint in `ProductController`, which:

- Mocks the `ProductService`
- Validates correct HTTP response and JSON structure

## 🧑‍💻 Author

Developed by [Farha Mansuri]

---

## 📄 License

This project is open source and available under the MIT License.