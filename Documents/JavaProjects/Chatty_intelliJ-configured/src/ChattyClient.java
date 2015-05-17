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
            JOptionPane.showMessageDialog(null, "Group ID already in use!");
        }
    myGui.updateGUI();

    }

    @Override
    public void deleteGroup (IChattyGroup group)
    {


        try {




            server.deleteGroup(group);

        }catch (GroupDoesNotExist groupDoesNotExistException){


            JOptionPane.showMessageDialog(null,"Requested Group does not exist!");

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
        sendMessage(new ChattyMessage(group,this.name, newLine +
               this.name + " joined the group." + newLine));
        myGui.updateGUI();
    }

    @Override
    public void leaveGroup (IChattyGroup group)
    {
        sendMessage(new ChattyMessage(group,this.name, newLine +
                this.name + " left the group." + newLine));

        group.leaveGroup(this);
        registeredGroups.remove(group);

        if (notRegisteredGroups.stream()
            .filter(a -> a.getGroupID().equals(group.getGroupID())).count()== 0)
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

        if((this.notRegisteredGroups.stream()
                .filter(r->r.getGroupID().equals(group.getGroupID())).count()==0)

               &&  (this.registeredGroups.stream()
                .filter(r->r.getGroupID().equals(group.getGroupID())).count()==0))

        {

            System.out.println("Client " + this.name +" Has has been informed of the new group.");
            this.notRegisteredGroups.add(group);


        }
        myGui.updateGUI();

    }



   @Override
    public void revokeGroup(IChattyGroup group) {



       if (registeredGroups.stream()
               .filter(a->a.getGroupID().equals(group.getGroupID())).count() != 0) {

           this.deliverMessage(new ChattyMessage(group, this.name," DELETION INQUIRY!"));

           try {

           System.out.println("restoration request by one or more clients.");
               server.createGroup(group.getGroupID());
           }
       catch (GroupAlreadyExists groupAlreadyExists)
           {

           }
       }
       else
       {
           this.notRegisteredGroups.remove(group);
           System.out.println("Client " + this.name + " attempted to delete the group " +group.getGroupID() +".");
       }


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
