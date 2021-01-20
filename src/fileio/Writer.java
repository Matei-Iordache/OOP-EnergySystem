package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Write the final list of distributors and consumers to output */
public final class Writer implements InputOutput {
  private final FileWriter output;

  public Writer(final String outputPath) throws IOException {
    this.output = new FileWriter(outputPath);
  }

  /**
   * Write the final list of distributors and consumers to output Writes only specific parameters of
   * a consumer of distributor and the parameters are renamed
   *
   * @param consumers list of consumers
   * @param distributors list of distributors
   */
  public void writeToOutput(
      final ArrayList<ConsumerData> consumers, final ArrayList<DistributorData> distributors,
      final ArrayList<ProducerData> producers) throws IOException {

      try {
          ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

          LinkedHashMap<String, Object> maps = new LinkedHashMap<>();
          List<Map<String, Object>> list = new ArrayList<>();
          List<Map<String, Object>> list3 = new ArrayList<>();
          for (DistributorData distributor : distributors) {
              Map<String, Object> map = new LinkedHashMap<>();
              map.put("id", distributor.getId());
              map.put("energyNeededKW", distributor.getEnergyNeededKW());
              map.put("contractCost", distributor.getCostContract());
              map.put("budget", distributor.getBudget());
              map.put("producerStrategy", distributor.getProducerStrategy());
              map.put("isBankrupt", distributor.getIsBankrupt());
              List<Map<String, Object>> list2 = new ArrayList<>();
              for (ConsumerData contract : distributor.getContracts()) {
                  Map<String, Object> map2 = new LinkedHashMap<>();
                  map2.put("consumerId", contract.getId());
                  map2.put("price", contract.getContractCost());
                  map2.put("remainedContractMonths", contract.getContractTime());
                  list2.add(map2);
              }
              map.put("contracts", list2);
              list.add(map);
          }
          for (ProducerData producer: producers) {
              Map<String, Object> map = new LinkedHashMap<>();
              map.put("id", producer.getId());
              map.put("maxDistributors", producer.getMaxDistributors());
              map.put("priceKW", producer.getPriceKW());
              map.put("energyType", producer.getEnergyType());
              map.put("energyPerDistributor", producer.getEnergyPerDistributor());
              List<Map<String, Object>> list2 = new ArrayList<>();
              for (MonthlyStats monthlyStats: producer.getMonthlyStats()) {
                  Map<String, Object> map2 = new LinkedHashMap<>();
                  map2.put("month", monthlyStats.getMonth());
                  map2.put("distributorsIds", monthlyStats.getDistributorsIds());
                  list2.add(map2);
              }
              map.put("monthlyStats", list2);
              list3.add(map);
          }
          maps.put("consumers", consumers);
          maps.put("distributors", list);
          maps.put("energyProducers", list3);
          mapper.writeValue(output, maps);
      } catch (Exception ex) {
          ex.printStackTrace();
      }
  }

}
