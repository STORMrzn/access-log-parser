import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int countFiles = 0;
        while (true) {
            String path;
            File file;
            boolean isItAFile = false;
            boolean isItADirectory = false;
            while ((!isItAFile) && (!isItADirectory)) {
                System.out.println("Введите адрес файла:");
                path = new Scanner(System.in).nextLine();
                file = new File(path);
                isItAFile = file.exists();
                if (isItAFile) {
                    isItADirectory = file.isDirectory();
                    if (isItADirectory) {
                        System.out.println("Указанный путь является путем к папке, а не к файлу");
                        isItAFile = false;
                        isItADirectory = false;
                    }
                }
                System.out.println("Файл не существует");
            }
            System.out.println("Путь указан верно");
            countFiles++;
            System.out.println("Это файл номер " + countFiles);
        }
    }
}