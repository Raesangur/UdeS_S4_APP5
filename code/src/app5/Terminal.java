package app5;

/** @author Ahmed Khoumsi */

/** Cette classe identifie les terminaux reconnus et retournes par
 *  l'analyseur lexical
 */
public class Terminal {


// Constantes et attributs
//  ....
  private String chaine;


  /** Un ou deux constructeurs (ou plus, si vous voulez)
   *   pour l'initalisation d'attributs
   */
  public Terminal(String s) {   // arguments possibles
    this.chaine = s;
    //
  }

  public String GetChaine() {
    return chaine;
  }
}
