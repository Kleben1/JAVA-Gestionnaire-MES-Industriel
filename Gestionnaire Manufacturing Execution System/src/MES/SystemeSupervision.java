package MES;

import java.util.Map;

public class SystemeSupervision {

    private Map<String, Machine> parcMachines;
    private GestionnaireBDD bdd;

    public SystemeSupervision() {
        this.bdd = new GestionnaireBDD();
        this.parcMachines = bdd.chargerMachines();
    }

    public void onMessageReseauRecu(String idMachine, String typeEvenement, int donnee) {
        Machine machineConcernee = parcMachines.get(idMachine);

        if (machineConcernee == null) {
            System.out.println("❌ Ignoré : La machine " + idMachine + " n'existe pas dans le parc.");
            return;
        }

        switch (typeEvenement) {
            case "DEMARRAGE":
                machineConcernee.demarrer();
                break;
            case "PRODUCTION":
                machineConcernee.produire(donnee);
                bdd.sauvegarderProduction(idMachine, donnee);
                break;
            case "PANNE":
                machineConcernee.signalerPanne();
                break;
            default:
                System.out.println("⚠️ Événement inconnu : " + typeEvenement);
        }
    }

    public void afficherDashboard() {
        System.out.println("\n=== DASHBOARD USINE EN TEMPS RÉEL ===");
        if (parcMachines.isEmpty()) {
            System.out.println("Aucune machine détectée. Vérifiez la base de données.");
        } else {
            for (Machine m : parcMachines.values()) {
                m.afficherStatut();
            }
        }
        System.out.println("=====================================\n");
    }

    public static void main(String[] args) {
        System.out.println("=== INITIALISATION DU MES INDUSTRIEL ===\n");

        SystemeSupervision supervision = new SystemeSupervision();

        supervision.afficherDashboard();

        System.out.println("📡 Simulation de réception de données capteurs...\n");

        supervision.onMessageReseauRecu("M001", "DEMARRAGE", 0);
        supervision.onMessageReseauRecu("M002", "DEMARRAGE", 0);

        supervision.onMessageReseauRecu("M001", "PRODUCTION", 50);
        supervision.onMessageReseauRecu("M002", "PRODUCTION", 20);
        supervision.onMessageReseauRecu("M001", "PRODUCTION", 100);

        supervision.onMessageReseauRecu("M002", "PANNE", 0);

        supervision.onMessageReseauRecu("M002", "PRODUCTION", 10);

        supervision.afficherDashboard();
    }
}