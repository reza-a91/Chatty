import java.util.Collection;
import java.util.Vector;

/**
 * Created by Reza on 13.05.2015.
 */
public class ChattyGroup implements IChattyGroup {

    private String group;
    private Collection<IChattyGroupObserver> observers= new Vector<IChattyGroupObserver>();

    public ChattyGroup(String group) {

        this.group = group;
    }

    public String getGroupID() {
        return group;
    }

    public void joinGroup(IChattyGroupObserver groupObserver) {

        observers.add(groupObserver);

    }


    public void leaveGroup(IChattyGroupObserver groupObserver) {

        observers.remove(groupObserver);
    }

    public void sendMessage (ChattyMessage message)
    {
        observers.forEach(observer -> observer.deliverMessage(message)) ;
    }

    @Override
    public String toString() {
    return null;
    }

}