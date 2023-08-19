import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class FilesWriter {
    private String outputFileName;
    private String charset = "windows-1251";

    public FilesWriter(Parameters parameters) {
        outputFileName = parameters.getOutputFileName();
    }

    public void writeOutputFile(List<String> sortedData) {
        File outputFile = createOutputFile(outputFileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            for (String line : sortedData) {
                fileOutputStream.write(line.getBytes(Charset.forName(charset)), 0, line.length());
                fileOutputStream.write("\n".getBytes());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Не удалось произвести запись");
        }
    }

    private File createOutputFile(String outputFileName) {
        File outputFile = new File(outputFileName);
        try {
            if (outputFile.createNewFile()) {
                System.out.println("Выходной файл: " + outputFileName + " - будет создан");
            } else {
                System.out.println("Выходной файл: " + outputFileName + " - будет перезаписан");
                if (!outputFile.canWrite()) {
                    System.out.println("Выходной файл: " + outputFileName + " - невозможно перезаписать, " +
                            "доступ к файлу только для чтения");
                    System.exit(2);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла");
        }
        return outputFile;
    }
}
