import java.util.ArrayList;

/**
 * Created by Lana on 25.01.2017.
 */
public class DBCollection {
    String name;
    ArrayList<Document> documents;

    public DBCollection(String name, ArrayList<Document> documents){
        this.name = name;
        this.documents = documents;
    }

    /**
     * Henter ut navnet på samlingen
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Henter ut ArrayList med alle dokumentene
     * @return documents
     */
    public ArrayList<Document> getDocuments(){
        return documents;
    }

    /**
     * Henter ut et dokument etter index i listen
     * @param index
     * @return Document
     */
    public Document getDocumentByIndex(int index){
        if(index < documents.size()) {
            return documents.get(index);
        }
        else{
            System.out.println("Index not found");
            return null;
        }
    }
    public int getSize(){
        return documents.size();
    }



}
