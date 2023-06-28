import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exceptions {
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
            if (!isItAFile) {
                System.out.println("Файл не существует");
                continue;
            }
            if (isItADirectory) {
                System.out.println("Указанный путь является путем к папке, а не к файлу");
                continue;
            }

            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                int lineNum = 0;
                int longLine = 0;
                int shortLine = 0;
                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    try {
                    if (line.length() < 1024) {
                            shortLine = length;
                            lineNum++;
                            if (shortLine > length) {
                                shortLine = length;
                            };
                            if (longLine < length) {
                                longLine = length;
                            };
                        } else {
                        throw new RuntimeException();
                    }
                        } catch (RuntimeException rte) {
                        //catch (Exteptions exc) {
                        //exc.printStackTrace() - пытался сделать исключение в классе, но какие-то проблемы
                        System.out.println("Какая-то линия содержит более 1024 символов и была пропущена " + rte);
                        //System.exit(0);
                    }
                }
                    System.out.println("Количество строк в файле: " + lineNum);
                    System.out.println("Длина самой длинной строки в файле: " + longLine);
                    System.out.println("Длина самой короткой строки в файле: " + shortLine);
                    System.out.println("Путь указан верно");
                    countFiles++;
                    System.out.println("Это файл номер " + countFiles);
            } catch (FileNotFoundException exc) {
                System.out.println("Фаил не найден " + exc);
                System.exit(0);
            } catch (IOException exx) {
                System.out.println("Ошибка ввода данных " + exx);
            }
        }
    }
}