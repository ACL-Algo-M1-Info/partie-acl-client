// PACKAGE

package parseur;

// IMPORTS

import types.livraisonVille.LivraisonVille;

import java.io.*;

// CLASSE

public class ParseurCSV implements Parseur
{

    // VARIABLES

    private final String separateur;


    // CONSTRUCTEUR

    public ParseurCSV(String separateur)
    {
        this.separateur = separateur;
    }


    // METHODES

    @Override
    public ReponseParseur parser(String nomFichier)
    {

        // Vérification du fichier

        BufferedReader br = verifierFichier(nomFichier);

        // Parsing de la première ligne

        ReponseParseur reponse;

        try
        {
            reponse = parserPremiereLigne(br);
        }
        catch (IOException io)
        {
            throw new IllegalArgumentException("Le fichier est inaccessilble...");
        }

        // Parsing du reste des lignes

        String ligneCourante;

        int compteur = -1;

        try
        {
            while ((ligneCourante = br.readLine()) != null)
            {

                // Split de la ligne

                String[] ligne = ligneCourante.split(separateur);

                reponse.livraisons.put(++compteur, new LivraisonVille(ligne[0], Integer.parseInt(ligne[1])));
            }
        }
        catch (IOException io)
        {
            throw new RuntimeException(io);
        }

        return reponse;
    }

    @Override
    public BufferedReader verifierFichier(String nomFichier) {

        if(nomFichier.endsWith(".csv"))
        {
            BufferedReader br;
            try
            {
                br =  new BufferedReader(new FileReader(nomFichier));
            }
            catch (FileNotFoundException f)
            {
                throw new IllegalArgumentException("Le fichier n'a pas été trouvé : " + f.toString());
            }

            return br;
        }
        else throw new IllegalArgumentException("Le fichier n'est pas au format CSV...");
    }

    private ReponseParseur parserPremiereLigne(BufferedReader br) throws IOException {

        String[] premiereLigne = br.readLine().split(this.separateur);

        return new ReponseParseur(Integer.parseInt(premiereLigne[2]));
    }
}
