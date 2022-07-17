package app5.Operandes;

public class Literal extends Operande {

  private int valeur;

  public Literal(String s) {
    super(s);
    valeur = Integer.parseInt(s);
  }

  @Override
  public int EvalElem() {
    return valeur;
  }

  @Override
  public String getNom() {
    return "Nombre:\t\t\t";
  }
}
