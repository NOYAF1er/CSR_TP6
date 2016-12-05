package fr.istic.m1.csr.supermarche.smapi.internals;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Classe Client
 * 
 * Permet � un client de faire ses courses en magasin en lui permettant
 * d'emprunter et restituer un chariot dans une file de chariots
 * d'avoir une liste de courses contenant les produits et la quantit� souhait�
 * 
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 */
public class Client extends Thread {
	
	/** Identifiant du client */
	int id;
	
	/** Liste de courses contenant les produits et leurs quantit�s */
	Map<Produits, Integer> listeDeCourses;
	
	/** Liste de courses restant à effectuer */
	Map<Produits, Integer> listeDeCoursesRestant;
	
	/** File de chariot du supermarch� */
	FileDeChariot fileDeChariot;
	
	/** Liste des rayons du supermarch� */
	List<Rayon> listeRayons;
	
	/** Caisse où se diriger pour le paiement */
	Caisse caisse;
	
	/** Les �tats du client */
	String etat;
	
	/**
	 * Constructeur
	 * D�finit 
	 * un identifiant
	 * une liste de courses, 
	 * un file de chariot,
	 * une liste de rayons
	 * une caisse
	 * 
	 * @param id
	 * @param fileDeChariot
	 * @param listeRayons
	 * @param caisse
	 */
	public Client(int id, FileDeChariot fileDeChariot, List<Rayon> listeRayon, Caisse caisse) {
		this.id = id;
		this.fileDeChariot = fileDeChariot;
		this.listeRayons = listeRayon;
		this.caisse = caisse;
		this.etat = "INITIALISATION";
		
		this.listeDeCourses = new HashMap<Produits, Integer>();
		this.setListeDeCourses();
		
		this.listeDeCoursesRestant = new HashMap<Produits, Integer>();
		this.listeDeCoursesRestant.putAll(listeDeCourses);
	}	
	/**
	 * Constructeur
	 * D�finit 
	 * une liste de courses, 
	 * un file de chariot,
	 * une liste de rayons
	 * une caisse
	 * 
	 * @param fileDeChariot
	 * @param listeRayons
	 * @param caisse
	 */
	public Client(FileDeChariot fileDeChariot, List<Rayon> listeRayon, Caisse caisse) {
		this.fileDeChariot = fileDeChariot;
		this.listeRayons = listeRayon;
		this.caisse = caisse;
		this.etat = "INITIALISATION";
		
		this.listeDeCourses = new HashMap<Produits, Integer>();
		this.setListeDeCourses();
		
		this.listeDeCoursesRestant = new HashMap<Produits, Integer>();
		this.listeDeCoursesRestant.putAll(listeDeCourses);
	}
	
	/**
	 * D�roulement du thread
	 * Emprunte un chariot
	 * Parcours tous les rayons pour recup�rer les produits de sa liste de courses
	 * Restitue le chariot
	 */
	public void run() {
		try {	
			//Emprunt d'un chariot
			this.emprunterChariot();
			
			//Parcours des rayons
			etat = "EN_COURSE";
			for(Rayon rayon: listeRayons){
				etat = "ATTENTE_PRODUIT";
				prendreProduits(rayon);
				Thread.sleep(Supermarche.TPS_MARCHE_CLT); // Simule le temps de marche entre les rayons
			}
			
			//Passage en caisse
			passerAlaCaisse();
			
			//Restitution du chariot
			this.restituerChariot();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Définition de l'identifiant du client
	 * @param id
	 */
	public void setUserId(int id){
		this.id = id;
	}
	
	/**
	 * 
	 * @return L'identifiant du client
	 */
	public int getUserId(){
		return this.id;
	}
	
	/**
	 * 
	 * @return L'etat du client
	 */
	public String getEtat(){
		return this.etat;
	}

	/**
	 * D�finit une liste de courses de fa�on al�atoire
	 */
	public void setListeDeCourses(){
		Random rd = new Random();
		
		this.listeDeCourses.put(Produits.SUCRE, rd.nextInt(10));
		this.listeDeCourses.put(Produits.FARINE, rd.nextInt(10));
		this.listeDeCourses.put(Produits.BEURRE, rd.nextInt(10));
		this.listeDeCourses.put(Produits.LAIT, rd.nextInt(10));
	}
	
	/**
	 * D�finit une liste de courses � partir de celle indiqu�
	 * @param listeDeCourses Liste de courses
	 */
	public void setListeDeCourses(Map<Produits, Integer> listeDeCourses){
		this.listeDeCourses = listeDeCourses;
	}
	
	/**
	 * Retourne l'ensemble des produits et les quantités respectives
	 * @return
	 */
	public Map<Produits, Integer> getListeDeCourses(){
		return listeDeCourses;
	}
	
	/**
	 * Retourne l'ensemble des courses restant à effectuer
	 * @return
	 */
	public Map<Produits, Integer> getListeDeCoursesRestant(){
		return listeDeCoursesRestant;
	}
	/**
	 * Emprunte un chariot dans la file de chariot
	 * 
	 * @throws InterruptedException
	 */
	public void emprunterChariot() throws InterruptedException {
		etat = "ATTENTE_CHARIOT";
		fileDeChariot.deStocker();
	}

	/**
	 * Restitue un chariot dans la file de chariot
	 * 
	 * @throws InterruptedException
	 */
	public void restituerChariot() throws InterruptedException {
		fileDeChariot.stocker();
	}
	
	/**
	 * Retire dans un rayon donn�, la quantit� de produit figurant sur 
	 * la liste de courses
	 * 
	 * @param rayon Rayon sur lequel sera retir� le produit
	 * @throws InterruptedException
	 */
	public void prendreProduits(Rayon rayon) throws InterruptedException{
		Produits produitRayon = rayon.getProduit();
		int qteProduit = listeDeCourses.get(produitRayon);
		
		while(qteProduit > 0){
			rayon.deStocker();
			qteProduit--;
			this.listeDeCoursesRestant.put(produitRayon, qteProduit);
		}	
	}
	
	/**
	 * Poser ses produits les uns après les autres sur le tapis de la caisse (dans les limites du tapis)
	 * après avoir vérifié que la caisse soit libre
	 * puis liberer la caisse
	 * @throws InterruptedException
	 */
	public void passerAlaCaisse() throws InterruptedException{
		etat = "ATTENTE_CAISSE";
		caisse.clientSuivant(); //Vérifie que la caisse est libre
		etat = "A_LA_CAISSE";
		for(Produits produit: listeDeCourses.keySet()){
			for(int i=0, qtProduit = listeDeCourses.get(produit); i < qtProduit; i++){
				caisse.charger(produit);
			}
		}
		caisse.reglementClient();
		etat = "TERMINE";
	}

}
