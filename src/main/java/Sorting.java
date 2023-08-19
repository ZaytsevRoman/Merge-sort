import java.util.ArrayList;
import java.util.List;

public class Sorting {
    private String sortedType;
    private String dataType;
    private List<List<String>> inputFilesData;
    private List<String> resultData;

    public Sorting(FilesReader reader) {
        sortedType = reader.getSortedType();
        dataType = reader.getDataType();
        inputFilesData = reader.getCorrectInputFilesData();
    }

    public void mergeSort(FilesWriter writer) {
        while (inputFilesData.size() > 1) {
            int numberOfInputFiles = inputFilesData.size();
            resultData = new ArrayList<>();
            while (isStillDataInFiles(numberOfInputFiles)) {
                writeIntoResultData(numberOfInputFiles);
            }
            writeRemainingDataInSecondFile(numberOfInputFiles);
            writeRemainingDataInFirstFile(numberOfInputFiles);
            inputFilesData.set(numberOfInputFiles - 2, resultData);
            inputFilesData.remove(numberOfInputFiles - 1);
        }
        writer.writeOutputFile(inputFilesData.get(0));
        System.out.println("Сортировка слиянием закончена");
    }

    private boolean isStillDataInFiles(int numberOfInputFiles) {
        if (inputFilesData.get(numberOfInputFiles - 1).size() > 0
                && inputFilesData.get(numberOfInputFiles - 2).size() > 0) {
            return true;
        }
        return false;
    }

    private void writeIntoResultData(int numberOfInputFiles) {
        String firstValue = inputFilesData.get(numberOfInputFiles - 1).get(0);
        String secondValue = inputFilesData.get(numberOfInputFiles - 2).get(0);
        int compareResult = getCompareResult(firstValue, secondValue);
        if (sortedType.equals("Ascending")) {
            if (compareResult <= 0) {
                writeFirstValue(firstValue, numberOfInputFiles);
            } else {
                writeSecondValue(secondValue, numberOfInputFiles);
            }
        } else {
            if (compareResult >= 0) {
                writeFirstValue(firstValue, numberOfInputFiles);
            } else {
                writeSecondValue(secondValue, numberOfInputFiles);
            }
        }
    }

    private int getCompareResult(String firstValue, String secondValue) {
        if (dataType.equals("String")) {
            return firstValue.compareTo(secondValue);
        } else {
            return Integer.valueOf(firstValue).compareTo(Integer.valueOf(secondValue));
        }
    }

    private void writeFirstValue(String firstValue, int numberOfInputFiles) {
        resultData.add(firstValue);
        inputFilesData.get(numberOfInputFiles - 1).remove(0);
    }

    private void writeSecondValue(String secondValue, int numberOfInputFiles) {
        resultData.add(secondValue);
        inputFilesData.get(numberOfInputFiles - 2).remove(0);
    }

    private void writeRemainingDataInSecondFile(int numberOfInputFiles) {
        while (inputFilesData.get(numberOfInputFiles - 1).size() > 0) {
            resultData.add(inputFilesData.get(numberOfInputFiles - 1).get(0));
            inputFilesData.get(numberOfInputFiles - 1).remove(0);
        }
    }

    private void writeRemainingDataInFirstFile(int numberOfInputFiles) {
        while (inputFilesData.get(numberOfInputFiles - 2).size() > 0) {
            resultData.add(inputFilesData.get(numberOfInputFiles - 2).get(0));
            inputFilesData.get(numberOfInputFiles - 2).remove(0);
        }
    }
}
