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
    public static boolean isRunning;
    public static boolean isLoggedIn;

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
        	System.out.println("File not found");
        }
        isRunning = true;

        mainMenu();

        scanner.close();
    }

    private static void mainMenu(){
        

        while (isRunning) {
            System.out.println("Welcome to the Social Media App!");
            System.out.println("\nMain Menu:");
            System.out.println("1. Login");
            System.out.println("2. Create a New Account");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");
            String choice = scanner.next();
            scanner.nextLine();

            switch (choice.trim()) {
                case "1" -> login(scanner);
                case "2" -> createAccount(scanner);
                case "3" -> {
                    quit();
                    isRunning = false;
                    break;
                }
                default -> System.out.println("\nInvalid choice. Please try again.\n");
            }
        }
    }

    // Handles user login
    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (loginTable.authenticate(username, password)) {
            currentUser = loginTable.getUser(username, password);
            System.out.println("\nLogin successful! Welcome, " + currentUser.getFirstName() + ".");
            isLoggedIn = true;
            userMenu(scanner);
        } else {
            System.out.println("\nInvalid username or password. Please try again.\n");
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
        
        if(firstName.isBlank() || lastName.isBlank() || username.isBlank() || password.isBlank() || city.isBlank()) {
        	System.out.println("\nNone of the entries should be empty! Try again.");
        	createAccount(scanner);
        } else {
        	currentUser = new User(userManager.getNumUsers() + 1, firstName, lastName, username, password, city,
                    InterestsArray);

            userManager.addUser(currentUser);
            loginTable.addUser(currentUser);

            System.out.println("\nAccount created successfully! You are now logged in.");
            numCurrentUsers++;
            isLoggedIn = true;
            userMenu(scanner);
        }
    }

    // Menu for logged-in users
    private static void userMenu(Scanner scanner) {
        
        while (isLoggedIn) {
            System.out.println("\nUser Menu:");
            System.out.println("1. View My Profile");
            System.out.println("2. View My Friends");
            System.out.println("3. Make New Friends (search by name, interest, or get recommendations here)");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                	System.out.println("\nYour Profile");
                	System.out.println("------------");
                	currentUser.viewFullProfile();
                	break;
                }
                case 2 -> viewFriends(scanner);
                case 3 -> makeNewFriends(scanner);
                case 4 -> {
                    currentUser = null;
                    isLoggedIn = false;
                    System.out.println("\nYou have been logged out.");
                    mainMenu();
                    break;

                }
                default -> System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    // View friends menu
    private static void viewFriends(Scanner scanner) {
        System.out.println("\nView Friends:");
        System.out.println("1. View Friends Sorted by Name");
        System.out.println("2. Search by Friend Name (option to remove friends in here)");
        System.out.println("3. Back to User Menu");

        System.out.print("Enter your choice: ");
        String choice = scanner.next();
        scanner.nextLine();

        switch (choice.trim()) {
            case "1" -> {
            	//System.out.println("Viewing friends sorted by name: ");
            	
            	if(currentUser.getNumFriends() == 0) {
            		System.out.println("\nYou currently have no friends! Go add some.");
            		viewFriends(scanner);
            		
            		break;
            	} else {
            		currentUser.viewFriendsAlphabetically();
            		System.out.println("\nGoing Back to View Friends\n.\n.\n.\nSuccessful!\n");
                    viewFriends(scanner);
                    
                    break;
            	}
            }
            case "2" -> {
                System.out.print("Enter friend's first name: ");
                String friendFirstName = scanner.nextLine().trim();
                System.out.print("Enter friend's Last name: ");
                String friendLastName = scanner.nextLine().trim();
                ArrayList<User> currentFriendList;
                currentFriendList = currentUser.searchFriendsByName(friendFirstName,friendLastName);

                if(currentFriendList.size() == 0){

                    System.out.println("\nSorry, no matching friends, try again!");

                    System.out.println("\nGoing Back to View Friends\n.\n.\n.\nSuccessful!\n");

                    viewFriends(scanner);
                    
                    break;
                }
                
                //System.out.println(currentFriendList.size());
                System.out.println("\nChoose options: \n");
                System.out.println("1. View profiles of friends with this name (may contain duplicates)");
                System.out.println("2. Remove any friends with this name (will print out all friends with this name)");
                System.out.print("Enter your choice: ");
                String choice2 = scanner.next();
                scanner.nextLine();

                switch(choice2.trim()){

                    case "1" -> {
                        userManager.viewProfileOfFriendsInList(currentFriendList);
                      
                        viewFriends(scanner);

                        break;
                    }

                    case "2" -> {
                        System.out.println("From the users below (may include duplicates), choose one to remove:\n");

                        // khiem handles this part(print all friends in friendlist) (DONE: one line below)
                        userManager.viewProfileOfFriendsInList(currentFriendList);

                        System.out.println("Enter the ID of the friend you want to remove:\n");

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

                        break;

                    }

	                default -> {
	                	System.out.println("Invalid choice. Returning to User Menu.\n.\n.\n.\nSuccessful!");
	                	userMenu(scanner);
	                }
                }
                break;
            }
            
            case "3" ->{
                System.out.println("\nGoing Back to UserMenu\n.\n.\n.\nSuccessful!\n");
                userMenu(scanner);
                break;
            }
            
            default -> System.out.println("\nInvalid choice. Returning to User Menu.");
        }
    }

    // Make new friends menu
    private static void makeNewFriends(Scanner scanner) {
        System.out.println("\nMake New Friends:");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Interest");
        System.out.println("3. Get Friend Recommendations");
        System.out.println("4. Back to User Menu");
        System.out.print("Enter your choice: ");
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
                System.out.println("\nDisplaying users with name: " + friendFirstName + " " + friendLastName + "\n");
                
                
                // removes current user from the wanted friends list (if they are there)
                if(wantedFriendsList.indexOf(currentUser) != -1) {
                	wantedFriendsList.remove(currentUser);
                }
                
                // removes any current friends
                for(User user : wantedFriendsList) {
                	if(friendGraph.areFriends(currentUser, user)) {
                		wantedFriendsList.remove(user);
                	}
                }

                if(wantedFriendsList.size() == 0){

                    System.out.println("The wanted user is either already a friend or there is no one else with that name!");

                    System.out.println("\nGoing back to Make New Friends\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);
                    
                    break;
                }

                for (User user : wantedFriendsList) {
                	
                	System.out.println(user);
                	
                }

                System.out.print("From the list above, type the ID of the friend you want to add, -1 if you want to add none of them: ");

                int friendId = scanner.nextInt();

                scanner.nextLine();

                boolean idValidation = false;

                boolean areFriendsValidation = true;

                for (User user : wantedFriendsList) {

                    if (user.getId() == friendId) {

                        idValidation = true;

                    }
                }

                for (User user : wantedFriendsList){
                    
                    if (!(friendGraph.areFriends(currentUser, user))) {

                    areFriendsValidation = false;

                    break;

                    }
                }
                //System.out.println("idValidation: "+ idValidation + " areFriendsValidtaion: " + ar);

                if (idValidation && (!areFriendsValidation)) {

                    currentUser.addFriend(userManager.searchUserById(friendId));

                    friendGraph.addFriend(currentUser.getId(), friendId);

                    

                    System.out.println("\nFriend successfully added!\n");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);
                    
                    break;

                } else if (friendId == -1) {

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);
                    
                    break;

                } else if (areFriendsValidation && idValidation) {

                    System.out.println("\nYou already have this user as your friend!\n");
                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);
                    
                    break;

                } else {

                    System.out.println("\nThe user ID you entered was either already in your friends list, or it wasn't displayed! Try again.");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);

                    break;
                }


            }
            case 2 -> {
                System.out.print("Enter interest: ");
                String interest = scanner.nextLine();

                ArrayList<User> wantedFriendsList;
                wantedFriendsList = interestManager.searchUsersByInterest(interest,friendGraph,currentUser);

                System.out.println("\nThese are the users that have the desired interest:\n");
                //System.out.println(wantedFriendsList.size());
                
                // removes current user from the wanted friends list (if they are there)
                if(wantedFriendsList.indexOf(currentUser) != -1) {
                	wantedFriendsList.remove(currentUser);
                }
                
             // removes any current friends
                for(User user : wantedFriendsList) {
                	if(friendGraph.areFriends(currentUser, user)) {
                		wantedFriendsList.remove(user);
                	}
                }

                if(wantedFriendsList.size() == 0){

                    System.out.println("\nNo user has that interest, or you have already added the users with this interest as a friend!");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);
                    
                    break;
                }

                for (User user : wantedFriendsList) {

                    System.out.println(user);

                }

                System.out.print("From the list above, type the ID of the friend you want to add, -1 if you want to add none of them: ");

                int friendId = scanner.nextInt();

                scanner.nextLine();

                boolean idValidation = false;

                boolean areFriendsValidation = true;

                for (User user : wantedFriendsList) {

                    if (user.getId() == friendId) {

                        idValidation = true;

                    }
                }

                for (User user : wantedFriendsList){
                    
                    if (!(friendGraph.areFriends(currentUser, user))) {

                    areFriendsValidation = false;

                    break;

                    }
                }

                //System.out.println("idValidation: "+ idValidation + " areFriendsValidtaion: " + areFriendsValidation);

                if (idValidation && (!areFriendsValidation)) {

                    currentUser.addFriend(userManager.searchUserById(friendId));

                    friendGraph.addFriend(currentUser.getId(), friendId);

                    System.out.println("Friend successfully added!\n");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);
                    
                    break;

                } else if (friendId == -1) {

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);
                    
                    break;

                } else if (areFriendsValidation && idValidation) {

                    System.out.println("You already have this user as your friend!\n");
                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);
                    
                    break;

                } else {

                    System.out.println("\nYou entered an invalid choice, try again.");

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);
                    
                    break;
                }

            }

            case 3 -> {

                System.out.print("Here are some users we recommend to you:\n");

                if (currentUser.getNumFriends() == 0) {

                    System.out.println(
                            "Sorry, you currently have no friends yet, no recommendation could be provided, try to add more friends first!\n");

                    System.out.println("\nGoing back to Make New Friends\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);
                    
                    break;
                }
                
               

                ArrayList<User> wantedFriendsList;
                wantedFriendsList = friendGraph.getRecommendations(currentUser, userManager, interestManager);
                
             // removes current user from the wanted friends list (if they are there)
                if(wantedFriendsList.indexOf(currentUser) != -1) {
                	wantedFriendsList.remove(currentUser);
                }
                
             // removes any current friends
                for(User user : wantedFriendsList) {
                	if(friendGraph.areFriends(currentUser, user)) {
                		wantedFriendsList.remove(user);
                	}
                }

                if(wantedFriendsList.size() == 0){

                    System.out.println("No recommendations at this time!");

                    System.out.println("\nGoing back to Make New Friends Tab\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);
                    
                    break;

                }

                // print the friends in list
                for (User user : wantedFriendsList) {

                    System.out.println(user.getProfileWithFriendCredit());
                
                    user.resetFriendCredit();
                }

                System.out.println("From the list above, type the ID of the friend you want to add, -1 if you want to add none of them: ");

                int friendId = scanner.nextInt();

                
                scanner.nextLine();

                boolean idValidation = false;

                boolean areFriendsValidation = true;

                for (User user : wantedFriendsList) {

                    if (user.getId() == friendId) {

                        idValidation = true;

                    }
                }

                for (User user : wantedFriendsList){
                    
                    if (!(friendGraph.areFriends(currentUser, user))) {

                    areFriendsValidation = false;

                    break;

                    }
                }

                if (idValidation && (!areFriendsValidation)) {

                    currentUser.addFriend(userManager.searchUserById(friendId));

                    friendGraph.addFriend(currentUser.getId(), friendId);

                    System.out.println("Friend successfully added!\n");

                    System.out.println("\nGoing back to Make New Friends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);
                    
                    break;

                } else if (friendId == -1) {

                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");
                    makeNewFriends(scanner);
                    
                    break;

                } else {

                    System.out.println("You already added this user as a friend or this user was not displayed!");
                    System.out.println("\nGoing back to makeNewFriends Tab\n.\n.\n.Successful!\n");

                    makeNewFriends(scanner);
                    
                    break;

                }

            }
            case 4 -> {

                System.out.println("\nGoing back to UserMenu\n.\n.\n.Successful!\n");

                userMenu(scanner);

                break;

            }
            default -> {
            	System.out.println("Invalid choice. Returning to User Menu.");
            	
            	userMenu(scanner);
            	
            	break;
            }
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
