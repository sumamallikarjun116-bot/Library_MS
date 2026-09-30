package com.library.dao;

import com.library.model.IssuedBook;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.*;

public class IssueDAO {
    public List<IssuedBook> all() throws SQLException {
        List<IssuedBook> list=new ArrayList<>();
        String q="SELECT i.*,b.title book_title,m.name member_name FROM issued_books i " +
                 "JOIN books b ON i.book_id=b.id JOIN members m ON i.member_id=m.id ORDER BY i.id DESC";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(q); ResultSet r=p.executeQuery()) {
            while(r.next()) {
                IssuedBook x=new IssuedBook();
                x.setId(r.getInt("id")); x.setBookId(r.getInt("book_id")); x.setMemberId(r.getInt("member_id"));
                x.setBookTitle(r.getString("book_title")); x.setMemberName(r.getString("member_name"));
                x.setIssueDate(r.getDate("issue_date")); x.setDueDate(r.getDate("due_date"));
                x.setReturnDate(r.getDate("return_date")); x.setStatus(r.getString("status"));
                x.setFine(r.getDouble("fine")); list.add(x);
            }
        }
        return list;
    }

    public void issue(int bookId,int memberId,Date due) throws SQLException {
        Connection c=null;
        try {
            c=DBConnection.getConnection(); c.setAutoCommit(false);
            try(PreparedStatement p=c.prepareStatement("SELECT available FROM books WHERE id=? FOR UPDATE")) {
                p.setInt(1,bookId);
                try(ResultSet r=p.executeQuery()) {
                    if(!r.next()) throw new SQLException("Book not found");
                    if(r.getInt(1)<=0) throw new SQLException("Book is not available");
                }
            }
            try(PreparedStatement p=c.prepareStatement("INSERT INTO issued_books(book_id,member_id,issue_date,due_date) VALUES(?,?,CURDATE(),?)")) {
                p.setInt(1,bookId); p.setInt(2,memberId); p.setDate(3,due); p.executeUpdate();
            }
            try(PreparedStatement p=c.prepareStatement("UPDATE books SET available=available-1 WHERE id=?")) {
                p.setInt(1,bookId); p.executeUpdate();
            }
            c.commit();
        } catch(Exception e) {
            if(c!=null)c.rollback();
            throw new SQLException(e.getMessage(),e);
        } finally { if(c!=null)c.close(); }
    }

    public void returnBook(int issueId) throws SQLException {
        Connection c=null;
        try {
            c=DBConnection.getConnection(); c.setAutoCommit(false);
            int bookId;
            try(PreparedStatement p=c.prepareStatement("SELECT book_id,status FROM issued_books WHERE id=? FOR UPDATE")) {
                p.setInt(1,issueId);
                try(ResultSet r=p.executeQuery()) {
                    if(!r.next())throw new SQLException("Issue record not found");
                    if("RETURNED".equals(r.getString("status")))throw new SQLException("Already returned");
                    bookId=r.getInt("book_id");
                }
            }
            try(PreparedStatement p=c.prepareStatement(
                "UPDATE issued_books SET return_date=CURDATE(),status='RETURNED',fine=GREATEST(DATEDIFF(CURDATE(),due_date),0)*5 WHERE id=?")) {
                p.setInt(1,issueId);p.executeUpdate();
            }
            try(PreparedStatement p=c.prepareStatement("UPDATE books SET available=available+1 WHERE id=?")) {
                p.setInt(1,bookId);p.executeUpdate();
            }
            c.commit();
        } catch(Exception e) {
            if(c!=null)c.rollback();
            throw new SQLException(e.getMessage(),e);
        } finally { if(c!=null)c.close(); }
    }
}
