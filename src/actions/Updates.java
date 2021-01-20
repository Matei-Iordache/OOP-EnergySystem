package actions;

import fileio.ConsumerData;
import fileio.DistributorData;
import fileio.MonthlyUpdatesInputData;

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

//  /**
//   * Updates the cost changes of distributors if there are any cost changes. A cost change reffers
//   * to a price change in productionCost or in InfrastructureCost
//   *
//   * @param turnNumber of current turn
//   * @param monthlyUpdates list of monthly updates
//   * @param distributors list of distributors
//   */
//  public static void updateCostChanges(
//      final int turnNumber,
//      final ArrayList<MonthlyUpdatesInputData> monthlyUpdates,
//      final ArrayList<DistributorData> distributors) {
//
//    ArrayList<CostsChangesInputData> costChanges =
//        monthlyUpdates.get(turnNumber - 1).getCostsChanges();
//    for (CostsChangesInputData costChange : costChanges) {
//      for (DistributorData distributor : distributors) {
//        if (distributor.getId() == costChange.getId()) {
//          distributor.setInitialInfrastructureCost(costChange.getInfrastructureCost());
//          distributor.setInitialProductionCost(costChange.getProductionCost());
//        }
//      }
//    }
//  }

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
