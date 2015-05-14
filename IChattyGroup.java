public interface
        IChattyGroup extends IChattyGroupSubject {

	/**
	 * @return
	 */
	public String getGroupID();

	/**
	 * @param msg
	 */
	public void sendMessage(ChattyMessage msg);

}
