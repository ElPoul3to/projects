package utils;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

@SuppressWarnings("unchecked") // pas de cas problématique
public class PicrossConverter {

    public static void main(String[] args) throws IOException, ParseException {
        // Spécifiez le chemin d'entrée et de sortie
        String firstRep = "src/nonogram_db/db/gnonograms";
        String secondRep= "src/nonogram_db/db/qnonograms/collection1";
        String thirdRep= "src/nonogram_db/db/qnonograms/examples";
        String fourthRep= "src/nonogram_db/db/webpbn";

        String outputFile = "src/mail/java/com.picross.collab/view/Database.json";

        Object ob = new JSONParser().parse(new FileReader(outputFile));
        JSONObject js = (JSONObject) ob;

        convertAllFiles(js, firstRep, outputFile);
        convertAllFiles(js, secondRep, outputFile);
        convertAllFiles(js, thirdRep, outputFile);
        convertAllFiles(js, fourthRep, outputFile);




    }

    private static void convertAllFiles(JSONObject js, String repertory, String outputFile){
        File repertoire = new File(repertory);
        String[] liste = repertoire.list();

        if (liste != null) {
            for (String s : liste) {
                convertFile(js, repertory + "/" + s, outputFile);
            }
        } else {
            System.err.println("Nom de repertoire invalide: "+repertory);
        }
    }

    private static void convertFile(JSONObject js, String inputFile, String outputFile){
        try {
            // Lire le fichier .non
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            JSONObject jsonObject = convertNonToJSON(reader, js);
            reader.close();

            // Écrire dans le fichier JSON
            FileWriter fileWriter = new FileWriter(outputFile);
            fileWriter.write(jsonObject.toString());
            fileWriter.close();

            System.out.println("Conversion réussie.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static JSONObject convertNonToJSON(BufferedReader reader, JSONObject currentData) throws IOException {
        JSONObject nonDetails = new JSONObject();
        long k;

        //Les nonogrammes vont de 1 à number 39
        if(currentData.get("number") == null){
            currentData.put("number",0);
            k=0;
        }else {
            k = (Long) currentData.get("number");
        }

        String line;
        int width= 0;
        int height = 0;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");

                String category = parts[0].toLowerCase();
                //System.out.println(Arrays.toString(parts));
                switch (category) {
                    case "title":
                        StringBuilder title = new StringBuilder();

                        for(int i=1;i<parts.length;i++){
                            title.append(parts[i]).append(" ");
                        }

                        nonDetails.put(category, title.substring(1,title.length()-2));
                        break;

                    case "height":
                        height = Integer.parseInt(parts[1]);
                        nonDetails.put(category, height);
                        break;

                    case "width":
                        width = Integer.parseInt(parts[1]);
                        nonDetails.put(category, width);
                        break;

                    case "rows":
                        nonDetails.put(category, readColumnsAndRows(reader,height));
                        break;

                    case "columns":
                        nonDetails.put(category, readColumnsAndRows(reader, width));
                        break;

                    case "goal":
                        nonDetails.put("solve", parts[1].substring(1,parts[1].length()-1));
                        break;
                }

        }
        currentData.put(k+"", nonDetails);
        currentData.put("number",k+1);
        return currentData;
    }

    private static String readColumnsAndRows(BufferedReader reader, int taille) throws IOException {
        String[] parts;
        StringBuilder rep = new StringBuilder();

        parts = reader.readLine().split(" ");

        for(int i=0; i<taille;i++){
            rep.append(parts[0]).append("|");


            parts = reader.readLine().split(" ");

        }
        return rep.toString();
    }
}