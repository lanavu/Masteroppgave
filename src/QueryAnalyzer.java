import java.util.ArrayList;

/**
 * Created by lanam on 24.01.2017.
 *
 */
public class QueryAnalyzer {
    DBCollection dbc;

    private class Aggregation{
        ArrayList<PiplineEntry> pipeline = new ArrayList<>();


        Aggregation(){
            System.out.println("Query Analyzer");
            //Gjennomsnittlig innbyggertall i hver stat
            pipeline.add(new PiplineEntry(new String[]{"$city","$state"}));
            pipeline.add(new PiplineEntry(new String[]{"$state"}));
            //End gjennomsnittlig innbyggertall i hver stat
        }

        ArrayList<PiplineEntry> getPipeline(){
            return pipeline;
        }
    }

    private class PiplineEntry{
        //Begrenser til funksjonene under
        String [] group;

        PiplineEntry(String [] group){
            this.group = group;

        }
        public String[] getGroup(){
            return group;
        }

    }

    public QueryAnalyzer(DBCollection dbc){
        this.dbc = dbc;
        aggregateAnalyzer();

    }

    /**
     * Analyse av aggregater.
     * Nøkkelord: aggregate, group, sort
     *
     */
    public void aggregateAnalyzer(){
        ArrayList<PiplineEntry> pipe = new Aggregation().getPipeline();
        int counter = 1;
        for(PiplineEntry entry: pipe){
            //Group-delen
            System.out.println("--- " + counter++ + ". pipe entry ---");
            if(entry.getGroup().length > 1){
                System.out.print("No functional dependency between ");
                for(String s: entry.getGroup()) System.out.print(s+", ");
                System.out.println();
            }
            for(String s: entry.getGroup()){
                System.out.println(s + " is not unique in " + dbc.getName());
            }
        }

    }
}
