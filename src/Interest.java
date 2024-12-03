/**
 * Interest class to store interests of users
 */

public class Interest {
	private int id;
	private String interest;
	
	/**
	 * constructor for interest class
	 * creates an Interest object w/ specific id and interest string
	 * @param id the specific id of the interest
	 * @param interest the interest as a string
	 */
	public Interest(int id, String interest) {
		this.id = id;
		this.interest = interest;
	}
	
	public Interest(int id) {
		this.id = id;
	}
	
	/**
	 * Get the id of the interest
	 * @return the id of the Interest object
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the interest string
	 * @return the interest as a string
	 */
	public String getInterest() {
		return interest;
	}
	
	/**
	 * returns a consistent hashCode
	 * for each interest by summing
	 * the Unicode values of the interest String
	 * Key = interest (String)
	 * @return the hash Code
	 */
	@Override
    public int hashCode() {
        String key = interest.toLowerCase();
        int sum = 0;
        for(int i = 0; i < key.length(); i++) {
        	sum += key.charAt(i);
        }
        return sum;
    }
	
	/**
     * Prints out the String of the Interest object
     */
    @Override
    public String toString() {
    	StringBuilder result = new StringBuilder();
    	result.append(interest);
    	return result.toString() + "\n";
    }
	
    /**
     * Compares the if Interests are equal
     * 
     * @param obj
     * @return boolean
     */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof Interest)) {
			return false;
		}
		Interest otherInterest = (Interest) obj;
		return this.interest.equalsIgnoreCase(otherInterest.interest);
	}
	
}