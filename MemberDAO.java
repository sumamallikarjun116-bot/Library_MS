package com.library.dao;

import com.library.model.Member;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.*;

public class MemberDAO {
    public List<Member> all() throws SQLException {
        List<Member> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();
            PreparedStatement p=c.prepareStatement("SELECT * FROM members ORDER BY id DESC");
            ResultSet r=p.executeQuery()) {
            while(r.next()) {
                Member m=new Member(); m.setId(r.getInt("id")); m.setName(r.getString("name"));
                m.setEmail(r.getString("email")); m.setPhone(r.getString("phone"));
                m.setMembershipType(r.getString("membership_type")); list.add(m);
            }
        }
        return list;
    }

    public void add(Member m) throws SQLException {
        try(Connection c=DBConnection.getConnection();
            PreparedStatement p=c.prepareStatement("INSERT INTO members(name,email,phone,membership_type) VALUES(?,?,?,?)")) {
            p.setString(1,m.getName()); p.setString(2,m.getEmail()); p.setString(3,m.getPhone());
            p.setString(4,m.getMembershipType()); p.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("DELETE FROM members WHERE id=?")) {
            p.setInt(1,id); p.executeUpdate();
        }
    }
}
