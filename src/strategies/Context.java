package strategies;

import fileio.DistributorData;
import fileio.ProducerData;

import java.util.ArrayList;

/**
 * Context Class for the Strategy Design Pattern
 */
public class Context {
    private final Strategy strategy;

    public Context(final Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Execute the strategy of the distributor
     * @param distributor distributor
     * @param producers list
     */
    public void execute(final DistributorData distributor,
                        final ArrayList<ProducerData> producers) {
        strategy.chooseProducer(distributor, producers);
    }
}
