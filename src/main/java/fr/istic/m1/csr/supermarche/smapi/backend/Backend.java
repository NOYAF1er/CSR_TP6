package fr.istic.m1.csr.supermarche.smapi.backend;

import fr.istic.m1.csr.supermarche.smapi.database.api.Database;
import fr.istic.m1.csr.supermarche.smapi.database.api.impl.InMemoryDatabase;

/**
 *
 * Backend for all resources.
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 */
public class Backend
{
    /** Database.*/
    private Database database_;
    
    public Backend()
    {
        database_ = new InMemoryDatabase();
    }

    public Database getDatabase()
    {
        return database_;
    }


}
