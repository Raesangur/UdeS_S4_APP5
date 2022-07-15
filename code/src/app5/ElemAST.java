package app5;

/** Classe Abstraite dont heriteront les classes FeuilleAST et NoeudAST
 */
public abstract class ElemAST {


  /** Evaluation d'AST
   */
  public abstract int EvalAST();


  /** Lecture d'AST
   */
  public abstract String LectAST();

  public abstract String postFix();

  public abstract String toString();

}