import java.util.ArrayList;
import java.util.Comparator;

/**
 * Manages all user-related operations, such as adding users, searching, and
 * viewing friends.
 */
public class UserManager {
	private BST<User> usersByName;
	private ArrayList<User> usersIndexedById;
	FirstNameComparator nameCmp = new FirstNameComparator();

    /**
     * Default constructor for UserManager.
     */
    public UserManager() {
    	usersByName = new BST<User>();
    	usersIndexedById.add(0, null); // make index 0 null because we don't need it
    }

    /**
     * Adds a new user to the system.
     *
     * @param user The User object to add.
     */
    public void addUser(User user) {
    	usersByName.insert(user, nameCmp);
    	usersIndexedById.add(user);
    }
    
    /**
     * Returns how many users are in the users BST
     * @return the number of registered users
     */
    public int getNumUsers() {
    	return usersByName.getSize();
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username The username to search for.
     * @return The User object if found, otherwise null.
     */
    public User getUserByUsername(String username, String password) {
        return null; // I think this method will need to use LoginTable to actually retrieve the User object. 
        			 // LoginTable should have a method to retrieve a User object given username and password.
        			 // Also this method probably needs another argument, String password, to make this happen.
        			 // Will need to change main to accommodate for this
        User userWanted = LoginTable.getUser(username, password); // example
        usersByName.search(userWanted, nameCmp);
        
    }

    /**
     * Searches for users with the specified name.
     *
     * @param name The name to search for.
     * @return A list of User objects with the specified name.
     */
    public ArrayList<User> searchUsersByName(String firstName, String lastName) {
        return null; // Similar to the method above, I probably need LoginTable
        			 // Also I need to add a BST method to be able to search 
                     // for and return an ArrayList of Users
    }

    public ArrayList<User> searchUsersByInterests(String interest){
        return null; // Need an ArrayList<User> where each index in the ArrayList
                     // corresponds to an Interest ID. Inside this index location will have
        			 // a BST of users who share this specific Interest ID. 
        			 // This ArrayList will be used to retrieve the correct User object
        		     // Maybe implement this in InterestManager 
    }

    public User searchUserById(int id){
         // Need an ArrayList<User> where each index in the ArrayList
		 // corresponds to a user ID. This ArrayList will be used to
	     // retrieve the correct User object. (I've added it at the top).
		 // Will implement this method
        return usersIndexedById.get(id);
    }

    /**
     * Outputs the User's profile as a formatted string.
     * I don't think we need this method as User.java has a toString() that does this (Khiem 11/29)
     * @return A string representation of the User's profile.
     */
    @Override
    public String toString() { 
        return null;
    }

    public void viewProfileOfFriendsInList(ArrayList<User> list) {
    	
    }
    
    class FirstNameComparator implements Comparator<User> {
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
            return user1.getFirstName().compareTo(user2.getFirstName());
        }
    }
}
