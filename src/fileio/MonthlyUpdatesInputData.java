package fileio;

import java.util.ArrayList;

/** Put the monthly updates from the input loader here */
public final class MonthlyUpdatesInputData {

  private final ArrayList<ConsumerData> newConsumers;
  private final ArrayList<DistributorChanges> distributorChanges;
  private final ArrayList<ProducerChanges> producerChanges;

  public MonthlyUpdatesInputData(
          final ArrayList<ConsumerData> newConsumers,
          final ArrayList<DistributorChanges> distributorChanges,
          final ArrayList<ProducerChanges> producerChanges) {
    this.newConsumers = newConsumers;
    this.distributorChanges = distributorChanges;
    this.producerChanges = producerChanges;
  }

  public ArrayList<ConsumerData> getNewConsumers() {
    return newConsumers;
  }

  public ArrayList<DistributorChanges> getDistributorChanges() {
    return distributorChanges;
  }

  public ArrayList<ProducerChanges> getProducerChanges() {
    return producerChanges;
  }

  @Override
  public String toString() {
    return "MonthlyUpdatesInputData{"
            + "newConsumers=" + newConsumers
            + ", distributorChanges=" + distributorChanges
            + ", producerChanges=" + producerChanges
            + '}' + "\n";
  }
}
