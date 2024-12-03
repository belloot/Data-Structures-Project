/**
 * Handles user authentication and login credential management.
 */
public class LoginHashTable {
	
	private HashTable<User> loginTable;

    /**
     * Default constructor for LoginHashTable.
     */
    public LoginHashTable() {
    	loginTable = new HashTable<>(15); //Size 10, suitable size? Might need more for when we create new account
    }

    /**
     * Authenticates a user by their username and password.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return True if the credentials are valid, otherwise false.
     */
    public boolean authenticate(String username, String password) {
        User tempUser = new User(username, password);
        return loginTable.get(tempUser) != null;
    }

    /**
     * Adds a user to the login hash table.
     * 
     * @param User, contains name and password
     */
    public void addUser(String username, String password) {
    	User user = new User(username, password);
        loginTable.add(user);
    }
    
    /**
     * Retrieves full User after authentication
     * 
     * @param username The username to look up
     * @param password The password to verify
     * @return The User object if found and authenticated, null otherwise
     */
    public User getUser(String username, String password) {
        User tempUser = new User(username, password); // Create a temporary User with just username/password
        return loginTable.get(tempUser); // Use tempUser to find the matching full User in the hash table
    }
}
