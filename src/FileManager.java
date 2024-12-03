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
                        LoginHashTable loginTable, int numCurrentUsers) throws FileNotFoundException {
                IdComparator IdCmp = new IdComparator(); // maybe no need it in here
                File file = new File("NFLPlayers.txt"); // Adjust the file path if needed
                if (file.exists()) {
                        Scanner fileReader = new Scanner(file);

                        while (fileReader.hasNextLine()) {
                        	numCurrentUsers++;
                        	//get id
                        	Integer Id = fileReader.nextInt();
                        	
                        	fileReader.nextLine();
                        	
                        	//get names
                        	
                        	String firstName = fileReader.next();
                        	
                        	String lastName = fileReader.next();
                        	
                        	fileReader.nextLine();
                        	//get Username and Password
                        	
                        	String userName = fileReader.nextLine();
                        	
                        	String passWord = fileReader.nextLine();
                        	
                        	//Populate loginTable and userManager
                        	
                        	User user  = new User(Id, firstName, lastName, userName, passWord);
                        	
              
                        	
                            loginTable.addUser(user);  
                            
                            userManager.addUser(user);
                            
                            //Populate FriendGraph
                            
                            Integer numFriends = fileReader.nextInt();
                            
                            fileReader.nextLine();
                          
                            for (int i = 0; i < numFriends; i++) {
                            	
                            	friendGraph.addFriend(Id, fileReader.nextInt());
                                
                                fileReader.nextLine();
                            }
                                   
                        	//Update User City
                        
                            user.updateCity(fileReader.nextLine());
                            
                            //Populate Interest Manager
                            Integer numInterests = fileReader.nextInt();
                            
                            for(int i = 0; i < numInterests; i++) {
                            	
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
                        for(int i = 1; i <= numCurrentUsers; i++) {
                        	// get adjacency list of friend ids of current user
                        	LinkedList<Integer> adj_list = friendNetwork.getAdjacencyList(i);
                        	adj_list.positionIterator();
                        	for(int j = 0; j < adj_list.getLength(); j++) {
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
