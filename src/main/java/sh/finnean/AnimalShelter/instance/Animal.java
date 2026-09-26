package sh.finnean.AnimalShelter.instance;

import java.security.SecureRandom;
import java.time.LocalDate;

public abstract class Animal{

    private final long id;
    private String name;
    private LocalDate vaccDate;
    private String ownerName;
    private String email;

    public Animal(String name, LocalDate vaccDate, String ownerName, String email) {
        // creates a pseudo random 64 bit id
        this.id = new SecureRandom().nextInt();
        // set states passed from sub classes via super()
        this.name = name;
        this.vaccDate = vaccDate;
        this.ownerName = ownerName;
        this.email = email;

    }

    // methods all sub classes must override
    public abstract double getAdoptionFee();
    public abstract boolean suitableForFamily();

    // getters
    public String getName() {return this.name;}
    public LocalDate getVaccDate() {return this.vaccDate; }
    public String ownerName() {return this.ownerName;}

    // setters
    public void setName(String newName) { this.name = newName; }
    public void setVaccDate(LocalDate newVaccDate) { this.vaccDate = newVaccDate; }
    public void setOwnerName(String newOwnerName) { this.ownerName = newOwnerName; }

}
