import java.util.ArrayList;
import java.util.Scanner;

// Task class
class Task {
    private String description;
    private String time;  // e.g., "09:00 AM"

    public Task(String description, String time) {
        this.description = description;
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String toString() {
        return time + " - " + description;
    }
}

// User class
class User {
    private String username;
    private String password;
    private ArrayList<Task> tasks;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.tasks = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}

// DailyPlanner class - handles user management and task operations
class DailyPlanner {
    private ArrayList<User> users;
    private User loggedInUser;

    public DailyPlanner() {
        users = new ArrayList<>();
        loggedInUser = null;
    }

    public boolean register(String username, String password) {
        if (getUserByUsername(username) != null) {
            return false;  // username taken
        }
        users.add(new User(username, password));
        return true;
    }

    public boolean login(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.checkPassword(password)) {
            loggedInUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        loggedInUser = null;
    }

    private User getUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}

public class Daily_Planner {
    private static Scanner scanner = new Scanner(System.in);
    private static DailyPlanner planner = new DailyPlanner();

    public static void main(String[] args) {
        while (true) {
            if (planner.getLoggedInUser() == null) {
                showWelcomeMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private static void showWelcomeMenu() {
        System.out.println("\n=== Daily Planner ===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> registerUser();
            case "2" -> loginUser();
            case "0" -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid option");
        }
    }

    private static void registerUser() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();

        if (planner.register(username, password)) {
            System.out.println("Registration successful! Please login.");
        } else {
            System.out.println("Username already taken.");
        }
    }

    private static void loginUser() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (planner.login(username, password)) {
            System.out.println("Logged in successfully. Welcome, " + username + "!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void showUserMenu() {
        System.out.println("\n=== Daily Planner - User: " + planner.getLoggedInUser().getUsername() + " ===");
        System.out.println("1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Delete Task");
        System.out.println("4. Logout");
        System.out.print("Choose option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> addTask();
            case "2" -> viewTasks();
            case "3" -> deleteTask();
            case "4" -> {
                planner.logout();
                System.out.println("Logged out.");
            }
            default -> System.out.println("Invalid option");
        }
    }

    private static void addTask() {
        System.out.print("Enter task time (e.g., 09:00 AM): ");
        String time = scanner.nextLine();
        System.out.print("Enter task description: ");
        String desc = scanner.nextLine();

        Task task = new Task(desc, time);
        planner.getLoggedInUser().addTask(task);
        System.out.println("Task added.");
    }

    private static void viewTasks() {
        ArrayList<Task> tasks = planner.getLoggedInUser().getTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled.");
        } else {
            System.out.println("\nYour Tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private static void deleteTask() {
        viewTasks();
        System.out.print("Enter task number to delete: ");
        try {
            int taskNum = Integer.parseInt(scanner.nextLine());
            planner.getLoggedInUser().removeTask(taskNum - 1);
            System.out.println("Task deleted (if valid number).");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }
}
