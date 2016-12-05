package fr.istic.m1.csr.supermarche.smapi.resources;

import org.restlet.data.MediaType;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 */
public class RootResource extends ServerResource {

	@Get
	public Representation getRoot() {
		return new FileRepresentation("templates/index.html", MediaType.TEXT_HTML); 
	}
	
}
