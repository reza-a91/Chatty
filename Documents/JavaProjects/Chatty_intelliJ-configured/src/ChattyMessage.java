public class ChattyMessage {
	private IChattyGroup group;
	private String client;
	private String message;

	public ChattyMessage(IChattyGroup group, String client, String message) {
		this.group = group;
		this.client = client;
		this.message = message;
	}

	public String getClient() {
		return client;
	}

	public IChattyGroup getGroup() {
		return group;
	}

	public String getMessage() {
		return (message);
	}

	@Override
	public String toString() {
		return "Group: " + group + "| Client: " + client + "| Msg: " + message;
	}
}
