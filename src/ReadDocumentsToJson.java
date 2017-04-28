import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by lana on 24.01.2017.
 * Antakelser
 * - Json-objektene må være skrevet på én linje
 * - Det må finnes en _id
 */
public class ReadDocumentsToJson {

    /**
     * Leser inn fil og returnerer en collection
     * @return dbc , DBCollection
     */
    public DBCollection readCollection(String input){
        String collectionName = input.substring(0, input.lastIndexOf('.'));
        ArrayList<Document> documents = new ArrayList<Document>();
        String document;
        FileReader file = null;

        try{
            file = new FileReader(input);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()){
                document = sc.nextLine();

                HashMap<String, String> docMap = jsonToMap(document);

                Document doc = new Document(docMap, collectionName);
                documents.add(doc);
            }
            sc.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        DBCollection dbc = new DBCollection(collectionName, documents);

        return dbc;
    }


    /**
     * Metode skrevet av Vinothkumar Arputharaj
     * http://stackoverflow.com/questions/22011200/creating-hashmap-from-a-json-string
     * @param bson
     * @throws JSONException
     */
    public HashMap<String, String> jsonToMap(String bson) throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        JSONObject jObject = new JSONObject(bson);
        Iterator<?> keys = jObject.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            String value = jObject.get(key).toString();
            map.put(key, value);
        }
        return map;
    }


}