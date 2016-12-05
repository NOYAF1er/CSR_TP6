package fr.istic.m1.csr.supermarche.smapi.internals;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale de l'application
 * Elle instancie l'ensemble des sources et des threads
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 */
public class Supermarche {
	
	static final int RAYON_STOCK_INIT = 5;
	static final int RAYON_STOCK_MAX = 10;
	static final int TAILLE_TAPIS = 5;
	static final int NB_CHARIOTS = 3;
	static final int TPS_CR_REAPPROVISIONNEMENT = 500;
	static final int TPS_CR_DEPLACEMENT_ENTRE_RAYON = 200;
	static final int TPS_MARCHE_CLT = 300;
	static final int TPS_PLACEMENT_CLT = 20;
	
	private List<Client> listeClients;
	
	private FileDeChariot fileDeChariot;
	private Caisse caisse;
	private List<Rayon> listeRayons;
	private ChefDeRayon chefDeRayon;
	private EmployeDeCaisse employeDeCaisse;
		
	/**
	 * Constructeur
	 */
	public Supermarche(){
		//Cr�ation du file de chariot
		this.fileDeChariot = new FileDeChariot(NB_CHARIOTS);
		
		//Création de la caisse
		this.caisse = new Caisse();
		
		//Cr�ation de la liste des rayons (contenant les rayons cr��s)
		this.listeRayons = new ArrayList<Rayon>();
		for(Produits produit: Produits.values()){
			Rayon rayon = new Rayon(produit);
			this.listeRayons.add(rayon);
		}
		
		//Cr�ation et mise en activit� du chef de rayon
		this.chefDeRayon = new ChefDeRayon(listeRayons);
		chefDeRayon.start();
		
		//Création et mise en activité de l'employé de caisse
		this.employeDeCaisse = new EmployeDeCaisse(caisse);
		employeDeCaisse.start();
		
		//Cr�ation des clients
		this.listeClients = new ArrayList<Client>();
	}
	
	/**
	 * Crée un nouveau client, l'ajoute à la liste de client et le démarre
	 * @return Client créé
	 */
	public Client addClient(){
		Client client = new Client(listeClients.size() ,getFileDeChariot(), getListeRayons(), getCaisse());
		listeClients.add(client);
    	client.start();
		return client;
	}
	
	/**
	 * @return the listeClients
	 */
	public List<Client> getListeClients() {
		return listeClients;
	}

	/**
	 * @param listeClients the listeClients to set
	 */
	public void setListeClients(List<Client> listeClients) {
		this.listeClients = listeClients;
	}

	/**
	 * @return the fileDeChariot
	 */
	public FileDeChariot getFileDeChariot() {
		return fileDeChariot;
	}

	/**
	 * @param fileDeChariot the fileDeChariot to set
	 */
	public void setFileDeChariot(FileDeChariot fileDeChariot) {
		this.fileDeChariot = fileDeChariot;
	}

	/**
	 * @return the listeRayons
	 */
	public List<Rayon> getListeRayons() {
		return listeRayons;
	}

	/**
	 * @param listeRayons the listeRayons to set
	 */
	public void setListeRayons(List<Rayon> listeRayons) {
		this.listeRayons = listeRayons;
	}

	/**
	 * @return the chefDeRayon
	 */
	public ChefDeRayon getChefDeRayon() {
		return chefDeRayon;
	}

	/**
	 * @param chefDeRayon the chefDeRayon to set
	 */
	public void setChefDeRayon(ChefDeRayon chefDeRayon) {
		this.chefDeRayon = chefDeRayon;
	}

	/**
	 * @return the employeDeCaisse
	 */
	public EmployeDeCaisse getEmployeDeCaisse() {
		return employeDeCaisse;
	}

	/**
	 * @param employeDeCaisse the employeDeCaisse to set
	 */
	public void setEmployeDeCaisse(EmployeDeCaisse employeDeCaisse) {
		this.employeDeCaisse = employeDeCaisse;
	}

	/**
	 * @return the caisse
	 */
	public Caisse getCaisse() {
		return caisse;
	}

}
