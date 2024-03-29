import java.io.*;
import java.time.LocalDateTime;
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
                int yandexBotCount = 0;
                int googleBotCount = 0;
                long totalTraffic = 0;
                int botCount = 0;
                Statistics stat = new Statistics();
                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    try {
                    if (line.length() < 1024) {
                            shortLine = length;
                            lineNum++;
                            if (shortLine > length) {
                                shortLine = length;
                            }
                            if (longLine < length) {
                                longLine = length;
                            };
                        } else {
                        throw new RuntimeException();
                    }
                        } catch (RuntimeException rte) {
                       // catch (Exteptions exc) {
                       // exc.printStackTrace()// - пытался сделать исключение в классе, но какие-то проблемы
                        System.out.println("Какая-то линия содержит более 1024 символов и была пропущена " + rte);
                        //System.exit(0);
                    }

                    LogEntry le = new LogEntry(line);
                    totalTraffic += le.getResponseSize();
                    LocalDateTime minTime = null;
                    LocalDateTime maxTime = null;

                    stat.addEntry(le);

                    String[] slashSeparator = line.split("\"");
                    String userAgentSep=slashSeparator[slashSeparator.length-1];
                    int openParenPosition = userAgentSep.indexOf("(");
                    int closeParenPosition = userAgentSep.lastIndexOf(")");
                    //System.out.println(userAgentSep);
                    String firstBrackets = userAgentSep.substring(openParenPosition+1);
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
                        if (bot.contains("Bot") || bot.contains("bot")) {
                            botCount++;
                        };
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

                System.out.println("Collections #1  возвращает успешные страницы сайта" + stat.getPages());
                System.out.println("Collections #1  " + stat.getUserOS());
                //ниже не работает - выводит нули.
                System.out.println(stat.getOsCounter());
                System.out.println("Collections #1  Метод рассчитывает долю для каждой операционной системы (от 0 до 1)" +
                        stat.getFractionOS());

                System.out.println("Collections #2  Метод, который возвращает список всех несуществующих страниц сайта" +
                        stat.getIncorrectPages());
                System.out.println("Collections #2  возвращает частоту встречаемости браузеров пользователей" +
                        stat.getListOfBrowsers());
                //Доли браузеров как и в коллекшен 1 не рассчитываются...
                System.out.println("Collections #2  доли браузеров " + stat.getFractionBrowser());

                System.out.println("Stream #1 - среднее кол-во посещей за час без ботов: " +
                        stat.getTrafficRateInHour(lineNum, botCount, stat.getMinTime(), stat.getMaxTime()));
                System.out.println("Stream #1 - подсчет количества ошибочных запросов в час: " +
                        stat.getErrorCodeInHour(stat.getCode4xxOr5xxCounter(), stat.getMinTime(), stat.getMaxTime()));
                System.out.println("Stream #1 - подсчет средней посещаемости одним пользователем: " +
                        stat.getAverageVisits(lineNum,botCount));

                System.out.println("Stream #2 - подсчет пиковой посещаемости сайта (в секунду)" +
                        stat.getVisitsInSec());
                System.out.println("Stream #2 - список сайтов, со страниц которых есть ссылки на текущий сайт " +
                        stat.getRefererList()); //не идеальный список. может быть есть какие-то битые адреса ? не придумал как их выявить...
                System.out.println("Stream #2 - расчёт максимальной посещаемости одним пользователем " +
                        stat.getMaxVisitByOneUserCounter()); //вроде бы по условиям задания ботом являются только те, у кого казано что они бот. а тут у этого ИП адреса не указано ничего

                System.out.println("Всего трафика: " + totalTraffic);
                System.out.println("minTime=  " + stat.getMinTime());
                System.out.println("maxTime=  " + stat.getMaxTime());
                System.out.println("трафик рейт "+ stat.getTrafficRate(stat.getMinTime(), stat.getMaxTime(), totalTraffic));
                    System.out.println("Количество строк в файле: " + lineNum);
                System.out.println("Длина самой длинной строки в файле: " + longLine);
                System.out.println("Длина самой короткой строки в файле: " + shortLine);


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