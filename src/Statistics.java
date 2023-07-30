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

    private final HashMap<String, Double> fractionOS = new HashMap<>();
    private int osCounter;
    private int browserCounter;

    private final HashMap<String, Double> fractionBrowser = new HashMap<>();
    private int code4xxOr5xxCounter;
    private UserAgent ua;


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
            incorrectPages.add(le.getReferer());
        }

        //collections 1
        ua = le.getUserAgentOfClients();

        if (listOfOS.isEmpty()) {
            listOfOS.put("Windows", 0);
            listOfOS.put("Linux", 0);
            listOfOS.put("Mac", 0);
        };
            if (ua.getTypeOfOs() != null)
            {
                if (ua.getTypeOfOs().matches("Windows")) {
                listOfOS.put("Windows", listOfOS.get("Windows") + 1);
            }
                if (ua.getTypeOfOs().matches("Linux")) {
                listOfOS.put("Linux", listOfOS.get("Linux") + 1);
            }
                if (ua.getTypeOfOs().matches("Mac")) {
                listOfOS.put("Mac", listOfOS.get("Mac") + 1);
            }
                osCounter = listOfOS.get("Windows") + listOfOS.get("Linux") + listOfOS.get("Mac");
                fractionOS.put("Windows", (double) (listOfOS.get("Windows")/osCounter));
                fractionOS.put("Linux", (double) (listOfOS.get("Linux")/osCounter));
                fractionOS.put("Mac", (double) (listOfOS.get("Mac")/osCounter));
            }

        //Collections #2

        if (listOfBrowsers.isEmpty()) {
            listOfBrowsers.put("Firefox", 0);
            listOfBrowsers.put("Chrome", 0);
            listOfBrowsers.put("Opera", 0);
            listOfBrowsers.put("Edge", 0);
            listOfBrowsers.put("Safari", 0);
        };
        if (ua.getBrowser() != null)
        {
            if (ua.getBrowser().matches("Firefox")) {
                listOfBrowsers.put("Firefox", listOfBrowsers.get("Firefox") + 1);
            }
            if (ua.getBrowser().matches("Chrome")) {
                listOfBrowsers.put("Chrome", listOfBrowsers.get("Chrome") + 1);
            }
            if (ua.getBrowser().matches("Opera")) {
                listOfBrowsers.put("Opera", listOfBrowsers.get("Opera") + 1);
            }
            if (ua.getBrowser().matches("Edge")) {
                listOfBrowsers.put("Edge", listOfBrowsers.get("Edge") + 1);
            }
            if (ua.getBrowser().matches("Opera")) {
                listOfBrowsers.put("Safari", listOfBrowsers.get("Safari") + 1);
            }
        }
        browserCounter = listOfBrowsers.get("Firefox") + listOfBrowsers.get("Chrome") + listOfBrowsers.get("Opera") +
                listOfBrowsers.get("Edge") + listOfBrowsers.get("Safari");
        if (browserCounter != 0) {
            fractionBrowser.put("Firefox", (double) (listOfBrowsers.get("Firefox") / browserCounter));
            fractionBrowser.put("Chrome", (double) (listOfBrowsers.get("Chrome") / browserCounter));
            fractionBrowser.put("Opera", (double) (listOfBrowsers.get("Opera") / browserCounter));
            fractionBrowser.put("Edge", (double) (listOfBrowsers.get("Edge") / browserCounter));
            fractionBrowser.put("Safari", (double) (listOfBrowsers.get("Safari") / browserCounter));
        }

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
        double hours = ChronoUnit.HOURS.between(minTime, maxTime);
        double trafficRateInHour = realUsersCounter/hours;
        return trafficRateInHour;
    }

    public double getErrorCodeInHour (int code4xxOr5xxCounter, LocalDateTime minTime, LocalDateTime maxTime) {
        double hours = ChronoUnit.HOURS.between(minTime, maxTime);
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
    public HashMap<String, Double> getFractionOS() {
        return fractionOS;
    }

    public int getOsCounter() {
        return osCounter;
    }

    public HashSet<String> getIncorrectPages() {
        return incorrectPages;
    }

    public HashMap<String, Integer> getListOfBrowsers() {
        return listOfBrowsers;
    }

    public HashMap<String, Double> getFractionBrowser() {
        return fractionBrowser;
    }
}
