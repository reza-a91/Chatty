/**
 *
 */

/**
 * @author RPopp
 *
 */
public class GroupDoesNotExist extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2324362655358722615L;
	private IChattyGroup grp;

	/**/
	public GroupDoesNotExist(IChattyGroup grp) {
		this.grp = grp;
	}

	@Override
	public String getMessage() {
		return toString();
	}

	@Override
	public String toString() {
		return "Group with id " + grp.getGroupID() + " does not exist";
	}

}
