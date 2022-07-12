package app5.Operandes;

public class Identifiant extends Operande {

  String valeur;

  public Identifiant(String s) {
    super(s);
    canEval = false;
  }

  @Override
  public int EvalElem() {
    // TODO: dealer avec la valeur d'un identifiant
    return 0;
  }

  @Override
  public String toString() {
    return valeur;
  }
}
