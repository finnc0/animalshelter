package sh.finnean.AnimalShelter.instance;

import java.security.SecureRandom;
import java.time.LocalDate;

public abstract class Animal {

    private final long id;
    private String name;
    private LocalDate vaccDate;
    private Owner owner;

    // its an animal shelter, we are housing animals, not humans therefore I think
    // the best approach is to have the animals own the owner object and not vice versa.

    public Animal(String name, LocalDate vaccDate, Owner owner) {
        // creates a pseudo random 64 bit id, use bitwise op to strip away the - sign to get max possible num.
        this.id = new SecureRandom().nextInt() & Integer.MAX_VALUE;
        // set states passed from sub classes via super()
        this.name = name;
        this.vaccDate = vaccDate;
        this.owner = owner;

    }

    // methods all sub classes must override from this class
    public abstract double getAdoptionFee();

    // getters
    public String getName() {return this.name;}
    public LocalDate getVaccDate() {return this.vaccDate; }
    public Owner getOwner() {return this.owner;}
    public long id() { return this.id; }

    // setters
    public void setName(String newName) { this.name = newName; }
    public void setVaccDate(LocalDate newVaccDate) { this.vaccDate = newVaccDate; }
    public void setOwner(Owner owner) { this.owner = owner; }

}
