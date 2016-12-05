package fr.istic.m1.csr.supermarche.smapi.resources;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import fr.istic.m1.csr.supermarche.smapi.backend.Backend;
import fr.istic.m1.csr.supermarche.smapi.internals.FileDeChariot;
import fr.istic.m1.csr.supermarche.smapi.internals.Rayon;
import fr.istic.m1.csr.supermarche.smapi.internals.Supermarche;

/**
 * Resource exposing supermache rayon's stock
 *
 */
public class StockResource extends ServerResource
{

    /** Backend. */
    private Backend backend_;

    /**
     * Constructor.
     * Call for every single request.
     */
    public StockResource()
    {
        backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    /**
     * Returns the user matching the id given in the URI
     * 
     * @return JSON representation of a user
     * @throws JSONException
     */
    @Get("json")
    public Representation getStock() throws Exception
    {
        JSONObject stockObject = new JSONObject();
        Supermarche supermarche = backend_.getDatabase().getSupermarche();
        FileDeChariot filedeChariot = supermarche.getFileDeChariot();
                
        // Get the Stock
        for(Rayon rayon: supermarche.getListeRayons()){
        	stockObject.put(rayon.getProduit().toString(), rayon.getStockDisponible());
        }
        
        // generate result
        JSONObject resultObject = new JSONObject();
        resultObject.put("StocksRayons", stockObject);
        resultObject.put("StockFileDeChariot", filedeChariot.getStockDisponible());
        JsonRepresentation result = new JsonRepresentation(resultObject);
        result.setIndenting(true);
        
        return result;
    }

}
