package fr.istic.m1.csr.supermarche.smapi.resources;


import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import fr.istic.m1.csr.supermarche.smapi.backend.Backend;
import fr.istic.m1.csr.supermarche.smapi.internals.Client;
import fr.istic.m1.csr.supermarche.smapi.internals.Produits;

/**
 * Resource exposing the users
 *
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 */
public class ClientResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /** Client handled by this resource. */
    private Client client_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public ClientResource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    @Get("html")
    public Representation getUsersHtml() {
    	return new FileRepresentation("templates/index.html", MediaType.TEXT_HTML);
    }
    
    /**
     * Returns the client matching the id given in the URI
     *
     * @return  JSON representation of the users
     * @throws JSONException
     */
    @Get("json")
    public Representation getClient() throws JSONException
    {
        String clientIdString = (String) getRequest().getAttributes().get("id");
        int clientId = Integer.valueOf(clientIdString);
        
    	try{
            client_ = backend_.getDatabase().getClient(clientId);
    	}
    	catch(Exception ex){
    		setStatus(new Status(Status.CLIENT_ERROR_NOT_FOUND , "Le client n'existe pas."));
    		return null;
    	}
    	
        JSONObject clientObject = new JSONObject();
        JSONObject listeCoursesRestantes = new JSONObject();
        
        for(Produits produit: client_.getListeDeCoursesRestant().keySet()){
        	listeCoursesRestantes.put(produit.toString(), client_.getListeDeCoursesRestant().get(produit));
        }
                
        clientObject.put("id", client_.getId());
        clientObject.put("liste", listeCoursesRestantes);
        clientObject.put("etat", client_.getEtat());
        
        JsonRepresentation result = new JsonRepresentation(clientObject);
        result.setIndenting(true);
        
        return result;
    }

}
