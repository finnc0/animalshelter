package sh.finnean.AnimalShelter.contracts;

import sh.finnean.AnimalShelter.instance.Owner;

import javax.management.InstanceNotFoundException;

public interface Adoptable {
    void adopt(Owner owner) throws InstanceNotFoundException, IllegalCallerException;
    void unAdopt() throws IllegalCallerException;
    void setAdoptable(boolean adoptionValue);
    boolean isAdoptable();
}
