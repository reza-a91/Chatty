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

    @Override
    public String getGroupID() {
        return group;
    }

    @Override
    public void joinGroup(IChattyGroupObserver groupObserver) {

        observers.add(groupObserver);

    }


    @Override
    public void leaveGroup(IChattyGroupObserver groupObserver) {

        observers.remove(groupObserver);
    }

    @Override
    public void sendMessage (ChattyMessage message)
    {
        observers.forEach(observer -> observer.deliverMessage(message)) ;
    }

    @Override
    public String toString() {
    return this.group;
    }

}