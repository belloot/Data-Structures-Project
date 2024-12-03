import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import FileWriter;

import javax.sound.sampled.spi.AudioFileReader;

/**
 * Handles file operations to load and save user data, friends, and interests.
 */
public class FileManager {
        private FullNameComparator fullNameCmp = new FullNameComparator();

        /**
         * Default constructor for FileHandler.
         */
        public FileManager() {

        }

        /**
         * Loads user, friend, and interest data from a file.
         *
         * @param userManager     UserManager to populate user data.
         * @param friendGraph     FriendGraph to populate relationships.
         * @param interestManager InterestManager to populate interests.
         * @param loginTable      LoginHashTable to populate login credentials.
         * @throws FileNotFoundException
         */
        public void loadData(UserManager userManager, FriendGraph friendGraph, InterestManager interestManager,
                        LoginHashTable loginTable, int numCurrentUsers) {
                IdComparator IdCmp = new IdComparator(); // maybe no need it in here
                File file = new File("NFLPlayers.txt"); // Adjust the file path if needed
                if (file.exists()) {
                        Scanner fileReader = new Scanner(file);

                        while (fileReader.hasNextLine()) {
                                numCurrentUsers++;
                                // get id
                                Integer Id = fileReader.nextInt();

                                fileReader.nextLine();

                                // get names

                                String firstName = fileReader.next();

                                String lastName = fileReader.next();

                                fileReader.nextLine();
                                // get Username and Password

                                String userName = fileReader.nextLine();

                                String passWord = fileReader.nextLine();

                                // Populate loginTable and userManager

                                User user = new User(Id, firstName, lastName, userName, passWord);

                                loginTable.addUser(user);

                                userManager.addUser(user);

                                // Populate FriendGraph

                                Integer numFriends = fileReader.nextInt();

                                fileReader.nextLine();

                                for (int i = 0; i < numFriends; i++) {

                                        friendGraph.addFriend(Id, fileReader.nextInt());

                                        fileReader.nextLine();
                                }

                                // Update User City

                                user.updateCity(fileReader.nextLine());

                                // Populate Interest Manager
                                Integer numInterests = fileReader.nextInt();

                                for (int i = 0; i < numInterests; i++) {

                                        // populating Interest Manager
                                        String currentInterestName = fileReader.nextLine();

                                        interestManager.addInterest(currentInterestName);

                                        interestManager.addUserInterest(user, currentInterestName);

                                        // populating LinkedList<Interest> of each user
                                        Interest tempInterest = new Interest(0, currentInterestName);

                                        Interest actualInterest = interestManager.getInterestTable().get(tempInterest);

                                        user.addInterest(actualInterest);
                                }
                        }
                        fileReader.close();

                        // populating friends BST of each user
                        Graph friendNetwork = friendGraph.getFriendGraph();
                        for (int i = 1; i <= numCurrentUsers; i++) {
                                // get adjacency list of friend ids of current user
                                LinkedList<Integer> adj_list = friendNetwork.getAdjacencyList(i);
                                adj_list.positionIterator();
                                for (int j = 0; j < adj_list.getLength(); j++) {
                                        // start populating current user's BST of friends
                                        User friend = userManager.searchUserById(adj_list.getIterator());
                                        userManager.searchUserById(i).getFriendsBST().insert(friend, fullNameCmp);
                                }
                        }
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
         * @throws IOException 
         */
        public void saveData(UserManager userManager, FriendGraph friendGraph, InterestManager interestManager,
                        LoginHashTable loginTable) throws IOException {

                String filePath = "NFLPlayers.txt";

                try (FileWriter deleter = new FileWriter(filePath)) {

                        deleter.write("");

                } catch (IOException e) {

                        System.out.println("An error occurred while clearing the file: " + e.getMessage());

                }
                
                
                FileWriter writer = new FileWriter(filePath);

                int numUsers = userManager.getNumUsers();

                for(int user = 0; user < numUsers; user++){

                        //Save Name

                        String firstName = userManager.getAllUsers().get(user).getFirstName();

                        String lastName = userManager.getAllUsers().get(user).getLastName();

                        String fullName = firstName + " " + lastName;

                        System.out.println(fullName+"\n");

                        writer.write(fullName+"\n");

                        //Save UserName and Password

                        String userName = userManager.getAllUsers().get(user).getUsername();

                        System.out.println(userName + "\n");

                        writer.write(userName+"\n");

                        String passWord = userManager.getAllUsers().get(user).getPassword();

                        System.out.println(passWord + "\n");

                        writer.write(passWord+"\n");

                        //Save friends' data

                        int numFriends = userManager.getAllUsers().get(user).getFriendsBST().getSize();

                        System.out.println(numFriends + "\n");

                        writer.write(numFriends +"\n");

                        ArrayList<User> listOfFriends = new ArrayList();

                        userManager.getAllUsers().get(user).getFriendsBST().inOrderTraversal(listOfFriends);

                        for(int friend = 0; friend < numFriends; friend++){

                                System.out.println(listOfFriends.get(friend) + "\n");

                                writer.write(listOfFriends.get(friend)+"\n");

                        }

                        //Save city data

                        String city = userManager.getAllUsers().get(user).getCity();

                        System.out.println(city + "\n");

                        writer.write(city +"\n");

                        //Save interests data

                        int numInterests = userManager.getAllUsers().get(user).getInterestsList().size();

                        System.out.println(numInterests + "\n");

                        writer.write(numInterests +"\n");

                        ArrayList<Interest> listOfInterests = userManager.getAllUsers().get(user).getInterestsList();

                        for(int interest = 0; interest < numInterests; interest++){
 
                                String inter = listOfInterests.get(interest).getInterest();

                                System.out.println(inter + "\n");

                                writer.write(inter +"\n");

                        }

                }

                writer.close();



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

        class FullNameComparator implements Comparator<User> {
                /**
                 * Compares the two Users by first names
                 * uses the String compareTo method to make the comparison
                 * 
                 * @param user1 the first User
                 * @param user2 the second User
                 * @return The comparison.
                 */
                @Override
                public int compare(User user1, User user2) {
                        String user1FullName = user1.getFirstName() + user1.getLastName();
                        String user2FullName = user2.getFirstName() + user2.getLastName();
                        return user1FullName.compareTo(user2FullName);
                }
        }
}