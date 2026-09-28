package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.Owner;
import sh.finnean.AnimalShelter.instance.contracts.Adoptable;
import sh.finnean.AnimalShelter.instance.contracts.Behavior;
import sh.finnean.AnimalShelter.utils.ShelterUtil;

import java.time.LocalDate;

public class Cat extends Animal implements Adoptable, Behavior {

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

        this.isAdopted = false;
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
    public void adopt(Owner owner) {

    }

    @Override
    public void setAdoptable(boolean adoptionValue) {

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
}
