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
    private String newLine = System.getProperty("line.separator");



    public ChattyClient (IChattyServerSubject server, String client)
    {

        this.server = server;
        this.name = client;

        server.registerClient(this);

        try
        {
            myGui = new ChattyGui(this, name);
        }catch(Exception exception)
        {
            JOptionPane.showMessageDialog(null,"Could not show client GUI!");
        }
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
            JOptionPane.showMessageDialog(null, "Group IDs are identical!");
        }
    myGui.updateGUI();

    }

    @Override
    public void deleteGroup (IChattyGroup group)
    {


        try {


            notRegisteredGroups.stream()
                    .filter(n -> n.getGroupID().equals(group.getGroupID()))
                    .forEach(n->n.sendMessage(new
                            ChattyMessage(group, this.name, newLine+
                            "This Group will be deleted. Please unregister it from your local session.")));


            server.deleteGroup(group);

        }catch (GroupDoesNotExist groupDoesNotExistException){


            JOptionPane.showMessageDialog(null,"Requested Group does not exist!");
            notRegisteredGroups.remove(group);
            myGui.updateGUI();


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
        sendMessage(new ChattyMessage(group,this.name,newLine +
               this.name + "Joined the group." + newLine));
        myGui.updateGUI();
    }

    @Override
    public void leaveGroup (IChattyGroup group)
    {
        sendMessage(new ChattyMessage(group,this.name,newLine +
                this.name + "left the group." + newLine));

        group.leaveGroup(this);
        registeredGroups.remove(group);

        notRegisteredGroups.add(group);
        myGui.updateGUI();
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
    public void sendMessage(ChattyMessage message)
    {
        registeredGroups.stream()
                        .filter(r->r.getGroupID().equals(message.getGroup().getGroupID()))
                        .forEach(r -> r.sendMessage(message));
    }

    @Override
    public void deliverMessage(ChattyMessage msg) {

        myGui.deliverMessage(msg);

    }

//IchattyGroupObserver implementation ends here.


}
