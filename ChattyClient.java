import javax.swing.*;
import java.util.Collection;

/**
 * Created by Reza on 13.05.2015.
 */
public abstract class ChattyClient implements IChattyGroupObserver, IChattyServerObserver {

    private IChattyServerSubject server;
    private IChattyGroup notRegisteredGroups;



    public ChattyClient (IChattyServerSubject serve, String client)
    {

    }

    public boolean createGroup(String groupName)
    {
       boolean created= false;

        try
        {
            server.createGroup(groupName);
            created=true;
        }
        catch (GroupAlreadyExists groupAlreadyExistsException)
        {
            JOptionPane.showMessageDialog(null,"Group already exists!","Exception", JPanel.ERROR );
        }

        return created;
    }

    public void deleteGroup (IChattyGroup group)
    {
        try {

            server.deleteGroup(group);

            }
        catch (GroupDoesNotExist groupDoesNotExistException)
        {

            JOptionPane.showMessageDialog(null,"Requested Group does not exist!","Exception", JPanel.ERROR );

        }
    }


    Collection<IChattyGroup> getNotRegisteredGroups ()
    {
        return null;
    }

    public void joinGroup(IChattyGroup group)
    {

    }

    void leaveGroup (IChattyGroup group)
    {

    }

    public void publishGroup (IChattyGroup group)
    {

    }

    public void revokeGroup(IChattyGroup group)
    {

    }

    public void sendMessage (ChattyMessage message)
    {

    }

    public void unregister()
    {

    }



}
