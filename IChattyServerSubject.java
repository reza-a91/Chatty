public interface IChattyServerSubject {

	/**
	 * @param groupid
	 * @return
	 * @throws GroupAlreadyExists
	 */
	public boolean createGroup(String groupid) throws GroupAlreadyExists;

	/**
	 * @param group
	 * @throws GroupDoesNotExist
	 */
	public void deleteGroup(IChattyGroup group) throws GroupDoesNotExist;

	/**
	 * @param obs
	 */
	public void registerClient(IChattyServerObserver obs);

	/**
	 * @param obs
	 */
	public void unregisterClient(IChattyServerObserver obs);

}
