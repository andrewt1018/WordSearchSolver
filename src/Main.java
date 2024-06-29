import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("funny");
        inputs.add("cow");
        inputs.add("factual");
        inputs.add("focus");
        inputs.add("fertile");
        inputs.add("fruition");
        inputs.add("flamingo");
        Gridmaker grid = new Gridmaker(inputs);
        System.out.println(grid);
        WordSearcher searcher = new WordSearcher(grid);
        System.out.println(searcher.searchWords());
    }

}
