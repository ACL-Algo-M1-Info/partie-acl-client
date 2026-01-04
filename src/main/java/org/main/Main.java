package org.main;

import parseur.ParseurCSV;
import tcp.ConnexionServeur;
import tcp.ReponseSocket;
import tcp.RequeteAPI;
import types.livraisonVille.LivraisonVille;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main
{

    public static void main(String[] args)
    {

        HashMap<Integer, LivraisonVille> repParseur = LireFichierCSV();
        try {
            DemarrageServeur();
        }
        catch (IOException io)
        {
            throw new RuntimeException("Problème connexion au serveur...");
        }

        //ConnexionServeur c = ConnexionServeur.getInstance();
        EnvoyerDonnees(repParseur);

    }

    /**
     * Lecture d'un fichier CSV et parsing
     */
    public static HashMap<Integer, LivraisonVille> LireFichierCSV()
    {

        System.out.println("Entrez le nom du fichier CSV à parcourir (avec le chemin) : ");

        String nomFichier = System.console().readLine();

        System.out.printf("Quel est le séparateur de votre fichier ? ATTENTION : seul le premier caractère sera pris en compte !\n");

        String separateurS = System.console().readLine();
        char separateur = separateurS.charAt(0);

        ParseurCSV parseur = new ParseurCSV(separateur);

        HashMap<Integer, LivraisonVille> resParseur = parseur.parser(nomFichier);

        System.out.printf("Résultat du parsing : ");
        for(Map.Entry m: resParseur.entrySet())
        {
            System.out.println("Id : " + m.getKey() + "; Ville à livrer : [ " + m.getValue() + " ]");
        }

        return resParseur;
    }

    public static void DemarrageServeur() throws IOException
    {

        System.out.println("Connexion au serveur...");

        ConnexionServeur.initialiser("127.0.0.1", 9111);
        ConnexionServeur c = ConnexionServeur.getInstance();
        c.demarrer();

        System.out.printf("Connexion réussie !\n");

    }

    public static void EnvoyerDonnees(HashMap<Integer, LivraisonVille> donnees)
    {

        // Conversation en tableau

        int[] p = new int[donnees.size()];

        System.out.println("Envoi des données suivantes : \n");
        for(int i = 0; i < donnees.size(); i++)
        {
            p[i] = donnees.get(i).quantiteALivrer;
            System.out.printf(p[i] + " ");
        }
        System.out.println("\n");

        int n = p.length;

        // Saisie du nombre de camions

        System.out.println("Combien voulez vous de camions ? ");
        int m;
        do
        {
            m = Integer.parseInt(System.console().readLine());
        }
        while(m <= 0);

        System.out.println("Instance : n = " + n + ", m = " + m + ", p = " + Arrays.toString(p));

        ReponseSocket reponse = ConnexionServeur.getInstance().envoyer(new RequeteAPI("ordo", m, p), -1);

        System.out.println("Réponse reçue : " + reponse);
    }

}