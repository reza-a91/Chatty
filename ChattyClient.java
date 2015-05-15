import javax.swing.*;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by Reza on 13.05.2015.
 */
public class ChattyClient implements IChattyServerObserver, IChattyGroupObserver, IGuiClient {





    private IChattyServerSubject server;
    private Collection <IChattyGroup> notRegisteredGroups = new Vector<IChattyGroup>();
    private Collection <IChattyGroup> registeredGroups = new Vector<IChattyGroup>();
    private String name;
    private IGui myGui;




    public ChattyClient (IChattyServerSubject server, String client)
    {

        this.server = server;
        this.name = client;

        server.registerClient(this);


    }
//IGuiClient Implementation starts Here
    public String getName()
    {
        return name;
    }

    public void createGroup(String groupName)
    {



        try
        {
            server.createGroup(groupName);



        }
        catch (GroupAlreadyExists groupAlreadyExistsException)
        {
            JOptionPane.showMessageDialog(null,"Group already exists!","Exception", JPanel.ERROR );
        }


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

        group.joinGroup(this);
        if  ( !notRegisteredGroups.contains(group))
        notRegisteredGroups.add(group);
    }

    public void leaveGroup (IChattyGroup group)
    {
        group.leaveGroup(this);
    }

    public void sendMessage (ChattyMessage message) {

    }

    public void unregister ()
    {
        server.unregisterClient(this);

        }


     //IGui implementation ends here.


    //IchattyServerObserver implementation starts here.

    public void publishGroup (IChattyGroup group) {
        if (!(registeredGroups.contains(group))) {
            this.registeredGroups.add(group);
            this.notRegisteredGroups.remove(group);
        }
    }


   public void revokeGroup(IChattyGroup group) {
       if (registeredGroups.contains(group)) {
           this.registeredGroups.remove(group);
           this.notRegisteredGroups.add(group);
       }
   }

    //chattyServerObserver implementation ends here.*/




 //IchattyGroupObserver implementation starts here.

    public void deliverMessage(ChattyMessage msg) {

        myGui.deliverMessage(msg);

    }

//IchattyGroupObserver implementation ends here.


}
