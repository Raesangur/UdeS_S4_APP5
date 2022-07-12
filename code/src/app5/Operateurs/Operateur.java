package app5.Operateurs;
import app5.Terminal;

public abstract class Operateur extends Terminal {

  public Operateur(String s) {
    super(s);
  }

  public abstract int Calculer(int val1, int val2);

  public abstract String toString();
}
