package com.picross.collab.solveur;

import com.picross.collab.network.common.Log;
import com.picross.collab.shared.UnsolvablePicrossException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import static java.lang.Math.min;

//plein d'algorithmes : https://webpbn.com/survey/#minizinc


public class PicrossSolveur {
    private int width;
    private int height;
    private String[] columnsInd; // de la forme : ["1,3,2","8,4"]
    private String[] rowsInd; // de la forme : ["1,3,2","8,4"]
    public String solution; // de la forme "00111011110110110101"
    private boolean debugMode = false;//ou Log.ON
    public String arrayToStr(String[] array) {
        String returnstr="|";
        for (String ind : array) {
            returnstr += ind + "|";
        }
        return returnstr;
    }
    @Override
    public String toString() {
        String returnstr ="solveur du picross des indications suivantes ;\ncolind: ";
        returnstr += arrayToStr(this.columnsInd);
        returnstr += "\nrowsind: ";
        returnstr += arrayToStr(this.rowsInd);

        return returnstr;

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public PicrossSolveur(int pic_id) throws IOException, ParseException {
        JSONObject allLevelsJSON = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/com/picross/collab/shared/Database.json"));
        JSONObject levelJSON;
        levelJSON = (JSONObject) allLevelsJSON.get(String.valueOf(pic_id));
        //récupération des informations nécessaires
        width = Math.round((long) levelJSON.get("width"));
        height = Math.round((long) levelJSON.get("height"));
        rowsInd = ((String) levelJSON.get("rows")).split("\\|");
        columnsInd = ((String) levelJSON.get("columns")).split("\\|");



        if (debugMode) {
            System.out.println("**************\nDEBUG MODE ON\n**************");
            System.out.println("Searching "+this);
        }

    }

//    public PicrossSolveur(com.picross.collab.shared.Level level) {
//
//        width = Math.toIntExact(level.getWidth());
//        height = Math.toIntExact(level.getHeight());
//
//        this.rowsInd=level.getRowsIndices();
//        this.columnsInd=level.getColumnsIndices();
//
//
//    }

    /*

     */
    public String solve() throws UnsolvablePicrossException {
        ArrayList<String> parseSolutions = new ArrayList<>();//ceci est la liste des différentes solutions qu'on va remplir / vider petit à petit.
        parseSolutions.add("");
        int i_row = 0;


        boolean totSolFounded = false;
        HashMap<Integer, String[]> lineSolutionsHashTable = new HashMap<>();

        while(i_row<height-1&&!totSolFounded) { //parcourons chaque ligne 1 à 1
            //extrayons ses indications de la ligne en cours de traitement
            ArrayList<String> tempParseSolutions = new ArrayList<>();

            if (parseSolutions.isEmpty()) {
                throw new UnsolvablePicrossException();
            }

            for (String parseSolution = parseSolutions.get(0); !(parseSolutions.isEmpty()); parseSolution = parseSolutions.get(0)) {//parcourons toutes les solutions partielles déjà trouvées.
                parseSolutions.remove(parseSolution); // on enlève la solution partielle en cours car on va explorer toutes ses filles.
                //il faut récup de la solution partielle précédente l'indice de la ligne en cours :
                i_row=Math.floorDiv(parseSolution.length(),width);
                //puis trouver toutes les solutions de cette fameuse ligne (on pourrait tout stocker dans un dico mais j'ai la flemme)
                ArrayList<Integer> ligneInd = new ArrayList<>();
                String[] strRowInd = this.rowsInd[i_row].split(",");
                for (String s : strRowInd) {
                    ligneInd.add(Integer.parseInt(s));
                }
                int[] ligneIndArr = ligneInd.stream().mapToInt(i -> i).toArray();



                //récupérons toutes les solutions de la ligne possible dans le tableau si elles existent déjà :
                String[] ligneSolutions;
                if (lineSolutionsHashTable.containsKey(i_row)) {
                    ligneSolutions = lineSolutionsHashTable.get(i_row);
                    if (debugMode) {
                        System.out.println("line solution "+i_row+" is in hashtable");
                    }
                }
                else {
                    if (debugMode) {
                        System.out.println("line solution of " +i_row+" is NOT in hashtable :");
                    }
                    //créons un objet ligne
                    Line ligneEnCours = new Line(ligneIndArr, this.getWidth());
                    ligneEnCours.debugMode = false; //pas flood le terminal
                    ligneSolutions = ligneEnCours.solve();//résolvons la ligne
                    lineSolutionsHashTable.put(i_row,ligneSolutions);

                }



                if (debugMode) {
//                    System.out.println("solutions de la ligne "+i_row+" :"); //commenter, ça prends trop de place sur le terminal et ça doit augmenter le temps
//                    for (String str : ligneSolutions) {
//                        System.out.print(str);
//                        System.out.print("|");
//                    }
                    System.out.println(" ");
                    //---------------------------------------------------\\
                    System.out.println("**************************************** exploring with "+parseSolutions.size()+" previous solutions :");
//                    for (String str : parseSolutions) {
//                        System.out.print("\""+str+"\"");
//                    }
                    System.out.println(" ");

                    System.out.println("************* & with removed partial solution \""+parseSolution+"\"");
                } else {
                    int nbToExplore = parseSolutions.size();
                    if (nbToExplore%1000==0) {
                        System.out.println("**************************************** exploring "+parseSolutions.size()+" parse solutions for line "+i_row);
                    }
                }
                boolean haveAtLeastOne = false; // ce booléen permet de repérer si cette solution partielle a au moins une solution partielle fille


                int i_sol=0;
                while (i_sol<ligneSolutions.length) {//parcourons toutes les solutions de la ligne suivante :

//                    if (debugMode) {
//                        System.out.println("testing partial solution : "+parseSolution+ligneSolutions[i_sol]);
//                    }
                    if (cParseValidBool(parseSolution+ligneSolutions[i_sol])) {
                        tempParseSolutions.add(parseSolution.concat(ligneSolutions[i_sol]));
//                        if (debugMode) {
//                            System.out.println("found valid line !!");
//                            System.out.println("****************************** added partial solution : "+parseSolution+ligneSolutions[i_sol]);
//                         }
                        if (parseSolution.concat(ligneSolutions[i_sol]).length()==width*height) {
                             totSolFounded=true;
                        }
                    }

                    i_sol++;
                }


                //il faut récup l'indice de la ligne en cours à partir de la solution partielle précédente :
                i_row=Math.floorDiv(parseSolution.length(),width);

//                if (debugMode) {
//                    System.out.println("end of for {....}, and parseSolutions is :");
//                    for (String str : parseSolutions) {
//                        System.out.print("|||" + str + "|||");
//                    }
//                    System.out.println("------");
//                    System.out.print(parseSolutions.size()+"==0");
//                    System.out.println(parseSolutions.isEmpty());
//                    System.out.println(tempParseSolutions.size());
//
//                }

                if (parseSolutions.isEmpty()) {
                    break;
                }
            }


            //ajout de toutes les solutions partielles possibles trouvées à la liste pour la prochaine ligne :
            parseSolutions.addAll(tempParseSolutions);

//            if (debugMode) {
//                System.out.println("parseSolutions is now :");
//                for (String str : parseSolutions) {
//                    System.out.print(str + "|||");
//                }
//                System.out.println(" ");
//            }




        }


        if (debugMode) {
            System.out.println(parseSolutions.size()+" solutions trouvées !");
        }



        // ATTENTION il peut y avoir plusieurs solutions, traite-t-on ces cas ? NON, on retourne al première solution trouvée si elle existe.
        this.solution=parseSolutions.get(0);
        return parseSolutions.get(0);
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    protected static class Line {
        private int width;
        private int[] indications;
        private int nbBlack;
        private int nbWhite;
        private boolean debugMode = Log.ON;
        int totsol; //nb totales de solutions pour la ligne
        int foundedsol;//nb de solution déjà trouvées

        public Line(int[] indications, int width) {
            this.nbBlack = 0;
            this.indications=indications;
            this.width=width;
            this.totsol = (int) Math.pow(indications.length +1, nbWhite-indications.length+1);
            for (int i = 0; i<indications.length; i++) {
                nbBlack += indications[i];
            }
            this.nbWhite = width-nbBlack;

            new Line(indications, width, totsol,0);
        }
        public Line(int[] indications, int width, int totsol, int foundedsol) {
            this.indications=indications;
            this.width = width;
            this.foundedsol = foundedsol;
            this.totsol=totsol;

//            if (debugMode) {
//                String returnstr = "création de la ligne d'indications |";
//                for (int n : indications) {
//                    returnstr += String.valueOf(n) + "|";
//                }
//                returnstr += " et de largeur "+String.valueOf(width);
//                System.out.println(returnstr);
//            }

            //comptons le nombre de cases noires et blanches dans la ligne + construisons les indications
            this.nbBlack = 0;
            for (int i = 0; i<indications.length; i++) {
                nbBlack += indications[i];
            }
            this.nbWhite = width-nbBlack;
//            if (debugMode) {
//                System.out.println("il y a "+ nbBlack + " cases noires et "+nbWhite+" blanches dans une longueur "+width);
//            }


        }

        /**
         *
         * @return Array of Strings solutions of this line
         */
        protected String[] solve() {
            // return all solutions sous la forme "001011111010111010"...


            //cas terminaux :
            if (nbWhite<indications.length-1) {
                if (debugMode) {
                    System.out.println("impossible.");
                }
                return new String[0];
            }

            if (indications.length == 0) { // il n'y a plus de noires :


                String sol = "0".repeat(width); // la solution est que des 0, blanches
                String[] solTab = new String[1];
                solTab[0] = sol;

                if (debugMode) {
                    System.out.println();
                }
                return solTab;
            }
            if (indications.length == 1 && this.width == indications[0]) {
                //il n'y a plus qu'une indication -> plus que des cases noires


                String sol = "1".repeat(this.width); // la solution est que des 1
                String[] solTab = new String[1];
                solTab[0] = sol;

                return solTab;
            }





            ArrayList<String> solutionF = new ArrayList<>();
            int[] followingInd;




            //soit la première case est noire (et les suivantes aussi en fct du nb indications[0]):
            String toAdd = "1".repeat(indications[0])+"0";//le cas ou ce sont les dernières noires est déjà traité

            followingInd= Arrays.copyOfRange(indications, 1, indications.length);//les prochaines indications sont alors les suivantes :
            Line followingLineB = new Line(followingInd, width-toAdd.length(), totsol, foundedsol); //récursivité : on résout la suite de la ligne
            followingLineB.debugMode = false;
            try {
                if (debugMode) {
                    System.out.println("noir + APPEL RECCURSIF");
                }
                String[] followingSolB = followingLineB.solve();
                for (String sol : followingSolB) {
                    solutionF.add(toAdd+sol);
                    if (debugMode) {
                        System.out.println("found 1 solution, which is "+toAdd+sol);
                        this.foundedsol+=1;
                        System.out.println("solution "+foundedsol+" out of "+totsol);
                    }
                }
            } catch(InvalidParameterException e) {
                if (debugMode) {System.out.print("r");}
            }


            //ou la première case est blanche, soit '0'
            //on ne change pas les indications (elles portent sur les cases noires, non les blanches)
            Line followingLineW = new Line(this.indications, this.width-1, totsol, foundedsol); //récursivité : on résous la suite de la ligne
            followingLineW.debugMode = false;
            try {
                if (debugMode) {
                    System.out.println("blanc + APPEL RECURSIF");
                }
                String[] followingSolW = followingLineW.solve();
                for (String sol : followingSolW) {
                    solutionF.add("0"+sol);
                    if (debugMode) {
                        System.out.println("found 1 solution, which is "+"0"+sol);
                        this.foundedsol+=1;
                        System.out.println("solution "+foundedsol+" out of "+totsol);
                    }
                }
            } catch(InvalidParameterException e) {
                if (debugMode) {System.out.print("r");}
            }




            //finalement, on renvoie toutes les solutions calculées.
            String[] solutionArray = new String[solutionF.size()];
            for (int i = 0; i < solutionF.size(); i++) {
                solutionArray[i] = solutionF.get(i);
            }
            return solutionArray;

        }
    }


    /**
     * @param parseColInd est l'array des indications trouvées pour le picross partiellement rempli.
     * @return true si le picross partiellement rempli peut être le début d'une solution, false sinon
     */
    protected boolean cParseValidArray(String[] parseColInd) { //cette fonction av prendre en entrée les indications des colonnes calculées pour la solution partielle (en cours de test)
        String[] realColInd = this.columnsInd;

        if (realColInd.length != parseColInd.length) {
            throw new InvalidParameterException("parse & real indicators must have the same number of columns/rows");
        }

        int size = realColInd.length;
        for (int i = 0; i < size; i++) { //on parcourt chaque indication (ex : 1,4,3) colonne/ligne par colonne/ligne
            String[] realInd = realColInd[i].split(",");
            String[] parseInd = parseColInd[i].split(",");

            int realLen = realInd.length;
            int parseLen = parseInd.length;

            if (parseLen > realLen) { // si la solution en cours possède plus de "groupes" de blocs noirs que nécessaire, elle est fausse
                return false;
            }

            for (int j = 0; j < parseLen-1; j++) {// on parcourt maintenant chaque chiffre des indications réeles et celles de la solution partielle (?)
                if (!Objects.equals(realInd[j], parseInd[j])) { // si les premiers groupes déjà créés et finis ne possèdent pas le bon nombre de noirs, la solution est fausse
                    return false;
                }
            }

            if (!parseInd[parseLen - 1].isEmpty() && (Integer.parseInt(parseInd[parseLen - 1]) > Integer.parseInt(realInd[parseLen - 1]))) { //si le dernier groupe en cours de remplissage possède déjà plus de noirs que nécessaires, la solution est fausse
                return false;
            }
        }

        //sinon, tout va bien on peut continuer
        return true; // renvoie vrai si la solution partielle peut donner une solution valide, faux sinon
    }

    /**
     *
     * @param picross est le picross partiellement rempli, écrit comme dans la BDD : 00110111011101110...
     * @return un tableau d'indications ex : [ "2,2" , "3" ]
     */
    protected String[] transformToColInd(String picross) { //fabrication des indications de colonnes (ce sont celles qui vont nous aider à vérif si c'est bon)
        int w = this.width;
        int h = picross.length()/w;

//        if (debugMode) {
//            System.out.println("calculating columns ind of "+ picross);
//            System.out.println("with width of "+w+" and height of "+h);
//        }

        String[] colInd = new String[w];

        for (int i = 0; i < w; i++) { // parcourons chaque colonne
            StringBuilder indications = new StringBuilder();
            int counter = 0;

            for (int j = 0; j < h ; j++) {//puis chaque ligne

                char actual = picross.charAt(j*w+i);//colonne i ligne j
//                if (debugMode) {
//                    System.out.println("nous sommes en :\n col "+i+"\n row "+j+"\n char = "+actual);
//                }

                if (actual=='1') { //si on tombe sur une case noire
                    counter+=1;
                } else { // si on a un blanc
                    if (counter != 0) { // alors qu'on avait un noir juste avant
                        indications.append(counter);
                        indications.append(',');
                        counter = 0;
                    }
                }
//                if (debugMode) {
//                    System.out.println(" counter = "+counter);
//                }
            }
            //à la fin des lignes, on append counter :
            if (counter!=0) {
                indications.append(counter);
                indications.append(','); //on rajoute quand même une virgule, cela nosu évite de distinguer les cas ou il y a une virgule à la fin ou pas
            }

            if (!indications.isEmpty()) {
                colInd[i]= indications.substring(0,indications.length()-1); // ne pas oublier d'enlever la dernière ',', d'ou le substring
            }
            else {
                colInd[i]="";
            }

        }

        return colInd;

    }

    protected boolean cParseValidBool(String picross) {
        return cParseValidArray(transformToColInd(picross));
    }

}