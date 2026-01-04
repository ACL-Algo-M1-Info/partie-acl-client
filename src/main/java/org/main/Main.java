package org.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import parseur.ParseurCSV;
import tcp.ConnexionServeur;
import tcp.ReponseSocket;
import tcp.RequeteAPI;
import types.camion.Camion;
import types.livraisonVille.LivraisonVille;
import types.typesCode.CodeStatutReponse;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
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

        ReponseSocket reponse = EnvoyerDonnees(repParseur);
        System.out.println("Réponse reçue : " + reponse);

        estOk(reponse);


        ArrayList<ArrayList<Integer>> ordos;

        try {
            ordos = Utils.jsonVersT(reponse.reponse);
        }
        catch (JsonProcessingException j)
        {
            throw new RuntimeException("Json illisible...");
        }

        afficherOrdos(ordos);

        ArrayList<Camion> camions = new ArrayList<>();
        for(ArrayList<Integer> ordo: ordos)
        {
            ArrayList<LivraisonVille> livraisons = new ArrayList<>();
            for(Integer i: ordo)
            {
                livraisons.add(repParseur.get(i));
            }
            camions.add(new Camion(livraisons));
        }

        System.out.println(camions);

        ConnexionServeur.getInstance().arreter();
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

    public static ReponseSocket EnvoyerDonnees(HashMap<Integer, LivraisonVille> donnees)
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

        return ConnexionServeur.getInstance().envoyer(new RequeteAPI("ordo", m, p), -1);

    }

    public static void estOk(ReponseSocket reponse)
    {
        if(reponse.statut != CodeStatutReponse.OK)
            throw new RuntimeException("Réponse invalide : " + reponse.toString());
    }

    public static void afficherOrdos(ArrayList<ArrayList<Integer>> ordos)
    {
        System.out.println("Ordonnancements :");
        System.out.println("[");
        for(ArrayList<Integer> i: ordos)
        {
            System.out.print("[ ");
            for(Integer j: i)
            {
                System.out.print(String.valueOf(j) + " ");
            }
            System.out.println("]");
        }
        System.out.println("]");
    }

}