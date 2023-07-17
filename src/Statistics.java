import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private LogEntry le;
    private final HashSet<String> pages = new HashSet<String>();
    private final HashSet<String> incorrectPages = new HashSet<String>();
    private final HashMap<String, Integer> listOfOS = new HashMap<>();
    private final HashMap<String, Integer> listOfBrowsers = new HashMap<>();
    private int botCount;
    private long hours;
    private final HashSet<String> uniqueIp = new HashSet<String>();
    private final HashMap<Integer, Integer> visitsInSec = new HashMap<>();
    private final HashSet<String> refererList = new HashSet<>();
    private int maxVisitByOneUserCounter;
    private final HashMap<String, Integer> uniqueVisitOfUsersMap = new HashMap<>();

    private int code4xxOr5xxCounter;


    public void addEntry(LogEntry le) {
        this.totalTraffic = Integer.parseInt(String.valueOf(le.getResponseSize()));
        if (minTime == null || minTime.compareTo(le.getTime()) > 0) {
            minTime = le.getTime();
        }
        if (maxTime == null || maxTime.compareTo(le.getTime()) < 0) {
            maxTime = le.getTime();
        }
        this.minTime = minTime;
        this.maxTime = maxTime;

        if (le.getResponseCode() == 200 && !le.getReferer().equals("-")) {
            pages.add(le.getReferer());
        }

        if (le.getResponseCode() == 404 && !le.getReferer().equals("-")) {
            pages.add(le.getReferer());
        }

        //collections 1
        //if (getTypeOfOs().matches("Windows")) {listOfOS.put("Windows", listOfOS.get("Windows") + 1);}
        //else {listOfOS.put("Windows", 0);}
        //if (ua.getTypeOfOs().matches("Linux")) {listOfOS.put("Linux", listOfOS.get("Linux") + 1);}
        //else {listOfOS.put("Linux", 0);}
        //if (ua.getTypeOfOs().matches("Mac")) {listOfOS.put("Mac", listOfOS.get("Mac") + 1);}
        //else {listOfOS.put("Mac", 0);}

        //Stream #1
        if (400 < le.getResponseCode()) {
            code4xxOr5xxCounter++;
        }

        if (!uniqueIp.contains(le.getIpAddr())) {uniqueIp.add(le.getIpAddr());};

        //Stream #2
        if (visitsInSec.isEmpty()) {
                  for (int i = 0; i < 60; i++) {
              visitsInSec.put(i,0);
             };
        };
        if (!le.getSplitUserAgent().contains("bot") || !le.getSplitUserAgent().contains("Bot")) {
            visitsInSec.put(le.getTime().getSecond(), visitsInSec.get(le.getTime().getSecond())+1);
        }

        String refererWithoutProtocol = le.getReferer().substring(le.getReferer().indexOf(".")+1);
        String [] arrayReferer = refererWithoutProtocol.split("\\/");
        String domen = arrayReferer[0];
        refererList.add(domen);

        if (uniqueIp.contains(le.getIpAddr()) && (!le.getSplitUserAgent().contains("bot") || !le.getSplitUserAgent().contains("Bot"))) {
            if (!uniqueVisitOfUsersMap.containsKey(le.getIpAddr())) {
                uniqueVisitOfUsersMap.put(le.getIpAddr(), 1);
            }
            uniqueVisitOfUsersMap.put(le.getIpAddr(), uniqueVisitOfUsersMap.get(le.getIpAddr())+1);
        };
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }

    public double getTrafficRate(LocalDateTime minTime, LocalDateTime maxTime, long totalTraffic) {
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        this.hours = hours;
        double trafficRate = (double) totalTraffic / hours;
        return trafficRate;
    }

    public HashSet<String> getPages() {
        return pages;
    }

    public HashMap<String, Integer> getUserOS() {
        return listOfOS;
    }

    public int getCode4xxOr5xxCounter() {
        return code4xxOr5xxCounter;
    }

    //Stream #1
    public double getTrafficRateInHour (int userVisits, int botCount, LocalDateTime minTime, LocalDateTime maxTime) {
        int realUsersCounter = userVisits-botCount;
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        double trafficRateInHour = realUsersCounter/hours;
        return trafficRateInHour;
    }

    public double getErrorCodeInHour (int code4xxOr5xxCounter, LocalDateTime minTime, LocalDateTime maxTime) {
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        double errorCodeInHour = code4xxOr5xxCounter/code4xxOr5xxCounter;
        return errorCodeInHour;
    }

    public double getAverageVisits (int userVisits, int botCount) {
        int realUsersCounter = userVisits-botCount;
        double averageVisits = realUsersCounter/uniqueIp.size();
        return averageVisits;
    }

    public int getMaxVisitByOneUserCounter () {
        int max = uniqueVisitOfUsersMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()).get().getValue();

        System.out.println(uniqueVisitOfUsersMap);
        return max;
    }

    public HashMap<Integer, Integer> getVisitsInSec() {
        return visitsInSec;
    }

    public HashSet<String> getRefererList() {
        return refererList;
    }
}
