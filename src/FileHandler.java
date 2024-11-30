import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

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

                                // Load friends
                                int numFriends = Integer.parseInt(fileReader.nextInt());
                                ArrayList<Integer> friends = new ArrayList<>();
                                for (int i = 0; i < numFriends; i++) {
                                        friends.add(Integer.parseInt(fileReader.nextInt()));
                                }

                                // Load city and interests
                                String city = fileReader.nextLine().trim();
                                int numInterests = Integer.parseInt(fileReader.nextInt());
                                ArrayList<String> interests = new ArrayList<>();
                                for (int i = 0; i < numInterests; i++) {
                                        interests.add(fileReader.nextInt());
                                }
                        }

                        fileReader.close();
                } else {
                        System.out.println("File NFLPlayers.txt not found.");
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
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                        ArrayList<User> users = userManager.getAllUsers();

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
}
