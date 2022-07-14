package app5;

import java.util.ArrayList;
import java.util.Arrays;

import app5.Operandes.*;
import app5.Operateurs.*;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

    static ArrayList<Character> operateurs = new ArrayList<>(); // '+' a part

// Attributs
    String              s;
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
        this.s = s.replaceAll("\\s+","") + ' ';
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
        int test = s.length(); // pour tester
        return pointeurLecture < (s.length() - 1);
    }

    private void analInitial(char c) throws AnalLexErreur {
        if (Character.isLetter(c)) {
            if(Character.isLowerCase(c))
                throw new AnalLexErreur("Caractere initial est une minuscule : " + c, s, pointeurLecture);
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

    private Operateur analIntermediairePlus(char c) {
        if (c != '+') {
            // not ++
            pointeurLecture--;
            return new Addition(terminal.substring(0, terminal.length() - 1));
        }
        etat = EtatLex.FINAL_OPERATEUR;
        return null;
    }

    private Terminal analFinalOperateur() throws AnalLexErreur {
        switch (s.charAt(--pointeurLecture - 1)) {
            case '-':
                return new Soustraction(terminal.substring(0, terminal.length() - 1));
            case '/':
                return new Division(terminal.substring(0, terminal.length() - 1));
            case '*':
                return new Multiplication(terminal.substring(0, terminal.length() - 1));
            case '+':
                return new PostFixPlus(terminal.substring(0, terminal.length() - 1));
            case '(':
                return new Terminal("(");
            case ')':
                return new Terminal(")");
            default:
                throw new AnalLexErreur("Caractere incompatible : " + s.charAt(pointeurLecture), s, pointeurLecture);
        }
    }

    private Operande analFinalIdentifiant(char c) {
        if (c == '_') {
            etat = EtatLex.INTERMEDIAIRE_IDENTIFIANT;
        } else if(!Character.isLetter(c)) {
            pointeurLecture--;
            return new Identifiant(terminal.substring(0, terminal.length() - 1));
        }

        if(!resteTerminal())
            return new Identifiant(terminal);
        return null;
    }

    private void analIntermediaireIdentifiant(char c) throws AnalLexErreur {
        if(Character.isLetter(c)) {
            etat = EtatLex.FINAL_IDENTIFIANT;
        } else if(c == '_') {
            throw new AnalLexErreur("Caractere invalide double __: " + c, s, pointeurLecture);
        } else {
            throw new AnalLexErreur("Caractere invalide impossible de finir avec un _", s, --pointeurLecture);
        }
    }

    private Operande analFinalLiteral(char c) {
        if(!Character.isDigit(c)) {
            pointeurLecture--;
            return new Literal(terminal.substring(0, terminal.length() - 1));
        }

        if(!resteTerminal())
            return new Literal(terminal);

        return null;
    }

    /** prochainTerminal() retourne le prochain terminal
    Cette methode est une implementation d'un AEF
    */
    public Terminal prochainTerminal() throws AnalLexErreur {
        terminal = "";
        etat = EtatLex.INITIAL;
        char c = ' ';

        // TODO : faire attention boucles infinis
        while(true) {

            c = s.charAt(pointeurLecture++);
            terminal += c;

            switch(etat) {
    
                case INITIAL:
                    analInitial(c);
                    break;

                case INTERMEDIAIRE_PLUS: {
                    Operateur opt = analIntermediairePlus(c);
                    if(opt != null) return opt;
                    break;
                }
                case FINAL_OPERATEUR: {
                    Terminal opt = analFinalOperateur();
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

  public static ArrayList<Terminal> Analyser(String str) throws AnalLexErreur {
      AnalLex lexical = new AnalLex(str.toString()); // Creation de l'analyseur lexical
      ArrayList<Terminal> terminaux = new ArrayList<>();

      // Execution de l'analyseur lexical
      while(lexical.resteTerminal()) {
          terminaux.add(lexical.prochainTerminal());
      }

      return terminaux;
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
      toWrite += t.getChaine() + "\n" ;  // toWrite contient le resultat
    }				   //    d'analyse lexicale
    System.out.println(toWrite); 	// Ecriture de toWrite sur la console
    Writer w = new Writer(args[1],toWrite); // Ecriture de toWrite dans fichier args[1]
    System.out.println("Fin d'analyse lexicale");
  }
}

