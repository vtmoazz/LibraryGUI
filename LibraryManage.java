package librarygui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import librarygui.BookArray;

public class LibraryManage extends JFrame {
    private BookArray bookArray = new BookArray();

    private JTextField codeField = new JTextField(10);
    private JTextField titleField = new JTextField(15);
    private JTextField authorField = new JTextField(15);
    private JTextField yearField = new JTextField(5);
    private JTextField priceField = new JTextField(7);
    private JTextField quantityField = new JTextField(5);
    private JTextArea displayArea = new JTextArea(5, 30);

    private DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Code", "Title", "Author", "Year", "Unit Price", "Quantity"}, 0);
    private JTable bookTable = new JTable(tableModel);

    public LibraryManage() {
        setTitle("Library Book Management");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Book Information"));
        inputPanel.add(new JLabel("Code:"));
        inputPanel.add(codeField);
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Published Year:"));
        inputPanel.add(yearField);
        inputPanel.add(new JLabel("Unit Price:"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton priceButton = new JButton("Price");
        JButton deleteButton = new JButton("Delete");
        JButton findButton = new JButton("Find");
        JButton largestButton = new JButton("Largest Quantity");
        JButton reloadButton = new JButton("Reload");

        buttonPanel.add(addButton);
        buttonPanel.add(priceButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(findButton);
        buttonPanel.add(largestButton);
        buttonPanel.add(reloadButton);

        addButton.addActionListener(new AddButtonListener());
        priceButton.addActionListener(new PriceButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());
        findButton.addActionListener(new FindButtonListener());
        largestButton.addActionListener(new LargestButtonListener());
        reloadButton.addActionListener(new ReloadButtonListener());

        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JScrollPane tableScrollPane = new JScrollPane(bookTable);
        bookTable.setFillsViewportHeight(true);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tableScrollPane, BorderLayout.EAST);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void addBookToTable(Book book) {
        tableModel.addRow(new Object[]{
                book.getCode(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublishedYear(),
                book.getUnitPrice(),
                book.getQuantity()
        });
    }


    private void refreshTable() {
        tableModel.setRowCount(0); 
        for (Book book : bookArray.getBooks()) {
            addBookToTable(book);
        }
    }

    private void clearForm() {
        codeField.setText("");
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
        priceField.setText("");
        quantityField.setText("");
    }

    private boolean validateInputs() {
        try {
            if (codeField.getText().isEmpty() || titleField.getText().isEmpty() || authorField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Code, Title, and Author cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            Integer.parseInt(yearField.getText()); 
            Double.parseDouble(priceField.getText()); 
            Integer.parseInt(quantityField.getText()); 
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Year, Unit Price, and Quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (validateInputs()) {
                String code = codeField.getText();
                String title = titleField.getText();
                String author = authorField.getText();
                int year = Integer.parseInt(yearField.getText());
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                Book book = new Book(code, title, author, year, price, quantity);
                if (bookArray.addBook(book)) {  
                    addBookToTable(book);
                    displayArea.append("Book added: " + title + "\n");
                    clearForm(); 
                } else {
                    JOptionPane.showMessageDialog(LibraryManage.this, "Book with code " + code + " already exists.", "Duplicate Code Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class PriceButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double totalPrice = bookArray.calculateTotalPrice();
            displayArea.append("Total Price (including transport): " + totalPrice + "\n");
        }
    }

    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String code = codeField.getText();
            if (bookArray.deleteBook(code)) {
                displayArea.append("Deleted book with code: " + code + "\n");
                refreshTable(); 
            } else {
                JOptionPane.showMessageDialog(LibraryManage.this, "No book found with code " + code, "Deletion Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class FindButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String searchTerm = codeField.getText().isEmpty() ? titleField.getText() : codeField.getText();
            tableModel.setRowCount(0); 
            boolean found = false;

            for (Book book : bookArray.getBooks()) {
                if (book.getCode().equalsIgnoreCase(searchTerm) || book.getTitle().equalsIgnoreCase(searchTerm)) {
                    addBookToTable(book);
                    found = true;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(LibraryManage.this, "No book found with code or title: " + searchTerm, "Search Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class LargestButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Book book = bookArray.findLargestQuantityBook();
            if (book != null) {
                displayArea.append("Book with largest quantity: " + book.getTitle() + ", Quantity: " + book.getQuantity() + "\n");
            } else {
                displayArea.append("No books in the library.\n");
            }
        }
    }

    private class ReloadButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            refreshTable(); 
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryManage gui = new LibraryManage();
            gui.setVisible(true);
        });
    }
}
