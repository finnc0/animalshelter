package sh.finnean.AnimalShelter.manager;

import sh.finnean.AnimalShelter.instance.Owner;

import java.util.*;

public class OwnerManager {

    // we can use a map for indexing owners by their email's making it easier to search by email vs having to loop through a list and run a method.
    private Map<String, Owner> animalOwners;

    public OwnerManager() {
        this.animalOwners = new HashMap<>();
    }

    public void addOwner(Owner owner) throws Error {
        // for this application we will assume the only @unique fields for Owner objects are the UUID and email.
        // we know UUID's are "unique" so next valid check is to verify email doesnt already exist
        String newOwnerEmail = owner.getEmail();
        if (animalOwners.containsKey(newOwnerEmail)) {
            throw new Error("A owner with the specified email already exists.");
        }
        // map owner by email (String)
        animalOwners.put(newOwnerEmail, owner);
    }

    public List<Owner> getAllOwners() {
        return new ArrayList<>(animalOwners.values());
    }
}
