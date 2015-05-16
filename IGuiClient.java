import java.util.Collection;

public interface IGuiClient {
	
	/**
	 * @param group
	 */
	public void createGroup(String group);

	/**
	 * @param group
	 */
	public void deleteGroup(IChattyGroup group);

	/**
	 * @return
	 */
	public Collection<IChattyGroup> getNotRegisteredGroups();

	/**
	 * @return
	 */
	public Collection<IChattyGroup> getRegisteredGroups();

	/**
	 * @param group
	 */
	public void joinGroup(IChattyGroup group);

	/**
	 * @param group
	 */
	public void leaveGroup(IChattyGroup group);

	/**
	 * @param msg
	 */
	public void sendMessage(ChattyMessage msg);

	/**
	 * 
	 */
	public void unregister();
}
