

import javax.swing.*;
import java.io.Console;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by Reza on 13.05.2015.
 */
public class ChattyServer implements IChattyServerSubject {
    public ChattyServer() {

    }

    private Collection<IChattyServerObserver> chattyServerObservers = new Vector<IChattyServerObserver>();
    private Collection<IChattyGroup> availableGroups = new Vector<IChattyGroup>();
    private Collection<ChattyGroup> groupsHistory = new Vector<ChattyGroup>();
    private String newLine = System.getProperty("line.separator");

    @Override
    public boolean createGroup(String groupID) throws GroupAlreadyExists {

        ChattyGroup g;


            if(this.availableGroups.stream()
                    .filter(r -> r.getGroupID().equals(groupID)).count()!=0)
             throw (new GroupAlreadyExists("Group Already Exists!"));

         else
         {
             if (groupsHistory.stream()
                     .filter(a->a.getGroupID().equals(groupID)).count()== 0){

                 g= new ChattyGroup(groupID);
                 groupsHistory.add(g);
                 availableGroups.add(g);

             }

             else{
             System.out.println(newLine + "Server| " + groupsHistory.size()+" Element(s) in Group History.");
                 g=groupsHistory.stream()
                         .filter(gR->gR.getGroupID().equals(groupID))
                         .iterator().next();
                 availableGroups.add(g);
             System.out.println(newLine+ "Server| Group " + g.getGroupID()+" was restored.");}

             chattyServerObservers.forEach(c -> c.publishGroup(g));
         }

        return true;
        }




    @Override
    public void deleteGroup(IChattyGroup chattyGroup) throws GroupDoesNotExist {




        if ((availableGroups.stream().filter(a->a.getGroupID().equals(chattyGroup.getGroupID())).count())!=1)

           throw (new GroupDoesNotExist(chattyGroup));

        else
        {
            availableGroups.remove(chattyGroup);

            chattyServerObservers.forEach(c -> c.revokeGroup(chattyGroup));
        }


    }



    @Override
    public void registerClient(IChattyServerObserver chattyServerObserver) {

        //if (chattyServerObservers.stream()
          //      .filter(o -> o.equals(chattyServerObserver))
           //     .count() ==0)
       // {
            chattyServerObservers.add(chattyServerObserver);

        //}

        }


    @Override
    public void unregisterClient(IChattyServerObserver chattyServerObserver) {

        if (chattyServerObservers.stream()
                .filter(o -> o.equals(chattyServerObserver))
                .count() !=0)
        {
            chattyServerObservers.remove(chattyServerObserver);

        }
    }


    public static void main(String args[]) {

        ChattyServer server= new ChattyServer();
        Pva myPVA= new Pva(server);

        //Chatty Command Line will be implemented here.
        Console chattyConsole = System.console();

        String newLine = System.getProperty("line.separator");


        String input;
        chattyConsole.printf(newLine + "***Chatty Student Control Panel.***" + newLine +
                                        newLine + "enter help for available commands:");

        while (true) {
            chattyConsole.printf(newLine + ">");
            switch (input= chattyConsole.readLine("")) {


                case "help":
                    chattyConsole.printf(newLine  +
                                         "Add new Client:                    new client" + newLine);
                    chattyConsole.printf("Get a list of available commands:  help" + newLine);
                    chattyConsole.printf("Credits:                           credits" +newLine);
                    chattyConsole.printf("Exit Program:                      exit" + newLine);

                    break;

                case "credits":
                    chattyConsole.printf("Chatty v1.0; Student Version," + newLine+
                            "Completed by Reza Aghideh, Vienna University of Technology.");
                    chattyConsole.printf(newLine);
                    break;


                case "new client":
                    input = chattyConsole.readLine("Enter Client Name: ");
                    new ChattyClient(server, input);

                    break;


                case "exit":
                    System.exit(0);
                    break;
            }



            }


        }





    }





