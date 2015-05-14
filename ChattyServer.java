import java.util.Collection;

/**
 * Created by Reza on 13.05.2015.
 */
public class ChattyServer implements IChattyServerSubject {
    ChattyServer()
    {

    }

    private IChattyServerObserver chattyServerObserver;
    private IChattyGroup availableGroups;





    public boolean createGroup(String groupID)
    {

        if ( (availableGroups.getGroupID()!=groupID ) )
        {

        }




    }

    public void deleteGroup(IChattyGroup chattyGroup)
    {
//
    }

    public void registerClient(IChattyServerObserver chattyServerObserver)
    {

    }

    public void unregisterClient (IChattyServerObserver chattyServerObserver )
    {

    }

    public void main (String args[])
    {


    }


}
