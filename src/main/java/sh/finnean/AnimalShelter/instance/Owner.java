package sh.finnean.AnimalShelter.instance;

import java.util.UUID;

public class Owner {

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


}
