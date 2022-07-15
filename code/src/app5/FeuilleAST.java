package app5;

import app5.Operandes.Operande;

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

  // Attribut(s)
  protected Operande operande;

  /**Constructeur pour l'initialisation d'attribut(s)
   */
  public FeuilleAST(Operande operande) {  // avec arguments
    this.operande = operande;
  }


  /** Evaluation de feuille d'AST
   */
  public int EvalAST( ) {

    return operande.EvalElem();
  }

  /** Lecture de chaine de caracteres correspondant a la feuille d'AST
   */
  public String LectAST( ) {
    return toString();
  }

  @Override
  public String toString() {
    return operande.toString();
  }

  @Override
  public String postFix() { return this.toString(); }
}