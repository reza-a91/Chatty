public class GroupAlreadyExists extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7014143202626252176L;

	public GroupAlreadyExists(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return getMessage();
	}

}
