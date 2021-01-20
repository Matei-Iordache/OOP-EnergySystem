package fileio;

import java.util.List;

public class MonthlyStats {
    private int month;
    private final List<Long> distributorsIds;

    public MonthlyStats(int month, List<Long> distributorsIds) {
        this.month = month;
        this.distributorsIds = distributorsIds;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Long> getDistributorsIds() {
        return distributorsIds;
    }

    @Override
    public String toString() {
        return "MonthlyStats{" +
                "month=" + month +
                ", distributorsIds=" + distributorsIds +
                '}';
    }
}
