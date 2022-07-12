package app5;

import java.util.ArrayList;
import app5.Operateurs.*;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

    static final ArrayList<char> operateurs = Arrays.AsList('-', '*', '/', '(', ')'); // '+' a part

// Attributs
    String              s;
    ArrayList<Terminal> uniteLexicales;
    int                 pointeurLecture;
    EtatLex             etat;
    String              terminal;
    
// Enum
    enum EtatLex {
        INITIAL,
        INTERMEDIAIRE_PLUS,
        FINAL_OPERATEUR,
        FINAL_IDENTIFIANT,
        INTERMEDIAIRE_IDENTIFIANT,
        FINAL_LITERAL
    }


  /** Constructeur pour l'initialisation d'attribut(s)
   */
    public AnalLex(String s) {
        this.s = s;
        uniteLexicales = new ArrayList<Terminal>();
        pointeurLecture = 0;
        etat = EtatLex.INITIAL;
    }


    /** resteTerminal() retourne :
     false  si tous les terminaux de l'expression arithmetique ont ete retournes
     true s'il reste encore au moins un terminal qui n'a pas ete retourne
     */
    public boolean resteTerminal( ) {
        return pointeurLecture != (s.length() - 1);
    }


    private void analInitial(char c) {
        if (Character.isLetter(c)) {
            etat = EtatLex.FINAL_IDENTIFIANT;
        }
        else if(Character.isDigit(c)) {
            etat = EtatLex.FINAL_LITERAL;
        }
        else if(operateurs.contains(c)) {
            etat = EtatLex.FINAL_OPERATEUR;
        }
        else if(c == '+') {
            etat = EtatLex.INTERMEDIAIRE_PLUS;
        }
        else {
            throw new AnalLexErreur("Caractere initial invalide : " + c, s, pointeurLecture);
        }
    }

  /** prochainTerminal() retourne le prochain terminal
   Cette methode est une implementation d'un AEF
   */
    public Terminal prochainTerminal( ) {
        terminal = "";
        while(true) {
            char c = s.charAt(pointeurLecture++);
            terminal += c;
    
            switch (etat) {
    
            case INITIAL:
                analInitial(c);
                break;
    
            case INTERMEDIAIRE_PLUS:
                if (c == '+') {
                    etat = EtatLex.FINAL_OPERATEUR;
                }
                else {
                    return new Addition();
                }
                break;
    
            case FINAL_OPERATEUR:
                break;
    
            case FINAL_IDENTIFIANT:
                break;
    
            case INTERMEDIAIRE_IDENTIFIANT:
                break;
    
            case FINAL_LITERAL:
                break;
            }
         }
  }


  /** ErreurLex() envoie un message d'erreur lexicale
   */
  public void ErreurLex(String s) {
    //
  }


  //Methode principale a lancer pour tester l'analyseur lexical
  public static void main(String[] args) {
    String toWrite = "";
    System.out.println("Debut d'analyse lexicale");
    if (args.length == 0){
      args = new String [2];
      args[0] = "ExpArith.txt";
      args[1] = "ResultatLexical.txt";
    }
    Reader r = new Reader(args[0]);

    AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

    // Execution de l'analyseur lexical
    Terminal t = null;
    while(lexical.resteTerminal()){
      t = lexical.prochainTerminal();
      toWrite += t.GetChaine() + "\n" ;  // toWrite contient le resultat
    }				   //    d'analyse lexicale
    System.out.println(toWrite); 	// Ecriture de toWrite sur la console
    Writer w = new Writer(args[1],toWrite); // Ecriture de toWrite dans fichier args[1]
    System.out.println("Fin d'analyse lexicale");
  }
}

