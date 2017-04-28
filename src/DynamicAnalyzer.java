import java.util.*;

/**
 * Created by lanam on 01.04.2017.
 */
public class DynamicAnalyzer {
    DBCollection dbc;
    ArrayList<Document> docs;
    List<String> fields;

    public DynamicAnalyzer(DBCollection dbc, Set<String> listfields){
        this.dbc = dbc;
        docs = dbc.getDocuments();
        fields = new ArrayList<>();
        fields.addAll(docs.get(0).getKeys());
        if(!listfields.isEmpty()) fields.removeAll(listfields);
        fields.removeAll(findKeys());

        Map<List<String>, List<String>> fds = findFDs(limitedPowerSet());
        for(List<String> l : fds.keySet()){
            System.out.println(l + " -> " + fds.get(l));
        }
    }

    /**
     * Metode som søker etter FDer
     * @param lpset
     * @return alle FDer som er funnet
     */
    public Map<List<String>, List<String>> findFDs(ArrayList <List<String>> lpset){
        Map<List<String>, List<String>> retrFDs = new HashMap<>();

        for(List<String> set: lpset){

            //Felter som ikke er med i venstresiden
            List<String> notInSet = new ArrayList<>(fields);
            notInSet.removeAll(set);

            for(String field: notInSet){
                if(checkMinimalFd(set, retrFDs, field)) continue;

                //Itererer gjennon alle dokumentene med documentCheck
                if(documentCheck(set, field)){

                    //Sjekker om det finnes en FD med lik venstreside fra før
                    if (!retrFDs.containsKey(set))
                        retrFDs.put(set, Arrays.asList(field));
                    else {
                        //Oppdaterer eksiterende verdier
                        List<String> tmpFields = new ArrayList<>(retrFDs.get(set));
                        tmpFields.add(field);
                        retrFDs.put(set, tmpFields);
                    }

                }

            }
        }
        return retrFDs;
    }

    public boolean checkMinimalFd(List<String> newSet, Map<List<String>, List<String>> existingFd, String field){

        boolean minimalExists = false;

        for(List<String> ex: existingFd.keySet()){
            if(newSet.containsAll(ex) && existingFd.get(ex).contains(field)) {
                minimalExists = true;
                break;
            }
        }

        return minimalExists;
    }

    /**
     * Hjelpemetode for FD-søket. Finner brudd på fd ved å traversere
     * dokumentene i samlingen
     * @param set
     * @param field
     * @return true eller false avhengig om en FD holder
     */
    public boolean documentCheck(List<String> set, String field){
        //Tmp hashmap for å teste data
        HashMap<String, String> mapFD = new HashMap<>();
        boolean trueFD = true;

        for(Document d: dbc.getDocuments()){
            String key = d.getValuesByNKeys(set);
            String value = d.getValuesByKey(field);

            if(mapFD.containsKey(key)) {
                if(!mapFD.get(key).equals(value)) {
                    trueFD = false;
                    break;
                }
            }else mapFD.put(key,value);
        }
        return trueFD;
    }

    /**
     * Metode for å finne alle felter som utgjør en nøkkel
     * @return Et sett av nøklene
     */
    public Set<String> findKeys(){
        Set<String> UniqueKeys = new HashSet<>();
        Set<String> dataset; //Bufferset

        //Itererer gjennom all data for hvert attributt
        for(String a: fields){
            dataset = new HashSet<String>();

            for(int i = 0; i<docs.size(); i++){
                //Optimalisering ved å sjekke nøkkelkrav
                if(i != dataset.size()) break;
                dataset.add(docs.get(i).getValuesByKey(a));
            }

            //Sjekker om alle verdiene er unike
            if(dataset.size() == docs.size()) UniqueKeys.add(a);
        }
        System.out.println("Keys:" + UniqueKeys);
        return UniqueKeys;
    }


    /**
     * Metode for å finne potenmengden begrenset til 3 felter
     * @return Alle subset av feltene
     */
    public ArrayList<List<String>> limitedPowerSet(){
        ArrayList<List<String>> pset = new ArrayList<>();

        for(int i=0; i<fields.size(); i++){

            pset.add(Arrays.asList(fields.get(i)));

            for(int j = i+1; j<fields.size(); j++){
                pset.add(Arrays.asList(fields.get(i), fields.get(j)));

                for(int k=j+1; k<fields.size(); k++){
                    pset.add(Arrays.asList(fields.get(i), fields.get(j), fields.get(k)));
                }

            }

        }

        Collections.sort(pset, new Comparator<List<String>> () {
            @Override
            public int compare(List<String> a, List<String> b) {
                return (a.size())-(b.size());
            }
        });
        return pset;
    }
}
