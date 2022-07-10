package app5.Operandes;

public class Literal extends Operande {

  private int valeur;

  public Literal() {
    canEval = true;
  }

  @Override
  public int EvalElem() {
    return valeur;
  }

  @Override
  public String toString() {
    return String.valueOf(valeur);
  }
}
