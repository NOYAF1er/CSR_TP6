package fr.istic.m1.csr.supermarche.smapi.internals;
import java.util.List;

/**
 * Classe Chef de rayon
 * 
 * Permet au chef de rayon de faire sa ronde en approvisionnant les rayons
 * et en se r�approvisionnant � l'entrepot
 * 
 * @author Yannick N'GUESSAN
 * @author Christian ADANE
 *
 */
public class ChefDeRayon extends Thread  {
	
	/** Liste des rayons du supermarch� */
	List<Rayon> listeRayons;
	
	/** Nombre maximal d'exemplaire de produit possible de transporter */
	private final int QTE_MAX_EXEMPLAIRE_TRANSPORTABLE = 5;
	
	/**
	 * Constructeur
	 * 
	 * D�finit ce thread comme �tant un deamon thread afin de ne l'arreter que lorsque 
	 * tous les thread autre que les deamons threads sont arr�t�s
	 * 
	 * Aussi d�finit
	 * la liste de rayon � approvisionner,
	 * 
	 * @param listeRayons
	 */
	public ChefDeRayon(List<Rayon> listeRayons){
		this.setDaemon(true);
		this.listeRayons = listeRayons;
	}
	
	/**
	 * D�roulement du thread
	 * Faire la ronde des rayons
	 */
	public void run(){
		try {
			this.parcourir();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Approvisionne le rayon d�finie, si n�cessaire, dans les limites des exemplaires 
	 * en possession du chef de rayons et de la capacit� du rayon
	 * 
	 * @throws InterruptedException
	 */
	public void approvisionner(Rayon rayon) throws InterruptedException {
		Thread.sleep(Supermarche.TPS_CR_DEPLACEMENT_ENTRE_RAYON); // Simule le temps de marche entre les rayons et entre l'entrepot et le 1er rayon
		
		int stockDispoRayon = rayon.getStockDisponible();
		int besoin = rayon.getCapacite() - stockDispoRayon;
		
		if(besoin > 0){
			if(besoin > QTE_MAX_EXEMPLAIRE_TRANSPORTABLE){
				rayon.setStockDisponible(stockDispoRayon + QTE_MAX_EXEMPLAIRE_TRANSPORTABLE);
			}
			else{
				rayon.setStockDisponible(stockDispoRayon + besoin);
			}
		}
	}
	
	/**
	 * Parcour l'ensemble des rayons afin de les approvisionner
	 * une fois au dernier rayons, il se rend � l'entrepot pour se r�approvisionner avant de reprendre sa ronde
	 * 
	 * @throws InterruptedException
	 */
	public void parcourir() throws InterruptedException{
		for(int i = 0, t = listeRayons.size(); i < t; i = (i+1) % t) {
			approvisionner(listeRayons.get(i));
			if(i == t){
				Thread.sleep(Supermarche.TPS_CR_REAPPROVISIONNEMENT);//Simule le reapprovisionnement � l'entrepot
			}
		}
	}
	
}
