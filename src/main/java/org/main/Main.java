package org.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import fenetre.FenetreGraphe;
import math.Vecteur2D;
import outils.mondeEcran.MondeEcran;
import outils.mondeEcran.obtentionCoordonnee.ObtentionCoordonnesVille;
import parseur.ParseurCSV;
import tcp.ConnexionServeur;
import tcp.ReponseSocket;
import tcp.RequeteAPI;
import types.camion.Camion;
import types.camion.TrajetCamion;
import types.livraisonVille.LivraisonVille;
import types.route.Route;
import types.typesCode.CodeStatutReponse;
import types.ville.Ville;
import utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        ReponseSocket reponse = envoyerDonnees(repParseur);
        System.out.println("Réponse reçue : " + reponse);

        estOk(reponse);


        ArrayList<ArrayList<Integer>> ordos;

        try {
            ordos = Utils.jsonVersT(reponse.reponse, new TypeReference<ArrayList<ArrayList<Integer>>>() {});
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

        ReponseSocket reponseTSP = appelTSP(camions);
        System.out.println("Réponse TSP : " + reponseTSP);
        estOk(reponseTSP);


        ArrayList<TrajetCamion> trajets;
        try
        {
            trajets = Utils.jsonVersT(reponseTSP.reponse, new TypeReference<ArrayList<TrajetCamion>>() {});
        }
        catch (JsonProcessingException j)
        {
            throw new RuntimeException("Json illisible :" + j);
        }


        dessinerGraphe(trajets);


        ConnexionServeur.getInstance().arreter();
    }

    public static String lireLigne()
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String res;

        try
        {
            res = reader.readLine();
        }
        catch(IOException e)
        {
            throw new RuntimeException("Impossible de lire le clavier...");
        }

        return res;
    }

    /**
     * Lecture d'un fichier CSV et parsing
     */
    public static HashMap<Integer, LivraisonVille> LireFichierCSV()
    {

        System.out.print("Entrez le nom du fichier CSV à parcourir (avec le chemin) : ");

        String nomFichier = lireLigne();

        System.out.print("Entrez le symbole du séparateur utilisé pour le csv (ATTENTION : seul le premier caractère sera pris en compte) : ");

        String separateurS = lireLigne();
        char separateur = separateurS.charAt(0);

        ParseurCSV parseur = new ParseurCSV(separateur);

        HashMap<Integer, LivraisonVille> resParseur = parseur.parser(nomFichier);

        System.out.print("Résultat du parsing : ");
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

        System.out.println("Connexion réussie !");

    }

    public static ReponseSocket envoyerDonnees(HashMap<Integer, LivraisonVille> donnees)
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

        System.out.print("Entrez le nombre de camions désiré : ");
        int m;
        do
        {
            m = Integer.parseInt(lireLigne());
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

    //tsp
    public static ReponseSocket appelTSP(ArrayList<Camion> camions)
    {
        ArrayList<ArrayList<String>> villesParcouruesParCamion = Camion.obtenirVillesParCamion(camions);

        return ConnexionServeur.getInstance().envoyer(new RequeteAPI("tsp", villesParcouruesParCamion));
    }

    public static void dessinerGraphe(ArrayList<TrajetCamion> trajets)
    {
        FenetreGraphe fenetre = new FenetreGraphe(10, 10, 700, 500, 20);



        MondeEcran<Ville> mondeEcran = new MondeEcran<>(new ObtentionCoordonnesVille(), fenetre, TrajetCamion.obtenirVillesParcourues(trajets));

        for(TrajetCamion trajet: trajets)
        {
            // Choix de la couleur

            ArrayList<Vecteur2D> points = new ArrayList<>();

            for(Ville ville: trajet.villesParcourues)
            {
                Vecteur2D vecteur = mondeEcran.resoudreMondeEcran(ville);
                fenetre.dessinerVille(vecteur, ville.nom);
                points.add(vecteur);
            }

            int indiceVille0 = 0;
            int indiceVille1 = 1;

            for(int i = 0; i < trajet.villesParcourues.size(); i++)
            {
                indiceVille0 = i;
                indiceVille1 = i+1;

                if(indiceVille1 >= trajet.villesParcourues.size())
                    indiceVille1 = 0;

                Vecteur2D pointA = points.get(indiceVille0);
                Vecteur2D pointB = points.get(indiceVille1);

                Route routeActuelle = trajet.routesParcourues.get(indiceVille0);
                fenetre.dessinerRoute(pointA, pointB, routeActuelle.typeRoute, routeActuelle.distance);
            }

        }

    }

}