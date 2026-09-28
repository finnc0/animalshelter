package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.Owner;

import java.time.LocalDate;

public class Horse extends Animal {

    private boolean isRideable;

    public Horse(String name,
                 LocalDate vaccDate,
                 Owner owner
    ) {
        super(name,vaccDate,owner);

        this.isRideable = false;
    }

    @Override
    public double getAdoptionFee() {
        return 0;
    }

    @Override
    public boolean suitableForFamily() {
        return false;
    }
}
