package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;

import java.time.LocalDate;

public class Horse extends Animal {

    private boolean isRideable;

    public Horse(String name,
                 LocalDate vaccDate,
                 String ownerName,
                 String email
    ) {
        super(name,vaccDate,ownerName,email);

        this.isRideable = false;
    }

    @Override
    public double getAdoptionFee() {
        return 0;
    }
}
