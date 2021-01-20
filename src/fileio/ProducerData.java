package fileio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import entities.EnergyType;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(
        value = {
                "renewable",
                "distributors"
        })
@JsonPropertyOrder(
        value = {
                "id",
                "maxDistributors",
                "priceKW",
                "energyType",
                "energyPerDistributor",
                "isBankrupt",
                "contracts"
        }
)
public class ProducerData {
    private long id;
    private String energyType;
    private long maxDistributors;
    private Double priceKW;
    private long energyPerDistributor;
    private boolean renewable;
    private List<MonthlyStats> monthlyStats = new ArrayList<>();
    private ArrayList<DistributorData> distributors = new ArrayList<>();

    public ProducerData
            (final long id,
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

    public void setId(long id) {
        this.id = id;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(String energyType) {
        this.energyType = energyType;
    }

    public long getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(long maxDistributors) {
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

    public void setEnergyPerDistributor(long energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
    }

    public List<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(List<MonthlyStats> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    public ArrayList<DistributorData> getDistributors() {
        return distributors;
    }

    public void setDistributors(ArrayList<DistributorData> distributors) {
        this.distributors = distributors;
    }

    @Override
    public String toString() {
        return "ProducerData{" +
                "id=" + id +
                ", energyType='" + energyType + '\'' +
                ", maxDistributors=" + maxDistributors +
                ", priceKW=" + priceKW +
                ", energyPerDistributor=" + energyPerDistributor +
                ", renewable=" + renewable +
                ", monthlyStats=" + monthlyStats +
                '}';
    }
}
