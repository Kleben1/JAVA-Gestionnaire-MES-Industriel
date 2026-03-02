# Système de Supervision d'Atelier (Mini-MES)

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)

## Contexte du projet
Ce projet est une simulation simplifiée d'un **MES (Manufacturing Execution System)**. Il a été développé pour illustrer le concept de remontée d'informations en temps réel dans le cadre de l'**Industrie 4.0**. 

Le programme écoute les événements de production (démarrage, fabrication, pannes) simulés par des machines, met à jour leur statut en temps réel, et sauvegarde l'historique de production dans une base de données relationnelle.



## Fonctionnalités
* **Gestion d'état des machines :** Suivi du cycle de vie des équipements (En pause, En production, En panne).
* **Traitement événementiel :** Réaction dynamique aux signaux de l'atelier (simulation de requêtes MQTT/OPC-UA).
* **Persistance des données :** Chargement dynamique du parc machine et sauvegarde des pièces produites dans une base de données MySQL.
* **Calcul de performance :** Suivi du taux d'accomplissement des objectifs de production.

## Architecture Technique
Le projet est architecturé autour de 4 composants principaux (Programmation Orientée Objet) :
* `Machine.java` : Le modèle métier représentant un équipement et sa logique interne.
* `EtatMachine.java` : Énumération listant les statuts industriels possibles.
* `GestionnaireBDD.java` : Couche d'accès aux données (DAO) gérant la connexion JDBC et les requêtes préparées.
* `SystemeSupervisionReel.java` : Le chef d'orchestre (Main) gérant la logique applicative et l'affichage du Dashboard.

## Prérequis
* **JDK 11** ou supérieur.
* **MySQL** (via WAMP, XAMPP, MAMP ou installation native).
* **Driver JDBC MySQL** (`mysql-connector-j`).

## Installation et Utilisation

**1. Préparer la base de données**
Exécutez le script SQL suivant dans votre SGBD (ex: phpMyAdmin) pour créer la base et les tables nécessaires :
```sql
CREATE DATABASE IF NOT EXISTS usine_mes;
USE usine_mes;

CREATE TABLE machines (
    id VARCHAR(10) PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    objectif_production INT NOT NULL
);

CREATE TABLE historique_production (
    id INT AUTO_INCREMENT PRIMARY KEY,
    machine_id VARCHAR(10),
    quantite INT NOT NULL,
    date_heure DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (machine_id) REFERENCES machines(id)
);

INSERT INTO machines (id, nom, objectif_production) VALUES 
('M001', 'Presse Hydraulique', 500),
('M002', 'Centre Usinage CNC', 150),
('M003', 'Convoyeur Principal', 1000);
