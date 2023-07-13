import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private LogEntry le;
    private HashSet<String> pages;
    //private HashSet<String, Integer> listOfOS;
    private int countUserOS;

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
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }

    public double getTrafficRate (LocalDateTime minTime, LocalDateTime maxTime, long totalTraffic) {
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        double trafficRate = (double) totalTraffic/hours;
        return trafficRate;
    }
}
/*
    //Collections #1
    public void getPages(LogEntry le) {
        if (le.getResponseCode() == 200) {
            pages.add(le.getReferer());
        }
    }

 /*   public void getUserOS(UserAgent userAgent) {
        {
            if (listOfOS.contains(getUserOS())) {
                HashSet<Integer> newhashSet=listOfOS.getClass(userAgent);
                if(newhashSet!=null)
                {
                    newhashSet.add(countUserOS++);
                }
                listOfOS.add(userAgent.getTypeOfOs()), newhashSet);
            }
            else {
                Set<Integer> ids = new HashSet<Integer>();
                ids.add(countUserOS++);
                listOfOS.add(userAgent.getTypeOfOs()), (HashSet<Integer>) ids);
            }
            //HashMap<String, Double> getPercentOfUserOS= new HashMap<>();
            //getPercentOfUserOS.put (userAgent.getTypeOfOs(),double);
        }
        } //return getPercentOfUserOS;
    }
// +аналогичный класс для 404

    //Stream #1
*/

/*}


//
/*
    public int getTrafficRate() {
        int difTimeInHours = maxTime-minTime;
        int trafficInHours=totalTraffic/difTimeInHours;
        return trafficInHours;
    }
}*/
