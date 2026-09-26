package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;

import java.time.LocalDate;

public class Dog extends Animal {

    private boolean likesWalks;

    public Dog(String name,
               LocalDate vaccDate,
               String ownerName,
               String email,
               boolean likesWalks
    ) {
        super(name,vaccDate,ownerName,email);
        this.likesWalks = likesWalks;
    }


    // getters
}
