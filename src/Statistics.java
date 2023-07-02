import java.time.LocalDateTime;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private LogEntry le;

    public void addEntry(LogEntry le) {
        this.totalTraffic = Integer.parseInt(le.getResponseSize());
        this.minTime =le.getLocalDateTime();
        this.maxTime =le.getLocalDateTime();
        if (minTime > le.getLocalDateTime()) {minTime=le.getLocalDateTime();};
        if (maxTime < le.getLocalDateTime()) {maxTime=le.getLocalDateTime();};
        }
    }
/*
    public int getTrafficRate() {
        int difTimeInHours = maxTime-minTime;
        int trafficInHours=totalTraffic/difTimeInHours;
        return trafficInHours;
    }
}*/
