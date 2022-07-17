package app5.Operandes;

import app5.Exceptions.IdentifiantException;
import app5.Terminal;

public abstract class Operande extends Terminal {

  public Operande(String s) {
    super(s);
  }

  public abstract int EvalElem() throws IdentifiantException;
}
