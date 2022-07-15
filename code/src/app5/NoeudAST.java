package app5;

import app5.Operateurs.Operateur;

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

    // Attributs
    private ElemAST noeudDroit;
    private ElemAST noeudGauche;
    private final Operateur operation;

    /** Constructeur pour l'initialisation d'attributs
     */
    public NoeudAST(Operateur operation) {
        this.operation = operation;
    }

    public void setEnfantDroit(ElemAST noeudDroit) {
        this.noeudDroit = noeudDroit;
    }

    public void setEnfantGauche(ElemAST noeudGauche) {
        this.noeudGauche = noeudGauche;
    }

    public boolean hasOneChild() {
        return operation.isUnaire();
    }

    /** Evaluation de noeud d'AST
     */
    public int EvalAST() {
        // TODO: gerer les identifiants et maybe les exceptions des calculs (division par 0)
        return (hasOneChild() ? operation.Calculer(noeudGauche.EvalAST(), 0): operation.Calculer(noeudGauche.EvalAST(), noeudDroit.EvalAST()));
    }


    /** Lecture de noeud d'AST
     */
    public String LectAST() {
        return toString();
    }

    @Override
    public String toString() {
        return "(" + (hasOneChild() ?
                noeudGauche.toString() + operation.toString() :
                noeudGauche.toString() + operation.toString() + noeudDroit.toString()) + ")";
    }

    @Override
    public String postFix() {
        return noeudGauche.postFix() + " " + (!hasOneChild() ? noeudDroit.postFix() : "") + " " + operation.toString();
    }
}

