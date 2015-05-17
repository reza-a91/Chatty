/**
 * Created by Reza on 13.05.2015.
 */

    import java.io.Console;

    import static java.lang.System.out;
public class Pva implements IChattyGroupObserver, IChattyServerObserver {

    Console chattyConsole=System.console();


    public Pva(ChattyServer server)
    {
       server.registerClient(this);
    }


    @Override
    public void deliverMessage (ChattyMessage message)
    {
        System.out.println("PVA| " + message.toString());
    }

    @Override
    public void publishGroup(IChattyGroup group)
    {
        System.out.println("PVA| " + "New Group available: " + group.getGroupID());
    }

    @Override
    public void revokeGroup(IChattyGroup group)
    {
        System.out.println("PVA| " + "Group deleted: " + group.getGroupID());
    }

}
