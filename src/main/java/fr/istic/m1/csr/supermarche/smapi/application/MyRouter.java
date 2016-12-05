package fr.istic.m1.csr.supermarche.smapi.application;

import java.io.File;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

import fr.istic.m1.csr.supermarche.smapi.resources.ClientResource;
import fr.istic.m1.csr.supermarche.smapi.resources.ClientsResource;
import fr.istic.m1.csr.supermarche.smapi.resources.RootResource;
import fr.istic.m1.csr.supermarche.smapi.resources.StockResource;

/**
 * @author Yannick N'GUESSAN
 * @author Christian ADANE

 */
public class MyRouter  extends Application
{
    public MyRouter(Context context)
    {
        super(context);
    }

    @Override
    public Restlet createInboundRoot()
    {
    	File staticDirectory = new File("static/");
    	Directory directory = new Directory(getContext(), "file:///" + staticDirectory.getAbsolutePath() + "/");
    	directory.isDeeplyAccessible();
    	directory.isListingAllowed();
    	    	
        Router router = new Router(getContext());
        router.attach("/", RootResource.class);
        router.attach("/static", directory);
        router.attach("/supermarche/clients", ClientsResource.class);
        router.attach("/supermarche/stock", StockResource.class);
        router.attach("/supermarche/clients/{id}", ClientResource.class);
        return router;
    }
}
