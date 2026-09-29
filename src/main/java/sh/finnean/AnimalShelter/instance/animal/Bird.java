package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.contracts.Adoptable;
import sh.finnean.AnimalShelter.contracts.Behavior;
import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.Owner;
import sh.finnean.AnimalShelter.contracts.Displayable;
import sh.finnean.AnimalShelter.utils.ShelterUtil;

import javax.management.InstanceNotFoundException;
import java.time.LocalDate;

public class Bird extends Animal implements Adoptable, Behavior, Displayable {

    private boolean isAdopted;
    private boolean canTalk;
    private boolean canFly;

    public Bird(String name,
                LocalDate vaccDate,
                Owner owner,
                boolean canTalk,
                boolean canFly

    ) {
        super(name,vaccDate,owner);

        this.isAdopted = false;
        this.canTalk = canTalk;
        this.canFly = canFly;
    }

    // getters
    public boolean getCanFly() { return this.canFly; }
    public boolean getCanTalk() { return this.canTalk; }

    // setters
    public void setCanFly(boolean flyState) { this.canFly = flyState; }
    public void setCanTalk(boolean talkState) { this.canTalk = talkState; }

    @Override
    public double getAdoptionFee() {
        double adoptionFee = ShelterUtil.birdAdoptionFee();

        // if bird can talk and fly add 220$ to total as bird is likely more wanted :(
        if (canTalk && canFly) {
            adoptionFee += 220;
        }

        // if bird isnt vacc, then add 80$ to total fee.
        if (this.getVaccDate() == null) adoptionFee += 80;
        return adoptionFee;
    }


    @Override
    public void displayInfo() {
        System.out.println("Shelter Animal: Bird");
        System.out.println("ID: " + this.id());
        System.out.println("Name: " + this.getName());
        System.out.println((this.getOwner() != null ? ("Owner Name: " + this.getOwner().getName()) : "No owner linked."));
        System.out.println("Vacc Date: " + this.getVaccDate());
        System.out.println("Is Adopted: " + this.isAdopted);
    }

    @Override
    public void adopt(Owner owner) throws IllegalCallerException, InstanceNotFoundException {
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
        return !isAdopted;
    }

    // if bird can talk but cant fly then it can be deemed suitable for a family.
    @Override
    public boolean isSuitableForFamily() {
        return canTalk && !canFly;
    }
}
