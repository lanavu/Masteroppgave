import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lanam on 26.03.2017.
 */
public class DirectMapping {
    Document doc;
    ReadDocumentsToJson toJson = new ReadDocumentsToJson();
    Set<String> listfields = new HashSet<>();

    public DirectMapping(Document doc){
        this.doc = doc;

        newEntity(doc.getCollectionName());
        recursiveMap(doc.getCollectionName(), doc.getMap());
    }

    public void recursiveMap(String currentEntity, HashMap<String, String> m){

        for(String key : m.keySet()){
            String value = m.get(key);
            char firstchar = value.charAt(0);

            //Field contains document
            if(firstchar == '{'){
                newEntity(key);
                newPredicate(currentEntity, "1", "n", key);
                recursiveMap(key, toJson.jsonToMap(value)); //Recursive step

            }else if(firstchar == '['){
                listfields.add(key);
                String firstElem = jsonToArray(value).get(0);
                newEntity(key);
                newPredicate(currentEntity, "m", "n", key);

                if(firstElem.charAt(0)=='{'){
                    recursiveMap(key, toJson.jsonToMap(firstElem));

                }else {
                    newValueType(key+"_value");
                    newPredicate(key, "1", "1", key+"_value");

                }
            }else{
                newValueType(key);
                newPredicate(currentEntity, "1", "n", key);

            }
        }
    }
    public void newEntity(String name){
        String enitity = name.substring(0, 1).toUpperCase() + name.substring(1);
        System.out.println(enitity + " : new entity");
    }
    public void newValueType(String name){
        System.out.println(name + " : new value type");
    }
    public void newPredicate(String name1, String c1, String c2, String name2){
        System.out.println(name1 + " - " + c1 + ":" + c2 + " - " + name2);
    }

    public ArrayList<String> jsonToArray(String s){
        ArrayList<String> list = new ArrayList<String>();
        JSONArray jsonArray = new JSONArray(s);
        if (jsonArray != null) {
            int len = jsonArray.length();
            for (int i=0;i<len;i++){
                list.add(jsonArray.get(i).toString());
            }
        }
        return list;
    }

    public Set<String> getListfields(){
        return listfields;
    }

}
