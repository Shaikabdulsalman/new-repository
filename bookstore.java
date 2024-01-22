import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Book {
    String title;
    String author;
    double price;

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
}

class User {
    String username;
    String password;
    List<Book> cart;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.cart = new ArrayList<>();
    }
}

class BookStore {
    List<Book> books;
    HashMap<String, User> users;
    List<Object[]> orders;
    Lock lock;

    public BookStore() {
        this.books = new ArrayList<>();
        this.users = new HashMap<>();
        this.orders = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void addUser(User user) {
        this.users.put(user.username, user);
    }

    public double processOrder(User user) {
        double orderTotal = user.cart.stream().mapToDouble(book -> book.price).sum();
        lock.lock();
        orders.add(new Object[]{user.username, user.cart, orderTotal});
        user.cart.clear();
        lock.unlock();
        return orderTotal;
    }

    public String registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return "Username already exists.";
        }
        addUser(new User(username, password));
        return "Registration successful.";
    }

    public User loginUser(String username, String password) {
        if (users.containsKey(username) && users.get(username).password.equals(password)) {
            return users.get(username);
        }
        return null;
    }
}

public class Main {
    public static void userShopping(BookStore store, User user) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAvailable Books:");
            for (int i = 0; i < store.books.size(); i++) {
                Book book = store.books.get(i);
                System.out.println(i + ". " + book.title + " by " + book.author + " - $" + book.price);
            }
            System.out.println("-1. Exit");

            System.out.print("Enter the index of the book you want to add to cart: ");
            int choice = scanner.nextInt();
            if (choice == -1) {
                break;
            }
            if (choice >= 0 && choice < store.books.size()) {
                user.cart.add(store.books.get(choice));
            } else {
                System.out.println("Invalid book index!");
            }
        }
    }

    public static void main(String[] args) {
        BookStore store = new BookStore();

        store.addBook(new Book("Clean Code", "Robert C. Martin", 40));
        store.addBook(new Book("Design Patterns", "Erich Gamma", 35));
        store.addBook(new Book("JavaScript: The Good Parts", "Douglas Crockford", 30));
        store.addBook(new Book("Refactoring", "Martin Fowler", 45));
        store.addBook(new Book("Learning Python", "Mark Lutz", 50));
        store.addBook(new Book("Node.js Design Patterns", "Mario Casciaro", 38));
        store.addBook(new Book("Django for Beginners", "William S. Vincent", 25));
        store.addBook(new Book("Ruby on Rails Tutorial", "Michael Hartl", 42));
        store.addBook(new Book("RESTful Web APIs", "Leonard Richardson", 32));
        store.addBook(new Book("The Pragmatic Programmer", "Andrew Hunt", 48));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = scanner.next();
                System.out.print("Enter password: ");
                String password = scanner.next();
                String message = store.registerUser(username, password);
                System.out.println(message);
            } else if (choice == 2) {
                System.out.print("Enter username: ");
                String username = scanner.next();
                System.out.print("Enter password: ");
                String password = scanner.next();
                User user = store.loginUser(username, password);
                if (user != null) {
                    System.out.println("Login successful.");
                    userShopping(store, user);
                    double orderTotal = store.processOrder(user);
                    System.out.println("Order total: $" + orderTotal);
                } else {
                    System.out.println("Login failed.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}