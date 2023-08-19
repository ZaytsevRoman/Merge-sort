
public class Main {

    public static void main(String[] args) {
        Parameters parameters = new Parameters(args);
        Sorting sorting = new Sorting(new FilesReader(parameters));
        sorting.mergeSort(new FilesWriter(parameters));
    }
}
