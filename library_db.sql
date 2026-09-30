CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

CREATE TABLE books (
 id INT PRIMARY KEY AUTO_INCREMENT,
 title VARCHAR(200) NOT NULL,
 author VARCHAR(150) NOT NULL,
 category VARCHAR(100),
 isbn VARCHAR(50) UNIQUE,
 quantity INT NOT NULL DEFAULT 1,
 available INT NOT NULL DEFAULT 1,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE members (
 id INT PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(150) NOT NULL,
 email VARCHAR(150) UNIQUE NOT NULL,
 phone VARCHAR(30),
 membership_type VARCHAR(50) DEFAULT 'Standard',
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE issued_books (
 id INT PRIMARY KEY AUTO_INCREMENT,
 book_id INT NOT NULL,
 member_id INT NOT NULL,
 issue_date DATE NOT NULL,
 due_date DATE NOT NULL,
 return_date DATE,
 status ENUM('ISSUED','RETURNED') DEFAULT 'ISSUED',
 fine DECIMAL(10,2) DEFAULT 0,
 FOREIGN KEY(book_id) REFERENCES books(id),
 FOREIGN KEY(member_id) REFERENCES members(id)
);

CREATE TABLE payments (
 id INT PRIMARY KEY AUTO_INCREMENT,
 member_id INT NOT NULL,
 amount DECIMAL(10,2) NOT NULL,
 payment_method ENUM('CASH','UPI','CARD','NET_BANKING') NOT NULL,
 payment_type VARCHAR(50) DEFAULT 'MEMBERSHIP',
 transaction_ref VARCHAR(100),
 payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 status ENUM('PAID','PENDING','FAILED') DEFAULT 'PAID',
 FOREIGN KEY(member_id) REFERENCES members(id)
);

INSERT INTO books(title,author,category,isbn,quantity,available) VALUES
('Java Programming','Herbert Schildt','Programming','ISBN001',5,5),
('Database System Concepts','Silberschatz','Database','ISBN002',4,4),
('Clean Code','Robert C. Martin','Programming','ISBN003',3,3);

INSERT INTO members(name,email,phone,membership_type) VALUES
('Suma','suma@example.com','9876543210','Premium'),
('Ravi','ravi@example.com','9876501234','Standard');
