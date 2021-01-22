package fileio;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public final class ProducerData extends Observable {
    private long id;
    private String energyType;
    private long maxDistributors;
    private Double priceKW;
    private long energyPerDistributor;
    private boolean renewable;
    private List<MonthlyStats> monthlyStats = new ArrayList<>();
    private ArrayList<DistributorData> distributors = new ArrayList<>();

    public ProducerData(
             final long id,
             final String energyType,
             final long maxDistributors,
             final Double priceKW,
             final long energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(final String energyType) {
        this.energyType = energyType;
    }

    public long getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(final long maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public Double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(Double priceKW) {
        this.priceKW = priceKW;
    }

    public long getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(final long energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(final boolean renewable) {
        this.renewable = renewable;
    }

    public List<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(final List<MonthlyStats> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    public ArrayList<DistributorData> getDistributors() {
        return distributors;
    }

    public void setDistributors(final ArrayList<DistributorData> distributors) {
        this.distributors = distributors;
    }

    /**
     * Update the producer and notify his observers
     * @param energyPerDistributor energyPerDistributor
     */
    public void updateProducer(long energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "ProducerData{"
                + "id=" + id
                + ", energyType='" + energyType + '\''
                + ", maxDistributors=" + maxDistributors
                + ", priceKW=" + priceKW
                + ", energyPerDistributor=" + energyPerDistributor
                + ", renewable=" + renewable
                + ", monthlyStats=" + monthlyStats
                + '}';
    }
}
