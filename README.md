# 3D Shoe Store Backend

This repository is part of a 3D E-commerce platform for selling shoes. It enables managing shoe inventory, employee performance, and tracking sales performance. The backend is built with **Spring Boot** and uses **MySQL** for data storage, with **PayHere** integrated as the payment gateway.

---

## What's New
- **Hibernate Configuration**: Automatic creation of database and tables on project startup.
- **Service Classes**: Provides reusable services throughout the project.
- **DAO Classes**: Direct interaction with the database.
- **Bug Fixes**: Improved handling for product images, security, etc.
- **IDE Compatibility**: Supports both Eclipse and IntelliJ IDEA.
- **Code Redesign**: Enhanced reusability and maintainability.

*Disclaimer*: The project is under active development. Some features, like cart functionality, are still in progress.

---

## Quickstart
1. **Clone the repository**.
2. **Open the project in your IDE** (IntelliJ IDEA recommended).
   - Ensure the IDE recognizes the project as a Spring Boot and Maven project.
3. **Configure the Database**: Update `application.properties` (more details below).
4. **Run the Project**: Start by running the main method in `ShoemanagementApplication.java`.
5. **Access the Application**: Open [http://localhost:8080](http://localhost:8080) in your browser.

---

## Database Configuration
- **Database**: MySQL or MariaDB.
- Configure the following in `src/main/resources/application.properties`:

  ```properties
  spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/shoe_management?createDatabaseIfNotExist=true
  spring.datasource.username=root
  spring.datasource.password=root
