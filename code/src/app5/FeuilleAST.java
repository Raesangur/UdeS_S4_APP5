package app5;

/** @author Ahmed Khoumsi */

import app5.Operandes.Operande;

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

  // Attribut(s)
  protected Operande operande;

  /**Constructeur pour l'initialisation d'attribut(s)
   */
  public FeuilleAST( ) {  // avec arguments
    //
  }


  /** Evaluation de feuille d'AST
   */
  public int EvalAST( ) {

    return operande.EvalElem();
  }


  /** Lecture de chaine de caracteres correspondant a la feuille d'AST
   */
  public String LectAST( ) {
    //
    return null;
  }

  public Boolean CanEval() {
    return operande.GetCanEval();
  }

  @Override
  public ElemAST AnalSynt() {
    return null;
  }

  @Override
  public String toString() {
    return null;
  }

}