/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarygui;

public class Book {

    private String title;
    private String author;
    private int publishedYear;
    private double unitPrice;
    private int quantity;
    private String code;

    public Book(String code, String title, String author, int publishedYear, double unitPrice, int quantity) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return unitPrice * quantity;
    }

    public double getTransportPrice() {
        double bookPrice = getTotalPrice();
        if (quantity <= 50) {
            return 0;
        } else if (quantity <= 500) {
            return bookPrice * 0.02;
        } else {
            return bookPrice * 0.05;
        }
    }
}
