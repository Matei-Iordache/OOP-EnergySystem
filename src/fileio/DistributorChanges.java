package fileio;

public class DistributorChanges {
    public long id;
    public long infrastructureCost;

    public DistributorChanges(long id, long infrastructureCost) {
        this.id = id;
        this.infrastructureCost = infrastructureCost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(long infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    @Override
    public String toString() {
        return "DistributorChanges{" +
                "id=" + id +
                ", infrastructureCost=" + infrastructureCost +
                '}';
    }
}
