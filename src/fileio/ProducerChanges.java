package fileio;

/**
 * Stores the producer changes in a month
 */
public final class ProducerChanges {
    private long id;
    private long energyPerDistributor;

    public ProducerChanges(final long id,
                           final long energyPerDistributor) {
        this.id = id;
        this.energyPerDistributor = energyPerDistributor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(long energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    @Override
    public String toString() {
        return "ProducerChanges{"
                + "id=" + id
                + ", energyPerDistributor=" + energyPerDistributor
                + '}';
    }
}
