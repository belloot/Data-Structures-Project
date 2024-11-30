/**
 * Handles user authentication and login credential management.
 */
public class LoginHashTable {
	
	private HashTable<User> loginTable;

    /**
     * Default constructor for LoginHashTable.
     */
    public LoginHashTable() {
    	loginTable = new HashTable<>(15); //Size 10, suitable size? 
    }

    /**
     * Authenticates a user by their username and password.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return True if the credentials are valid, otherwise false.
     */
    public boolean authenticate(String username, String password) {
        User user = new User(username, password); //need default constructor for user for name and pass
        return loginTable.contains(user); //check if loginTable contains user
    }

    /**
     * Adds a user to the login hash table.
     * 
     * @param User, contains name and password
     */
    //public void addUser(String username, String password)
    public void addUser(String userName, String passWord ) {

        User user = new User(userName, passWord);

    	loginTable.add(user);
    }
    
    /**
     * Retrieves full User after authentication
     * 
     */
    public User getUser(String username, String password) {
    	User user = new User(username, password);
    	return loginTable.get(user);
    }
}