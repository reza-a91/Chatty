import java.util.Collection;
import java.util.Vector;

/**
 * Created by Reza on 13.05.2015.
 */
public abstract class ChattyServer implements IChattyServerSubject {
    public ChattyServer() {

    }

    private Collection<IChattyServerObserver> chattyServerObservers = new Vector<IChattyServerObserver>();
    private Collection<IChattyGroup> availableGroups = new Vector<IChattyGroup>();



    public boolean createGroup(String groupID) throws GroupAlreadyExists {
        boolean created= false;


        if (((availableGroups.stream()
                                .filter((available)-> available.getGroupID().equals(available.getGroupID()))
                                .count()) == 0)) {
        ChattyGroup g = new ChattyGroup(groupID);

            availableGroups.add(g);

            created=true;


        } else {
            throw new GroupAlreadyExists("Group already Exists");

        }
        return created;
    }

    public void deleteGroup(IChattyGroup chattyGroup) throws GroupDoesNotExist {
        if  (availableGroups.stream()
                .filter((availableGroup)->availableGroup.equals(chattyGroup)).count()!=0)
        {

            chattyServerObservers.forEach(c -> c.revokeGroup(chattyGroup));
            availableGroups.remove(chattyGroup);

        }else {
            throw new GroupDoesNotExist(chattyGroup);
        }

        }




    public void registerClient(IChattyServerObserver chattyServerObserver) {

        if (chattyServerObservers.stream()
                .filter(o -> o.equals(chattyServerObserver))
                .count() ==0)
        {
            chattyServerObservers.add(chattyServerObserver);

        }

        }



    public void unregisterClient(IChattyServerObserver chattyServerObserver) {

        if (chattyServerObservers.stream()
                .filter(o -> o.equals(chattyServerObserver))
                .count() !=0)
        {
            chattyServerObservers.remove(chattyServerObserver);

        }
    }

    public void main(String args[]) {


    }
}




