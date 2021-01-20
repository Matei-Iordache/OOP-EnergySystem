package fileio;

import java.util.ArrayList;

/** Store the input of the json files */
public final class Input {

  private final ArrayList<ConsumerData> consumers;

  private final ArrayList<DistributorData> distributors;

  private final ArrayList<ProducerData> producers;

  private final ArrayList<MonthlyUpdatesInputData> monthlyUpdates;

  private final long numberOfTurns;

  public Input(
      final ArrayList<ConsumerData> consumers,
      final ArrayList<DistributorData> distributors,
      final ArrayList<ProducerData> producers,
      final ArrayList<MonthlyUpdatesInputData> monthlyUpdates,
      final long numberOfTurns) {
    this.consumers = consumers;
    this.distributors = distributors;
    this.producers = producers;
    this.monthlyUpdates = monthlyUpdates;
    this.numberOfTurns = numberOfTurns;
  }

  public ArrayList<ConsumerData> getConsumers() {
    return consumers;
  }

  public ArrayList<DistributorData> getDistributors() {
    return distributors;
  }

  public ArrayList<ProducerData> getProducers() {
    return producers;
  }

  public ArrayList<MonthlyUpdatesInputData> getMonthlyUpdates() {
    return monthlyUpdates;
  }

  public long getNumberOfTurns() {
    return numberOfTurns;
  }

}
