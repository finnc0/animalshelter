package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.Owner;

import java.time.LocalDate;

public class Bird extends Animal {

    public Bird(String name,
                LocalDate vaccDate,
                Owner owner

    ) {
        super(name,vaccDate,owner);
    }

    @Override
    public double getAdoptionFee() {
        return 0;
    }


}
