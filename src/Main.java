import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int countFiles = 0;
        String path;
        File file;
        boolean isItAFile;
        boolean isItADirectory;
        while (true) {
                System.out.println("Введите адрес файла:");
                path = new Scanner(System.in).nextLine();
                file = new File(path);
                isItAFile = file.exists();
                isItADirectory = file.isDirectory();
                if (!isItAFile){
                    System.out.println("Файл не существует");
                    continue;
                }
                if (isItADirectory) {
                    System.out.println("Указанный путь является путем к папке, а не к файлу");
                    continue;
                }
            System.out.println("Путь указан верно");
            countFiles++;
            System.out.println("Это файл номер " + countFiles);
        }
    }
}