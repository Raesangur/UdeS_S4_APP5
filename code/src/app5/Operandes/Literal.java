package app5.Operandes;

public class Literal extends Operande {

  private int valeur;

  public Literal(String s) {
    super(s);
    canEval = true;
    valeur = Integer.parseInt(s);
  }

  @Override
  public int EvalElem() {
    return valeur;
  }

  @Override
  public String toString() {
    return getChaine();
  }
}
