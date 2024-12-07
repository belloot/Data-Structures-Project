/**
 * Handles user authentication and login credential management.
 */
public class LoginHashTable {
	
	private HashTable<User> loginTable;

    /**
     * Default constructor for LoginHashTable.
     */
    public LoginHashTable() {
    	loginTable = new HashTable<>(200); //Size 10, suitable size? Might need more for when we create new account
    }

    /**
     * Authenticates a user by their username and password.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return True if the credentials are valid, otherwise false.
     */
    public boolean authenticate(String username, String password) {
        User user = findCorrectUser(username, password);
        if(user.getFirstName() == null && user.getLastName() == null) {
        	return false;
        } else {
        	return true;
        }
    }
    
    // finding the correct user
    public User findCorrectUser(String username, String password) {
    	User user = new User(username, password);
    	int bucket = user.hashCode() % loginTable.getNumBuckets();
    	LinkedList<User> listAtBucket = loginTable.getBucket(bucket);
    	for(int i = 0; i < listAtBucket.getLength(); i++) {
    		listAtBucket.advanceIteratorToIndex(i);
    		User currUser = listAtBucket.getIterator();
    		if(currUser.getUsername().equals(username) && currUser.getPassword().equals(password)) {
    			return currUser; // actual correct user
    		}
    	}
    	user = new User();
    	return user; // never ever reached based on how we wrote the program
    }

    /**
     * Adds a user to the login hash table.
     * 
     * @param User, contains name and password
     */
    public void addUser(User user) {
        loginTable.add(user);
    }
    
    public void addUser(String userName, String passWord ) {

        User user = new User(userName, passWord);

    	loginTable.add(user);
    }
    
    public User getUser(String username, String password) {
    	return findCorrectUser(username, password);
    }
    
    
    public String toString() {
    	return loginTable.rowToString();
    }
}

