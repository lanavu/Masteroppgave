import java.util.HashMap;
import java.util.Map;

/**
 * Created by lanam on 24.01.2017.
 */
public class MapDocument {

    /**
     * Metode for å formatere Json til et relasjonsskjema
     * @return rel , relasjonsskjema
     */
    public String mapToRelation(HashMap<String, String> documentMap, String collectionName){
        String rel = ""; //Genererer relasjonsskjema med konkatenering
        int numAttributes = documentMap.size();
        int counter = 1; //Teller iterasjoner

        rel = rel.concat(collectionName + "(");
        for (HashMap.Entry<String, String> entry : documentMap.entrySet()) {
            rel = rel.concat(entry.getKey());

            //Sjekker om iterasjonen er på det siste elementet
            if(counter != numAttributes) {
                rel = rel.concat(",");
            }
            counter++;
        }
        rel = rel.concat(")");
        System.out.println(rel);

        return rel;
    }

}
