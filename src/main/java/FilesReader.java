import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FilesReader {
    private String sortedType;
    private String dataType;
    private List<String> inputFilesNames;
    private List<List<String>> correctInputFilesData;
    private Scanner scanner;

    public FilesReader(Parameters parameters) {
        sortedType = parameters.getSortedType();
        dataType = parameters.getDataType();
        correctInputFilesData = new ArrayList<>();
        inputFilesNames = getInputFilesNames(parameters.getInputFilesNames());
        correctInputFilesData = getCorrectInputFilesData(inputFilesNames);
    }

    private List<String> getInputFilesNames(List<String> inputFilesNames) {
        for (int i = 0; i < inputFilesNames.size(); ) {
            String inputFileName = inputFilesNames.get(i);
            File inputFile = new File(inputFileName);
            if (inputFile.exists()) {
                if (inputFile.canRead()) {
                    System.out.println("Входной файл: " + inputFileName + " - прочитан");
                    i++;
                } else {
                    System.out.println("Входной файл: " + inputFileName + " - не может быть прочитан");
                    inputFilesNames.remove(i);
                }
            } else {
                System.out.println("Входной файл: " + inputFileName + " не найден");
                inputFilesNames.remove(i);
            }
        }
        if (inputFilesNames.size() < 1) {
            System.out.println("Отсутствуют входные файлы для сортировки");
            System.exit(2);
        }
        return inputFilesNames;
    }

    private List<List<String>> getCorrectInputFilesData(List<String> inputFilesNames) {
        for (String fileName : inputFilesNames) {
            List<String> correctLinesInFile = new ArrayList<>();
            try {
                FileInputStream inputStream = new FileInputStream(fileName);
                scanner = new Scanner(inputStream);
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (searchIncorrectLines(line, fileName)) {
                    continue;
                }
                correctLinesInFile.add(line);
            }
            checkingCorrectnessSortingLines(correctLinesInFile, fileName);
            correctInputFilesData.add(correctLinesInFile);
        }
        return correctInputFilesData;
    }

    private boolean searchIncorrectLines(String line, String fileName) {
        if (line == null) {
            System.out.println("Данные в файле: " + fileName + " - отсутствуют");
            return true;
        }
        if (dataType.equals("String")) {
            if (!line.matches("[А-я A-z]+")) {
                System.out.println("Неккоректный символ: " + "\"" + line + "\"" +
                        " в файле: " + fileName + " не будет учавствовать в сортировке");
                return true;
            }
        } else {
            try {
                Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Нечисловой символ: " + "\"" + line + "\"" +
                        " в файле: " + fileName + " не будет учавствовать в сортировке");
                return true;
            }
        }
        return false;
    }

    private void checkingCorrectnessSortingLines(List<String> correctLinesInFile, String fileName) {
        if (correctLinesInFile.size() < 2) return;
        for (int lineNumber = 0; lineNumber < correctLinesInFile.size() - 1; lineNumber++) {
            if (lineCompare(correctLinesInFile, lineNumber)) {
                System.out.println("Данные не отсортированы или отсартированы неверно, файл: "
                        + fileName + " не будет участвовать в сортировке");
                while (lineNumber < correctLinesInFile.size()) {
                    correctLinesInFile.remove(lineNumber);
                }
                return;
            }
        }
    }

    private boolean lineCompare(List<String> correctLinesInFile, int lineNumber) {
        int compareResult;
        if (dataType.equals("String")) {
            compareResult = correctLinesInFile.get(lineNumber).compareTo(correctLinesInFile.get(lineNumber + 1));
        } else {
            compareResult = Integer.valueOf(correctLinesInFile.get(lineNumber))
                    .compareTo(Integer.valueOf(correctLinesInFile.get(lineNumber + 1)));
        }
        if (sortedType.equals("Ascending")) {
            if (compareResult > 0) return true;
        } else {
            if (compareResult < 0) return true;
        }
        return false;
    }

    public String getSortedType() {
        return sortedType;
    }

    public String getDataType() {
        return dataType;
    }

    public List<List<String>> getCorrectInputFilesData() {
        return correctInputFilesData;
    }
}
