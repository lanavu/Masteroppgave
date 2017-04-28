import java.util.*;

/**
 * Created by lanam on 25.01.2017.
 * Dokumentobjektet for å lagre alle documentene i en samling
 */
public class Document{
    String collectionName;
    HashMap<String, String> doc;

    public Document(HashMap<String, String> doc, String collectionName){
        this.doc = doc;
        this.collectionName = collectionName;
    }

    /**
     * Henter ut dokumentet
     * @return doc , en hashmap med dokument felt og verdi
     */
    public HashMap<String, String> getMap(){
        return doc;
    }


    /**
     * Henter ut verdien gitt en nøkkel
     * @param key
     * @return value
     */
    public String getValuesByKey(String key){
        return doc.get(key);
    }

    /**
     * Henter ut kombinasjoner av verdier gitt n nøkler
     * @param combinations
     * @return Konkatenerte verdier
     */
    public String getValuesByNKeys(List<String> combinations){
        String s = "";
        for(String c : combinations){
            s += doc.get(c) + " ";
        }
        return s;
    }

    /**
     * Henter ut collectionname
     * @return String collection name
     */
    public String getCollectionName(){
        return collectionName;
    }

    /**
     * Henter ut alle nøklene tilsvarende attributter
     * @return
     */
    public Set<String> getKeys(){
        return doc.keySet();
    }

    /**
     * Endrer
     * @param key
     * @param val
     */
    public void updateValue(String key, String val){
        doc.put(key, val);
    }

    public void removeValue(String key){
            doc.remove(key);

    }
}
