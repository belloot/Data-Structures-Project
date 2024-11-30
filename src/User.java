import java.util.Comparator;
import java.util.ArrayList;

/**
 * Represents an individual user with personal details, friends, and interests.
 */
public class User implements Comparable<User> {
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String city;
    private BST<User> friends;
    private LinkedList<Interest> interests; // recommended to create an Interest class
    // test commit

    /**
     * One argument constructor for User that only
     * takes in the id
     * 
     * @param id the user id
     */
    public User(int id) {
        this.id = id;
    }
    
    /////Kai's edit
    /**
     * Constructor to be used to create temp user for login authentication
     * 2 arguments constructor for User that
     * takes in the username, password
     * 
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    
    /////Kai's edit
    /**
     * default constructor for User
     * 
     * @param id the user id
     */
    public User() {
    	this.firstName = "tempUser";
    	this.lastName = "tempUser"; 	
    }

    /**
     * Constructor for User
     * 
     * @param id          The unique ID of the user.
     * @param firstName   The first name of the user.
     * @param lastName    The last name of the user.
     * @param username    The username of the user.
     * @param userFriends an arrayList of the user's friends' ids
     *                    (will be inserted into friends BST)
     */
    public User(int id, String firstName, String lastName, String username, ArrayList<Integer> userFriends,
            String city, ArrayList<String> userInterests) {
        NameComparator nameCmp = new NameComparator();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = username;
        // add friends using one argument User constructor
        for (int i = 0; i < userFriends.size(); i++) {
            friends.insert(new User(userFriends.get(i)), nameCmp);
        }
    }

    /**
     * Retrieves the user's ID.
     *
     * @return The unique ID of the user.
     */
    public int getId() {
        return 0;
    }

    /**
     * Retrieves the user's first name.
     *
     * @return The first name of the user.
     */
    public String getFirstName() {
        return null;
    }

    /**
     * Retrieves the user's username.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return userName; //Kais edit
    }
    
    /**
     * Retrieves the user's password.
     *
     * @return The password of the user.
     */
    public String getPassword() {
    	return password; //Kais edit
    }

    /**
     * Adds a new interest to the user's list of interests.
     *
     * @param interest The interest to add.
     */
    public void addInterest(String interest) {
    }

    /**
     * Adds a friend to the user's friend list.
     * 
     * @param friend The User object to add as a friend.
     */
    public void addFriend(User friend) {
        friends.insert(friend, null);
    }

    /**
     * Displays the user's friends in alphabetical order.
     */
    public void viewFriendsAlphabetically() {
    }

    /**
     * Compares this user with another user based on their first name.
     *
     * @param other The other User object to compare.
     * @return An integer result of the comparison.
     */
    @Override
    public int compareTo(User other) {
        return 0;
    }

    public ArrayList<User> searchUsersByName(String firstName, String lastName) {

        return null;
    }

    /**
     * returns a consistent hashCode
     * for each interest by summing
     * the Unicode values of the interest String
     * Key = interest (String)
     * 
     * @return the hash Code
     */
    @Override
    public int hashCode() {
        String key = userName + password;
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum += key.charAt(i);
        }
        return sum;
    }
    
    /////Kai's edit
    /**
     * Compares the if Users are equal
     * by name or password
     * 
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User otherUser = (User) obj;
        return this.userName.equals(otherUser.userName) && this.password.equals(otherUser.password);
    }

    public void removeFriend(int id) {

    }

    class NameComparator implements Comparator<User> {
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
