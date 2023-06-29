import java.io.*;
import java.time.Period;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                int yandexBotCount=0;
                int googleBotCount=0;
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
                    //user-agent
                    String ipUser=line.split(" ")[0];
                    //System.out.println(ipUser);//ip адрес выводится
                    // не работаетString features=line.split("0",1)[0];
                    //System.out.println(features);
                    String dateAndTime=line.substring(line.indexOf("[")+1,line.indexOf("]"));
                    //System.out.println(dateAndTime);
                    String method=line.substring(line.indexOf("\"")+1, line.indexOf(" /"));
                    //System.out.println(method);
                    String splitStatusCode=line.substring(line.indexOf("\" ")+2);
                    String[] splitStatusCodeToArray=splitStatusCode.split("\\s+");
                    String statusCode=splitStatusCodeToArray[0];
                    //System.out.println(statusCode); статус код
                    String volumeOfBytes=splitStatusCodeToArray[1];
                    //System.out.println(volumeOfBytes); байты
                    String pathOfVisits=splitStatusCodeToArray[2];
                    //System.out.println(pathOfVisits); путь к странице
                    String[] splitToLast=splitStatusCode.split("\\s+\"");
                    String splitUserAgent="\"" + splitToLast[2];
                    //System.out.println(splitUserAgent); userAgent

                    String[] slashSeparator = line.split("\"");
                    String userAgentSep=slashSeparator[slashSeparator.length-1];
                    int openParenPosition = userAgentSep.indexOf("(");
                    int closeParenPosition = userAgentSep.lastIndexOf(")");
                    System.out.println(userAgentSep);
                    String firstBrackets = userAgentSep.substring(openParenPosition+1);
                    System.out.println(firstBrackets);
                    //разделите эту часть по точке с запятой:
                    String[] parts = firstBrackets.split(";");
                    if (parts.length >= 2) {
                        String fragment = parts[1];
                        //очистьте от пробелов каждый получившийся фрагмент;
                        fragment=fragment.replaceAll("\\s","");
                        //возьмите второй фрагмент;
                        //отделите в этом фрагменте часть до слэша.
                        String[] withoutSlash=fragment.split("/");
                        String bot=withoutSlash[0];
                        System.out.println(bot);
                        //Определяя равенство найденного фрагмента строкам GoogleBot или YandexBot,
                        // подсчитывайте количество строк в файле, соответствующих запросам от данных ботов.
                        if (bot.matches("YandexBot")) {
                            yandexBotCount++;
                        }
                        if (bot.matches("Googlebot")) {
                            googleBotCount++;
                        }
                    }
                }
                    System.out.println("Количество строк в файле: " + lineNum);
                double yandexBotsRequestsFract=(double)yandexBotCount/lineNum;
                double googleBotsRequestsFract=(double)googleBotCount/lineNum;
                System.out.println("Доля запросов от Яндекс ботов: " + yandexBotsRequestsFract);
                System.out.println("Доля запросов от Гугл ботов: " + googleBotsRequestsFract);
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