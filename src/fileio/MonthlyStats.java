package fileio;

import java.util.List;

public final class MonthlyStats {
    private int month;
    private final List<Long> distributorsIds;

    public MonthlyStats(final int month,
                        final List<Long> distributorsIds) {
        this.month = month;
        this.distributorsIds = distributorsIds;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(final int month) {
        this.month = month;
    }

    public List<Long> getDistributorsIds() {
        return distributorsIds;
    }

    @Override
    public String toString() {
        return "MonthlyStats{"
                + "month=" + month
                + ", distributorsIds=" + distributorsIds
                + '}';
    }
}
