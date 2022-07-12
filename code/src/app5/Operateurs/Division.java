package app5.Operateurs;

public class Division extends Operateur {

  public Division(String s) {
    super(s);
  }

  @Override
  public int Calculer(int val1, int val2) {
    // TODO: gerer les divisions par 0 (exception?)
    return val1 / val2;
  }

  @Override
  public String toString() {
    return "/";
  }
}
