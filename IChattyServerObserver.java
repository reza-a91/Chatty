
public interface IChattyServerObserver {

	/**
	 * @param group
	 */
	public void publishGroup(IChattyGroup group);

	/**
	 * @param group
	 */
	public void revokeGroup(IChattyGroup group);
}
