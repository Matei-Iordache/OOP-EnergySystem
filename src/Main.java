import fileio.Input;
import fileio.InputLoader;
import fileio.InputOutputFactory;
import fileio.Writer;

/**
 * Starting point of the program Private constructor to solve the checkstyle warning final class to
 * solve the checkstyle warning
 */
public final class Main {

  private Main() {

  }

  /**
   * Starting point of game implementation
   *
   * @param args list of command line arguments
   * @throws Exception in case of exception
   */
  public static void main(final String[] args) throws Exception {

    InputOutputFactory factory = InputOutputFactory.getInstance();
//    factory.setInputPath("checker/resources/in/basic_5.json");
//    factory.setOutputPath("checker/resources/out/basic_5.json");
    factory.setInputPath(args[0]);
    factory.setOutputPath(args[1]);

    InputLoader loader = (InputLoader) factory.getInputOutput("input");
    Input input = loader.readData();
    Game game = new Game(input.getConsumers(), input.getDistributors(),
                         input.getProducers(), input.getMonthlyUpdates());
    game.execute(input.getNumberOfTurns());

    Writer writer = (Writer) factory.getInputOutput("output");
    writer.writeToOutput(input.getConsumers(), input.getDistributors(), input.getProducers());
  }
}
