/**
 * Manages user interests and provides search functionality based on interests.
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class InterestManager {
	
	private HashTable<Interest> interestTable; //Stores Interest objects
	private ArrayList<BST<User>> usersByInterest; //Indexed by interest id
	
    /**
     * Default constructor for InterestManager.
     */
    public InterestManager() {
    	interestTable = new HashTable<>(10);
    	usersByInterest = new ArrayList<>();
    }
    
    /**
     * Adds a new interest
     * 
     * @param newInterest the new interest
     */
    public void addInterest(String newInterestStr) {
    	Interest newInterest = new Interest(usersByInterest.size(), newInterestStr); //maybe random number for new interest id?
    	if(!interestTable.contains(newInterest)) { //check if a completely new interest
    		interestTable.add(newInterest);
    		usersByInterest.add(new BST<User>()); //add new BST user to store users with this interest
    	}
    }
    
    /**
     * Adds a user to an interest
     * 
     * @param newInterest the new interest
     */
    public void addUserInterest(User user, String interest) {
    	Interest tempInterest = new Interest(0, interest); //no need to care about id, use this to search for existing interest in interestTable
    	Interest actualInterest = interestTable.get(tempInterest); //Check if already exists in interestTable
    	if(actualInterest == null) {//interest doesnt exist
    		addInterest(interest); //add interest if doesnt exist
    		actualInterest = interestTable.get(tempInterest); //after adding, get the interest
    	}
    	BST<User> userBST = usersByInterest.get(actualInterest.getId()); //get the BST that contains users who have this interest



    	User tempUser = new User(); //create a comparator that will be used to keep the BST ordering when inserting the user
    	Comparator<User> userComparator = tempUser.new NameComparator(); 

        // May have problems


    	userBST.insert(user, userComparator); //add user to the BST of users with same interest
    }
    
    /**
     * Searches for users who share a specific interest.
     *
     * @param interest The interest to search for.
     * @return A list of User objects who share the specified interest.
     */
    public ArrayList<User> searchUsersByInterest(String interest) {
    	Interest tempInterest = new Interest(0, interest);
    	Interest actualInterest = interestTable.get(tempInterest);
    	if(actualInterest == null) {
    		return new ArrayList<>(); //No users found
    	}
    	BST<User> userBST = usersByInterest.get(actualInterest.getId());
    	ArrayList<User> usersList = new ArrayList<>();
    	userBST.inOrderTraversal(usersList);
    	return usersList;
    }

    // prompt the user to enter all of their interests
    // handle all the interests:
    // putting all users with same existing interest in the correct Bst
    // if there are new interests, add to the interests arraylist
    // return arraylist of users so that user can be constructed

   public ArrayList<Interest> createInterestArray(Scanner s) {
      return null;
  }
}
