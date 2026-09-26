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

    public abstract void getAdoptionFee();

    public String getName() {return this.name;}
    public LocalDate getVaccDate() {return this.vaccDate; }
    public String ownerName() {return this.ownerName;}
}
