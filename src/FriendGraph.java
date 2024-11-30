// Since friends are stored in a BST<User>, we don’t have a simple
// getFriends() method. I retrieved friends through BST<User>
// and convert them as needed (e.g., using getFriendsList()).
// Do we need to add a helper method like getFriendsList()
// in the User class to convert the BST<User> into a LinkedList<User> for easier manipulation

import java.util.ArrayList;

/**
 * Manages the graph of relationships between users.
 */
public class FriendGraph {

    private ArrayList<User> users;  // List to store users
    private Graph friendGraph;

    public FriendGraph() {
        this.users = new ArrayList<>();
    }

    /**
     * Adds a user to the graph.
     *
     * @param user The user to add.
     */
    public void addUser(User user) {
        users.add(user);  // Add the user to the list
    }

    /**
     * Checks if two users are friends.
     *
     * @param user1 The first user.
     * @param user2 The second user.
     * @return True if the users are friends, otherwise false.
     */
    public boolean areFriends(User user1, User user2) {
        return user1.getFriends().contains(user2);
    }

    /**
     * Adds a friendship connection between two users.
     *
     * @param user   The first user.
     * @param friend The friend to add.
     */
    public void addFriend(User user, User friend) {
        user.addFriend(friend); // Using the addFriend method from the User class
    }

    /**
     * Generates friend recommendations for a user.
     *
     * @param user The user for whom recommendations are being generated.
     * @return A list of recommended friends.
     */
    public ArrayList<User> getRecommendations(User user) {
        ArrayList<User> recommendations = new ArrayList<>();

        // Iterate through all the user's friends
        LinkedList<User> friends = user.getFriendsList(); // Assuming you expose a method to get the list of friends
        friends.positionIterator();

        while (!friends.offEnd()) {
            User friend = friends.getIterator();

            // For each friend, get their friends (friends of friends)
            LinkedList<User> friendsOfFriend = friend.getFriendsList();
            friendsOfFriend.positionIterator();

            while (!friendsOfFriend.offEnd()) {
                User friendOfFriend = friendsOfFriend.getIterator();

                // Only recommend users who are not already friends with the user
                if (!user.getFriendsList().contains(friendOfFriend)) {
                    recommendations.add(friendOfFriend);
                }
                friendsOfFriend.advanceIterator();
            }
            friends.advanceIterator();
        }

        return recommendations;
    }
}
