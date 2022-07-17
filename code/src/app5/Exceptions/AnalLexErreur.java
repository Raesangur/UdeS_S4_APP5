package app5.Exceptions;

public class AnalLexErreur extends Exception {
    public AnalLexErreur(String msg, String s, int pointeur) {
        super("Erreur Analyse Lexicale: " + msg + '\n' + s + '\n' + "~".repeat(pointeur - 1) + "^");
        
    }
}
