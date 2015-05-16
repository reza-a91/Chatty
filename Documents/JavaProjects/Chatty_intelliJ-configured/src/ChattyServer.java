

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


    @Override
    public boolean createGroup(String groupID) throws GroupAlreadyExists {

        ChattyGroup g = new ChattyGroup(groupID);

         if (availableGroups.stream()
        .filter(a->a.getGroupID().equals(groupID)).count()!= 0)

            throw (new GroupAlreadyExists("Group Already Exists!"));

         else
         {
             availableGroups.add(g);
             chattyServerObservers.forEach(c -> c.publishGroup(g));
         }

        return true;
        }




    @Override
    public void deleteGroup(IChattyGroup chattyGroup) throws GroupDoesNotExist {



        if (!(availableGroups.stream().map(a ->a.getGroupID())
                              .equals(chattyGroup.getGroupID()))
                 )

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

        Process process;
        String input;

        while (true) {
            chattyConsole.printf(newLine + ">");
            switch (input= chattyConsole.readLine("")) {

                case "clear":
                    try {
                       process=  Runtime.getRuntime().exec("cls");
                    }catch(Exception exec) {
                    JOptionPane.showMessageDialog(null, "No Console Objects in the Runtime!");
                }

                   break;

                case "help":
                    chattyConsole.printf("Add Client:                        new" + newLine);
                    chattyConsole.printf("Clear Console:                     clear" + newLine);
                    chattyConsole.printf("Get a list of available commands:  help" + newLine);
                    chattyConsole.printf("Exit Program:                      exit" + newLine);
                    chattyConsole.printf("Credits:                           credits");
                    break;

                case "credits":
                    chattyConsole.printf("Chatty v1.0; Student Version, Completed by Reza Aghideh, Vienna University of Technology.");
                    chattyConsole.printf(newLine);
                    break;


                case "new":
                    input = chattyConsole.readLine("Enter Client Name: ");
                    new ChattyClient(server, input);

                    break;




                case "end":
                    System.exit(0);
                    break;
            }



            }


        }





    }





