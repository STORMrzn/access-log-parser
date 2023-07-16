import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
        //String features=line.split("0",1)[0];
        //System.out.println(features);
        String localDateTime1=line.substring(line.indexOf("[")+1,line.indexOf("]"));
        //String[] localDateTime = localDateTime1.split(" \\+");
        String ll= localDateTime1.replaceAll("\\/", "-");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy:HH:mm:ss Z", Locale.US);
        LocalDateTime dateTime=LocalDateTime.parse(ll, formatter);
        //System.out.println(dateTime);
        String method=line.substring(line.indexOf("\"")+1, line.indexOf(" /"));
        //System.out.println(method);
        String splitStatusCode=line.substring(line.indexOf("\" ")+2);
        String[] splitStatusCodeToArray=splitStatusCode.split("\\s+");
        int responseCode = Integer.parseInt(splitStatusCodeToArray[0]);
        //System.out.println(statusCode); статус код
        int responseSize = Integer.parseInt(splitStatusCodeToArray[1]);
        //System.out.println(responseSize); байты
        String referer=splitStatusCodeToArray[2];
        referer = referer.substring(1, referer.length() - 1);
        //System.out.println(referer); путь к странице
        String[] splitToLast=splitStatusCode.split("\\s+\"");
        String splitUserAgent="\"" + splitToLast[2];

        UserAgent userAgentOfClients = new UserAgent(splitUserAgent);


        this.ipAddr = ipAddr;
        this.time = dateTime;
        this.method = MethodsHTTP.valueOf(method);
        this.referer = referer;
        this.responseCode = responseCode;
        this.responseSize = responseSize;
        this.splitUserAgent = splitUserAgent;
    }


}

