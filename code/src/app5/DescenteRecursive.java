package app5;


import app5.Operandes.Operande;
import app5.Operateurs.*;

import java.util.ArrayList;

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

    static ArrayList<Terminal> uniteLexicales;

    // Attributs
    String   s;
    String[] ss;
    int      ptrLect;
    Terminal currentToken;

    /** Constructeur de DescenteRecursive :
     - recoit en argument le nom du fichier contenant l'expression a analyser
     - pour l'initalisation d'attribut(s)
     */
    public DescenteRecursive(String in) {
        Reader r = new Reader(in);
        s  = r.toString().replaceAll("\\ \\t","");
        ss = s.split("\\n");
        ptrLect = 0;
    }

    public void scanNextToken() {
        if(resteTerminal())
            currentToken = uniteLexicales.get(ptrLect++);
    }

    public boolean resteTerminal() {
        return ptrLect < uniteLexicales.size();
    }

    public NoeudAST creationNoeudAST(ElemAST elemAST) {
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
            scanNextToken();
            return n;
        }
        return unaire;
    }

    public ElemAST parseU() throws AnalSyntErreur {
        if(currentToken.toString().equals("(")) {
            scanNextToken();
            ElemAST n = parseE();

            if(!resteTerminal() && !currentToken.toString().equals(")"))
                throw new AnalSyntErreur("Manque parenthese fermante : " , currentToken, uniteLexicales, ptrLect);

            scanNextToken();
            return n;
        } else {
            FeuilleAST operande = new FeuilleAST((Operande) currentToken);
            scanNextToken();
            return operande;
        }
    }

    public ElemAST parseE() throws AnalSyntErreur {
        if(currentToken instanceof Operande || currentToken.toString().equals("(")) {
            ElemAST terme = parseT();
            if(currentToken instanceof Addition || currentToken instanceof Soustraction) {
                NoeudAST n = creationNoeudAST(terme);
                n.setEnfantDroit(parseE());

                return n;
            }
            return terme;
        }
        throw new AnalSyntErreur("Ce terminal devrait etre une operande : " , currentToken, uniteLexicales, ptrLect);
    }


    /** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
     *    Elle retourne une reference sur la racine de l'AST construit
     */
    public ArrayList<ElemAST> AnalSynt() throws AnalLexErreur, AnalSyntErreur {
        ArrayList<ElemAST> elems = new ArrayList<>();
        for(String s : ss) {
            uniteLexicales = AnalLex.Analyser(s);
            if (uniteLexicales.size() == 0) {
                continue;
            }
            scanNextToken();
            for (Terminal t : uniteLexicales) {
                System.out.println(t.lexicalString());
            }
            elems.add(parseE());
            ptrLect = 0;

            System.out.println("-----------\n");
        }
        return elems;
    }

    //Methode principale a lancer pour tester l'analyseur syntaxique
    public static void main(String[] args) {
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
            ArrayList<ElemAST> RacinesAST = dr.AnalSynt();
            for(ElemAST RacineAST : RacinesAST) {
                System.out.println("Lecture postfix : " + RacineAST.postFix());
                String writeLect = "Lecture de l'AST trouve : " + RacineAST.LectAST() + "\n";
                toWriteLect += writeLect;
                System.out.println(writeLect);
                String writeEval = "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
                toWriteEval = writeEval;
                System.out.println(writeEval);
            }
            Writer w = new Writer(args[1], toWriteLect + toWriteEval); // Ecriture de toWrite dans fichier args[1]
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(51);
        }
        System.out.println("Analyse syntaxique terminee");
    }

}

