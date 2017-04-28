import java.util.ArrayList;

/**
 * Created by lanam on 04.02.2017.
 */
public class DBMigration {
    DBCollection dbc;
    public DBMigration(String[] input){
        dbc = new ReadDocumentsToJson().readCollection(input[0]);

        System.out.println("--Direkte kartlegging--");
        DirectMapping dm = new DirectMapping(dbc.getDocumentByIndex(0));

        System.out.println("\n--Dynamisk analyse av data--");
        //new DynamicAnalyzer(dbc, dm.getListfields());

        System.out.println("---Statisk analyse av spørringer-");
        //new QueryAnalyzer(dbc);
    }

}
