package strategies;

import fileio.DistributorData;
import fileio.ProducerData;

import java.util.ArrayList;

public interface Strategy {
    public void chooseProducer(final DistributorData distributor,
                              final ArrayList<ProducerData> producers);
}
