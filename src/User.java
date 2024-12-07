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
    private BST<User> friendsByName;
    private LinkedList<Interest> interests; // recommended to create an Interest class
    private int friendCredit = 0;

    /**
     * One argument constructor for User that only
     * takes in the id
     * 
     * @param id the user id
     */
    public User(int id) {
        this.id = id;
        friendsByName = new BST<>();
        interests = new LinkedList<>();
    }
    
    /**
     * Four argument constructor for User 
     * @param id The unique ID of the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param username the username of the user
     */
    public User(int id, String firstName, String lastName, String username, String password, String city) {
    	this.id = id;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.userName = username;
    	this.password = password;
    	this.city = city;
    	friendsByName = new BST<>();
        interests = new LinkedList<>();
    }
    
    /**
     * Seven argument constructor for User
     * @param id The unique ID of the user
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param city
     * @param interestsArray ArrayList storing Interest objects
     */
    public User(int id, String firstName, String lastName, String username, String password, String city, ArrayList<Interest> interestsArray) {
    	this.id = id;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.userName = username;
    	this.password = password;
    	this.city = city;
    	friendsByName = new BST<>();
    	for(int i = 0; i < interestsArray.size(); i++) {
    		addInterest(interestsArray.get(i));
    	}
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
     * Full User constructor
     * 
     * @param id          The unique ID of the user.
     * @param firstName   The first name of the user.
     * @param lastName    The last name of the user.
     * @param username    The username of the user.
     * @param userFriends an arrayList of the user's friends' ids
     *                    (will be inserted into friends BST)
     */
    public User(int id, String firstName, String lastName, String username, String password, ArrayList<Integer> friendsIds,
            String city, ArrayList<Interest> userInterests) {
        FullNameComparator nameCmp = new FullNameComparator();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = username;
        this.password = password;
        
        // start filling in user's interests
        for(int i = 0; i < userInterests.size(); i++) {
        	interests.addLast(userInterests.get(i));
        }
        
        
        // This constructor is still in progress, may not even need it depending on how loadData is written
    }

    public User(Integer id, String firstName, String lastName, String userName, String passWord) {
    	
    	this.id = id;
    	
        this.firstName = firstName;
        
        this.lastName = lastName;
        
        this.userName = userName;
        
        this.password = passWord;
		
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
    
    public BST<User> getFriendsBST() {
    	return this.friendsByName;
    }
    
    /**
     * Retrieves the number of friends this user has
     * @return how many friends the user has
     */
    public int getNumFriends() {
    	return friendsByName.getSize();
    }
    
    // might not need this method 
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
    	if(!interests.contains(interest)) {
    		interests.addLast(interest);
    	}
    }
    
    // updates city
    public void updateCity(String city) {
    	
    	this.city = city;
    }
    
    // get the friend's user credit
    public int getFriendCredit() {
    	return friendCredit;
    }
    
    // increments friend credit for friend recommendation system
    public void incrementCredit(int value) {
    	friendCredit += value;
    }
    
    // reset this user's friend credit back to 0
 	public void resetFriendCredit() {
 		friendCredit = 0;
 	}

    /**
     * Adds a friend to the user's friend list.
     * 
     * @param friend The User object to add as a friend.
     */
    public void addFriend(User friend) {
    	FullNameComparator nameCmp = new FullNameComparator();
        friendsByName.insert(friend, nameCmp);
    }
    
    // remove a friend
    public void removeFriend(int id, UserManager userManager) {
    	FullNameComparator nameCmp = new FullNameComparator();
    	User friendToRemove = userManager.searchUserById(id);
    	friendsByName.removeDuplicate(friendToRemove, nameCmp);
    }

    /**
     * Displays the user's friends in alphabetical order.
     */
    public void viewFriendsAlphabetically() {
    	System.out.println("Viewing friends alphabetically");
    	System.out.println("Format is (ID). (First name) + (Last name)\n");
    	System.out.print(friendsByName.inOrderString());
    }

    /**
     * Compares this user with another user based on their first name.
     * (can potentially be useful to identify users w/ duplicate names)
     * @param other The other User object to compare.
     * @return An integer result of the comparison.
     */
    public boolean hasSameName(User other) {
        if(firstName.equals(other.getFirstName()) && lastName.equals(other.getLastName())) {
        	return true;
        } else {
        	return false;
        }
    }
    
    
    // search FRIENDS by name and returns an ArrayList of friends having that name
    // this is the method where we need the BST search to return an ArrayList
    // assuming that our friends BST doesn't have any duplicate names, do we need 
    // this method to return an ArrayList? There is a similar method in UserManager
    // but I think that is different

    /**
     * Searches through the user's friends list for friends with the specified name.
     * Creates a new ArrayList to store and return the results from searching
     * the friends BST for users with matching first and last names.
     * 
     * @param firstName the first name to search for in friends list
     * @param lastName the last name to search for in friends list
     * @return ArrayList containing all friends with matching first and last names,
     *         returns empty ArrayList if no matches are found
     */
    public ArrayList<User> searchFriendsByName(String firstName, String lastName) {
    	ArrayList<User> friendsWithName = new ArrayList<User>();
        ArrayList<User> searchResults = friendsByName.searchByName(firstName, lastName);
        for(int i = 0; i < searchResults.size(); i++) {
            friendsWithName.add(searchResults.get(i));
        }
    	// need to 
    	return friendsWithName;
    }
    
    /**
     * Creates a String of the current user information in the following format:
     * (ID #). (First name) (Last name)
     */
    @Override
    public String toString() {
    	StringBuilder result = new StringBuilder();
    	result.append("" + id + ". " + firstName + " " + lastName + "\n" +
    			"Interests:\n" + interests.numberedListString());
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
    
    /**
     * Determines whether the given Object is
     * another User, containing
     * the same user id
     * @param obj another Object
     * @return whether there is equality
     */
    @SuppressWarnings("unchecked") //good practice to remove warning here
    @Override public boolean equals(Object obj) {
        if(obj == this) {
        	return true;
        } else if (!(obj instanceof User)) {
        	return false;
        } else {
        	User user = (User) obj;
        	if(user.getId() != this.id) {
        		return false;
        	}
        	return true;
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
    
    // in case we need it
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
}
