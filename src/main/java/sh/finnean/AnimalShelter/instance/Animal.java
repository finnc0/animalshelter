package sh.finnean.AnimalShelter.instance;

import java.security.SecureRandom;
import java.time.LocalDate;

public abstract class Animal {

    private final long id;
    private String name;
    private LocalDate vaccDate;
    private Owner owner;
    private boolean adopted;

    // its an animal shelter, we are housing animals, not humans therefore I think
    // the best approach is to have the animals own the owner object and not vice versa.

    public Animal(String name, LocalDate vaccDate, Owner owner) {
        // creates a pseudo random 64 bit id
        this.id = new SecureRandom().nextInt();
        // set states passed from sub classes via super()
        this.name = name;
        this.vaccDate = vaccDate;
        this.owner = owner;

        // we default this to false as in case an animal type does not want to implement Adoptable, this state will always be false
        // only if they can be adoptable will this change
        this.adopted = false;
    }

    // methods all sub classes must override from this class
    public abstract double getAdoptionFee();

    // getters
    public String getName() {return this.name;}
    public LocalDate getVaccDate() {return this.vaccDate; }
    public Owner getOwner() {return this.owner;}
    public boolean isAdopted() { return this.adopted; }
    public long id() { return this.id; }

    // setters
    public void setName(String newName) { this.name = newName; }
    public void setVaccDate(LocalDate newVaccDate) { this.vaccDate = newVaccDate; }
    public void setOwner(Owner owner) { this.owner = owner; }

}
