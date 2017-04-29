import java.util.ArrayList;

/**
 * Created by lanam on 24.01.2017.
 *
 */
public class QueryAnalyzer {
    DBCollection dbc;

    private class Aggregation{
        ArrayList<PipelineEntry> pipeline = new ArrayList<>();


        Aggregation(){
            System.out.println("Query Analyzer");
            //Gjennomsnittlig innbyggertall i hver stat
            pipeline.add(new PipelineEntry(new String[]{"$city","$state"}));
            pipeline.add(new PipelineEntry(new String[]{"$state"}));
            //End gjennomsnittlig innbyggertall i hver stat
        }

        ArrayList<PipelineEntry> getPipeline(){
            return pipeline;
        }
    }

    private class PipelineEntry {
        //Begrenser til funksjonene under
        String [] group;

        PipelineEntry(String [] group){
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
        ArrayList<PipelineEntry> pipe = new Aggregation().getPipeline();
        int counter = 1;
        for(PipelineEntry entry: pipe){
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
