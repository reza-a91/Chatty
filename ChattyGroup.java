/**
 * Created by Reza on 13.05.2015.
 */
public abstract class ChattyGroup implements IChattyGroup {

    private String group;

    public ChattyGroup(String group) {
        this.group = group;
    }

    String getGroup() {
        return group;
    }

    void joinGroup(IChattyGroupObserver groupObserver) {

    }


    void leaveGroup(IChattyGroupObserver groupObserver) {

    }

    void sendMessage (ChattyMessage message)
    {

    }

    @Override
    public String toString() {
    return null;
    }

}