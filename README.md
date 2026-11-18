# DishDiscover

A recipe discovery web application built using Java. Users can browse recipes, search for meals, explore categories, and save their favourite recipes.

## Features

- Browse and search recipes.
- Explore recipes by category.
- View recipe details and instructions.
- User registration and login.
- Add and remove favourite recipes.
- Update user profile details.

## Tech Stack

- Java 21
- Servlets and JSP
- MySQL and JDBC
- HTML, CSS, JavaScript
- Gson
- BCrypt
- Maven
- Apache Tomcat 10.1

## API

Uses [TheMealDB API](https://www.themealdb.com/) to fetch recipe information.

## Setup

1. Clone the repository.
2. Create a MySQL database named `dishdiscover`.
3. Configure your database credentials in a `.env` file using `.env.example` as a reference.
4. Run the SQL script `FAVORITES_SETUP.sql` to set up the favorites table.
5. Build the project:

   ```bash
   mvn clean package
   ```

6. Deploy `target/dishdiscover.war` to Apache Tomcat.
7. Open `http://localhost:8080/dishdiscover/` in your browser.

