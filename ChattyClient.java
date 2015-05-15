import javax.swing.*;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by Reza on 13.05.2015.
 */
public abstract class ChattyClient implements IChattyGroupObserver, IChattyServerObserver, IGuiClient {

    private IChattyServerSubject server;
    private Collection <IChattyGroup> notRegisteredGroups = new Vector<IChattyGroup>();
    private Collection <IChattyGroup> registeredGroups = new Vector<IChattyGroup>();



    public ChattyClient (IChattyServerSubject server, String client)
    {
        this.server =server;

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


    public Collection<IChattyGroup> getNotRegisteredGroups ()
    {
        return notRegisteredGroups;

    }

    public Collection<IChattyGroup> getRegisteredGroups ()
    {
        return registeredGroups;
    }


    public void joinGroup(IChattyGroup group)
    {

        server.

    }

    public void leaveGroup (IChattyGroup group)
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
