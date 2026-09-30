package com.library.model;

import java.sql.Timestamp;

public class Payment {
    private int id,memberId;
    private String memberName,paymentMethod,paymentType,transactionRef,status;
    private double amount;
    private Timestamp paymentDate;

    public int getId(){return id;}
    public void setId(int v){id=v;}
    public int getMemberId(){return memberId;}
    public void setMemberId(int v){memberId=v;}
    public String getMemberName(){return memberName;}
    public void setMemberName(String v){memberName=v;}
    public double getAmount(){return amount;}
    public void setAmount(double v){amount=v;}
    public String getPaymentMethod(){return paymentMethod;}
    public void setPaymentMethod(String v){paymentMethod=v;}
    public String getPaymentType(){return paymentType;}
    public void setPaymentType(String v){paymentType=v;}
    public String getTransactionRef(){return transactionRef;}
    public void setTransactionRef(String v){transactionRef=v;}
    public String getStatus(){return status;}
    public void setStatus(String v){status=v;}
    public Timestamp getPaymentDate(){return paymentDate;}
    public void setPaymentDate(Timestamp v){paymentDate=v;}
}
