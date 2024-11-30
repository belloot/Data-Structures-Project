import java.util.Scanner;
import java.util.ArrayList;

public class SocialMedia {
    private static UserManager userManager; // Manages users (BST)
    private static FriendGraph friendGraph; // Manages relationships (Graph)
    private static InterestManager interestManager; // Manages interests (HashTable + ArrayList of BSTs)
    private static LoginHashTable loginTable; // Manages authentication (HashTable)
    private static FileHandler fileHandler; // Handles file operations
    private static User currentUser; // Tracks the logged-in user 13e12
    private static Scanner scanner = new Scanner(System.in);

    // could have two hashTables here
    // 1. hashTable that stores all users (could be stored in UserManager)
    // 2. hashTable that stores all interests (could be stored in InterestManager)

    /**
     * STRATEGY TO READ IN USERS FROM INPUT FILE
     * 1. When we reach a User, we add that user to the hash table using the hash
     * method.
     * The BST for this user will store friends with no other attributes except id.
     * 2. When we finish reading from the file, our hash table will have all the
     * users in it.
     * 3. We loop through the hashTable, when we arrive at a user, we traverse
     * through their
     * friends BST. At each node in the BST, we get the id of that user, then access
     * that friend
     * through the hash table, then set the BST node to have the attributes of the
     * friend we just accessed
     * (what to do if duplicate values...?)`1vdvsv
     */

    public static void main(String[] args) {

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
            User currentUser = userManager.getUserByUsername(username);
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
        ArrayList<Interest> InterestsArray;
        InterestsArray = interestManager.createInterestArray(scanner);

        User currentUser = new User();// khien update this one

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
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> currentUser.toString();
                case 2 -> viewFriends(scanner);
                case 3 -> makeNewFriends(scanner);
                case 4 -> {
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

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> currentUser.viewFriendsAlphabetically();
            case 2 -> {
                System.out.print("Enter friend's first name: ");
                String friendFirstName = scanner.nextLine();
                System.out.print("Enter friend's Last name: ");
                String friendLastName = scanner.nextLine();
                ArrayList<User> currentFriendList;
                currentFriendList = currentUser.searchUsersByName(friendFirstName,friendLastName);
                System.out.println("Choose options: \n");
                System.out.println("1. View these friends' profiles");
                System.out.println("2. Remove any friends\n");
                int choice2 = scanner.nextInt();
                scanner.nextLine();

                switch(choice2){

                    case 1 -> userManager.viewProfileOfFriendsInList(currentFriendList);
                    case 2 -> {
                        System.out.println("From the Friends(May include duplicate names) below, choose one to remove:\n");

                        // khiem handles this part(print all friends in friendlist)

                        System.out.println("Enter the id of the friend you want to remove:\n")

                        int removeId = scanner.nextInt();

                        scanner.nextLine();

                        currentUser.removeFriend(removeId);

                    }
                
                    default -> System.out.println("Invalid choice. Returning to User Menu.");
                    
                }
            }
            
            default -> System.out.println("Invalid choice. Returning to User Menu.");
        }
    }

    // Make new friends menu
    private static void makeNewFriends(Scanner scanner) {
        System.out.println("\nMake New Friends:");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Interest");
        System.out.println("3. GetFriendRecommendations");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();

                System.out.print("Enter friend's first name: ");
                String friendFirstName = scanner.nextLine();
                System.out.print("Enter friend's Last name: ");
                String friendLastName = scanner.nextLine();

                ArrayList<User> wantedFriendsList;
                wantedFriendsList = userManager.searchUsersByName(friendFirstName,friendLastName);

                //print all users in list

                System.out.println("Type the id of the friend you want to add, -1 if none you want to add: \n");

                int friendId = scanner.nextInt();

                scanner.nextLine();

                if(!(friendId == -1)){

                    currentUser.addFriend(userManager.searchUserById(friendId));

                    friendGraph.addFriend(currentUser, userManager.searchUserById(friendId));

                }else{
                // go back to make new friends tab
                }
                
            }
            case 2 -> {
                System.out.print("Enter interest: ");
                String interest = scanner.nextLine();

                ArrayList<User> wantedFriendsList;
                wantedFriendsList = userManager.searchUsersByInterests(interest);

                
                //print all users in list

                System.out.println("Type the id of the friend you want to add, -1 if none you want to add: \n");

                int friendId = scanner.nextInt();

                scanner.nextLine();

                if(!(friendId == -1)){

                    currentUser.addFriend(userManager.searchUserById(friendId));

                    friendGraph.addFriend(currentUser, userManager.searchUserById(friendId));

                }else{

                    //go back to make friends tab
                }
             

            }
               

            case 3 ->{

                System.out.print("Here are some friends we recommend to you:\n");
                

                ArrayList<User> wantedFriendsList;
                wantedFriendsList = friendGraph.getRecommendations(currentUser);

                //print the friends in list

                System.out.println("Type the id of the friend you want to add, -1 if none you want to add: \n");

                int friendId = scanner.nextInt();

                scanner.nextLine();

                if(!(friendId == -1)){

                    currentUser.addFriend(userManager.searchUserById(friendId));

                    friendGraph.addFriend(currentUser, userManager.searchUserById(friendId));

                }else{

                    //back to make new friends tab
                }
             
          
            }
            default -> System.out.println("Invalid choice. Returning to User Menu.");
        }

    }



    // Quit the application
    private static void quit() {
        System.out.println("Saving data...");
        fileHandler.saveData(userManager, friendGraph, interestManager, loginTable);
        System.out.println("Goodbye!");
    }
}
