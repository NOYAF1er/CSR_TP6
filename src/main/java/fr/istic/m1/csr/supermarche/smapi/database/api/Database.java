package fr.istic.m1.csr.supermarche.smapi.database.api;

import java.util.Collection;

import fr.istic.m1.csr.supermarche.smapi.internals.Client;
import fr.istic.m1.csr.supermarche.smapi.internals.Supermarche;


/**
 *
 * Interface to the database.
 *
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 */
public interface Database
{

	public Client createClient()  throws InterruptedException;
	public Supermarche getSupermarche();
    Collection<Client> getClients();
    Client getClient(int id);

}
