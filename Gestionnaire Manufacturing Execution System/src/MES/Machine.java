package MES;

public class Machine {
    private String id;
    private String nom;
    private EtatMachine etat;
    private int piecesProduites;
    private int objectifProduction;

    public Machine(String id, String nom, int objectifProduction) {
        this.id = id;
        this.nom = nom;
        this.etat = EtatMachine.EN_PAUSE;
        this.piecesProduites = 0;
        this.objectifProduction = objectifProduction;
    }

    public String getId() { return id; }
    public String getNom() { return nom; }

    public void demarrer() {
        this.etat = EtatMachine.EN_PRODUCTION;
        System.out.println("▶ Machine " + nom + " (" + id + ") démarrée.");
    }

    public void signalerPanne() {
        this.etat = EtatMachine.EN_PANNE;
        System.out.println(" ALERTE : La machine " + nom + " (" + id + ") est en panne.");
    }

    public void produire(int quantite) {
        if (this.etat == EtatMachine.EN_PRODUCTION) {
            this.piecesProduites += quantite;
            System.out.println("[" + id + "] a produit " + quantite + " pièces. (Total: " + piecesProduites + ")");
        } else {
            System.out.println("[" + id + "] Impossible de produire : machine non démarrée ou en panne.");
        }
    }

    public double calculerTauxAccomplissement() {
        return ((double) piecesProduites / objectifProduction) * 100;
    }

    public void afficherStatut() {
        System.out.printf("Machine: %-15s | État: %-15s | Produit: %d/%d (%.1f%%)%n",
                nom, etat, piecesProduites, objectifProduction, calculerTauxAccomplissement());
    }
}