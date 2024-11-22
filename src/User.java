import java.util.LinkedList;

/**
 * Represents an individual user with personal details, friends, and interests.
 */
public class User implements Comparable<User> {

    /**
     * Default constructor for User.
     *
     * @param id The unique ID of the user.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param username The username of the user.
     */
    public User(int id, String firstName, String lastName, String username) {}

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
        return null;
    }

    /**
     * Adds a new interest to the user's list of interests.
     *
     * @param interest The interest to add.
     */
    public void addInterest(String interest) {}

    /**
     * Adds a friend to the user's friend list.
     *
     * @param friend The User object to add as a friend.
     */
    public void addFriend(User friend) {}

    /**
     * Displays the user's friends in alphabetical order.
     */
    public void viewFriends() {}

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
}
