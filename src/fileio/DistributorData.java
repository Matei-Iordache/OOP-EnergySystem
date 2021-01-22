package fileio;

import actions.Commands;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;

/** Store the data of any distributor in the game */
@JsonIgnoreProperties(
    value = {
      "contractLength",
      "initialInfrastructureCost",
      "initialProductionCost",
      "profit",
      "costContract",
      "producers"
    })
@JsonPropertyOrder(
   value = {
      "id",
      "energyNeededKW",
      "contractCost",
      "budget",
      "producerStrategy",
      "isBankrupt",
      "contracts"
   }
)
public final class DistributorData implements Observer {
  private long id;
  private final long contractLength;
  private long budget;
  private long initialInfrastructureCost;
  private long initialProductionCost;

  private long energyNeededKW;
  private String producerStrategy;

  private boolean isBankrupt = false;
  private long profit;
  private final ArrayList<ConsumerData> contracts = new ArrayList<>();

  private List<ProducerData> producers = new ArrayList<>();
  private long costContract;

  private boolean change = false;

  public DistributorData(
          final long id,
          final long contractLength,
          final long initialBudget,
          final long initialInfrastructureCost,
          final long energyNeededKW,
          final String producerStrategy) {
    this.id = id;
    this.contractLength = contractLength;
    this.budget = initialBudget;
    this.initialInfrastructureCost = initialInfrastructureCost;
    this.energyNeededKW = energyNeededKW;
    this.producerStrategy = producerStrategy;
  }

  public long getId() {
    return id;
  }

  public long getContractLength() {
    return contractLength;
  }

  public long getBudget() {
    return budget;
  }

  public long getInitialInfrastructureCost() {
    return initialInfrastructureCost;
  }

  public long getInitialProductionCost() {
    return initialProductionCost;
  }

  public long getProfit() {
    return profit;
  }

  public void setProfit(final long profit) {
    this.profit = profit;
  }

  public ArrayList<ConsumerData> getContracts() {
    return contracts;
  }

  public long getCostContract() {
    return costContract;
  }

  public void setCostContract(final long constContract) {
    this.costContract = constContract;
  }

  public void setInitialBudget(final long initialBudget) {
    this.budget = initialBudget;
  }

  public boolean getIsBankrupt() {
    return isBankrupt;
  }

  public void setBankrupt(final boolean bankrupt) {
    isBankrupt = bankrupt;
  }

  public void setInitialInfrastructureCost(final long initialInfrastructureCost) {
    this.initialInfrastructureCost = initialInfrastructureCost;
  }

  public void setInitialProductionCost(final long initialProductionCost) {
    this.initialProductionCost = initialProductionCost;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public List<ProducerData> getProducers() {
    return producers;
  }

  public void setProducers(List<ProducerData> producers) {
    this.producers = producers;
  }

  public long getEnergyNeededKW() {
    return energyNeededKW;
  }

  public String getProducerStrategy() {
    return producerStrategy;
  }

  public void setProducerStrategy(String producerStrategy) {
    this.producerStrategy = producerStrategy;
  }

  public void setEnergyNeededKW(long energyNeededKW) {
    this.energyNeededKW = energyNeededKW;
  }

  public boolean isChange() {
    return change;
  }

  public void setChange(boolean change) {
    this.change = change;
  }

  @Override
  public String toString() {
    return "DistributorData{" +
            "id=" + id +
            ", contractLength=" + contractLength +
            ", budget=" + budget +
            ", initialInfrastructureCost=" + initialInfrastructureCost +
            ", initialProductionCost=" + initialProductionCost +
            ", energyNeededKW=" + energyNeededKW +
            ", producerStrategy='" + producerStrategy +
            ", isBankrupt=" + isBankrupt +
            ", profit=" + profit +
            ", contracts=" + contracts +
            ", producers=" + producers +
            ", costContract=" + costContract +
            '}';
  }

  // arg - list of distributors of a producer
  // o - producer
  @Override
  public void update(Observable o, Object arg) {
//    List<DistributorData> copy = new ArrayList<>((ArrayList<DistributorData>) arg);
//    for (DistributorData distributor: copy) {
//      distributor.getProducers().clear();
//      ((ProducerData) o).getDistributors().remove(distributor);
//      o.deleteObserver(distributor);
//    for (ProducerData producer: getProducers()) {
//        producer.getDistributors().remove(this);
//    }
    if (!change) {
      change = true;
    }
    //this.getProducers().clear();
    o.deleteObserver(this);
    }
}
