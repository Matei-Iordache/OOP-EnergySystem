package strategies;

import fileio.DistributorData;
import fileio.ProducerData;

import java.util.ArrayList;

public class Context {
    Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute(DistributorData distributor,
                        ArrayList<ProducerData> producers) {
        strategy.chooseProducer(distributor, producers);
    }
}
