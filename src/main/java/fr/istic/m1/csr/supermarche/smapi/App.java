package fr.istic.m1.csr.supermarche.smapi;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;

import fr.istic.m1.csr.supermarche.smapi.application.MyRouter;
import fr.istic.m1.csr.supermarche.smapi.backend.Backend;

/**
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 */
public class App 
{
	/** Hide constructor. */
    private App()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Main method.
     *
     * @param args  The arguments of the command line
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        // Create a component
        Component component = new Component();
        Context context = component.getContext().createChildContext();
        component.getServers().add(Protocol.HTTP, 5000);
        component.getClients().add(Protocol.FILE);

        // Create an application
        Application application = new MyRouter(context);

        // Add the backend into component's context
        Backend backend = new Backend();
        context.getAttributes().put("backend", backend);
        component.getDefaultHost().attach(application);
       
        // Start the component
        component.start();
    }
}
