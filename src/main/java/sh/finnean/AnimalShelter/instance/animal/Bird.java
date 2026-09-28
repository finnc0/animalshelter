package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.Owner;
import sh.finnean.AnimalShelter.instance.contracts.Displayable;

import java.time.LocalDate;

public class Bird extends Animal implements Displayable {

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


    @Override
    public void displayInfo() {
        System.out.println("Shelter Animal: Bird");
        System.out.println("ID: " + this.id());
        System.out.println("Name: " + this.getName());
        System.out.println("Owner Name: " + this.getOwner().getName());
        System.out.println("Vacc Date: " + this.getVaccDate());
        System.out.println("Is Adopted: " + this.isAdopted());
    }
}
