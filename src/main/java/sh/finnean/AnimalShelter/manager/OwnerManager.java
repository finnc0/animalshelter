package sh.finnean.AnimalShelter.manager;

import sh.finnean.AnimalShelter.instance.Owner;

import javax.management.InstanceNotFoundException;
import java.util.*;

public class OwnerManager {

    // we can use a map for indexing owners by their email's making it easier to search by email vs having to loop through a list and run a method.
    private Map<String, Owner> animalOwners;

    public OwnerManager() {
        this.animalOwners = new HashMap<>();
    }

    public void addOwner(Owner owner) throws IllegalStateException {
        // for this application we will assume the only @unique fields for Owner objects are the UUID and email.
        // we know UUID's are "unique" so next valid check is to verify email doesnt already exist
        String newOwnerEmail = owner.getEmail();
        if (animalOwners.containsKey(newOwnerEmail)) {
            throw new IllegalStateException("Failed to add new owner! An owner with the provided email already exists.");
        }
        // map owner by email (String)
        animalOwners.put(newOwnerEmail, owner);
    }

    public Owner getByEmail(String email) throws InstanceNotFoundException {
        if (!this.animalOwners.containsKey(email)) throw new InstanceNotFoundException("An owner with the specified email could not be found.");
        return this.animalOwners.get(email);
    }

    public void removeOwner(Owner owner) {
        this.animalOwners.remove(owner.getEmail());
    }

    public List<Owner> getAllOwners() {
        return new ArrayList<>(animalOwners.values());
    }
}
