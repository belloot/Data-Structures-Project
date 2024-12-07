/**
 * Manages user interests and provides search functionality based on interests.
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class InterestManager {
	
	private HashTable<Interest> interestTable; //Stores Interest objects
	private ArrayList<BST<User>> usersByInterest; //Indexed by interest id
	private FullNameComparator fullNameCmp = new FullNameComparator();
	
    /**
     * Default constructor for InterestManager.
     */
    public InterestManager() {
    	interestTable = new HashTable<>(200);
    	usersByInterest = new ArrayList<>();
    }
    
    // get the HashTable of Interest objects
    public HashTable<Interest> getInterestTable() {
		return this.interestTable;
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
    	interest = interest.toLowerCase();
    	Interest tempInterest = new Interest(0, interest); //no need to care about id, use this to search for existing interest in interestTable
    	Interest actualInterest = interestTable.get(tempInterest); //Check if already exists in interestTable
    	if(actualInterest == null) {//interest doesnt exist
    		addInterest(interest); //add interest if doesnt exist
    		actualInterest = interestTable.get(tempInterest); //after adding, get the interest
    	}
    	usersByInterest.get(actualInterest.getId()).insert(user, fullNameCmp); //get the BST that contains users who have this interest id

    	//User tempUser = new User(); //create a comparator that will be used to keep the BST ordering when inserting the user
    	//Comparator<User> userComparator = tempUser.new NameComparator(); // past FullNameComparator inside this file can use it, no need to make new User

        // May have problems


    	//userBST.insert(user, userComparator); //add user to the BST of users with same interest id
    }
    
    /**
     * Searches for users who share a specific interest.
     *
     * @param interest The interest to search for.
     * @return A list of User objects who share the specified interest.
     */
    public ArrayList<User> searchUsersByInterest(String interest) {
    	interest = interest.toLowerCase();
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
      ArrayList<Interest> userInterests = new ArrayList<>();
      System.out.println("Enter your interest. Type 'finish' when finished");
      while(true) {
    	  System.out.print("Enter an interest: ");
    	  String interestName = s.nextLine();
    	  
    	  if(interestName.equalsIgnoreCase("finish")) {
    		  break;
    	  }
    	  interestName = interestName.toLowerCase();
    	  
    	  Interest tempInterest = new Interest(0, interestName); //temp interest to check if exists
    	  Interest realInterest = interestTable.get(tempInterest); //exists then get it
    	  
    	  if(realInterest == null) { //interest doesnt exist
    		  addInterest(interestName); //add new if doesnt exist
    		  realInterest = interestTable.get(tempInterest);
    	  }
    	  
    	  if(!userInterests.contains(realInterest) ) {
    		  userInterests.add(realInterest);
    	  } else {
    		  System.out.println("Interest already added");
    	  }
      }
      return userInterests;
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
}
