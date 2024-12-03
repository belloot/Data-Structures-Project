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

    public Graph friendGraph;

    public FriendGraph(UserManager userManager, Integer numCurrentUsers) {
        friendGraph = new Graph(numCurrentUsers + 15);
    }
    
    
    // return the network of friends
 	public Graph getFriendGraph() {
 		return this.friendGraph;
 	}
 	
    /**
     * Adds a friendship connection between two users.
     *
     * @param user   The first user.
     * @param friend The friend to add.
     */
    public void addFriend(Integer id, Integer friendsId) {
        friendGraph.addUndirectedEdge(id, friendsId);
    }
    
    /**
     * Checks if two users are friends.
     *
     * @param user1 The first user.
     * @param user2 The second user.
     * @return True if the users are friends, otherwise false.
     */
    public boolean areFriends(User user1, User user2) {
        return !(friendGraph.getAdjacencyList(user1.getId()).findIndex(user2.getId()) == -1);
    }

    /**
     * Generates friend recommendations for a user.
     *
     * @param user The user for whom recommendations are being generated.
     * @return A list of recommended friends.
     */
    public ArrayList<User> getRecommendations(User user, UserManager userManager) {
    	// take into consideration the distance between the recommendation and the user
    	// take into consideration if the recommendation and the user has one or more common interests
        ArrayList<User> recommendations = new ArrayList<>();
        LinkedList<Integer> friends_adj_list = friendGraph.getAdjacencyList(user.getId());
        
        
        return recommendations;
        /*
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
        */
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
    // This method should be written in FriendGraph
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
}