package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;

import java.time.LocalDate;

public class Bird extends Animal {

    public Bird(String name, LocalDate vaccDate, String ownerName, String email) {
        super(name,vaccDate,ownerName,email);
    }
}
