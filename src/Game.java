import actions.Commands;
import actions.Updates;
import fileio.ConsumerData;
import fileio.DistributorData;
import fileio.MonthlyUpdatesInputData;
import fileio.ProducerData;

import java.util.ArrayList;

/** Performs the game */
public class Game {

  private final ArrayList<ConsumerData> consumers;
  private final ArrayList<DistributorData> distributors;
  private final ArrayList<ProducerData> producers;
  private final ArrayList<MonthlyUpdatesInputData> monthlyUpdates;

  public Game(
          final ArrayList<ConsumerData> consumers,
          final ArrayList<DistributorData> distributors,
          ArrayList<ProducerData> producers, final ArrayList<MonthlyUpdatesInputData> monthlyUpdates) {
    this.consumers = consumers;
    this.distributors = distributors;
    this.producers = producers;
    this.monthlyUpdates = monthlyUpdates;
  }

  /**
   * Execute the game for a total of numberTurns times
   *
   * @param numberTurns of the game
   */
  public void execute(final long numberTurns) {
    for (int i = 0; i <= numberTurns; i++) {
      if (i > 0) {
        Updates.updateConsumers(i, monthlyUpdates, consumers);
      }

      if (i == 0) {
        Commands.chooseProducers(distributors, producers);
        Commands.calculateProductionCost(distributors, producers);
      }

      Commands.calculateProfit(distributors);

      Commands.calculateContractCost(distributors);

      Updates.updateDistributorContract(distributors, consumers);

      Commands.chooseDistributors(distributors, consumers);

      Commands.consumerNewBudget(consumers);

      Commands.distributorNewBudget(distributors);

      Updates.updateConsumerContractTime(consumers);

      if (i != 0) {
        Commands.chooseProducers(distributors, producers);
        Commands.calculateProductionCost(distributors,producers);
        Commands.setMonthlyStats(producers, i);
      }

//      System.out.println(consumers);
//      System.out.println(distributors);
//      System.out.println(producers);
//      System.out.println("-----------------------------------------------\n\n");
    }
  }
}
