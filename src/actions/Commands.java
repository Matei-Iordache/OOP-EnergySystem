package actions;

import fileio.ConsumerData;
import fileio.DistributorData;
import fileio.MonthlyStats;
import fileio.ProducerData;
import strategies.Context;
import strategies.greenStrategy;
import strategies.priceStrategy;
import strategies.quantityStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Class with different helper methods */
public final class Commands {

  private Commands() {

  }

  private static final int MAX_COST = 1000000;
  private static final double PROFIT_CONSTANT = 0.2;
  private static final double INTEREST_RATE = 1.2;

  /**
   * Calculate the profit of all distributors with the formula in the documentation
   *
   * @param distributors List of distributors
   */
  public static void calculateProfit(final ArrayList<DistributorData> distributors) {
    for (DistributorData distributor : distributors) {
      distributor.setProfit((long) (PROFIT_CONSTANT * distributor.getInitialProductionCost()));
    }
  }

  /**
   * Calculate the contract cost of the distributors
   *
   * @param distributors list of distributors
   */
  public static void calculateContractCost(final ArrayList<DistributorData> distributors) {
    for (DistributorData distributor : distributors) {
      if (distributor.getContracts().size() == 0) {
        distributor.setCostContract(
            distributor.getInitialInfrastructureCost()
                + distributor.getInitialProductionCost()
                + distributor.getProfit());
      } else {
        distributor.setCostContract(
            distributor.getInitialInfrastructureCost() / distributor.getContracts().size()
                + distributor.getInitialProductionCost()
                + distributor.getProfit());
      }
    }
  }

  /**
   * Choose where the consumers will subscribe to
   *
   * @param distributors list of distributors
   * @param consumers list of consumers
   */
  public static void chooseDistributors(
      final ArrayList<DistributorData> distributors, final ArrayList<ConsumerData> consumers) {
    DistributorData cheapest = new DistributorData(2, 2, 2, 2, 2, "GREEN");
    cheapest.setCostContract(MAX_COST);

    // Get the cheapest distributor for the round
    for (DistributorData distrbutor : distributors) {
      if (distrbutor.getCostContract() < cheapest.getCostContract()
          && !distrbutor.getIsBankrupt()) {
        cheapest = distrbutor;
      }
    }

    // Subscribe the consumers to the cheapest distributor if they have no contract
    for (ConsumerData consumer : consumers) {
      if (consumer.getContractTime() == 0 && !consumer.getIsBankrupt()) {
        cheapest.getContracts().add(consumer);
        consumer.setContractTime(cheapest.getContractLength());
        consumer.setContractCost(cheapest.getCostContract());
        consumer.setDistributorId(cheapest.getId());
      }
    }
  }

  /**
   * Calculate the new budget for all the consumers except for the ones that are bankrupt
   *
   * @param consumers list of consumers
   */
  public static void consumerNewBudget(final ArrayList<ConsumerData> consumers) {
    for (ConsumerData consumer : consumers) {
      if (!consumer.getIsBankrupt()) {

        long budget = consumer.getBudget();
        long monthlyIncome = consumer.getMonthlyIncome();
        long almostBankruptBill =
            Math.round(Math.floor(INTEREST_RATE * consumer.getDatorie()))
            + consumer.getContractCost();
        long bill = consumer.getContractCost();

        if (consumer.isAlmostBankrupt()) {
          consumer.setBudget(budget + monthlyIncome - almostBankruptBill);
        } else {
          consumer.setBudget(budget + monthlyIncome - bill);
        }

        /*
        if a consumer has a negative budget after applying the bill it means he cannot pay his bill.
        Add his monthly income and
        */
        if (consumer.getBudget() < 0) {
          consumer.setBudget(budget + monthlyIncome);
          consumer.setNegative(true);
        }
      }
    }
  }

  /**
   * Calculate the new budget of all distributors at the end of the month
   *
   * @param distributors list of distributors
   */
  public static void distributorNewBudget(final ArrayList<DistributorData> distributors) {
    for (DistributorData distributor : distributors) {
      // if a distributor is bankrupt do not calculate a new budget
      if (distributor.getIsBankrupt()) {
        continue;
      }
      long spendings;
      long incomes = 0;

      /*
         Iterate through the consumer list and find any consumer that cannot pay.
         Then process according to documentation.
      */
      ArrayList<ConsumerData> bankrupt = new ArrayList<>();
      for (ConsumerData consumer : distributor.getContracts()) {
        if (!consumer.isNegative()) {
          incomes += consumer.getContractCost();
        } else {
          if (consumer.isAlmostBankrupt()) {
            consumer.setBankrupt(true);
            bankrupt.add(consumer);
          } else {
            consumer.setAlmostBankrupt(true);
            consumer.setDatorie(consumer.getContractCost());
          }
        }
      }
      spendings =
          distributor.getInitialInfrastructureCost()
              + distributor.getInitialProductionCost() * distributor.getContracts().size();

      distributor.getContracts().removeAll(bankrupt);

      long newIncome = distributor.getBudget() - spendings + incomes;
      distributor.setInitialBudget(newIncome);

      // Declare a distributor bankrupt if he has a negative newIncome
      if (distributor.getBudget() < 0) {
        distributor.setBankrupt(true);
      }
    }
  }

  public static void chooseProducers(final ArrayList<DistributorData> distributors,
                                     final ArrayList<ProducerData> producers) {
    Context context;
    for (DistributorData distributor: distributors) {
        switch (distributor.getProducerStrategy()) {
          case "GREEN" -> context = new Context(new greenStrategy());
          case "PRICE" -> context = new Context(new priceStrategy());
          case "QUANTITY" -> context = new Context(new quantityStrategy());
          default -> throw new IllegalStateException("Unexpected value: " + distributor.getProducerStrategy());
        }
        context.execute(distributor, producers);
    }
  }

  public static void addProducersToDistributor(final DistributorData distributor,
                                                 final ArrayList<ProducerData> sortedProducers) {

    if (distributor.isChange()) {
          for (ProducerData producer: distributor.getProducers()) {
            producer.getDistributors().remove(distributor);
            //producer.deleteObserver(distributor);
          }
        distributor.getProducers().clear();
        distributor.setChange(false);
    }
    long energyNeeded = distributor.getEnergyNeededKW();
    int i = 0;
    if (distributor.getProducers().size() > 0) {
      for (ProducerData producer: distributor.getProducers()) {
          energyNeeded -= producer.getEnergyPerDistributor();
      }
    }

    if (sortedProducers.size() > 0) {
      while (energyNeeded > 0) {
        if (sortedProducers.get(i).getDistributors().size() == sortedProducers.get(i).getMaxDistributors()) {
          i++;
          continue;
        }
        distributor.getProducers().add(sortedProducers.get(i));
        sortedProducers.get(i).getDistributors().add(distributor);
        energyNeeded -= sortedProducers.get(i).getEnergyPerDistributor();
        i++;
      }
    }
  }

  public static void calculateProductionCost(ArrayList<DistributorData> distributors,
                                             ArrayList<ProducerData> producers) {
    for (DistributorData distributor: distributors) {
      double cost = 0;
      for (ProducerData producer: distributor.getProducers()) {
        cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
      }
      distributor.setInitialProductionCost(Math.round(Math.floor(cost / 10)));
    }
  }

  public static void setMonthlyStats(ArrayList<ProducerData> producers, int monthNumber) {
    List<Long> ids = new ArrayList<>();
    for (ProducerData producer: producers) {
      for (DistributorData disributor: producer.getDistributors()) {
        ids.add(disributor.getId());
      }
      List<Long> sorted = ids.stream().sorted().collect(Collectors.toList());
      producer.getMonthlyStats().add(new MonthlyStats(monthNumber, new ArrayList<>(sorted)));
      ids.clear();
    }
  }
}
