package app5;

public class AnalSyntErreur extends Exception {
    public AnalSyntErreur(String msg, Terminal t) {
        super("Erreur Analyse Syntaxique: " + msg + '\n' + t.toString() + '\n');
    }
}
