package app5.Operandes;

import app5.Exceptions.IdentifiantException;

public class Identifiant extends Operande {

    public Identifiant(String s) {
        super(s);
    }

    @Override
    public int EvalElem() throws IdentifiantException {
        throw new IdentifiantException(getChaine());
    }

    @Override
    public String getNom() {
        return "Identifiant:\t";
    }
}
