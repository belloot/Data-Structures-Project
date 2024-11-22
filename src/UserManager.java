import java.util.ArrayList;

/**
 * Manages all user-related operations, such as adding users, searching, and viewing friends.
 */
public class UserManager {

    /**
     * Default constructor for UserManager.
     */
    public UserManager() {}

    /**
     * Adds a new user to the system.
     *
     * @param user The User object to add.
     */
    public void addUser(User user) {}

    /**
     * Retrieves a user by their username.
     *
     * @param username The username to search for.
     * @return The User object if found, otherwise null.
     */
    public User getUserByUsername(String username) {
        return null;
    }

    /**
     * Displays all friends of the logged-in user in alphabetical order.
     */
    public void viewFriends() {}

    /**
     * Searches for users with the specified name.
     *
     * @param name The name to search for.
     * @return A list of User objects with the specified name.
     */
    public ArrayList<User> searchUsersByName(String name) {
        return null;
    }

    /**
     * Outputs the User's profile as a formatted string.
     *
     * @return A string representation of the User's profile.
     */
    @Override
    public String toString() {
        return null;
    }
}
