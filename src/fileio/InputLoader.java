package fileio;

import utils.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/** Reads data from the json files */
public final class InputLoader implements InputOutput {
  private final String inputPath;

  public InputLoader(final String inputPath) {
    this.inputPath = inputPath;
  }

  public String getInputPath() {
    return inputPath;
  }

  /**
   * Method that reads data from the json file
   *
   * @return Input in object form
   */
  public Input readData() {
    JSONParser jsonParser = new JSONParser();
    ArrayList<ConsumerData> consumers = new ArrayList<>();
    ArrayList<DistributorData> distributors = new ArrayList<>();
    ArrayList<ProducerData> producers = new ArrayList<>();
    ArrayList<MonthlyUpdatesInputData> monthlyUpdates = new ArrayList<>();
    long numberOfTurns = 0;
    try {
      JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(inputPath));

      numberOfTurns = (long) jsonObject.get(Constants.NUMBER_OF_TURNS);

      JSONObject jsonInitialData = (JSONObject) jsonObject.get(Constants.INITIAL_DATA);

      JSONArray jsonConsumers = (JSONArray) jsonInitialData.get(Constants.CONSUMERS);

      JSONArray jsonDistributors = (JSONArray) jsonInitialData.get(Constants.DISTRIBUTORS);

      JSONArray jsonProducers = (JSONArray) jsonInitialData.get(Constants.PRODUCERS);

      JSONArray jsonMonthlyUpdates = (JSONArray) jsonObject.get(Constants.MONTHLY_UPDATES);

      if (jsonConsumers != null) {
        for (Object jsonConsumer : jsonConsumers) {
          consumers.add(
              new ConsumerData(
                  (long) ((JSONObject) jsonConsumer).get(Constants.ID),
                  (long) ((JSONObject) jsonConsumer).get(Constants.INITIAL_BUDGET),
                  (long) ((JSONObject) jsonConsumer).get(Constants.MONTHLY_INCOME)));
        }
      }

      if (jsonDistributors != null) {
        for (Object jsonDistributor : jsonDistributors) {
          distributors.add(
              new DistributorData(
                  (long) ((JSONObject) jsonDistributor).get(Constants.ID),
                  (long) ((JSONObject) jsonDistributor).get(Constants.CONTRACT_LENGTH),
                  (long) ((JSONObject) jsonDistributor).get(Constants.INITIAL_BUDGET),
                  (long) ((JSONObject) jsonDistributor).get(Constants.INITIAL_INFRASTRUCTURE_COST),
                  (long) ((JSONObject) jsonDistributor).get(Constants.ENERGY_NEEDED_KW),
                  (String) ((JSONObject) jsonDistributor).get(Constants.PRODUCER_STRATEGY)));
        }
      }

      if (jsonProducers != null) {
        for (Object jsonProducer : jsonProducers) {
          producers.add(
                  new ProducerData(
                          (long) ((JSONObject) jsonProducer).get(Constants.ID),
                          (String) ((JSONObject) jsonProducer).get(Constants.ENERGY_TYPE),
                          (long) ((JSONObject) jsonProducer).get(Constants.MAX_DISTRIBUTORS),
                          (Double) ((JSONObject) jsonProducer).get(Constants.PRICE_KW),
                          (long) ((JSONObject) jsonProducer).get(Constants.ENERGY_PER_DISTRIBUTOR)));
        }
      }

      if (jsonMonthlyUpdates != null) {
        for (Object monthlyUpdate : jsonMonthlyUpdates) {

          JSONArray jsonNewConsumers =
              (JSONArray) ((JSONObject) monthlyUpdate).get(Constants.NEW_CONSUMERS);

          ArrayList<ConsumerData> newConsumers = new ArrayList<>();

          if (jsonNewConsumers != null) {
            for (Object jsonNewConsumer : jsonNewConsumers) {
              newConsumers.add(
                  new ConsumerData(
                      (long) ((JSONObject) jsonNewConsumer).get(Constants.ID),
                      (long) ((JSONObject) jsonNewConsumer).get(Constants.INITIAL_BUDGET),
                      (long) ((JSONObject) jsonNewConsumer).get(Constants.MONTHLY_INCOME)));
            }
          }

          JSONArray jsonDistributorChanges =
                  (JSONArray) ((JSONObject) monthlyUpdate).get(Constants.DISTRIBUTOR_CHANGES);

          ArrayList<DistributorChanges> distributorChanges = new ArrayList<>();

          if (jsonDistributorChanges != null) {
            for (Object jsonDistributorChange : jsonDistributorChanges) {
              distributorChanges.add(
                      new DistributorChanges(
                          (long) ((JSONObject) jsonDistributorChange).get(Constants.ID),
                          (long) ((JSONObject) jsonDistributorChange).get(Constants.INFRASTRUCTURE_COST)));
            }
          }

          JSONArray jsonProducerChanges =
                  (JSONArray) ((JSONObject) monthlyUpdate).get(Constants.PRODUCER_CHANGES);

          ArrayList<ProducerChanges> producerChanges = new ArrayList<>();

          if (jsonProducerChanges != null) {
            for (Object jsonDistributorChange : jsonProducerChanges) {
              producerChanges.add(
                      new ProducerChanges(
                              (long) ((JSONObject) jsonDistributorChange).get(Constants.ID),
                              (long) ((JSONObject) jsonDistributorChange).get(Constants.ENERGY_PER_DISTRIBUTOR)));
            }
          }

          monthlyUpdates.add(new MonthlyUpdatesInputData(newConsumers, distributorChanges, producerChanges));
        }
      }

      if (jsonConsumers == null) {
        consumers = null;
      }

      if (jsonDistributors == null) {
        distributors = null;
      }

      if (jsonMonthlyUpdates == null) {
        monthlyUpdates = null;
      }
      if (jsonProducers == null) {
        producers = null;
      }
    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }
    return new Input(consumers, distributors, producers, monthlyUpdates, numberOfTurns);
  }
}
