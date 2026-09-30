package com.library.model;

public class Book {
    private int id, quantity, available;
    private String title, author, category, isbn;

    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public int getQuantity(){return quantity;}
    public void setQuantity(int quantity){this.quantity=quantity;}
    public int getAvailable(){return available;}
    public void setAvailable(int available){this.available=available;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}
    public String getAuthor(){return author;}
    public void setAuthor(String author){this.author=author;}
    public String getCategory(){return category;}
    public void setCategory(String category){this.category=category;}
    public String getIsbn(){return isbn;}
    public void setIsbn(String isbn){this.isbn=isbn;}
}
