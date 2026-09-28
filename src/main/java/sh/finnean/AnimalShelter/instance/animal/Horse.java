package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.Owner;
import sh.finnean.AnimalShelter.instance.contracts.Adoptable;
import sh.finnean.AnimalShelter.instance.contracts.Displayable;
import sh.finnean.AnimalShelter.instance.contracts.Friendly;

import javax.management.InstanceNotFoundException;
import java.time.LocalDate;

public class Horse extends Animal implements Adoptable, Friendly, Displayable {

    private boolean isRideable;

    // we specify this in every class that implements the Adoptable interface as it could be possible,
    // some animals wont implement this interface, therefore it would not be correct to add those to the super class.
    private boolean isAdopted;

    public Horse(String name,
                 LocalDate vaccDate,
                 Owner owner
    ) {
        super(name,vaccDate,owner);

        // set a default value, will get updated during CSV import if field is other than false in CSV row.
        this.isRideable = false;
        this.isAdopted = false;
    }

    @Override
    public double getAdoptionFee() {
        return 0;
    }

    @Override
    public void adopt(Owner owner) throws InstanceNotFoundException, IllegalCallerException {
        // verify owner is not null;
        if (owner == null) throw new InstanceNotFoundException("Adopt failed as specified owner could not be found.");
        if (!isAdoptable()) throw new IllegalCallerException("Adoption failed as animal is already marked as adopted.");

        this.setOwner(owner);
        this.setAdoptable(true);
    }

    // in case we ever need to add more functionality to setAdoptable, I added this to the interface
    @Override
    public void setAdoptable(boolean adoptionValue) {
        this.isAdopted = true;
    }

    // we need this method for access to the private var isAdopted outside of this class, therefore, it's applied in the interface
    @Override
    public boolean isAdoptable() {
        return !this.isAdopted();
    }

    // if a horse is rideable than it is suitable for a family.
    @Override
    public boolean isSuitableForFamily() {
        return isRideable;
    }

    @Override
    public void displayInfo() {
        System.out.println("Shelter Animal: Horse");
        System.out.println("ID: " + this.id());
        System.out.println("Name: " + this.getName());
        System.out.println("Is Rideable: " + this.isRideable);
        System.out.println("Owner Name: " + this.getOwner().getName());
        System.out.println("Vacc Date: " + this.getVaccDate());
        System.out.println("Is Adopted: " + this.isAdopted());
    }
}
