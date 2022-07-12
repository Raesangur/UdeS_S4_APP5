package app5;

import java.io.CharConversionException;
import java.util.ArrayList;
import java.util.Arrays;

import app5.Operandes.*;
import app5.Operateurs.*;

import javax.crypto.spec.ChaCha20ParameterSpec;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

    static ArrayList<Character> operateurs = new ArrayList<>();
        //Arrays.asList('-', '*', '/', '(', ')'); // '+' a part

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

        Character[] ops = new Character[] {'-', '*', '/', '(', ')'};
        operateurs.addAll(Arrays.asList(ops));
    }


    /** resteTerminal() retourne :
     false  si tous les terminaux de l'expression arithmetique ont ete retournes
     true s'il reste encore au moins un terminal qui n'a pas ete retourne
     */
    public boolean resteTerminal( ) {
        return pointeurLecture != (s.length() - 1);
    }

    private void analInitial(char c) throws AnalLexErreur {
        if (Character.isLetter(c)) {
            if(Character.isUpperCase(c))
                throw new AnalLexErreur("Caractere initial est une minuscule : " + c, s, pointeurLecture);
            etat = EtatLex.FINAL_IDENTIFIANT;
            terminal += c;
        }
        else if(Character.isDigit(c)) {
            etat = EtatLex.FINAL_LITERAL;
            terminal += c;
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

    private Operateur analIntermediairePlus(char c) {
        if (c != '+') {
            // not ++
            pointeurLecture--;
            return new Addition(c + "");
        }
        etat = EtatLex.FINAL_OPERATEUR;
        return null;
    }

    private Operateur analFinalOperateur(char c) {
        switch (c) {
            case '-':
                return new Soustraction(c + "");
            case '/':
                return new Division(c + "");
            case '*':
                return new Multiplication(c + "");
            case '+':
                return new PostFixPlus(c + "");
            default:
                return null;
        }
    }

    private Operande analFinalIdentifiant(char c) {
        if (c == '_') {
            etat = EtatLex.INTERMEDIAIRE_IDENTIFIANT;
        } else if(Character.isDigit(c) || operateurs.contains(c)) {
            pointeurLecture--;
            return new Identifiant(terminal);
        }
        terminal += c;
        return null;
    }

    private void analIntermediaireIdentifiant(char c) throws AnalLexErreur {
        if(Character.isLetter(c)) {
            etat = EtatLex.FINAL_IDENTIFIANT;
        } else if(c == '_') {
            throw new AnalLexErreur("Caractere invalide double __: " + c, s, pointeurLecture);
        } else if(Character.isDigit(c) || operateurs.contains(c) || c == '+') {
            throw new AnalLexErreur("Caractere invalide impossible de finir avec un _: " + c, s, pointeurLecture);
        }
    }

    private Operande analFinalLiteral(char c) {
        if(Character.isLetter(c) || operateurs.contains(c) || c == '+') {
            pointeurLecture--;
            return new Literal(terminal);
        }

        terminal += c;
        return null;
    }

    /** prochainTerminal() retourne le prochain terminal
    Cette methode est une implementation d'un AEF
    */
    public Terminal prochainTerminal() throws AnalLexErreur {
        terminal = "";
        etat = EtatLex.INITIAL;

        // TODO : faire attention boucles infinis
        while(true) {
            char c = s.charAt(pointeurLecture++);
//            terminal += c;
    
            switch (etat) {
    
                case INITIAL:
                    analInitial(c);
                    break;

                case INTERMEDIAIRE_PLUS: {
                    Operateur opt = analIntermediairePlus(c);
                    if(opt != null) return opt;
                    break;
                }
                case FINAL_OPERATEUR: {
                    Operateur opt = analFinalOperateur(c);
                    if(opt != null) return opt;
                    break;
                }
                case INTERMEDIAIRE_IDENTIFIANT: {
                    analIntermediaireIdentifiant(c);
                    break;
                }
                case FINAL_IDENTIFIANT: {
                    Operande opd = analFinalIdentifiant(c);
                    if (opd != null) return opd;
                    break;
                }
                case FINAL_LITERAL: {
                    Operande opd = analFinalLiteral(c);
                    if (opd != null) return opd;
                    break;
                }
            }
         }
  }


  /** ErreurLex() envoie un message d'erreur lexicale
   */
  public void ErreurLex(String s) {
    //
  }


  //Methode principale a lancer pour tester l'analyseur lexical
  public static void main(String[] args) throws AnalLexErreur {
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

