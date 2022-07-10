package app5.Operateurs;

public class Multiplication extends Operateur {
  @Override
  public int Calculer(int val1, int val2) {
    return val1 * val2;
  }

  @Override
  public String toString() {
    return "*";
  }
}
