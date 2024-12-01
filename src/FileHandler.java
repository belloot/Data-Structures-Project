import java.io.File;
import java.io.FileReader;
import java.io.FileReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.sound.sampled.spi.AudioFileReader;

/**
 * Handles file operations to load and save user data, friends, and interests.
 */
public class FileHandler {

        /**
         * Default constructor for FileHandler.
         */
        public FileHandler() {

        }

        /**
         * Loads user, friend, and interest data from a file.
         *
         * @param userManager     UserManager to populate user data.
         * @param friendGraph     FriendGraph to populate relationships.
         * @param interestManager InterestManager to populate interests.
         * @param loginTable      LoginHashTable to populate login credentials.
         */
        public void loadData(UserManager userManager, FriendGraph friendGraph, InterestManager interestManager,
                        LoginHashTable loginTable) {
 
                IdComparator IdCmp = new IdComparator();
                File file = new File("NFLPlayers.txt"); // Adjust the file path if needed
                if (file.exists()) {
                        Scanner fileReader = new Scanner(file);

                        while (fileReader.hasNextLine()) {
                                // Load and parse user details
                                int id = Integer.parseInt(fileReader.nextLine().trim());

                                String fullName = fileReader.nextLine().trim();
                                String[] nameParts = fullName.split(" ");
                                String firstName = nameParts.length > 0 ? nameParts[0] : "Unknown";
                                String lastName = nameParts.length > 1 ? nameParts[1] : "Unknown";

                                String username = fileReader.nextLine().trim();
                                String password = fileReader.nextLine().trim();

                               



                                // Load city and interests
                                String city = fileReader.nextLine().trim();
                                int numInterests = Integer.parseInt(fileReader.nextInt());

                                InterestManager currenInterestManager = new InterestManager();

                                ArrayList<Interest> interestofCurrentUser = new ArrayList<>();
                                for (int i = 0; i < numInterests; i++) {
                                        String interest = fileReader.nextLine().trim();
                                        Interest tempInterest = new Interest(0, interest);

                                        // If the interest is not existed
                                        if (currenInterestManager.interestTable.find(tempInterest) == -1) {
                                                interestManager.addInterest(interest);
                                                interestofCurrentUser.interestTable.add(interest);
                                        }

                                        interestofCurrentUser.add(interest);
                                }

                                User user = new User(id, firstName, lastName, username, password,
                                                city, interestofCurrentUser);

                                // Connect the user with the interests
                                for (String interest : interestofCurrentUser) {
                                        interestManager.addUserInterest(user, interest);

                                }


                                loginTable.addUser(username, password);

                                 // Load friends
                                 int numFriends = fileReader.nextInt();
                                 fileReader.nextLine();
                                 ArrayList<User> friends = new ArrayList<>();
                                 for (int i = 0; i < numFriends; i++) {
                                         friends.add(userManager.searchUserById(fileReader.nextInt()));
                                         fileReader.nextLine();
                                 }


                                 for(User friend : friends){

                                        user.getFriendBST().insert(friend,IdCmp);
                                        
                                        if(!(areFriends(user,friend))){

                                                friendGraph.addFriend(user,friend);

                                        }

                                 }


                                 
                                 

                                 

                        }

                        fileReader.close();
                } else {
                        System.out.println("File not found.");
                }
        }

        /**
         * Saves the current state of the application to a file.
         *
         * @param userManager     UserManager containing all users.
         * @param friendGraph     FriendGraph containing all relationships.
         * @param interestManager InterestManager containing all interests.
         * @param loginTable      LoginHashTable containing login credentials.
         */
        public void saveData(UserManager userManager, FriendGraph friendGraph, InterestManager interestManager,
                        LoginHashTable loginTable) {
                String fileName = "NFLPlayers.txt";
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false)) {  // remove all the original data
                       
                        // ArrayList<User> users = userManager.getAllUsers();

                      
                        // put all the current data in the file

                        for (User user : users) {
                                // Save user ID
                                writer.println(user.getId());

                                // SAve name
                                writer.println(user.getFirstName() + " " + user.getLastName());

                                // Save username and password
                                writer.println(user.getUsername());
                                writer.println(user.getPassword());

                                // Save number of friends and friend IDs
                                ArrayList<Integer> friends = user.getFriendsList();
                                writer.println(friends.size());
                                for (int friendId : friends) {
                                        writer.println(friendId);
                                }

                                // Save city
                                writer.println(user.getCity());

                                // Save number of interests and interests
                                LinkedList<String> interests = user.getInterest();
                                writer.println(interests.size());
                                for (String interest : interests) {
                                        writer.println(interest);
                                }

                                writer.println();
                        }
                } catch (IOException e) {
                        System.err.println("Error writing to file.");
                }

        }

        class IdComparator implements Comparator<User> {
                /**
             * Compares the two Users by id
             * uses the Integer compare method to make the comparison
             * 
             * @param user1 the first User
             * @param user2 the second User
             * @return The comparison.
             */
            @Override
            public int compare(User user1, User user2) {
                return Integer.compare(user1.getId(), user2.getId());
            }
        }
}
