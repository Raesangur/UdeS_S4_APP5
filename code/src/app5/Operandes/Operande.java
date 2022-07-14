package app5.Operandes;

import app5.Terminal;

public abstract class Operande extends Terminal {

  protected Boolean canEval;

  public Operande(String s) {
    super(s);
  }

  public abstract int EvalElem();

  public Boolean GetCanEval() {
    return canEval;
  }
}
