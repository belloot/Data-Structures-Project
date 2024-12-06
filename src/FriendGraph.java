// Since friends are stored in a BST<User>, we don’t have a simple
// getFriends() method. I retrieved friends through BST<User>
// and convert them as needed (e.g., using getFriendsList()).
// Do we need to add a helper method like getFriendsList()
// in the User class to convert the BST<User> into a LinkedList<User> for easier manipulation

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Manages the graph of relationships between users.
 */
public class FriendGraph {

    public Graph friendGraph;
    private CreditComparator creditCmp = new CreditComparator();
    
    public FriendGraph() {
    	
    }
    
    public FriendGraph(UserManager userManager, Integer numCurrentUsers) {
        friendGraph = new Graph(numCurrentUsers * 2);
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
    public ArrayList<User> getRecommendations(User user, UserManager userManager, InterestManager interestManager) {
    	// take into consideration the distance between the recommendation and the user
    	// take into consideration if the recommendation and the user has one or more common interests
        
        ArrayList<User> potentialFriendsList = new ArrayList<>(); // used to store potential friends (unranked)
        BST<User> potentialFriendsBST = new BST<>(); // used to order the potential friends based on credit system
        ArrayList<User> recommendations = new ArrayList<>(); // used to store actual recommendations (ranked)
        
        friendGraph.BFS(user.getId()); // call BFS on current user to find relationships
        
        // this for loop is to get all potential friends (friends of friends, etc.)
        for(int id = 1; id < userManager.getNumUsers(); id++) {
        	int distanceOfCurrentUser = friendGraph.getDistance(id);
        	if(distanceOfCurrentUser == 0 || distanceOfCurrentUser == -1 || distanceOfCurrentUser == 1) {
        		continue;
        	} else {
        		potentialFriendsList.add(userManager.searchUserById(id));
        	}
        }
        
        // updating friend recommendation credit for interests
        ArrayList<Interest> loggedInUserInterestList = user.getInterestsList();
        for(int i = 0; i < loggedInUserInterestList.size(); i++) { // track logged in user's interests
        	Interest loggedInUserCurrentInterest = loggedInUserInterestList.get(i);
        	
        	for(int j = 0; j < potentialFriendsList.size(); j++) { // track potential friend's interests
        		ArrayList<Interest> potentialFriendInterestList = potentialFriendsList.get(j).getInterestsList();
        		// see if logged in user current interest exists inside potential friend's interest list
        		if(potentialFriendInterestList.indexOf(loggedInUserCurrentInterest) != -1) {
        			potentialFriendsList.get(j).incrementCredit(1);
        		}	
        	}
        }
        
        // updating friend recommendation credit for friend connections
        for(int i = 0; i < potentialFriendsList.size(); i++) {
        	User currentPotentialFriend = potentialFriendsList.get(i);
        	int currentPotentialFriendId = currentPotentialFriend.getId();
        	int distanceOfPotentialFriend = friendGraph.getDistance(currentPotentialFriendId);
        	if(distanceOfPotentialFriend == 2) {
        		currentPotentialFriend.incrementCredit(3);
        	} else if (distanceOfPotentialFriend > 2 && distanceOfPotentialFriend <= 4) {
        		currentPotentialFriend.incrementCredit(2);
        	} else {
        		currentPotentialFriend.incrementCredit(1);
        	}
        }
        
        // insert all potential friends into BST to get them sorted
        for(int i = 0; i < potentialFriendsList.size(); i++) {
        	potentialFriendsBST.insert(potentialFriendsList.get(i), creditCmp);
        }
        
        // collect the sorted potential friends from BST into actual recommendations list
        potentialFriendsBST.reversedInOrderTraversal(recommendations);
        
        // reset every potential friend's credit value
        for(int i = 0; i < potentialFriendsList.size(); i++) {
        	potentialFriendsList.get(i).resetFriendCredit();
        }
        
        return recommendations;
    }
    
    class CreditComparator implements Comparator<User> {
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
        	return Integer.compare(user1.getFriendCredit(), user2.getFriendCredit());
        }
    }
    
}
