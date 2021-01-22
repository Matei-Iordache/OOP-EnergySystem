package strategies;

import actions.Commands;
import fileio.DistributorData;
import fileio.ProducerData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * This strategy will sort producers by:
 * -Renewable energy
 * -Lowest price per KW
 * -Biggest energy per distributor
 * -IDs
 * And then add the producers to the distributor list
 */
public final class GreenStrategy implements Strategy {

    @Override
    public void chooseProducer(final DistributorData distributor,
                               final ArrayList<ProducerData> producers) {

        ArrayList<ProducerData> sorted = (ArrayList<ProducerData>) producers
            .stream()
            .sorted(Comparator.comparing(ProducerData::isRenewable, Comparator.reverseOrder())
            .thenComparing((ProducerData::getPriceKW))
            .thenComparing(ProducerData::getEnergyPerDistributor, Comparator.reverseOrder())
            .thenComparing(ProducerData::getId))
            .collect(Collectors.toList());

        Commands.addProducersToDistributor(distributor, sorted);
    }
}
