package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.Owner;
import sh.finnean.AnimalShelter.contracts.Adoptable;
import sh.finnean.AnimalShelter.contracts.Displayable;
import sh.finnean.AnimalShelter.contracts.Behavior;
import sh.finnean.AnimalShelter.instance.animalmodmenu.EditableField;
import sh.finnean.AnimalShelter.instance.animalmodmenu.FieldType;
import sh.finnean.AnimalShelter.utils.ShelterUtil;

import javax.management.InstanceNotFoundException;
import java.time.LocalDate;
import java.util.List;

public class Horse extends Animal implements Adoptable, Behavior, Displayable {

    private boolean isRideable;

    // we specify this in every class that implements the Adoptable interface as it could be possible,
    // some animals wont implement this interface, therefore it would not be correct to add those to the super class.
    private boolean isAdopted;

    public Horse(String name,
                 LocalDate vaccDate,
                 Owner owner,
                 boolean isRideable
    ) {
        super(name,vaccDate,owner);

        // set a default value, will get updated during CSV import if field is other than false in CSV row.
        this.isRideable = isRideable;

        // we set isAdopted to true if owner obj exists, otherwise its default is false
        this.isAdopted = owner != null;
    }

    // getters
    public boolean getIsRideable() { return this.isRideable; }

    // setters
    public void setIsRideable(boolean isRideable) { this.isRideable = isRideable; }

    @Override
    public List<EditableField> getEditableFields(Animal a) {
        List<EditableField> editableFields = super.getEditableFields(a);
        editableFields.add(new EditableField("is rideable", FieldType.BOOLEAN, this::getIsRideable,v -> this.setIsRideable((boolean) v)));
        return editableFields;
    }

    @Override
    public double getAdoptionFee() {
        // horses are expensive to house in a shelter, so we just add a flat 1000$ fee for every adoption which covers everything (theoretical)
        return ShelterUtil.horseAdoptionFee() + 1000;
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
        this.isAdopted = adoptionValue;
    }

    // we need this method for access to the private var isAdopted outside of this class, therefore, it's applied in the interface
    @Override
    public boolean isAdoptable() {
        return !this.isAdopted;
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
        System.out.println((this.getOwner() != null ? ("Owner Name: " + this.getOwner().getName()) : "No owner linked."));
        System.out.println("Vacc Date: " + (this.getVaccDate() == null ? "N/A" : this.getVaccDate()));
        System.out.println("Is Adopted: " + this.isAdopted);
        System.out.println("Is suitable for family: " + this.isSuitableForFamily());
    }
}
