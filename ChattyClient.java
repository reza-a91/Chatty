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

    @Override
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

    @Override
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



    @Override
    public Collection<IChattyGroup> getNotRegisteredGroups ()
    {
        return notRegisteredGroups;

    }

    @Override
    public Collection<IChattyGroup> getRegisteredGroups ()
    {
        return registeredGroups;
    }


    @Override
    public void joinGroup(IChattyGroup group)
    {

        group.joinGroup(this);
        registeredGroups.add(group);
        notRegisteredGroups.remove(group);
        myGui.updateGUI();
    }

    @Override
    public void leaveGroup (IChattyGroup group)
    {
        group.leaveGroup(this);
        registeredGroups.remove(group);
        notRegisteredGroups.remove(group);
        myGui.updateGUI();
    }

    @Override
    public void sendMessage (ChattyMessage message) {

    }

    @Override
    public void unregister ()
    {
        server.unregisterClient(this);

        }


     //IGui implementation ends here.


    //IchattyServerObserver implementation starts here.

    @Override
    public void publishGroup (IChattyGroup group) {
            this.notRegisteredGroups.add(group);
            myGui.updateGUI();
        }



   @Override
    public void revokeGroup(IChattyGroup group) {

           this.notRegisteredGroups.remove(group);
           myGui.updateGUI();

   }

    //chattyServerObserver implementation ends here.*/




 //IchattyGroupObserver implementation starts here.

    @Override
    public void deliverMessage(ChattyMessage msg) {

        myGui.deliverMessage(msg);

    }

//IchattyGroupObserver implementation ends here.


}
