package fr.istic.m1.csr.supermarche.smapi.database.api.impl;

import java.util.Collection;

import fr.istic.m1.csr.supermarche.smapi.database.api.Database;
import fr.istic.m1.csr.supermarche.smapi.internals.Client;
import fr.istic.m1.csr.supermarche.smapi.internals.Supermarche;

/**
 *
 * In-memory database 
 *
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 */
public class InMemoryDatabase implements Database
{
    /** Supermarche */
    private Supermarche supermarche_;
    
    /**
     * Constructeur
     */
    public InMemoryDatabase(){
    	supermarche_ = new Supermarche();
    }

	@Override
	public synchronized Client createClient() throws InterruptedException {
		Client client = supermarche_.addClient();
		return client;
	}
	
	/**
	 * 
	 * @return Le supermarché
	 */
	public synchronized Supermarche getSupermarche(){
		return supermarche_;
	}
    
	/**
	 * @return La liste des clients
	 */
	@Override
	public synchronized Collection<Client> getClients() {
		return supermarche_.getListeClients();
	}
	
	/**
	 * @return Le client dont l'id à été spécifié
	 */
	@Override
	public synchronized Client getClient(int id) {
		return supermarche_.getListeClients().get(id);
	}

}
