package configuration;

import org.apache.commons.cli.*;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    public static final String InputMesh = "i";
    public static final String OutputMesh = "o";
    public static final String Mode = "mode";
    public static final String Shape = "shape";
    public static final String Altitude = "altitude";
    public static final String Lakes = "lakes";
    public static final String Aquifers = "aquifers";
     public static final String HELP = "help";
    public static final String SOIL = "soil";
    public static final String BIOME = "biome";
    public static final String CITY = "city";


    private CommandLine cli;

    public Configuration(String[] args) {
        try {
            this.cli = parser().parse(options(), args);
            if (cli.hasOption(HELP)) {
                help();
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar Island.jar", options());
        System.exit(0);
    }

    public Map<String, String> export() {
        Map<String, String> result = new HashMap<>();
        for(Option o: cli.getOptions()){
            result.put(o.getOpt(), o.getValue(""));
        }
        return result;
    }

    private CommandLineParser parser() {return new DefaultParser();}
    public String inputMesh() {
        return this.cli.getOptionValue(InputMesh);
    }
    public String outputMesh() {
        return this.cli.getOptionValue(OutputMesh);
    }
    public String mode() {
        return this.cli.getOptionValue(Mode);
    }
    public String shape() {
        return this.cli.getOptionValue(Shape);
    }
    public String soil() {return this.cli.getOptionValue(SOIL);}
    public String biome() {return this.cli.getOptionValue(BIOME);}
    public String altitude() {
        return this.cli.getOptionValue(Altitude);
    }
    public boolean modeIsLagoon(){
        if (this.cli.getOptionValue(Mode).equals("lagoon")) return true;
        else return false;
    }
    public int numAquifers()
    {
        try { return Integer.parseInt(this.cli.getOptionValue(Aquifers)); }
        catch (Exception e) { return 0;} //aquifer input is not an int
    }

    public int numLakes() {
        try { return Integer.parseInt(this.cli.getOptionValue(Lakes)); }
        catch (Exception e) { return 0;} //aquifer input is not an int
    }
    public int numCities() {
        try { return Integer.parseInt(this.cli.getOptionValue(CITY)); }
        catch (Exception e) { return 0;}} //aquifer input is not an int

    private Options options() {
        Options options = new Options();
        options.addOption(Option.builder().longOpt(Mode).desc("Island mesh mode (options are 'lagoon' or 'regular', anything else results in regular by default)").hasArg(true).build());
        options.addOption(Option.builder().longOpt(Shape).desc("Island base shape (options are 'circle' or 'rectangle', anything else results in circle by default)").hasArg(true).build());
        options.addOption(Option.builder().longOpt(Altitude).desc("Island elevation type (options are 'plateau' or 'flat' or 'steep', anything else results in flat by default)").hasArg(true).build());
        options.addOption(Option.builder().longOpt(Lakes).desc("Number of Island Lakes (input number of lakes, example '5')").hasArg(true).build());
        options.addOption(Option.builder().longOpt(Aquifers).desc("Number of Island Aquifers (input number of aquifers, example '5')\"").hasArg(true).build());
        options.addOption(Option.builder().longOpt(SOIL).desc("Soil Absorption (options are 'quick' or 'medium' or 'slow', anything else defaults to medium)").hasArg(true).build());
        options.addOption(Option.builder().longOpt(BIOME).desc("Biome type (options are 'rocky' or 'arctic' or 'grassland', anything else is grassland by default)").hasArg(true).build());
        options.addOption(Option.builder().longOpt(CITY).desc("Biome type (options are 'rocky' or 'arctic' or 'grassland', anything else is grassland by default)").hasArg(true).build());

        options.addOption(new Option(InputMesh, true, "Input file name"));
        options.addOption(new Option(OutputMesh, true, "Output file name"));

        // Global help
        options.addOption(new Option(HELP, false, "print help message"));
        return options;
    }
}