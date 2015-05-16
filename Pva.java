/**
 * Created by Reza on 13.05.2015.
 */

    import static java.lang.System.out;
public class Pva implements IChattyGroupObserver, IChattyServerObserver {


    public Pva(ChattyServer server)
    {
       server.registerClient(this);
    }

    public void deliverMessage (ChattyMessage message)
    {
        out.println ("PVA:" + message.toString());
    }

    public void publishGroup(IChattyGroup group)
    {
     out.println("PVA registered in Group: " + group.getGroupID() );
    }

    public void revokeGroup(IChattyGroup group)
    {
        out.println("PVA unregistered from Group: " + group.getGroupID() );
    }

}
