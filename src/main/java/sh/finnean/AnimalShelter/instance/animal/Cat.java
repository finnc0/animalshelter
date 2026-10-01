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

public class Cat extends Animal implements Adoptable, Behavior, Displayable {

    private boolean likesCatNip;
    private boolean litterBoxTrained;
    private boolean isAdopted;

    public Cat(String name,
               LocalDate vaccDate,
               Owner owner,
               boolean likesCatNip,
               boolean litterBoxTrained
    ) {
        super(name,vaccDate,owner);

        this.likesCatNip = likesCatNip;
        this.litterBoxTrained = litterBoxTrained;

        this.isAdopted = owner != null;
    }

    @Override
    public List<EditableField> getEditableFields(Animal a) {
        List<EditableField> editableFields = super.getEditableFields(a);
        editableFields.add(new EditableField("likes catnip", FieldType.BOOLEAN, this::likesCatNip, v -> this.setLikesCatNip((boolean) v)));
        editableFields.add(new EditableField("litterbox trained",FieldType.BOOLEAN, this::litterBoxTrained, v -> this.setLitterBoxTrained((boolean) v)));
        return editableFields;
    }

    @Override
    public double getAdoptionFee() {

        // cat adoption fee is base fee unless vaccdate is null then it adds an additional 89$ charge
        // if cat likes catnip it also adds an additional charge of 55$
        double fee = ShelterUtil.catAdoptionFee();

        if (this.getVaccDate() == null) {
            fee += 89.00;
        }

        if (this.likesCatNip) {
            fee += 55.00;
        }

        return fee;
    }



    // getters
    public boolean likesCatNip() { return this.likesCatNip; }
    public boolean litterBoxTrained() { return this.litterBoxTrained; }

    // setters
    public void setLikesCatNip(boolean newLikeState) { this.likesCatNip = newLikeState; }
    public void setLitterBoxTrained(boolean litterBoxTrained) { this.litterBoxTrained = litterBoxTrained; }

    @Override
    public void adopt(Owner owner) throws InstanceNotFoundException, IllegalCallerException {
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
        // cat is suitable for family if they like catnip and have been vacc before
        if (this.likesCatNip && this.getVaccDate() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void unAdopt() {
        if (this.isAdoptable()) throw new IllegalCallerException("Animal is already unadopted.");
        // able to be unadopted.
        this.setOwner(null);
        this.setAdoptable(true);
    }

    @Override
    public void displayInfo() {
        System.out.println("Shelter Animal: Cat");
        System.out.println("ID: " + this.id());
        System.out.println("Name: " + this.getName());
        System.out.println("Likes Catnip: " + this.likesCatNip);
        System.out.println((this.getOwner() != null ? ("Owner Name: " + this.getOwner().getName()) : "No owner linked."));
        if (this.getOwner() != null) System.out.println("Owner Email: " + this.getOwner().getEmail());
        System.out.println("Vacc Date: " + (this.getVaccDate() == null ? "N/A" : this.getVaccDate()));
        System.out.println("Is Adopted: " + this.isAdopted);
        System.out.println("Litterbox trained: " + this.litterBoxTrained);
        System.out.println("Is suitable for family: " + this.isSuitableForFamily());
    }
}
