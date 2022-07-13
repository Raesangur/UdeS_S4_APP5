package app5;

/** @author Ahmed Khoumsi */

import app5.Operandes.Operande;
import app5.Operateurs.Operateur;

import java.util.ArrayList;

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

    static ArrayList<Terminal> uniteLexicales;

    // Attributs
    String s;
    int ptrLect;
    Terminal nextToken;
    Terminal currentToken;

    /** Constructeur de DescenteRecursive :
     - recoit en argument le nom du fichier contenant l'expression a analyser
     - pour l'initalisation d'attribut(s)
     */
    public DescenteRecursive(String in) throws AnalLexErreur {
        Reader r = new Reader(in);
        s = r.toString().replaceAll("\\s+","");
        ptrLect = 0;
    }

    public void scanNextToken() {
        currentToken = uniteLexicales.get(ptrLect++);
        nextToken = uniteLexicales.get(ptrLect);
    }

    public boolean resteTerminal() {
        return ptrLect < uniteLexicales.size() - 1;
    }

    public ElemAST parseE() throws AnalSyntErreur {
        if(currentToken instanceof Operande && nextToken instanceof Operateur) {
            NoeudAST n = new NoeudAST((Operateur) nextToken);
            n.setEnfantGauche(new FeuilleAST((Operande) currentToken));
            scanNextToken();
            n.setEnfantDroit(parseE());
            return n;
        } else if(currentToken instanceof Operateur && nextToken instanceof Operande) {
            if(!resteTerminal())
                return new FeuilleAST((Operande) nextToken);
            scanNextToken();
            return parseE();
        } else {
            throw new AnalSyntErreur(".... : " , currentToken);
        }

    }


    /** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
     *    Elle retourne une reference sur la racine de l'AST construit
     */
    public ElemAST AnalSynt() throws AnalLexErreur, AnalSyntErreur {
        uniteLexicales = AnalLex.Analyser(s);
        scanNextToken();
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

