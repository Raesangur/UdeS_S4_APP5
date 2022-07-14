package app5;

/** @author Ahmed Khoumsi */

import app5.Operandes.Operande;
import app5.Operateurs.*;

import java.util.ArrayList;

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

    static ArrayList<Terminal> uniteLexicales;

    // Attributs
    String s;
    int ptrLect;
    Terminal currentToken;

    /** Constructeur de DescenteRecursive :
     - recoit en argument le nom du fichier contenant l'expression a analyser
     - pour l'initalisation d'attribut(s)
     */
    public DescenteRecursive(String in) {
        Reader r = new Reader(in);
        s = r.toString().replaceAll("\\s+","");
        ptrLect = 0;
    }

    public void scanNextToken() {
        if(resteTerminal())
            currentToken = uniteLexicales.get(ptrLect++);
    }

    public boolean resteTerminal() {
        return ptrLect < uniteLexicales.size();
    }

    public NoeudAST creationNoeudAST(ElemAST elemAST) throws AnalSyntErreur {
        NoeudAST n = new NoeudAST((Operateur) currentToken);
        n.setEnfantGauche(elemAST);
        scanNextToken();

        return n;
    }

    public ElemAST parseT() throws AnalSyntErreur {
        ElemAST facteur = parseF();
        if (currentToken instanceof Multiplication || currentToken instanceof Division) {
            NoeudAST n = creationNoeudAST(facteur);
            n.setEnfantDroit(parseT());

            return n;
        }
        return facteur;
    }

    public ElemAST parseF() throws AnalSyntErreur {
        ElemAST unaire = parseU();
        if (currentToken instanceof PostFixPlus) {
            NoeudAST n = new NoeudAST((Operateur) currentToken);
            n.setEnfantGauche(unaire);
            return n;
        }
        return unaire;
    }

    public ElemAST parseU() throws AnalSyntErreur {
        if(currentToken.toString() == "(") {
            scanNextToken();
            ElemAST n = parseE();

            if(!resteTerminal() && currentToken.toString() != ")")
                throw new AnalSyntErreur("Manque parenthese fermante : " , currentToken);
            scanNextToken();
            return n;
        } else {
            FeuilleAST operande = new FeuilleAST((Operande) currentToken);
            scanNextToken();
            return operande;
        }
    }

    public ElemAST parseE() throws AnalSyntErreur {
        if(currentToken instanceof Operande) {
            ElemAST terme = parseT();
            if(currentToken instanceof Addition || currentToken instanceof Soustraction) {
                NoeudAST n = creationNoeudAST(terme);
                n.setEnfantDroit(parseE());

                return n;
            }
            return terme;
        }
        // TODO: gerer les exceptions
        return null;
//        else {
//            throw new AnalSyntErreur(".... : " , currentToken);
//        }

    }


    /** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
     *    Elle retourne une reference sur la racine de l'AST construit
     */
    public ElemAST AnalSynt() throws AnalLexErreur, AnalSyntErreur {
        uniteLexicales = AnalLex.Analyser(s);
        scanNextToken();
        for (Terminal t : uniteLexicales) {
            System.out.print(t);
        }
        ElemAST racineAST = parseE();


//        if(ptrLect <= uniteLexicales.size())
//            throw new AnalSyntErreur("Terminal final invalide : " , nextToken);

        return racineAST;
    }

// Methode pour chaque symbole non-terminal de la grammaire retenue
// ...
// ...



    /** ErreurSynt() envoie un message d'erreur syntaxique
     */
    public void ErreurSynt(String s)
    {
        //
    }



    //Methode principale a lancer pour tester l'analyseur syntaxique
    public static void main(String[] args) throws AnalLexErreur {
        String toWriteLect = "";
        String toWriteEval = "";

        uniteLexicales = new ArrayList<>();

        System.out.println("Debut d'analyse syntaxique");
        if (args.length == 0){
            args = new String [2];
            args[0] = "ExpArith.txt";
            args[1] = "ResultatSyntaxique.txt";
        }
        DescenteRecursive dr = new DescenteRecursive(args[0]);
        try {
            ElemAST RacineAST = dr.AnalSynt();
            toWriteLect += "Lecture de l'AST trouve : " + RacineAST.LectAST() + "\n";
            System.out.println(toWriteLect);
            toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
            System.out.println(toWriteEval);
            Writer w = new Writer(args[1],toWriteLect+toWriteEval); // Ecriture de toWrite
            // dans fichier args[1]
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(51);
        }
        System.out.println("Analyse syntaxique terminee");
    }

}

