import org.apache.commons.cli.*;

import java.util.List;

public class Parameters {
    private String sortedType;
    private String dataType;
    private String outputFileName;
    private List<String> inputFilesNames;

    public Parameters(String[] args) {
        generateParameters(args);
    }

    private void generateParameters(String[] args) {
        Options options = new Options();
        generateOptions(options);
        CommandLineParser cmdParser = new DefaultParser();
        CommandLine cmdLine = getCmdLine(cmdParser, options, args);
        checkingArgsListSize(cmdLine);
        if (cmdLine != null) {
            setTypes(cmdLine);
            setFilenames(cmdLine);
        }
    }

    private void generateOptions(Options options) {
        OptionGroup sortedTypeGroup = new OptionGroup();
        sortedTypeGroup.addOption(new Option("a", false, "Ascending"));
        sortedTypeGroup.addOption(new Option("d", false, "Descending"));
        options.addOptionGroup(sortedTypeGroup);
        OptionGroup dataTypeGroup = new OptionGroup();
        dataTypeGroup.addOption(new Option("i", "Integer"));
        dataTypeGroup.addOption(new Option("s", "String"));
        dataTypeGroup.setRequired(true);
        options.addOptionGroup(dataTypeGroup);
    }

    private CommandLine getCmdLine(CommandLineParser cmdParser, Options options, String[] args) {
        CommandLine cmdLine = null;
        try {
            cmdLine = cmdParser.parse(options, args);
        } catch (MissingOptionException e) {
            System.out.println("Не был указан тип данных -i/-s");
            System.exit(1);
        } catch (UnrecognizedOptionException e) {
            System.out.println(e.getOption() + " неизвестный параметр");
            System.exit(1);
        } catch (ParseException e) {
            System.out.println("Параметры введены неверно");
            System.exit(1);
        }
        return cmdLine;
    }

    private void checkingArgsListSize(CommandLine cmdLine) {
        if (cmdLine != null) {
            if (cmdLine.getArgList().size() < 2) {
                System.out.println("Не было указано имя входного/выходного файла");
                System.exit(1);
            }
        }
    }

    private void setTypes(CommandLine cmdLine) {
        if (cmdLine.hasOption("d")) {
            sortedType = "Descending";
        } else {
            sortedType = "Ascending";
        }
        if (cmdLine.hasOption("i")) {
            dataType = "Integer";
        } else {
            dataType = "String";
        }
    }

    private void setFilenames(CommandLine cmdLine) {
        if (cmdLine.getArgList().size() > 1) {
            outputFileName = cmdLine.getArgList().get(0);
            cmdLine.getArgList().remove(0);
            inputFilesNames = cmdLine.getArgList();
        }
    }

    public String getSortedType() {
        return sortedType;
    }

    public String getDataType() {
        return dataType;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public List<String> getInputFilesNames() {
        return inputFilesNames;
    }
}
