package com.library.model;

import java.sql.Date;

public class IssuedBook {
    private int id,bookId,memberId;
    private String bookTitle,memberName,status;
    private Date issueDate,dueDate,returnDate;
    private double fine;

    public int getId(){return id;}
    public void setId(int v){id=v;}
    public int getBookId(){return bookId;}
    public void setBookId(int v){bookId=v;}
    public int getMemberId(){return memberId;}
    public void setMemberId(int v){memberId=v;}
    public String getBookTitle(){return bookTitle;}
    public void setBookTitle(String v){bookTitle=v;}
    public String getMemberName(){return memberName;}
    public void setMemberName(String v){memberName=v;}
    public String getStatus(){return status;}
    public void setStatus(String v){status=v;}
    public Date getIssueDate(){return issueDate;}
    public void setIssueDate(Date v){issueDate=v;}
    public Date getDueDate(){return dueDate;}
    public void setDueDate(Date v){dueDate=v;}
    public Date getReturnDate(){return returnDate;}
    public void setReturnDate(Date v){returnDate=v;}
    public double getFine(){return fine;}
    public void setFine(double v){fine=v;}
}
