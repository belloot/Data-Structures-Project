import java.util.Scanner;

public class SocialMedia {
    private static UserManager userManager;       // Manages users (BST)
    private static FriendGraph friendGraph;       // Manages relationships (Graph)
    private static InterestManager interestManager; // Manages interests (HashTable + ArrayList of BSTs)
    private static LoginHashTable loginTable;     // Manages authentication (HashTable)
    private static FileHandler fileHandler;       // Handles file operations
    private static User currentUser;              // Tracks the logged-in user  13e12
    
    //Test
    // could have two hashTables here
    // 1. hashTable that stores all users (could be stored in UserManager)
    // 2. hashTable that stores all interests (could be stored in InterestManager)
    
    /**
     * STRATEGY TO READ IN USERS FROM INPUT FILE
     * 1. When we reach a User, we add that user to the hash table using the hash method.
     * The BST for this user will store friends with no other attributes except id.
     * 2. When we finish reading from the file, our hash table will have all the users in it.
     * 3. We loop through the hashTable, when we arrive at a user, we traverse through their
     * friends BST. At each node in the BST, we get the id of that user, then access that friend
     * through the hash table, then set the BST node to have the attributes of the friend we just accessed
     * (what to do if duplicate values...?)
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize components
        fileHandler = new FileHandler();
        userManager = new UserManager();
        friendGraph = new FriendGraph();
        interestManager = new InterestManager();
        loginTable = new LoginHashTable();

        // Load data from the file
        fileHandler.loadData(userManager, friendGraph, interestManager, loginTable);

        System.out.println("Welcome to the Social Media App!");
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Login");
            System.out.println("2. Create a New Account");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> login(scanner);
                case 2 -> createAccount(scanner);
                case 3 -> {
                    quit();
                    isRunning = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    // Handles user login
    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (loginTable.authenticate(username, password)) {
            currentUser = userManager.getUserByUsername(username);
            System.out.println("Login successful! Welcome, " + currentUser.getFirstName() + ".");
            userMenu(scanner);
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    // Handles creating a new user account
    private static void createAccount(Scanner scanner) {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter city: ");
        String city = scanner.nextLine();

        // Create new user
        currentUser = new User(userManager.searchUsersByName(username).size() + 1, firstName, lastName, username);
        currentUser.addInterest("Example Interest 1");
        currentUser.addInterest("Example Interest 2");

        userManager.addUser(currentUser);
        loginTable.addUser(username, password);
        friendGraph.addUser(currentUser);

        System.out.println("Account created successfully! You are now logged in.");
        userMenu(scanner);
    }

    // Menu for logged-in users
    private static void userMenu(Scanner scanner) {
        boolean isLoggedIn = true;

        while (isLoggedIn) {
            System.out.println("\nUser Menu:");
            System.out.println("1. View My Profile");
            System.out.println("2. View My Friends");
            System.out.println("3. Make New Friends");
            System.out.println("4. Get Friend Recommendations");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> currentUser.toString();
                case 2 -> viewFriends(scanner);
                case 3 -> makeNewFriends(scanner);
                case 4 -> getFriendRecommendations();
                case 5 -> {
                    currentUser = null;
                    isLoggedIn = false;
                    System.out.println("You have been logged out.");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // View friends menu
    private static void viewFriends(Scanner scanner) {
        System.out.println("\nView Friends:");
        System.out.println("1. View Friends Sorted by Name");
        System.out.println("2. Search by Friend Name");
        System.out.println("3. View Friend's Full Profile");
        System.out.println("4. Remove a Friend");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> currentUser.viewFriends();
            case 2 -> {
                System.out.print("Enter friend's name: ");
                String friendName = scanner.nextLine();
                currentUser.searchUsersByName(friendName);
            }
            case 3 -> {
                System.out.print("Enter friend's name: ");
                String friendName = scanner.nextLine();
                System.out.println(userManager.getUserByUsername(friendName));
            }
            case 4 -> {
                System.out.print("Enter friend's name to remove: ");
                String friendName = scanner.nextLine();
                // Logic to remove friend
            }
            default -> System.out.println("Invalid choice. Returning to User Menu.");
        }
    }

    // Make new friends menu
    private static void makeNewFriends(Scanner scanner) {
        System.out.println("\nMake New Friends:");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Interest");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.println(userManager.searchUsersByName(name));
            }
            case 2 -> {
                System.out.print("Enter interest: ");
                String interest = scanner.nextLine();
                System.out.println(interestManager.searchUsersByInterest(interest));
            }
            default -> System.out.println("Invalid choice. Returning to User Menu.");
        }
    }

    // Get friend recommendations
    private static void getFriendRecommendations() {
        System.out.println("\nFriend Recommendations:");
        // Logic to rank and display friend recommendations
    }

    // Quit the application
    private static void quit() {
        System.out.println("Saving data...");
        fileHandler.saveData(userManager, friendGraph, interestManager, loginTable);
        System.out.println("Goodbye!");
    }
}
