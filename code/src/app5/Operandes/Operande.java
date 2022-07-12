package app5.Operandes;

public abstract class Operande extends Terminal {

  protected Boolean canEval;

  public abstract int EvalElem();
  public abstract String toString();

  public Boolean GetCanEval() {
    return canEval;
  }
}
