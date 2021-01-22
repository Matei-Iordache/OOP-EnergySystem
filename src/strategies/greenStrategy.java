package strategies;

import entities.EnergyType;
import fileio.DistributorData;
import fileio.ProducerData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import actions.Commands;

// renewable, pret, cantitate
public class greenStrategy implements Strategy {

    @Override
    public void chooseProducer(DistributorData distributor, ArrayList<ProducerData> producers) {
        for (ProducerData producer: producers) {
            producer.setRenewable(!EnergyType.valueOf(producer.getEnergyType()).isRenewable());
        }

        ArrayList<ProducerData> sorted = (ArrayList<ProducerData>) producers
                .stream()
                .sorted(Comparator.comparing(ProducerData::isRenewable)
                .thenComparing((ProducerData::getPriceKW))
                .thenComparing(ProducerData::getEnergyPerDistributor, Comparator.reverseOrder())
                .thenComparing(ProducerData::getId))
                .collect(Collectors.toList());

        // sorted.removeIf(producer -> distributor.getProducers().contains(producer));
        Commands.addProducersToDistributor(distributor,sorted);
    }
}
