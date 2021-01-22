package fileio;

/**
 * Stores the distributor changes in a month
 */
public final class DistributorChanges {
    private long id;
    private long infrastructureCost;

    public DistributorChanges(final long id,
                              final long infrastructureCost) {
        this.id = id;
        this.infrastructureCost = infrastructureCost;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final long infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    @Override
    public String toString() {
        return "DistributorChanges{"
                + "id=" + id
                + ", infrastructureCost=" + infrastructureCost
                + '}';
    }
}
