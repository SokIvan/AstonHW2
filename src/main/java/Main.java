


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.UserService;
import utils.HibernateSessionFactoryUtil;
import entity.User;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        logger.info("User Service Application Started");

        try {
            displayMenu();
            boolean running = true;

            while (running) {
                System.out.print("\nEnter your choice (1-6): ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        createUser();
                        break;
                    case "2":
                        getUserById();
                        break;
                    case "3":
                        getAllUsers();
                        break;
                    case "4":
                        updateUser();
                        break;
                    case "5":
                        deleteUser();
                        break;
                    case "6":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

                if (running) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    displayMenu();
                }
            }

            System.out.println("Thank you for using User Service!");

        } catch (Exception e) {
            logger.error("Application error: ", e);
        } finally {
            HibernateSessionFactoryUtil.shutdown();
            scanner.close();
            logger.info("User Service Application Stopped");
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== User Service ===");
        System.out.println("1. Create User");
        System.out.println("2. Get User by ID");
        System.out.println("3. Get All Users");
        System.out.println("4. Update User");
        System.out.println("5. Delete User");
        System.out.println("6. Exit");
    }

    private static void createUser() {
        System.out.println("\n--- Create User ---");

        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter age: ");
            int age = Integer.parseInt(scanner.nextLine());

            User user = userService.createUser(name, email, age);
            logger.info("User created successfully: " + user);

        } catch (NumberFormatException e) {
            logger.error("Invalid age format. Please enter a number.");
        } catch (Exception e) {
            logger.error("Error creating user: " + e.getMessage());
        }
    }

    private static void getUserById() {
        System.out.println("\n--- Get User by ID ---");

        try {
            System.out.print("Enter user ID: ");
            Long id = Long.parseLong(scanner.nextLine());

            userService.getUserById(id).ifPresentOrElse(
                    user -> logger.info("User found: " + user),
                    () -> logger.info("User not found with ID: " + id)
            );

        } catch (NumberFormatException e) {
            logger.error("Invalid ID format. Please enter a number.");
        }
    }

    private static void getAllUsers() {
        System.out.println("\n--- All Users ---");

        try {
            List<User> users = userService.getAllUsers();

            if (users.isEmpty()) {
                logger.info("No users found.");
            } else {
                users.forEach(System.out::println);
            }

        } catch (Exception e) {
            logger.error("Error retrieving users: " + e.getMessage());
        }
    }

    private static void updateUser() {
        System.out.println("\n--- Update User ---");

        try {
            System.out.print("Enter user ID to update: ");
            Long id = Long.parseLong(scanner.nextLine());

            System.out.print("Enter new name: ");
            String name = scanner.nextLine();

            System.out.print("Enter new email: ");
            String email = scanner.nextLine();

            System.out.print("Enter new age: ");
            int age = Integer.parseInt(scanner.nextLine());

            User updatedUser = userService.updateUser(id, name, email, age);
            logger.info("User updated successfully: " + updatedUser);

        } catch (NumberFormatException e) {
            logger.error("Invalid number format. Please check your input.");
        } catch (Exception e) {
            logger.error("Error updating user: " + e.getMessage());
        }
    }

    private static void deleteUser() {
        System.out.println("\n--- Delete User ---");

        try {
            System.out.print("Enter user ID to delete: ");
            Long id = Long.parseLong(scanner.nextLine());

            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                logger.info("User deleted successfully.");
            } else {
                logger.info("User not found with ID: " + id);
            }

        } catch (NumberFormatException e) {
            logger.error("Invalid ID format. Please enter a number.");
        }
    }
}