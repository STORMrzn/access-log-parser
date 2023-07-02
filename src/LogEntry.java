import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry{
    private final String ipAddr;
    private final LocalDateTime time;
    private final MethodsHTTP method;
    private final String referer;
    private final int responseCode;
    private final int responseSize;
    private final String splitUserAgent;

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public MethodsHTTP getMethod(String method) {
        return this.method;
    }

    public String getReferer() {
        return referer;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getSplitUserAgent() {
        return splitUserAgent;
    }

    public LogEntry (String line) {
        //user-agent
        String ipAddr=line.split(" ")[0];
        //System.out.println(ipAddr);//ip адрес выводится
        // не работаетString features=line.split("0",1)[0];
        //System.out.println(features);
        //LocalDateTime dateTime = LocalDateTime.parse(line.substring(line.indexOf("[")+1,line.indexOf("]"));
        DateTimeFormatter.ofPattern("[dd/mmm/yyyy:hh:mm:ss]"); //погуглил- вроде бы тайм зону не принимает парсер. может и не надо если использоваться будет в 1 тайм зоне?
        LocalDateTime time=LocalDateTime.parse(line.substring(line.indexOf("[")+1,line.indexOf("]")));
        System.out.println("123132131321321  " +time);


        //System.out.println(LocalDateTime);
        String method=line.substring(line.indexOf("\"")+1, line.indexOf(" /"));
        //System.out.println(method);
        String splitStatusCode=line.substring(line.indexOf("\" ")+2);
        String[] splitStatusCodeToArray=splitStatusCode.split("\\s+");
        int responseCode = Integer.parseInt(splitStatusCodeToArray[0]);
        //System.out.println(statusCode); статус код
        int responseSize = Integer.parseInt(splitStatusCodeToArray[1]);
        //System.out.println(responseSize); байты
        String referer=splitStatusCodeToArray[2];
        //System.out.println(referer); путь к странице
        String[] splitToLast=splitStatusCode.split("\\s+\"");
        String splitUserAgent="\"" + splitToLast[2];
        //System.out.println(splitUserAgent); userAgent


        this.ipAddr = ipAddr;
        this.time = time;
        this.method = MethodsHTTP.valueOf(method); //застрял на енаме. как я понимаю - хочу отправить стринг, получить объект класса MethodsHTTP
        this.referer = referer;
        this.responseCode = responseCode;
        this.responseSize = responseSize;
        this.splitUserAgent = splitUserAgent;
    }


}

