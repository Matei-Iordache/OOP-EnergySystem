package strategies;

import fileio.DistributorData;
import fileio.ProducerData;

import java.util.ArrayList;

/**
 *  Core of the strategy design pattern
 */
public interface Strategy {
    void chooseProducer(DistributorData distributor, ArrayList<ProducerData> producers);
}
