package app5;

import java.util.ArrayList;

public class AnalSyntErreur extends Exception {

    static String ErrorLocation(ArrayList<Terminal> ts, int currentTerm) {
        String terminaux = "";
        String subterm   = "";
        for(int i = 0; i < ts.size(); i++) {
            Terminal tt = ts.get(i);
            terminaux += tt.toString();
            if (i != currentTerm - 1)
                subterm   += "~".repeat(tt.toString().length());
            else
                subterm   += "^".repeat(tt.toString().length());
        }
        terminaux += '\n' + subterm;
        return terminaux;
    }
    public AnalSyntErreur(String msg, Terminal t, ArrayList<Terminal> ts, int currentTerm) {
        super("Erreur Analyse Syntaxique: " + msg + t.toString() + '\n' + ErrorLocation(ts, currentTerm));
    }
}
