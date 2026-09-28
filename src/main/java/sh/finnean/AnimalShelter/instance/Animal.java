package sh.finnean.AnimalShelter.instance;

import java.security.SecureRandom;
import java.time.LocalDate;

public abstract class Animal{

    private final long id;
    private String name;
    private LocalDate vaccDate;
    private Owner owner;

    // its an animal shelter, we are housing animals, not humans therefore I think
    // the best approach is to have the animals own the owner object and not vice versa.

    public Animal(String name, LocalDate vaccDate, Owner owner) {
        // creates a pseudo random 64 bit id
        this.id = new SecureRandom().nextInt();
        // set states passed from sub classes via super()
        this.name = name;
        this.vaccDate = vaccDate;
        this.owner = owner;
    }

    // methods all sub classes must override
    public abstract double getAdoptionFee();
    public abstract boolean suitableForFamily();

    // getters
    public String getName() {return this.name;}
    public LocalDate getVaccDate() {return this.vaccDate; }
    public Owner getOwner() {return this.owner;}

    // setters
    public void setName(String newName) { this.name = newName; }
    public void setVaccDate(LocalDate newVaccDate) { this.vaccDate = newVaccDate; }
    public void setOwner(Owner owner) { this.owner = owner; }

}
