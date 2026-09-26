package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;

import java.time.LocalDate;

public class Cat extends Animal {

    public Cat(String name, LocalDate vaccDate, String ownerName, String email) {
        super(name,vaccDate,ownerName,email);
    }
}
