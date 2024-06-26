package com.picross.collab.solveur;

import com.picross.collab.shared.UnsolvablePicrossException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

class PicrossSolveurTest {
    PicrossSolveur solveur;
    String solution;
    int pic_id = 1;




    @Test
    @DisplayName("résolution d'une ligne")
    void lineSolve1() throws Exception {
        int wid = 12;
        int[] ind = new int[]{ 3,5,1 };
        String[] solutions = new String[]{ "011101111101","111001111101","111011111001","111011111010" };
        PicrossSolveur.Line ligne = new PicrossSolveur.Line(ind, wid);
        String[] calcsol = ligne.solve();

        int differences = 0;

        System.out.println("sol trouvée : ");
        for (String st : calcsol) {
            System.out.println(st+"\n");
            if (!Arrays.stream(solutions).toList().contains(st)) {
                differences+= 1;
            }
        }
        if (solutions.length != calcsol.length || differences!=0) {
            throw new Exception("Mauvaise résolution");
        }
    }

    @Test
    @DisplayName("résolution ligne simple")
    void lineSolve2() throws Exception {
        int wid = 5;
        int[] ind = new int[]{ 2,2 };
        String[] solutions = new String[]{ "11011" };
        PicrossSolveur.Line ligne = new PicrossSolveur.Line(ind, wid);
        String[] calcsol = ligne.solve();

        int differences = 0;

        System.out.println("sol trouvée : ");
        for (String st : calcsol) {
            System.out.println(st+"\n");
            if (!Arrays.stream(solutions).toList().contains(st)) {
                differences+= 1;
            }
        }
        if (solutions.length != calcsol.length || differences!=0) {
            throw new Exception("Mauvaise résolution");
        }
    }

    @Test
    @DisplayName("résolution autre ligne")
    void lineSolve3() throws Exception {
        int wid = 3;
        int[] ind = new int[]{ 1 };
        String[] solutions = new String[]{ "100", "010", "001" };
        PicrossSolveur.Line ligne = new PicrossSolveur.Line(ind, wid);
        String[] calcsol = ligne.solve();


        int differences = 0;

        System.out.println("sol trouvée : ");
        for (String st : calcsol) {
            System.out.println(st+"\n");
            if (!Arrays.stream(solutions).toList().contains(st)) {
                differences+= 1;
            }
        }
        if (solutions.length != calcsol.length || differences!=0) {
            throw new Exception("Mauvaise résolution");
        }
    }



    @Nested
    public class BigTests {
        @BeforeEach
        void setUp() throws IOException, ParseException {

        }

        @Test
        @DisplayName("test de transformToColInd & cParseValid POUR picross basic")
        void testParse() throws Exception {
            System.out.println("\n\n\n\n");
            System.out.println("utilisation de l'id " + 1);
            solveur = new PicrossSolveur(1);
            JSONObject allLevelsJSON = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/com/picross/collab/shared/Database.json"));
            JSONObject levelJSON;
            levelJSON = (JSONObject) allLevelsJSON.get(String.valueOf(1));
            solution = (String) levelJSON.get("solve");
            int width = Math.round((long) levelJSON.get("width"));
            int height = Math.round((long) levelJSON.get("height"));
            String solve = (String) levelJSON.get("solve");


            String[] foundInd = solveur.transformToColInd("111111101101110");
            String[] expectedInd = new String[]{"2","3","1,1","3","2"};
            boolean b = (foundInd.length != expectedInd.length);

            System.out.println("ind trouvés :");
            for (int i = 0; i < foundInd.length; i++) {
                b = (!foundInd[i].equals(expectedInd[i]));
                System.out.print(foundInd[i]);
                System.out.print(" expected :");
                System.out.println(expectedInd[i]);
            }
            if ( b ) {
                throw new Exception("ERREUR : transformToColInd est faux");
            }


            //*********************cParseValid*****************



            if (!solveur.cParseValidArray(foundInd)) {
                throw new Exception("cParseValid faux ! (40)");

            }


        }


        @Test
        @DisplayName("test de transformToColInd & cParseValid POUR bloop bloop")
        void testParse2() throws Exception {
            System.out.println("\n\n\n\n");
            System.out.println("utilisation de l'id " + 3);
            solveur = new PicrossSolveur(3);
            JSONObject allLevelsJSON = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/com/picross/collab/shared/Database.json"));
            JSONObject levelJSON;
            levelJSON = (JSONObject) allLevelsJSON.get(String.valueOf(3));
            solution = (String) levelJSON.get("solve");
            int width = Math.round((long) levelJSON.get("width"));
            int height = Math.round((long) levelJSON.get("height"));
            String solve = (String) levelJSON.get("solve");


            String[] foundInd = solveur.transformToColInd("1110000110111000000011000011000000011110000001111000110011000111100001011110001100110000110000000001");
            String[] expectedInd = new String[]{"3","3,2","2,4","4","2","2","4","1,4","1,2,2","4"};
            boolean b = (foundInd.length != expectedInd.length);

            System.out.println("ind trouvés :");
            for (int i = 0; i < foundInd.length; i++) {
                b = (!foundInd[i].equals(expectedInd[i]));
                System.out.print(foundInd[i]);
                System.out.print(" expected :");
                System.out.println(expectedInd[i]);
            }
            if ( b ) {
                throw new Exception("ERREUR : transformToColInd est faux");
            }


            //*********************cParseValid*****************



            if (!solveur.cParseValidArray(foundInd)) {
                throw new Exception("cParseValid faux ! (37)");

            }


        }


        @Test
        @DisplayName("test de résolution du picross pic_id ")
        void solve1() throws Exception {
            System.out.println("\n\n\n\n");
            System.out.println("utilisation de l'id " + pic_id);
            solveur = new PicrossSolveur(pic_id);
            JSONObject allLevelsJSON = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/com/picross/collab/shared/Database.json"));
            JSONObject levelJSON;
            levelJSON = (JSONObject) allLevelsJSON.get(String.valueOf(pic_id));
            solution = (String) levelJSON.get("solve");


            System.out.println("\n\n\n\n");
            String sol= solveur.solve();
            System.out.println("FINAL founded solution is : "+sol);
            System.out.println("expected solution is "+solution);
            if (!sol.equals(solution)) {
                throw new Exception("incorrect solution");
            }


        }

        @Test
        @DisplayName("test de résolution du picross groupe")
        void solve2() throws Exception {
            int id = 2;
            System.out.println("\n\n\n\n");
            System.out.println("utilisation de l'id " + id);
            solveur = new PicrossSolveur(id);
            JSONObject allLevelsJSON = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/com/picross/collab/shared/Database.json"));
            JSONObject levelJSON;
            levelJSON = (JSONObject) allLevelsJSON.get(String.valueOf(id));
            solution = (String) levelJSON.get("solve");


            System.out.println("\n\n\n\n");
            String sol= solveur.solve();
            System.out.println("FINAL founded solution is : "+sol);
            System.out.println("expected solution is "+solution);
            if (!sol.equals(solution)) {
                throw new Exception("incorrect solution");
            }


        }



        @Test
        @DisplayName("test de résolution du picross bloop bloop")
        void solve3() throws Exception {
            int id = 3;
            System.out.println("\n\n\n\n");
            System.out.println("utilisation de l'id " + id);
            solveur = new PicrossSolveur(id);
            JSONObject allLevelsJSON = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/com/picross/collab/shared/Database.json"));
            JSONObject levelJSON;
            levelJSON = (JSONObject) allLevelsJSON.get(String.valueOf(id));
            solution = (String) levelJSON.get("solve");


            System.out.println("\n\n\n\n");
            String sol= solveur.solve();
            System.out.println("FINAL founded solution is : "+sol);
            System.out.println("expected solution is "+solution);
            if (!sol.equals(solution)) {
                throw new Exception("incorrect solution");
            }


        }

        @Test
        @DisplayName("test de résolution du picross canard")
        void solve4() throws Exception {
            int id = 6;
            System.out.println("\n\n\n\n");
            System.out.println("utilisation de l'id " + id);
            solveur = new PicrossSolveur(id);
            JSONObject allLevelsJSON = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/com/picross/collab/shared/Database.json"));
            JSONObject levelJSON;
            levelJSON = (JSONObject) allLevelsJSON.get(String.valueOf(id));
            solution = (String) levelJSON.get("solve");


            System.out.println("\n\n\n\n");
            String sol= solveur.solve();
            System.out.println("FINAL founded solution is : "+sol);
            System.out.println("expected solution is "+solution);
            if (!sol.equals(solution)) {
                throw new Exception("incorrect solution");
            }


        }

        @Test
        @DisplayName("test de résolution du picross palmier")
        void solve5() throws Exception {
            int id = 5;
            System.out.println("\n\n\n\n");
            System.out.println("utilisation de l'id " + id);
            solveur = new PicrossSolveur(id);
            JSONObject allLevelsJSON = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/com/picross/collab/shared/Database.json"));
            JSONObject levelJSON;
            levelJSON = (JSONObject) allLevelsJSON.get(String.valueOf(id));
            solution = (String) levelJSON.get("solve");


            System.out.println("\n\n\n\n");
            String sol= solveur.solve();
            System.out.println("FINAL founded solution is : "+sol);
            System.out.println("expected solution is "+solution);
            if (!sol.equals(solution)) {
                throw new Exception("incorrect solution");
            }


        }


        @Test
        @DisplayName("test de résolution du picross spade")
        void solve6() throws Exception {
            int id = 11;
            System.out.println("\n\n\n\n");
            System.out.println("utilisation de l'id " + id);
            solveur = new PicrossSolveur(id);
            JSONObject allLevelsJSON = (JSONObject) new JSONParser().parse(new FileReader("src/main/java/com/picross/collab/shared/Database.json"));
            JSONObject levelJSON;
            levelJSON = (JSONObject) allLevelsJSON.get(String.valueOf(id));
            solution = (String) levelJSON.get("solve");


            System.out.println("\n\n\n\n");
            String sol= solveur.solve();
            System.out.println("FINAL founded solution is : "+sol);
            System.out.println("expected solution is "+solution);
            if (!sol.equals(solution)) {
                throw new Exception("incorrect solution");
            }


        }

        @AfterEach
        void tearDown() {
        }
    }


}