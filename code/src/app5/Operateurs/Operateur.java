package app5.Operateurs;
import app5.Terminal;

public abstract class Operateur extends Terminal {

  protected boolean unaire;

  public Operateur(String s) {
    super(s);
    unaire = false;
  }

  public boolean isUnaire() {
    return unaire;
  }

  public abstract int Calculer(int val1, int val2);

  public abstract String toString();
}
