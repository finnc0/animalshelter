package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.Owner;
import sh.finnean.AnimalShelter.instance.contracts.Adoptable;
import sh.finnean.AnimalShelter.instance.contracts.Friendly;
import sh.finnean.AnimalShelter.utils.ShelterUtil;

import java.time.LocalDate;

public class Dog extends Animal implements Adoptable, Friendly {

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

        this.isAdopted = false;
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
    public void adopt(Owner owner) {
        // verify owner is not null;
        if (owner != null) {
            this.setOwner(owner);
            this.setAdoptable(true);
        } else {
            System.out.println("Failed to adopt. Owner does not exist.");
        }
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


}
