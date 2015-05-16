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
        boolean created= false;


        if (((availableGroups.stream()
                                .filter((available)-> available.getGroupID().equals(available.getGroupID()))
                                .count()) == 0)) {
        ChattyGroup g = new ChattyGroup(groupID);

            availableGroups.add(g);
            chattyServerObservers.forEach(c -> c.publishGroup(g));

            created=true;


        } else {
            throw new GroupAlreadyExists("Group already Exists");

        }
        return created;
    }

    @Override
    public void deleteGroup(IChattyGroup chattyGroup) throws GroupDoesNotExist {
        if  (availableGroups.stream()
                .filter((availableGroup)->availableGroup.equals(chattyGroup)).count()!=0)
        {

            chattyServerObservers.forEach(c -> c.revokeGroup(chattyGroup));
            availableGroups.remove(chattyGroup);
            chattyServerObservers.forEach(c -> c.revokeGroup(chattyGroup));

        }else {
            throw new GroupDoesNotExist(chattyGroup);
        }

        }



    @Override
    public void registerClient(IChattyServerObserver chattyServerObserver) {

        if (chattyServerObservers.stream()
                .filter(o -> o.equals(chattyServerObserver))
                .count() ==0)
        {
            chattyServerObservers.add(chattyServerObserver);

        }

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


    public void main(String args[]) {
        ChattyServer server= new ChattyServer();
        Pva myPVA= new Pva(server);

        //Chatty Command Line will be implemented here.
        Console chattyConsole = System.console();
        String newLine = System.getProperty("line.separator");
        Collection<ChattyClient> clients= new Vector<ChattyClient>();

        while (true) {
            chattyConsole.printf(newLine + ">");
            switch (chattyConsole.readLine()) {
                case "clear":
                    chattyConsole.flush();
                    break;
                case "dir":
                    chattyConsole.printf("Clear Console:                     clear" + newLine);
                    chattyConsole.printf("Get a list of available commands:  dir" + newLine);
                    chattyConsole.printf("Add Client:                        new");
                    break;
                case "new":
                    String clientName = chattyConsole.readLine();
                    clients.add(new ChattyClient(server, clientName));
                    break;
            }


            }


        }





    }





