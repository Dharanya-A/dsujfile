import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    @Override
    public String toString() {
        return "Book ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Issued: " + (isIssued ? "Yes" : "No");
    }
}
public class Student {
    private String studentId;
    private String name;
    private List<Book> issuedBooks;  // List to keep track of issued books

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.issuedBooks = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<Book> getIssuedBooks() {
        return issuedBooks;
    }

    public void issueBook(Book book) {
        issuedBooks.add(book);
    }

    public void returnBook(Book book) {
        issuedBooks.remove(book);
    }

       public String toString() {
        return "Student ID: " + studentId + ", Name: " + name;
    }
}
public class Node<T> {
    private T data;
    private Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }

    public T getData() {
        return data;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getNext() {
        return next;
    }
}
public class LinkedList<T> {
    private Node<T> head;

    public LinkedList() {
        this.head = null;
    }

    // Add a node at the end
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }

    // Find a node by a custom key (such as bookId or studentId)
    public T find(String key, java.util.function.Function<T, String> getKey) {
        Node<T> current = head;
        while (current != null) {
            if (getKey.apply(current.getData()).equals(key)) {
                return current.getData();
            }
            current = current.getNext();
        }
        return null;
    }

    // Remove a node by a custom key
    public void remove(String key, java.util.function.Function<T, String> getKey) {
        if (head == null) return;

        if (getKey.apply(head.getData()).equals(key)) {
            head = head.getNext();
            return;
        }

        Node<T> current = head;
        while (current.getNext() != null) {
            if (getKey.apply(current.getNext().getData()).equals(key)) {
                current.setNext(current.getNext().getNext());
                return;
            }
            current = current.getNext();
        }
    }

    // Display all elements in the list
    public void display() {
        Node<T> current = head;
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNext();
        }
    }
}
public class Library {
    private LinkedList<Book> books;
    private LinkedList<Student> students;

    public Library() {
        books = new LinkedList<>();
        students = new LinkedList<>();
    }

    // Add a new book
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    // Add a new student
    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Student added: " + student.getName());
    }

    // Issue a book to a student
    public void issueBook(String bookId, String studentId) {
        Book book = books.find(bookId, Book::getBookId);  // Find book by ID
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (book.isIssued()) {
            System.out.println("Book is already issued.");
            return;
        }

        Student student = students.find(studentId, Student::getStudentId); // Find student by ID
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        book.setIssued(true);
        student.issueBook(book);
        System.out.println("Book issued to " + student.getName());
    }

    // Return a book
    public void returnBook(String bookId, String studentId) {
        Book book = books.find(bookId, Book::getBookId);  // Find book by ID
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (!book.isIssued()) {
            System.out.println("Book is not issued.");
            return;
        }

        Student student = students.find(studentId, Student::getStudentId); // Find student by ID
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        book.setIssued(false);
        student.returnBook(book);
        System.out.println("Book returned by " + student.getName());
    }

    // Display all books
    public void displayBooks() {
        System.out.println("Books in the library:");
        books.display();
    }

    // Display all students
    public void displayStudents() {
        System.out.println("Registered students:");
        students.display();
    }
}
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Register Student");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display Books");
            System.out.println("6. Display Students");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // Add a new book
                    System.out.print("Enter book ID: ");
                    String bookId = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    Book newBook = new Book(bookId, title, author);
                    library.addBook(newBook);
                    break;

                case 2:
                    // Register a new student
                    System.out.print("Enter student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    Student newStudent = new Student(studentId, name);
                    library.addStudent(newStudent);
                    break;

                case 3:
                    // Issue a book to a student
                    System.out.print("Enter book ID to issue: ");
                    String issueBookId = scanner.nextLine();
                    System.out.print("Enter student ID: ");
                    String issueStudentId = scanner.nextLine();
                    library.issueBook(issueBookId, issueStudentId);
                    break;

                case 4:
                    // Return a book
                    System.out.print("Enter book ID to return: ");
                    String returnBookId = scanner.nextLine();
                    System.out.print("Enter student ID: ");
                    String returnStudentId = scanner.nextLine();
                    library.returnBook(returnBookId, returnStudentId);
                    break;

                case 5:
                    // Display all books
                    library.displayBooks();
                    break;

                case 6:
                    // Display all students
                    library.displayStudents();
                    break;

                case 7:
                    // Exit the system
                    System.out.println("Exiting the system...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
