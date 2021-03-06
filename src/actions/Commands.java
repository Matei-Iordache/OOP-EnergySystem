package actions;

import entities.EnergyType;
import fileio.ConsumerData;
import fileio.DistributorData;
import fileio.MonthlyStats;
import fileio.ProducerData;
import strategies.Context;
import strategies.GreenStrategy;
import strategies.PriceStrategy;
import strategies.QuantityStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        Add his monthly income and set him as almost bankrupt
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

  /**
   * Chose the producers a distributor wants to subscribe to
   * based on the strategy the distributor has
   * @param distributors list
   * @param producers list
   */
  public static void chooseProducers(final ArrayList<DistributorData> distributors,
                                     final ArrayList<ProducerData> producers) {
    Context context;
    for (DistributorData distributor: distributors) {
        switch (distributor.getProducerStrategy()) {
          case "GREEN" -> context = new Context(new GreenStrategy());
          case "PRICE" -> context = new Context(new PriceStrategy());
          case "QUANTITY" -> context = new Context(new QuantityStrategy());
          default -> throw new IllegalStateException("Unexpected value: " + distributor.getProducerStrategy());
        }
        context.execute(distributor, producers);
    }
  }

  /**
   * Subscribe the distributor to producers until it has enough energy.
   * @param distributor list
   * @param sortedProducers list
   */
  public static void addProducersToDistributor(final DistributorData distributor,
                                               final ArrayList<ProducerData> sortedProducers) {
    // do this for every distributor that is affected by a producer's change
    if (distributor.isChange()) {
          for (ProducerData producer: distributor.getProducers()) {
            producer.getDistributors().remove(distributor);
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

      while (energyNeeded > 0) {
        if (sortedProducers.get(i).getDistributors().size() == sortedProducers.get(i).getMaxDistributors()) {
          i++;
          continue;
        }
        // add the producer to the distributor's list
        distributor.getProducers().add(sortedProducers.get(i));
        // add the distributor to the producer's list
        sortedProducers.get(i).getDistributors().add(distributor);

        energyNeeded -= sortedProducers.get(i).getEnergyPerDistributor();
        i++;
      }
  }

  /**
   * Calculate the production cost of the distributors
   * @param distributors list
   */
  public static void calculateProductionCost(final ArrayList<DistributorData> distributors) {
    for (DistributorData distributor: distributors) {
      double cost = 0;
      for (ProducerData producer: distributor.getProducers()) {
        cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
      }
      distributor.setInitialProductionCost(Math.round(Math.floor(cost / 10)));
    }
  }

  /**
   * Set the monthly stats of the producers
   * Add the month number and a list of the distributors subscribed to a producer
   * @param producers list
   * @param monthNumber list
   */
  public static void setMonthlyStats(final ArrayList<ProducerData> producers,
                                     final int monthNumber) {
    List<Long> ids = new ArrayList<>();
    for (ProducerData producer: producers) {
      for (DistributorData distributor: producer.getDistributors()) {
        ids.add(distributor.getId());
      }
      Collections.sort(ids);
      producer.getMonthlyStats().add(new MonthlyStats(monthNumber, new ArrayList<>(ids)));
      ids.clear();
    }
  }

  /**
   * Set the Renewable parameter of a producer to true if he has renewable energy
   * @param producers list
   */
  public static void setRenewableProducers(ArrayList<ProducerData> producers) {
    for (ProducerData producer: producers) {
      producer.setRenewable(EnergyType.valueOf(producer.getEnergyType()).isRenewable());
    }
  }
}
