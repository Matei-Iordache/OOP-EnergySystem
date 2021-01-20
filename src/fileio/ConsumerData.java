package fileio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Store the Data of any consumer in the game */
@JsonIgnoreProperties(
    value = {
      "contractTime",
      "distributor",
      "almostBankrupt",
      "negative",
      "monthlyIncome",
      "distributorId",
      "contractCost",
      "datorie"
    })
public final class ConsumerData {
  private long id;
  private boolean isBankrupt = false;
  private long budget;
  private long monthlyIncome;

  private long contractTime;
  private boolean almostBankrupt = false;
  private boolean negative = false;

  private long distributorId;
  private long contractCost;
  private long debt;

  public ConsumerData(final long id, final long initialBudget,
                      final long monthlyIncome) {
    this.id = id;
    this.budget = initialBudget;
    this.monthlyIncome = monthlyIncome;
  }

  /** get the id of the consumer */
  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }
  /** get the budget of the consumer */
  public long getBudget() {
    return budget;
  }

  public void setBudget(final long initialBudget) {
    this.budget = initialBudget;
  }

  /** get the income of the consumer */
  public long getMonthlyIncome() {
    return monthlyIncome;
  }

  public void setMonthlyIncome(final long monthlyIncome) {
    this.monthlyIncome = monthlyIncome;
  }

  /** get the contract time of the consumer */
  public long getContractTime() {
    return contractTime;
  }

  public void setContractTime(final long contractTime) {
    this.contractTime = contractTime;
  }

  /** check if a consumer is bankrupt */
  public boolean getIsBankrupt() {
    return isBankrupt;
  }

  public void setBankrupt(final boolean bankrupt) {
    isBankrupt = bankrupt;
  }

  /** check if a consumer is almost bankrupt */
  public boolean isAlmostBankrupt() {
    return almostBankrupt;
  }

  public void setAlmostBankrupt(final boolean almostBankrupt) {
    this.almostBankrupt = almostBankrupt;
  }

  /** check if a consumer would have a negative income if he paid his distributor */
  public boolean isNegative() {
    return negative;
  }

  public void setNegative(final boolean negative) {
    this.negative = negative;
  }

  /** get the id of the consumer's distributor */
  public long getDistributorId() {
    return distributorId;
  }

  public void setDistributorId(final long distributorId) {
    this.distributorId = distributorId;
  }

  /** get the contract cost of the consumer's distributor */
  public long getContractCost() {
    return contractCost;
  }

  public void setContractCost(final long contractCost) {
    this.contractCost = contractCost;
  }
  /** get the debt the consumer ows the distributor */
  public long getDatorie() {
    return debt;
  }

  public void setDatorie(final long datorie) {
    this.debt = datorie;
  }

  @Override
  public String toString() {
    return "ConsumerData{"
        + "id="
        + id
        + ", initialBudget="
        + budget
        + ", monthlyIncome="
        + monthlyIncome
        + ", contractTime="
        + contractTime
        + ", isBankrupt="
        + isBankrupt
        + ", almostBankrupt="
        + almostBankrupt
        + ", negative="
        + negative
        + '}'
        + "\n";
  }
}
