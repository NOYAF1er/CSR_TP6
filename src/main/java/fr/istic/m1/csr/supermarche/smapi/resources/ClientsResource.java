package fr.istic.m1.csr.supermarche.smapi.resources;


import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import fr.istic.m1.csr.supermarche.smapi.backend.Backend;
import fr.istic.m1.csr.supermarche.smapi.internals.Client;

/**
 * Resource exposing the users
 *
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 */
public class ClientsResource extends ServerResource
{
    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public ClientsResource()
    {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    @Get("html")
    public Representation getUsersHtml() {
    	return new FileRepresentation("templates/list-clients.html", MediaType.TEXT_HTML);
    }
    
    /**
     * Returns the list of all the clients
     *
     * @return  JSON representation of the users
     * @throws JSONException
     */
    @Get("json")
    public Representation getClients() throws JSONException
    {
        Collection<Client> clients = backend_.getDatabase().getClients();
        Collection<JSONObject> jsonClients = new ArrayList<JSONObject>();

        for (Client client : clients)
        {
            JSONObject current = new JSONObject();

            current.put("id", client.getId());
            current.put("url", getReference().toString() + "/" + client.getId());

            jsonClients.add(current);
        }
        JSONArray jsonArray = new JSONArray(jsonClients);
        JsonRepresentation result = new JsonRepresentation(jsonArray);
        result.setIndenting(true);
        
        return result;
    }

    /**
     * Create a client with the data present in the json representation
     * 
     * @param json representation of the user to create
     * @return JSON representation of the newly created user
     * @throws JSONException
     */
    @Post("json")
    public Representation createClient(JsonRepresentation representation) throws Exception
    {
        JSONObject nbClient = representation.getJsonObject();
        
        // Save the clients
        for(String key: nbClient.keySet()){
            for(int i = 0; i < nbClient.getInt(key); i++){
                backend_.getDatabase().createClient();
            }
        }
     
        return getClients();
    }

}
