import java.util.Comparator;
import java.util.ArrayList;

/**
 * Represents an individual user with personal details, friends, and interests.
 */
public class User {
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
        FirstNameComparator nameCmp = new FirstNameComparator();
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
        return id;
    }

    /**
     * Retrieves the user's first name.
     *
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Retrieves the user's last name.
     * @return The last name of the user
     */
    public String getLastName() {
    	return lastName;
    }
    
    /**
     * Retrieves the user's city.
     * @return The city of the user
     */
    public String getCity() {
    	return city;
    }

    /**
     * Retrieves the user's username.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return userName;
    }
    
    /**
     * Retrieves the user's password
     * @return The password of the user
     */
    public String getPassword() {
    	return password;
    }
    
    public ArrayList<Interest> getInterestsList() {
    	ArrayList<Interest> interestsList = new ArrayList<Interest>(interests.getLength());
    	interests.positionIterator();
    	for(int i = 0; i < interests.getLength(); i++) {
    		interestsList.add(i, interests.getIterator());
    		interests.advanceIterator();
    	}
    	return interestsList;
    }

    /**
     * Adds a new interest to the user's list of interests.
     *
     * @param interest The interest to add.
     */
    public void addInterest(Interest interest) {
    	interests.addLast(interest);
    }

    /**
     * Adds a friend to the user's friend list.
     * 
     * @param friend The User object to add as a friend.
     */
    public void addFriend(User friend) {
        friends.insert(friend, null);
    }
    
    // remove a friend
    public void removeFriend(int id) {
    	
    }

    /**
     * Displays the user's friends in alphabetical order.
     */
    public void viewFriendsAlphabetically() {
    	System.out.println("Viewing friends alphabetically");
    	System.out.println("Format is (ID). (First name) + (Last name)\n");
    	System.out.print(friends.inOrderString());
    }

    /**
     * Compares this user with another user based on their first name.
     * (can potentially be useful to identify users w/ duplicate names)
     * @param other The other User object to compare.
     * @return An integer result of the comparison.
     */
    public boolean hasSameName(User other) {
        if(firstName.equals(other.getFirstName())) {
        	return true;
        } else {
        	return false;
        }
    }
    
    /**
     * this is supposed to be the same "compare(User user)" method in the TodoList.docx
     * Determines if the current user and another user meet at least
     * one of these conditions
     * 1. They are from the same city
     * 2. They have at least one same friend
     * 3. They have at least one same interest
     * @param other the other user to compare with
     * @return if they could be potential friends
     */
    public boolean canBePotentialFriends(User other){
    	// return false if they are not at the same city
    	if(!city.equals(other.getCity())){
    		return false;
    	}
    	
    	// return false if they don't have at least one same interest
    	boolean oneSameInterest = false;
    	ArrayList<Interest> otherInterestsList = other.getInterestsList();
    	// go through the other user's interests list, they there is an interest the other has
    	// that the current user also has, then they have at least one same interest
    	for(int i = 0; i < otherInterestsList.size(); i++) {
    		if(interests.findIndex(otherInterestsList.get(i)) != -1) {
    			oneSameInterest = true;
    		}
    	}
    	if(oneSameInterest == false) {
    		return false;
    	}
    	
    	/*
    	 * return false if they don't have at least one same friend
    	 * will implement using future friendGraph method (see my Google Doc for details)
    	 */
    	
    	// return true because the conditions haven't been violated
    	return true;
    }
    
    // search friends by name and returns an ArrayList of friends having that name
    public ArrayList<User> searchUsersByName(String firstName, String lastName) {
    	ArrayList<User> 
    }
    
    /**
     * Creates a String of the current user information in the following format:
     * (ID #). (First name) (Last name)
     */
    @Override
    public String toString() {
    	StringBuilder result = new StringBuilder();
    	result.append("" + id + ". " + firstName + " " + lastName + "\n");
    	return result.toString() + "\n";
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
