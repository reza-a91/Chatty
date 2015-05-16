public interface IChattyGroupSubject {

	/**
	 * @param obs
	 */
	public void joinGroup(IChattyGroupObserver obs);

	/**
	 * @param obs
	 */
	public void leaveGroup(IChattyGroupObserver obs);

}
