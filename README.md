# 📚 Library Management System — Java + JDBC + MySQL

A complete responsive Library Management System for GitHub.

## Stack
- Frontend: HTML5, CSS3, JavaScript, JSP
- Backend: Java Servlet
- Database access: JDBC
- Database: MySQL
- Build: Maven
- Server: Apache Tomcat 10+

## Features
- Dashboard
- Add / delete books
- Add / delete members
- Search books
- Issue books
- Return books
- Fine calculation
- Membership/payment records
- Payment method: Cash, UPI, Card, Net Banking
- Payment status
- JDBC PreparedStatement
- JDBC transactions
- Responsive UI

## Setup
1. Install JDK 17+, MySQL, Maven and Tomcat 10+.
2. Run `database/library_db.sql` in MySQL.
3. Edit `src/main/java/com/library/util/DBConnection.java`.
4. Put your MySQL username/password there.
5. Run `mvn clean package`.
6. Deploy `target/library-management-jdbc.war` to Tomcat.
7. Open: `http://localhost:8080/library-management-jdbc/library`

Never commit a real database password to a public GitHub repository. Use environment variables or server configuration for production.
