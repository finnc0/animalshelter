package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.utils.ShelterUtil;

import java.time.LocalDate;

public class Cat extends Animal {

    private boolean likesCatNip;

    public Cat(String name,
               LocalDate vaccDate,
               String ownerName,
               String email,
               boolean likesCatNip
    ) {
        super(name,vaccDate,ownerName,email);

        this.likesCatNip = likesCatNip;
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

    @Override
    public boolean suitableForFamily() {
        if (this.likesCatNip) {
            return this.getVaccDate() != null;
        }
        return false;
    }

    // getters
    public boolean likesCatNip() { return this.likesCatNip; }

    // setters
    public void setLikesCatNip(boolean newLikeState) { this.likesCatNip = newLikeState; }
}
