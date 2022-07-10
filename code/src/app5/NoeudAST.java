package app5;

/** @author Ahmed Khoumsi */

import app5.Operateurs.Operateur;

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  // Attributs
  protected ElemAST noeudDroit;
  protected ElemAST noeudGauche;
  protected Operateur operation;

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(ElemAST noeudDroit, ElemAST noeudGauche) { // avec arguments
    //
    this.noeudDroit = noeudDroit;
    this.noeudGauche = noeudGauche;
  }

  /** Evaluation de noeud d'AST
   */
  public int EvalAST() {
    // TODO: gerer les identifiants et maybe les exceptions des calculs (division par 0)
    return operation.Calculer(noeudDroit.EvalAST(), noeudGauche.EvalAST());
  }


  /** Lecture de noeud d'AST
   */
  public String LectAST() {
    //
    return null;
  }

  @Override
  public ElemAST AnalSynt() {
    return null;
  }

  @Override
  public String toString() {
    return noeudDroit.toString() + operation + noeudGauche.toString();
  }
}

