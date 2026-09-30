package com.library.dao;

import com.library.model.Book;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.*;

public class BookDAO {
    public List<Book> all() throws SQLException {
        List<Book> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();
            PreparedStatement p=c.prepareStatement("SELECT * FROM books ORDER BY id DESC");
            ResultSet r=p.executeQuery()) {
            while(r.next()) {
                Book b=new Book();
                b.setId(r.getInt("id")); b.setTitle(r.getString("title"));
                b.setAuthor(r.getString("author")); b.setCategory(r.getString("category"));
                b.setIsbn(r.getString("isbn")); b.setQuantity(r.getInt("quantity"));
                b.setAvailable(r.getInt("available")); list.add(b);
            }
        }
        return list;
    }

    public void add(Book b) throws SQLException {
        String q="INSERT INTO books(title,author,category,isbn,quantity,available) VALUES(?,?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(q)) {
            p.setString(1,b.getTitle()); p.setString(2,b.getAuthor()); p.setString(3,b.getCategory());
            p.setString(4,b.getIsbn()); p.setInt(5,b.getQuantity()); p.setInt(6,b.getQuantity());
            p.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("DELETE FROM books WHERE id=?")) {
            p.setInt(1,id); p.executeUpdate();
        }
    }

    public List<Book> available() throws SQLException {
        List<Book> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();
            PreparedStatement p=c.prepareStatement("SELECT * FROM books WHERE available>0");
            ResultSet r=p.executeQuery()) {
            while(r.next()) {
                Book b=new Book(); b.setId(r.getInt("id")); b.setTitle(r.getString("title"));
                b.setAvailable(r.getInt("available")); list.add(b);
            }
        }
        return list;
    }
}
