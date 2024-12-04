import java.util.ArrayList;
import java.util.Comparator;

/**
 * Manages all user-related operations, such as adding users, searching, and
 * viewing friends.
 */
public class UserManager {
	private BST<User> usersByName;
	private ArrayList<User> usersIndexedById;
	FullNameComparator fullNameCmp = new FullNameComparator();

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
    	usersByName.insert(user, fullNameCmp);
    	usersIndexedById.add(user);
    }
    
    /**
     * Returns how many users are in the users BST
     * @return the number of registered users
     */
    public int getNumUsers() {
    	return usersByName.getSize();
    }

    public User searchUserById(int id){
         // Need an ArrayList<User> where each index in the ArrayList
		 // corresponds to a user ID. This ArrayList will be used to
	     // retrieve the correct User object. (I've added it at the top).
		 // Will implement this method
        return usersIndexedById.get(id);
    }
    
    // Return an ArrayList of Users ordered by ID
    public ArrayList<User> getAllUsers() {
    	return this.usersIndexedById;
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
    
    // print out all the profiles of all Users in this list
    public void viewProfileOfFriendsInList(ArrayList<User> list) {
    	System.out.println("Viewing these friends' profiles: ");
    	for(int i = 0; i < list.size(); i++) {
    		list.get(i).toString();
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
