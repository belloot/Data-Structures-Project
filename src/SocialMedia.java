import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SocialMedia {
    private static UserManager userManager; // Manages users (BST)
    private static FriendGraph friendGraph; // Manages relationships (Graph)
    private static InterestManager interestManager; // Manages interests (HashTable + ArrayList of BSTs)
    private static LoginHashTable loginTable; // Manages authentication (HashTable)
    private static FileManager fileHandler; // Handles file operations
    private static User currentUser; // Tracks the logged-in user 13e12
    private static Scanner scanner = new Scanner(System.in);
    public static int numCurrentUsers = 0;

    // TESTING (Khiem 2:44 PM)
    // TESTING (Khiem 2:52 PM)

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
     * @throws FileNotFoundException 
     */

    public static void main(String[] args) {

        // Initialize components
        fileHandler = new FileManager();
        userManager = new UserManager();
        friendGraph = new FriendGraph();
        interestManager = new InterestManager();
        loginTable = new LoginHashTable();

        // Load data from the file
        try {
        	fileHandler.loadData(userManager, friendGraph, interestManager, loginTable, numCurrentUsers);
        } catch (Exception e) {
        	System.out.println("File not founds");
        }

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
            currentUser = loginTable.getUser(username, password);
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

        currentUser = new User(userManager.getNumUsers() + 1, firstName, lastName, username, password, city,
                InterestsArray);

        userManager.addUser(currentUser);
        loginTable.addUser(currentUser);

        System.out.println("Account created successfully! You are now logged in.");
        numCurrentUsers++;
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
                case 1 -> currentUser.viewFullProfile();
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
        System.out.println("3. Back to User Menu");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
            	//System.out.println("Viewing friends sorted by name: ");
            	currentUser.viewFriendsAlphabetically();
                //scanner.nextLine();

                System.out.println("\nGoing Back to ViewFriends\n.\n.\n.\nSuccessful!\n");
                viewFriends(scanner);
            }
            case 2 -> {
                System.out.print("Enter friend's first name: ");
                String friendFirstName = scanner.nextLine().trim();
                System.out.print("Enter friend's Last name: ");
                String friendLastName = scanner.nextLine().trim();
                ArrayList<User> currentFriendList;
                currentFriendList = currentUser.searchFriendsByName(friendFirstName,friendLastName);

                if(currentFriendList.size() == 0){

                    System.out.println("Sorry, no mathching friends, try again!");

                    System.out.println("\nGoing Back to ViewFriends\n.\n.\n.\nSuccessful!\n");

                    viewFriends(scanner);

                }
                
                //System.out.println(currentFriendList.size());
                System.out.println("Choose options: \n");
                System.out.println("1. View these friends' profiles(May contain duplicates)");
                System.out.println("2. Remove any friends with this name (will print out all friends with this name)\n");
                
                int choice2 = scanner.nextInt();
                scanner.nextLine();

                switch(choice2){

                    case 1 -> {
                        userManager.viewProfileOfFriendsInList(currentFriendList);
                      
                        viewFriends(scanner);
                    }

                    case 2 -> {
                        System.out.println("From the Friends(May include duplicate names) below, choose one to remove:\n");

                        // khiem handles this part(print all friends in friendlist) (DONE: one line below)
                        userManager.viewProfileOfFriendsInList(currentFriendList);

                        System.out.println("Enter the id of the friend you want to remove:\n");

                        int removeId = scanner.nextInt();

                        scanner.nextLine();

                        boolean removeValidation = false;

                        for(User user : currentFriendList){
                            if(user.getId() == removeId){
                                removeValidation = true;
                            }
                        }

                        if (removeValidation == true) {
                            currentUser.removeFriend(removeId, userManager);// Remove this persion in friend graph
                            friendGraph.removeFriend(currentUser.getId(), removeId);
                            System.out.println("Friend successfully removed!");
                            System.out.println("\nGoing Back to ViewFriends\n.\n.\n.\nSuccessful!\n");

                        } else {
                            System.out.println("The ID you entered was not displayed!");
                            System.out.println("\nGoing Back to ViewFriends\n.\n.\n.\nSuccessful!\n");

                        }
                        viewFriends(scanner);

                    }
                
                    case 3 -> {
                        System.out.println("Invalid choice. Returning to User Menu.\n.\n.\n.\nSuccessful!");
                        userMenu(scanner);
                    }

                    default -> {System.out.println("Invalid choice. Returning to User Menu.\n.\n.\n.\nSuccessful!");
                    	userMenu(scanner);}
                	}
            }
            case 3 ->{
                System.out.println("\nGoing Back to UserMenu\n.\n.\n.\nSuccessful!\n");
                userMenu(scanner);}
            
            default -> System.out.println("Invalid choice. Returning to User Menu.");
        }
    }

    // Make new friends menu
    private static void makeNewFriends(Scanner scanner) {
        System.out.println("\nMake New Friends:");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Interest");
        System.out.println("3. Get FriendRecommendations");
        System.out.println("4. Back to User Menu");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter friend's first name: ");
                String friendFirstName = scanner.nextLine();
                System.out.print("Enter friend's Last name: ");
                String friendLastName = scanner.nextLine();

                ArrayList<User> wantedFriendsList;
                wantedFriendsList = userManager.searchUsersByName(friendFirstName, friendLastName);
                //System.out.println(wantedFriendsList.size());

                if(wantedFriendsList.size() == 0){

                    System.out.println("Nobody has that Name!");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);

                }

                for (User user : wantedFriendsList) {

                    System.out.println(user);

                }

                System.out.println("Type the id of the friend you want to add, -1 if you want to add none of them: \n");

                int friendId = scanner.nextInt();

                scanner.nextLine();

                boolean idValidation = false;

                boolean areFriendsValidation = true;

                for (User user : wantedFriendsList) {

                    if (user.getId() == friendId) {

                        idValidation = true;

                    }

                    if (!(friendGraph.areFriends(currentUser, user))) {

                        areFriendsValidation = false;

                        break;

                    }


                }
                //System.out.println("idValidation: "+ idValidation + " areFriendsValidtaion: " + ar);

                if (idValidation && (!areFriendsValidation)) {

                    currentUser.addFriend(userManager.searchUserById(friendId));

                    friendGraph.addFriend(currentUser.getId(), friendId);

                    

                    System.out.println("Friend successfully added!\n");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);

                } else if (friendId == -1) {

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);

                } else if (areFriendsValidation && idValidation) {

                    System.out.println("You already have this user as your friend!\n");
                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);

                } else {

                    System.out.println("You entered incorrect id, please check again next time.");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);

                }

            }
            case 2 -> {
                System.out.print("Enter interest: ");
                String interest = scanner.nextLine();

                ArrayList<User> wantedFriendsList;
                wantedFriendsList = interestManager.searchUsersByInterest(interest,friendGraph,currentUser);

                System.out.println("These are the friends that have the desired interest.\n");
                //System.out.println(wantedFriendsList.size());

                if(wantedFriendsList.size() == 0){

                    System.out.println("Nobody has that interest!");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);

                }

                for (User user : wantedFriendsList) {

                    System.out.println(user);

                }

                System.out.println("Type the id of the friend you want to add, -1 if none you want to add: \n");

                int friendId = scanner.nextInt();

                scanner.nextLine();

                boolean idValidation = false;

                boolean areFriendsValidation = true;

                for (User user : wantedFriendsList) {

                    if (user.getId() == friendId) {

                        idValidation = true;

                    }

                    if (!(friendGraph.areFriends(currentUser, user))) {

                        areFriendsValidation = false;

                        break;

                    }


                }

                if (idValidation && (!areFriendsValidation)) {

                    currentUser.addFriend(userManager.searchUserById(friendId));

                    friendGraph.addFriend(currentUser.getId(), friendId);

                    System.out.println("Friend successfully added!\n");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);

                } else if (friendId == -1) {

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);

                } else if (areFriendsValidation && idValidation) {

                    System.out.println("You already have this user as your friend!\n");
                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);

                } else {

                    System.out.println("You entered incorrect id, please check again next time.");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);

                }

            }

            case 3 -> {

                System.out.print("Here are some friends we recommend to you:\n");

                if (currentUser.getNumFriends() == 0) {

                    System.out.println(
                            "Sorry, you currently have no friends yet, no recommendation could be provided, try to add more friends first!\n");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);


                }
                
               

                ArrayList<User> wantedFriendsList;
                wantedFriendsList = friendGraph.getRecommendations(currentUser, userManager, interestManager);

                if(wantedFriendsList.size() == 0){

                    System.out.println("No recommendations at this time!");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);

                }

                // print the friends in list
                for (User user : wantedFriendsList) {

                    System.out.println(user.getProfileWithFriendCredit());
                
                    user.resetFriendCredit();
                }

                System.out.println("Type the id of the friend you want to add, -1 if none you want to add: \n");

                int friendId = scanner.nextInt();

                
                scanner.nextLine();

                boolean idValidation = false;

                boolean areFriendsValidation = true;

                for (User user : wantedFriendsList) {

                    if (user.getId() == friendId) {

                        idValidation = true;

                    }

                    if (!(friendGraph.areFriends(currentUser, user))) {

                        areFriendsValidation = false;

                        break;

                    }


                }

                if (idValidation && (!areFriendsValidation)) {

                    currentUser.addFriend(userManager.searchUserById(friendId));

                    friendGraph.addFriend(currentUser.getId(), friendId);

                    System.out.println("Friend successfully added!\n");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);

                } else if (friendId == -1) {

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);

                } else {

                    System.out.println("You already added him/her as your friend or he is not recommended at all!");
                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);

                }

            }
            case 4 -> {

                System.out.println("\nGoing back to UserMenu\n.\n.\n.Successful!\n");

                userMenu(scanner);
            }
            default -> System.out.println("Invalid choice. Returning to User Menu.");
        }

    }

    // Quit the application
    private static void quit() {
        System.out.println("Saving data...");
        try {
        	fileHandler.saveData(userManager, friendGraph, interestManager, loginTable);
        } catch(Exception e) {
        	System.out.println("Error occurred while writing to file.");
        }
        System.out.println("Goodbye!");
    }

    
}
