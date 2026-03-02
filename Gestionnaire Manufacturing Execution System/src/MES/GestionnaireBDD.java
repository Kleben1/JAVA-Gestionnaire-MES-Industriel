package MES;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class GestionnaireBDD {

    // CHANGEZ LES " " AVEC VOS IDENTIFIANTS MYSQL
    private static final String URL = " ";
    private static final String UTILISATEUR = " ";
    private static final String MOT_DE_PASSE = " ";

    public Map<String, Machine> chargerMachines() {
        Map<String, Machine> parc = new HashMap<>();
        System.out.println("Connexion à MySQL en cours...");

        try (Connection connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
             Statement statement = connexion.createStatement();
             ResultSet resultat = statement.executeQuery("SELECT * FROM machines")) {

            while (resultat.next()) {
                String id = resultat.getString("id");
                String nom = resultat.getString("nom");
                int objectif = resultat.getInt("objectif_production");

                Machine m = new Machine(id, nom, objectif);
                parc.put(id, m); // On ajoute la machine au dictionnaire
            }
            System.out.println("✅ Succès : " + parc.size() + " machines chargées depuis la BDD.");

        } catch (Exception e) {
            System.err.println("❌ Erreur de lecture BDD (Avez-vous lancé WAMP/XAMPP et créé la base ?) : " + e.getMessage());
        }
        return parc;
    }

    public void sauvegarderProduction(String idMachine, int quantite) {
        String requete = "INSERT INTO historique_production (machine_id, quantite) VALUES (?, ?)";

        try (Connection connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
             PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {

            preparedStatement.setString(1, idMachine);
            preparedStatement.setInt(2, quantite);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println("❌ Erreur d'écriture BDD : " + e.getMessage());
        }
    }
}
