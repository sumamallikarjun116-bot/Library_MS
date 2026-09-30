package com.library.web;

import com.library.dao.*;
import com.library.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/library")
public class LibraryServlet extends HttpServlet {
    private final BookDAO books=new BookDAO();
    private final MemberDAO members=new MemberDAO();
    private final IssueDAO issues=new IssueDAO();
    private final PaymentDAO payments=new PaymentDAO();

    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        try {
            req.setAttribute("books",books.all());
            req.setAttribute("availableBooks",books.available());
            req.setAttribute("members",members.all());
            req.setAttribute("issues",issues.all());
            req.setAttribute("payments",payments.all());
            req.getRequestDispatcher("/index.jsp").forward(req,resp);
        }catch(Exception e){throw new ServletException(e);}
    }

    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        try {
            String a=req.getParameter("action");
            if("addBook".equals(a)){
                Book b=new Book();b.setTitle(req.getParameter("title"));b.setAuthor(req.getParameter("author"));
                b.setCategory(req.getParameter("category"));b.setIsbn(req.getParameter("isbn"));
                b.setQuantity(Integer.parseInt(req.getParameter("quantity")));books.add(b);
            }else if("deleteBook".equals(a))books.delete(Integer.parseInt(req.getParameter("id")));
            else if("addMember".equals(a)){
                Member m=new Member();m.setName(req.getParameter("name"));m.setEmail(req.getParameter("email"));
                m.setPhone(req.getParameter("phone"));m.setMembershipType(req.getParameter("membershipType"));members.add(m);
            }else if("deleteMember".equals(a))members.delete(Integer.parseInt(req.getParameter("id")));
            else if("issueBook".equals(a))issues.issue(Integer.parseInt(req.getParameter("bookId")),
                    Integer.parseInt(req.getParameter("memberId")),Date.valueOf(req.getParameter("dueDate")));
            else if("returnBook".equals(a))issues.returnBook(Integer.parseInt(req.getParameter("issueId")));
            else if("addPayment".equals(a))payments.add(Integer.parseInt(req.getParameter("memberId")),
                    Double.parseDouble(req.getParameter("amount")),req.getParameter("paymentMethod"),
                    req.getParameter("paymentType"),req.getParameter("transactionRef"));
            resp.sendRedirect(req.getContextPath()+"/library");
        }catch(Exception e){req.setAttribute("error",e.getMessage());doGet(req,resp);}
    }
}
