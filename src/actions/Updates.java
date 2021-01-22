package actions;

import fileio.*;

import java.util.ArrayList;

/** Helper class which contains different update methods */
public final class Updates {

  private Updates() {

  }

  /**
   * Updates the list of consumers if there are any changes
   *
   * @param turnNumber of current turn
   * @param monthlyUpdates list of monthly updates
   * @param consumers list of consumers
   */
  public static void updateConsumers(
      final int turnNumber,
      final ArrayList<MonthlyUpdatesInputData> monthlyUpdates,
      final ArrayList<ConsumerData> consumers) {

    ArrayList<ConsumerData> newConsumer = monthlyUpdates.get(turnNumber - 1).getNewConsumers();
    consumers.addAll(newConsumer);
  }

  /**
   * Updates the required distributors if there are any changes
   *
   * @param turnNumber of current turn
   * @param monthlyUpdates list of monthly updates
   * @param distributors list of distributors
   */
  public static void updateDistributors(final int turnNumber,
                                        final ArrayList<MonthlyUpdatesInputData> monthlyUpdates,
                                        final ArrayList<DistributorData> distributors) {
    ArrayList<DistributorChanges> distributorChanges = monthlyUpdates.get(turnNumber - 1).getDistributorChanges();
    for (DistributorChanges distributorChange: distributorChanges) {
      distributors.forEach(
        distributor -> {
          if (distributor.getId() == distributorChange.getId()) {
            distributor.setInitialInfrastructureCost(distributorChange.getInfrastructureCost());
          }
        });
    }
  }

  /**
   * Updates the required producers if there are any changes
   *
   * @param turnNumber of current turn
   * @param monthlyUpdates list of monthly updates
   * @param producers list of producers
   */
  public static void updateProducers(final int turnNumber,
                                     final ArrayList<MonthlyUpdatesInputData> monthlyUpdates,
                                     final ArrayList<ProducerData> producers) {
    ArrayList<ProducerChanges> producerChanges = monthlyUpdates.get(turnNumber - 1).getProducerChanges();
    for (ProducerChanges producerChange: producerChanges) {
      producers.forEach(
              producer -> {
                if (producer.getId() == producerChange.getId()) {
                  producer.updateProducer(producerChange.getEnergyPerDistributor());
                }
              }
      );
    }
  }

  /**
   * Deletes users from the contract list of their distributor if their contract expired
   *
   * @param distributors list of distributors
   * @param consumers list of consumers
   */
  public static void updateDistributorContract(
      final ArrayList<DistributorData> distributors, final ArrayList<ConsumerData> consumers) {

    for (ConsumerData consumer : consumers) {
      if (consumer.getContractTime() == 0) {
        for (DistributorData distributor : distributors) {
          if (distributor.getId() == consumer.getDistributorId()) {
            distributor.getContracts().remove(consumer);
          }
        }
      }
    }
  }

  /**
   * Lower the contract time of all users at the end of the month
   *
   * @param consumers list of consumers
   */
  public static void updateConsumerContractTime(final ArrayList<ConsumerData> consumers) {

    for (ConsumerData consumer : consumers) {
      consumer.setContractTime(consumer.getContractTime() - 1);
      if (consumer.getContractTime() < 0) {
        consumer.setContractTime(0);
      }
    }
  }
}
