/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarygui;

import java.util.ArrayList;

public class BookArray {
    private ArrayList<Book> books = new ArrayList<>();

    public boolean addBook(Book book) {
        if (bookExists(book.getCode())) {
            return false; 
        }
        books.add(book);
        return true;
    }

    public boolean bookExists(String code) {
        for (Book book : books) {
            if (book.getCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (Book book : books) {
            total += book.getTotalPrice() + book.getTransportPrice();
        }
        return total;
    }

    public boolean deleteBook(String code) {
        return books.removeIf(book -> book.getCode().equalsIgnoreCase(code));
    }

    public Book findBook(String code) {
        for (Book book : books) {
            if (book.getCode().equalsIgnoreCase(code)) {
                return book;
            }
        }
        return null;
    }

    public Book findLargestQuantityBook() {
        if (books.isEmpty()) return null;
        Book largestBook = books.get(0);
        for (Book book : books) {
            if (book.getQuantity() > largestBook.getQuantity()) {
                largestBook = book;
            }
        }
        return largestBook;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
