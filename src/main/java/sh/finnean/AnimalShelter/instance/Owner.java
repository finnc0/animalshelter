package sh.finnean.AnimalShelter.instance;

import sh.finnean.AnimalShelter.instance.contracts.Displayable;

import java.util.UUID;

public class Owner implements Displayable {

    private final UUID id;
    private String name;
    private String email;
    private long phoneNumber;

    public Owner(String name, String email, long phoneNumber) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // getters
    public String getName() { return this.name; }
    public String getEmail() { return this.email; }
    public UUID id() { return this.id; }


    @Override
    public void displayInfo() {
        System.out.println("Owner: " + this.id);
        System.out.println("Email: " + this.email);
        System.out.println("Name: " + this.name);
        System.out.println("Phone number: " + this.phoneNumber);
    }
}
