package app5.Operateurs;

public class PostFixPlus extends Operateur {

  public PostFixPlus(String s) {
    super(s);
  }

  @Override
  public int Calculer(int val1) {
    return val1 + 1;
  }

  @Override
  public String toString() {
    return "+";
  }
}
