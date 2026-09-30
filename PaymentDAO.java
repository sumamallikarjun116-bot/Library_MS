package com.library.dao;

import com.library.model.Payment;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.*;

public class PaymentDAO {
    public List<Payment> all() throws SQLException {
        List<Payment> list=new ArrayList<>();
        String q="SELECT p.*,m.name member_name FROM payments p JOIN members m ON p.member_id=m.id ORDER BY p.id DESC";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(q); ResultSet r=p.executeQuery()) {
            while(r.next()) {
                Payment x=new Payment(); x.setId(r.getInt("id")); x.setMemberId(r.getInt("member_id"));
                x.setMemberName(r.getString("member_name")); x.setAmount(r.getDouble("amount"));
                x.setPaymentMethod(r.getString("payment_method")); x.setPaymentType(r.getString("payment_type"));
                x.setTransactionRef(r.getString("transaction_ref")); x.setPaymentDate(r.getTimestamp("payment_date"));
                x.setStatus(r.getString("status")); list.add(x);
            }
        }
        return list;
    }

    public void add(int memberId,double amount,String method,String type,String ref) throws SQLException {
        String q="INSERT INTO payments(member_id,amount,payment_method,payment_type,transaction_ref,status) VALUES(?,?,?,?,?,'PAID')";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(q)) {
            p.setInt(1,memberId);p.setDouble(2,amount);p.setString(3,method);
            p.setString(4,type);p.setString(5,ref);p.executeUpdate();
        }
    }
}
