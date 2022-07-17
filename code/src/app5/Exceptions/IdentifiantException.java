package app5.Exceptions;

public class IdentifiantException extends Exception {
    public IdentifiantException(String s) {
        super("Impossible d'evaluer cet identifiant : " + s);
    }
}
