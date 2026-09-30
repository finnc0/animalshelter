package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.Owner;
import sh.finnean.AnimalShelter.contracts.Adoptable;
import sh.finnean.AnimalShelter.contracts.Behavior;
import sh.finnean.AnimalShelter.contracts.Displayable;
import sh.finnean.AnimalShelter.instance.animalmodmenu.EditableField;
import sh.finnean.AnimalShelter.instance.animalmodmenu.FieldType;
import sh.finnean.AnimalShelter.utils.ShelterUtil;

import javax.management.InstanceNotFoundException;
import java.time.LocalDate;
import java.util.List;

public class Dog extends Animal implements Adoptable, Behavior, Displayable {

    private boolean likesWalks;
    private boolean crateTrained;
    private boolean isAdopted;

    public Dog(String name,
               LocalDate vaccDate,
               Owner owner,
               boolean likesWalks,
               boolean crateTrained
    ) {
        super(name,vaccDate,owner);

        this.likesWalks = likesWalks;
        this.crateTrained = crateTrained;

        this.isAdopted = owner != null;
    }

    @Override
    public List<EditableField> getEditableFields(Animal a) {
        List<EditableField> editableFields = super.getEditableFields(a);
        editableFields.add(new EditableField("crate trained", FieldType.BOOLEAN, this::isCrateTrained, v -> this.setCrateTrained((boolean) v)));
        editableFields.add(new EditableField("likes walks",FieldType.BOOLEAN, this::likesWalks, v -> this.setLikesWalks((boolean) v)));
        return editableFields;
    }

    @Override
    public double getAdoptionFee() {
        // dog adoption fee is the base fee unless they are unvacc, then its base fee + 100$;
        // if dog likes walks, then add 25$ to base fee;
        double fee = ShelterUtil.dogAdoptionFee();
        // if vacc date is unavail it means its not set, therefore add an additional charge to the adoption fee
        if (this.getVaccDate() == null) {
            fee += 100;
        }

        // dog is likely more wanted lol if they like walks so we upcharge the customer
        if (likesWalks) { fee += 25;}

        return fee;
    }


    @Override
    public void adopt(Owner owner) throws InstanceNotFoundException {
        // verify owner is not null;
        if (owner == null) throw new InstanceNotFoundException("Adopt failed as specified owner could not be found.");
        if (!isAdoptable()) throw new IllegalCallerException("Adoption failed as animal is already marked as adopted.");

        this.setOwner(owner);
        this.setAdoptable(true);
    }

    @Override
    public void setAdoptable(boolean adoptionValue) {
        this.isAdopted = adoptionValue;
    }

    @Override
    public boolean isAdoptable() {
        return !this.isAdopted;
    }

    @Override
    public boolean isSuitableForFamily() {
        // if dog likes walks, is crate trained, and has been vacc before that would make them suitable for a family.
        if (likesWalks && crateTrained && getVaccDate() != null) {
            return true;
        }
        return false;
    }


    // getters
    public boolean isCrateTrained() { return this.crateTrained; }
    public boolean likesWalks() { return this.likesWalks; }

    // setters
    public void setCrateTrained(boolean crateTrained) { this.crateTrained = crateTrained; }
    public void setLikesWalks(boolean likesWalks) { this.likesWalks = likesWalks; }


    @Override
    public void displayInfo() {
        System.out.println("Shelter Animal: Dog");
        System.out.println("ID: " + this.id());
        System.out.println("Name: " + this.getName());
        System.out.println("Likes Walks: " + this.likesWalks);
        System.out.println((this.getOwner() != null ? ("Owner Name: " + this.getOwner().getName()) : "No owner linked."));
        System.out.println("Vacc Date: " + (this.getVaccDate() == null ? "N/A" : this.getVaccDate()));
        System.out.println("Is Adopted: " + this.isAdopted);
        System.out.println("Crate Trained: " + this.crateTrained);
        System.out.println("Is suitable for family: " + this.isSuitableForFamily());
    }
}
